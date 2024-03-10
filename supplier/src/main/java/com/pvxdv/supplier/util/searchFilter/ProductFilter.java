package com.pvxdv.supplier.util.searchFilter;

import java.math.BigDecimal;


public record ProductFilter(String name, String description, BigDecimal price, Long categoryId,
                            Integer offset, Integer limit) {
}
