package com.unisoft.algotrader.provider.ib.api.event;


import com.unisoft.algotrader.provider.ib.api.IBConstants;

/**
 * Created by alex on 8/26/15.
 */
public class OrderStatusUpdateEvent extends IBEvent<OrderStatusUpdateEvent>  {

    public final IBConstants.OrderStatus orderStatus;
    public final int filledQuantity;
    public final int remainingQuantity;
    public final double averageFilledPrice;
    public final int permanentId;
    public final String parentOrderId;
    public final double lastFilledPrice;
    public final int clientId;
    public final String heldCause;

    public OrderStatusUpdateEvent(final String orderId, final IBConstants.OrderStatus orderStatus, final int filledQuantity,
                                  final int remainingQuantity, final double averageFilledPrice, final int permanentId,
                                  final String parentOrderId, final double lastFilledPrice, final int clientId, final String heldCause){
        super(orderId);
        this.orderStatus = orderStatus;
        this.filledQuantity = filledQuantity;
        this.remainingQuantity = remainingQuantity;
        this.averageFilledPrice = averageFilledPrice;
        this.permanentId = permanentId;
        this.parentOrderId = parentOrderId;
        this.lastFilledPrice = lastFilledPrice;
        this.clientId = clientId;
        this.heldCause = heldCause;
    }
}