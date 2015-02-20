package users.controller;

import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import users.security.CurrentUser;

import java.util.Map;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@AuthenticationPrincipal CurrentUser currentUser, @RequestParam Map<String, String> params, Model model) {
        if (currentUser != null) {
            return "redirect:/";
        }

        if (params.containsKey("error")) {
            model.addAttribute("error", true);
        }
        else if (params.containsKey("logout")) {
            model.addAttribute("logout", true);
        }
        else {
            model.addAttribute("login", true);
        }

        return "login";
    }
}
