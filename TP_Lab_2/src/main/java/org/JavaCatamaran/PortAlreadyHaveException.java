package org.JavaCatamaran;

public class PortAlreadyHaveException extends Exception {
    public PortAlreadyHaveException(){
        super("The port already have this transport");
    }
}
