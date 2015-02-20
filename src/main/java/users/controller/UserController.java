package users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import users.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {
    final Sort USERS_SORT = new Sort("email");

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        return new ModelAndView("users", "users", userRepository.findAll(USERS_SORT));
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        return "user";
    }

    @RequestMapping(value = "/update{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") long id) {
        return "user";
    }

    @RequestMapping(value = "/list1", method = RequestMethod.GET)
    public RedirectView redirectList(RedirectAttributes redirectAttributes) {
        final RedirectView redirectView = new RedirectView("/user/list");
        redirectView.setPropagateQueryParams(true);
        return redirectView;
    }

}
