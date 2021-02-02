package by.bsuir.pokerface.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class MainController {

    @PostMapping("message")
    public Map<String, String> doMessage() {
        Map map = new HashMap<>();
        map.put("12", "2");
        return map;
    }
}
