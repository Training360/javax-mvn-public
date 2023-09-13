package training.hellospringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/")
public class HelloController {

    @GetMapping
    @ResponseBody
    public String hello() {
        return "hello " + LocalDateTime.now();
    }
}
