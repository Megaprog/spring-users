package users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import users.model.Address;
import users.model.User;
import users.repository.UserRepository;
import users.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    final Sort USERS_SORT = new Sort("email");

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        return new ModelAndView("users", "users", userRepository.findAll(USERS_SORT));
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute("user") User user) {
        return "user";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView update(@PathVariable("id") long id, ModelMap model) {
        if (model.containsAttribute("user")) {
            return new ModelAndView("user", model);
        }

        final User user = userRepository.findOne(id);
        if (user == null) {
            throw new ExceptionAdvice.UserNotFoundException();
        }

        return new ModelAndView("user", "user", user);
    }

    @RequestMapping(value = "/address", params = {"addAddress"}, method = RequestMethod.POST)
    public String addAddress(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        user.getAddresses().add(new Address());
        return processAddress(user, redirectAttributes);
    }

    @RequestMapping(value = "/address", params = {"removeAddress"}, method = RequestMethod.POST)
    public String removeAddress(@RequestParam("removeAddress") int index, @ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        user.getAddresses().remove(index);
        return processAddress(user, redirectAttributes);
    }

    protected String processAddress(User user, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("user", user);

        if (userService.isNew(user)) {
            return "redirect:create";
        }

        redirectAttributes.addAttribute("id", user.getId());
        return "redirect:update/{id}";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("user") User user, BindingResult bindingResult) {
        userService.createOrUpdate(user);
        return "redirect:/";
    }

}
