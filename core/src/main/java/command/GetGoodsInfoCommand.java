package command;

public record GetGoodsInfoCommand(
        String orderId,
        String userId,
        Integer goodsId
) {
}
