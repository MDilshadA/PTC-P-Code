package com.zeeshan.account.exceptions;

public class CustomerAlreadyExistsException extends RuntimeException{

   public CustomerAlreadyExistsException(String msg){
        super(msg);
    }
}
