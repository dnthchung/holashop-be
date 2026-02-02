package com.chungdt03.holashopbe.mapper;

import com.chungdt03.holashopbe.dtos.ProductDTO;
import com.chungdt03.holashopbe.models.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductDTO productDTO);
}
