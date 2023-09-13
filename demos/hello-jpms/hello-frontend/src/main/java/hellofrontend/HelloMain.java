package hellofrontend;

import hellobackend.Hello;

import java.util.ServiceLoader;

public class HelloMain {

    public static void main(String[] args) {
        var hello = ServiceLoader.load(Hello.class)
                .findFirst().orElseThrow();

        System.out.println(hello.sayHello("Trainer"));
    }
}
