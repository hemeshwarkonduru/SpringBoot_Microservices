package com.everythingin.order.exception;

public class MyCustomException extends Exception{

    public MyCustomException(){
        super();
    }

    public MyCustomException(String message){
        super(message);
    }

    public MyCustomException(Throwable clause){
        super(clause);
    }

    public MyCustomException(String message,  Throwable clause){
        super(message, clause);
    }

}
