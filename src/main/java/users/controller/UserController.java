package users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
import users.validator.UserValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
    final Sort USERS_SORT = new Sort("email");

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserValidator userValidator;

    @InitBinder("user")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        return new ModelAndView("users", "users", userRepository.findAll(USERS_SORT));
    }

    @PreAuthorize("hasAuthority('editor')")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(ModelMap model) {
        if (model.containsAttribute("user")) {
            return new ModelAndView("user", model);
        }

        return new ModelAndView("user", "user", new User());
    }

    @PreAuthorize("hasAuthority('editor')")
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

    @PreAuthorize("hasAuthority('editor')")
    @RequestMapping(value = "/address", params = {"addAddress"}, method = RequestMethod.POST)
    public String addAddress(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        user.getAddresses().add(new Address());
        return processRedirect(user, redirectAttributes);
    }

    @PreAuthorize("hasAuthority('editor')")
    @RequestMapping(value = "/address", params = {"removeAddress"}, method = RequestMethod.POST)
    public String removeAddress(@RequestParam("removeAddress") int index, @ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        user.getAddresses().remove(index);
        return processRedirect(user, redirectAttributes);
    }

    @PreAuthorize("hasAuthority('editor')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            return processRedirect(user, redirectAttributes);
        }

        try {
            userService.createOrUpdate(user);
        }
        catch (Exception e) {
            bindingResult.reject("unexpected.error", "Unexpected error occurs. Form data cannot be saved.");
        }

        return "redirect:/";
    }

    protected String processRedirect(User user, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("user", user);

        if (userService.isNew(user)) {
            return "redirect:/user/create";
        }

        redirectAttributes.addAttribute("id", user.getId());
        return "redirect:/user/update/{id}";
    }

}
