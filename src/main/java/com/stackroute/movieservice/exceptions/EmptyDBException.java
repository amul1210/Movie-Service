package com.stackroute.movieservice.exceptions;

public class EmptyDBException extends Exception {
    public EmptyDBException(String errMessage){
        super(errMessage);
    }
}
