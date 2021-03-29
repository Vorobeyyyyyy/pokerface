package by.bsuir.pokerface.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {

    @GetMapping("/main")
    public String getMainPage() {
        return "main.html";
    }

    @GetMapping("/test")
    public String test(){
        return "test/test.html";
    }
}
