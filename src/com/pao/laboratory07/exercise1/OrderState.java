package com.pao.laboratory07.exercise1;

public enum OrderState {
    PLACED("the order has been placed"),
    PROCESSED ("the order is processed"),
    SHIPPED ("the order has been shipped"),
    DELIVERED ("the order has been delivered - final state"),
    CANCELED("the order has been canceled - final state")
    ;

    OrderState(String s) {}
}
