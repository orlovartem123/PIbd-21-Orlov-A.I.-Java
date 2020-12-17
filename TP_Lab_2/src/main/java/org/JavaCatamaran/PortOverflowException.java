package org.JavaCatamaran;

public class PortOverflowException extends Exception {

    public PortOverflowException() {
        super("There are not empty places in the port");
    }

}
