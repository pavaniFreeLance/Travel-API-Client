package com.afkl.cases.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
 
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResultNotFoundException extends RuntimeException 
{
    public ResultNotFoundException(String exception) {
        super(exception);
    }
}