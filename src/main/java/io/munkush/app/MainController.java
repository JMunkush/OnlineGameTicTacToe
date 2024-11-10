package io.munkush.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static io.munkush.app.MessageController.players;
import static io.munkush.app.MessageController.roomList;

@Controller
public class MainController {


    @GetMapping
    public String index(){
        return "index";
    }

    @GetMapping("/clear")
    public String clear(){
        roomList.clear();
        players.clear();
        return "index";
    }
}
