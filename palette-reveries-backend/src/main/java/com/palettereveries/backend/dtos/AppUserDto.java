package com.palettereveries.backend.dtos;

import com.palettereveries.backend.entities.Role;

public record AppUserDto(Long id, String username,Role role,String email) {
}
