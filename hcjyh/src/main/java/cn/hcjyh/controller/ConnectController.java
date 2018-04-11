package cn.hcjyh.controller;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@EnableAutoConfiguration
public class ConnectController {

    @RequestMapping("/connect")
    @ResponseBody
    String connect() {
        return "Hello World!";
    }

//    public static void main(String[] args) throws Exception {
//        SpringApplication.run(ConnectController.class, args);
//    }
}
