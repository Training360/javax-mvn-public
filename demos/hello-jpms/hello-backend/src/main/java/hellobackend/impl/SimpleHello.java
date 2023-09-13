package hellobackend.impl;

import hellobackend.Hello;

public class SimpleHello implements Hello {

    @Override
    public String sayHello(String name) {
        return "Hello %s!".formatted(name);
    }
}
