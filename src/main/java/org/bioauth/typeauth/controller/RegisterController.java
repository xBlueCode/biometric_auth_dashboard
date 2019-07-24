package org.bioauth.typeauth.controller;

import org.bioauth.typeauth.config.UserSecurityUtil;
import org.bioauth.typeauth.domain.User;
import org.bioauth.typeauth.service.UserServiceDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private UserServiceDb userServiceMySql;

	@Autowired
	private UserSecurityUtil userSecurityUtil;

	@GetMapping
	public String showRegisterForm(Model model)
	{
		User user;
		if ((user = userSecurityUtil.getAuthenticatedUser()) != null)
			return "redirect:/dashboard";
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping
	public String register(@ModelAttribute("user") @Valid User user, BindingResult bindingResult)
	{
		if (userServiceMySql.findUserByUsername(user.getUsername()).isPresent())
			bindingResult.rejectValue("username", null, "There is already an account with that username");
		if (bindingResult.hasErrors())
			return "register";
		userServiceMySql.save(user);
		return "login";
	}
}
