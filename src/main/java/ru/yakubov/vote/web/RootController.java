package ru.yakubov.vote.web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yakubov.vote.web.user.AdminVoteRestController;

@Controller
@RequestMapping(value = RootController.REST_URL)
public class RootController {
    public static final String REST_URL = "/vote";

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/vote")
    public String vote() {
        return "index";
    }
}
