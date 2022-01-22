package eu.borostomi.mongodbdemo.controller;

import eu.borostomi.mongodbdemo.service.MainService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping(path = "/")
    public String home() {
        return mainService.getMain();

    }
}
