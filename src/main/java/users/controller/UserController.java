package users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import users.model.User;
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
    public ModelAndView create() {
        return new ModelAndView("user", "user", new User());
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView update(@PathVariable("id") long id) {
        final User user = userRepository.findOne(id);
        if (user == null) {
            throw new ExceptionAdvice.UserNotFoundException();
        }

        return new ModelAndView("user", "user", user);
    }

    @RequestMapping(value = "/address", params = {"removeAddress"})
    public String removeAddress(@RequestParam("removeAddress") int index, @ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        user.getAddresses().remove(index);

        if (user.getId() == 0) {
            return "redirect:/create";
        }

        redirectAttributes.addAttribute("id", user.getId());
        return "redirect:/update/{id}";
    }

    @RequestMapping(value = "/list1", method = RequestMethod.GET)
    public RedirectView redirectList(RedirectAttributes redirectAttributes) {
        final RedirectView redirectView = new RedirectView("/user/list");
        redirectView.setPropagateQueryParams(true);
        return redirectView;
    }

}
