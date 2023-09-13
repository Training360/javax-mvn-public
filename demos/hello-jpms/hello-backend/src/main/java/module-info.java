import hellobackend.impl.SimpleHello;
import hellobackend.Hello;

module hello.backend {

    exports hellobackend;

    provides Hello with SimpleHello;
}