package com.tutorialjwtsecurity.jwt.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDto {

    @NotBlank(message = "el nombre de producto es obligatorio")
    private String nombre;
    @Min(value = 0, message = "el precio es obligatorio")
    private Float precio;


}
