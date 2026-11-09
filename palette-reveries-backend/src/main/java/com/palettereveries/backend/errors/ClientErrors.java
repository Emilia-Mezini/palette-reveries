package com.palettereveries.backend.errors;


import com.palettereveries.backend.entities.Review;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class ClientErrors {
    private static final Logger logger = LoggerFactory.getLogger(ClientErrors.class);

    private static ResponseStatusException log(ResponseStatusException e) {
        logger.error("{}\n{}", ExceptionUtils.getMessage(e), ExceptionUtils.getStackTrace(e));
        return e;
    }

    public static ResponseStatusException artworkNotFound(Long id){
        return log(new ResponseStatusException(HttpStatus.NOT_FOUND,"artwork with id: "+ id));
    }

    public static ResponseStatusException artworkTitleTaken(String title){
        return log(new ResponseStatusException(HttpStatus.CONFLICT,"artwork with title:"+title));
    }

    public static ResponseStatusException usernameTaken(String username){
        return log(new ResponseStatusException(HttpStatus.CONFLICT,"username:"+ username+" already taken"));
    }

    public static ResponseStatusException emailTaken(String email){
        return log(new ResponseStatusException(HttpStatus.CONFLICT,"email: "+ email+" already taken."));

    }

    public static  ResponseStatusException userNotFound(Long id){
        return log(new ResponseStatusException(HttpStatus.NOT_FOUND,"user with id: "+id));
    }

    public static  ResponseStatusException inquiryNotFound(Long id){
        return log(new ResponseStatusException(HttpStatus.NOT_FOUND,"inquiry with id: "+id));
    }

    public static ResponseStatusException reviewNotFound(Long id){
        return log(new ResponseStatusException(HttpStatus.NOT_FOUND,"review with id: "+id));
    }

    public static ResponseStatusException invalidRating(int rating){
        return log(new ResponseStatusException(HttpStatus.BAD_REQUEST,"Rating: "+rating+" is invalid."));

    }

    public static ResponseStatusException orderNotFound(Long id){
        return log(new ResponseStatusException(HttpStatus.NOT_FOUND,"order with id: "+id));
    }

    public static ResponseStatusException emptyOrder(){
        return log(new ResponseStatusException(HttpStatus.BAD_REQUEST,"Order must contain at least one item."));

    }

    public static ResponseStatusException invalidQuantity(int quantity){
        return log(new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid quantity: "+quantity));
    }




}
