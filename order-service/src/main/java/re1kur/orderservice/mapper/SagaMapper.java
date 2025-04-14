package re1kur.orderservice.mapper;

public interface SagaMapper {
    String getGoodsInfoCommand(String message);

    String blockUserBalanceCommand(String message, String debit);

    String rejectOrderCommand(String orderId);

    String goodsInfoReceiveFailedEvent(String message);

    String userBalanceBlockFailedEvent(String message);

    String userBalanceBlockedEvent(String message);
}
