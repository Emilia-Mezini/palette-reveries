package com.palettereveries.backend.services;

import com.palettereveries.backend.dtos.*;
import com.palettereveries.backend.entities.AppUser;
import com.palettereveries.backend.entities.Artwork;
import com.palettereveries.backend.entities.Order;
import com.palettereveries.backend.entities.OrderItem;
import com.palettereveries.backend.errors.ClientErrors;
import com.palettereveries.backend.repositories.AppUserRepository;
import com.palettereveries.backend.repositories.ArtworkRepository;
import com.palettereveries.backend.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final AppUserRepository appUserRepository;
    private final ArtworkRepository artworkRepository;

    public OrderService(OrderRepository orderRepository,AppUserRepository appUserRepository,ArtworkRepository artworkRepository){
        this.orderRepository = orderRepository;
        this.appUserRepository = appUserRepository;
        this.artworkRepository = artworkRepository;
    }

    private OrderItemDto toDto(OrderItem item){
        return new OrderItemDto(item.getId(), item.getArtwork().getId(),item.getArtwork().getTitle(),item.getQuantity(),item.getUnitPrice());
    }

    private OrderDto toDto(Order order){
        AppUser buyer = order.getBuyer();
        AppUserDto buyerDto = new AppUserDto(buyer.getId(),buyer.getUsername(),buyer.getRole(), buyer.getEmail());
        List<OrderItemDto> orderItems = order.getItems().stream().map(this::toDto).toList();
        return new OrderDto(order.getId(),
                buyerDto,orderItems,order.getStatus(),
                order.getCreatedAt(),order.getShippingAddress(),order.getTotal());
    }

    public List<OrderDto> getAllOrders(){
        return orderRepository.findAll().stream().map(this::toDto).toList();
    }

    public OrderDto getOrder(Long id){
        return orderRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> ClientErrors.orderNotFound(id));
    }

    public List<OrderDto> getMyOrders(Long currentUserId){
        AppUser buyer = appUserRepository.findById(currentUserId).orElseThrow(()-> ClientErrors.userNotFound(currentUserId));
        return orderRepository.findByBuyer(buyer).stream().map(this::toDto).toList();
    }

    public OrderDto createOrder(CreateOrderDto dto, Long currentUserId){
        AppUser buyer = appUserRepository.findById(currentUserId)
                .orElseThrow(() -> ClientErrors.userNotFound(currentUserId));
        if (dto.items() == null || dto.items().isEmpty()){
            throw ClientErrors.emptyOrder();
        }
        Order order = new Order();
        order.setBuyer(buyer);
        order.setShippingAddress(dto.shippingAddress());

        for (CreateOrderItemDto line : dto.items()){
            if (line.quantity() < 1){
                throw ClientErrors.invalidQuantity(line.quantity());
            }
            Artwork artwork = artworkRepository.findById(line.artworkId())
                    .orElseThrow(() -> ClientErrors.artworkNotFound(line.artworkId()));

            OrderItem item = new OrderItem();
            item.setArtwork(artwork);
            item.setQuantity(line.quantity());
            item.setUnitPrice(artwork.getPrice());
            order.addItem(item);
        }
        Order saved = orderRepository.save(order);
        return toDto(saved);
    }

    public OrderDto updateOrderStatus(Long id, UpdateOrderStatusDto dto){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> ClientErrors.orderNotFound(id));
        order.setStatus(dto.status());
        return toDto(orderRepository.save(order));
    }

    public void deleteOrder(Long id){
        if (!orderRepository.existsById(id)){
            throw ClientErrors.orderNotFound(id);
        }
        orderRepository.deleteById(id);
    }

}
