package users.controller;

import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import users.security.CurrentUser;

@ControllerAdvice
public class CurrentUserAdvice {

    @ModelAttribute("user")
    public CurrentUser getCurrentUser(@AuthenticationPrincipal CurrentUser currentUser) {
        return currentUser;
    }
}
