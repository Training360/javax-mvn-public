package hello;

public class HelloMain {

    public static void main(String[] args) {
        var message = new Hello().sayHello("Trainer");
        System.out.println(message);

        new QrCodeHello().writeHelloImage("Trainer");
    }
}
