package com.pao.laboratory07.exercise1;

import java.util.ArrayList;
import java.util.List;

public class Order {
    OrderState state;
    List<OrderState> history = new ArrayList<>();

    public Order(OrderState ord){
        this.state = ord;
    }
    public void nextState(){
        if(state.equals(OrderState.DELIVERED) || (state.equals(OrderState.CANCELED))){
            throw new OrderIsAlreadyFinalException();
        }
        history.add(state);
        if(state.equals(OrderState.PLACED)){
            state = OrderState.PROCESSED;
            System.out.println("Order state updated to: " + state);
        } else if(state.equals(OrderState.PROCESSED)){
            state= OrderState.SHIPPED;
            System.out.println("Order state updated to: " + state);
        } else if(state.equals(OrderState.SHIPPED)){
            state = OrderState.DELIVERED;
            System.out.println("Order state updated to: " + state);
        }
    }

    public void cancel(){
        if(state.equals(OrderState.DELIVERED) || state.equals(OrderState.CANCELED)){
            throw new CannotCancelFinalOrderException();
        }
        history.add(state);
        state = OrderState.CANCELED;
        System.out.println("Order has been canceled.");
    }

    public void undoState(){
        if(history.isEmpty()){
            throw new CannotRevertInitialOrderStateException();
        }
        else {
            state = history.removeLast();
            System.out.println("Order state reverted to: " + state);
        }
    }
}