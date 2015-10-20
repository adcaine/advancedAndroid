package com.caine.allan.networkarchitecture.exceptions;

/**
 * Created by allancaine on 2015-10-20.
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(RuntimeException cause){
        super(cause);
    }
}
