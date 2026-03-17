package com.pao.laboratory03.exceptions;

import java.util.List;

public class DuplicateEntryException extends RuntimeException{
    public DuplicateEntryException(String message){
        super(message);
    }
}
