package com.pao.laboratory03.bonus;

public class InvalidTransitionException extends RuntimeException {
    private Status fromStatus;
    private Status toStatus;

    public InvalidTransitionException(Status statusCurent, Status newStatus) {
        this.fromStatus = statusCurent;
        this.toStatus = newStatus;
    }

    @Override
    public String getMessage() {
        return "Nu se poate trece din " + fromStatus + " in " + toStatus;
    }
}