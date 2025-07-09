package ru.alfabeton.request.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.alfabeton.request.dto.RequestDto;
import ru.alfabeton.request.dto.RequestItemDto;
import ru.alfabeton.request.entity.Request;
import ru.alfabeton.request.entity.RequestItem;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "items", source = "items")
    RequestDto toDto(Request request);

    @Mapping(target = "items", source = "items")
    Request toEntity(RequestDto requestDto);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(target = "itemTotalPrice", expression = "java(calcTotalPrice(item))")
    RequestItemDto toDto(RequestItem item);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "priceRub", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "request", ignore = true)
    RequestItem toEntity(RequestItemDto dto);

    default BigDecimal calcTotalPrice(RequestItem item) {
        if (item.getPriceRub() == null || item.getVolumeM3() == null) {
            return null;
        }
        return item.getPriceRub().multiply(BigDecimal.valueOf(item.getVolumeM3()));
    }
}
