package com.pvxdv.supplier.dto;


import java.math.BigDecimal;


public record ProductDto (Long id, String name, String description, BigDecimal price, Long categoryId) {

}
