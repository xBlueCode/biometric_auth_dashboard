package org.bioauth.typeauth.controller;

import org.bioauth.typeauth.config.UserSecurityUtil;
import org.bioauth.typeauth.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	private UserSecurityUtil userSecurityUtil;

	@GetMapping()
	public String home(){
		User user;
		if ((user = userSecurityUtil.getAuthenticatedUser()) == null)
			return "home";
		return "redirect:/dashboard";
	}
}
