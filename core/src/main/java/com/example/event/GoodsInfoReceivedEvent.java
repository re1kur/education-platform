package com.example.event;

import java.math.BigDecimal;

public record GoodsInfoReceivedEvent(
        String orderId,
        String userId,
        Integer goodsId,
        BigDecimal price
) {
}
