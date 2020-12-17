package org.JavaCatamaran;

public class PortNotFoundException extends Exception {

    public PortNotFoundException(int i) {
        super("Not found transport by place " + i);
    }

}
