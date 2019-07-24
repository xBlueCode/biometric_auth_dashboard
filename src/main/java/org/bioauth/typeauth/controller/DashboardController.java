package org.bioauth.typeauth.controller;

import org.bioauth.typeauth.config.UserSecurityUtil;
import org.bioauth.typeauth.domain.Client;
import org.bioauth.typeauth.domain.ResourceId;
import org.bioauth.typeauth.domain.User;
import org.bioauth.typeauth.repository.AuthGrantTypeRepository;
import org.bioauth.typeauth.repository.GrantedAuthorityClientRepository;
import org.bioauth.typeauth.repository.ResourceIdRepository;
import org.bioauth.typeauth.repository.ScopeRepository;
import org.bioauth.typeauth.service.ClientServiceDb;
import org.bioauth.typeauth.service.UserServiceDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private UserSecurityUtil userSecurityUtil;
	@Autowired
	private ClientServiceDb clientServiceDb;
	@Autowired
	private UserServiceDb userServiceDb;
	@Autowired
	private AuthGrantTypeRepository authGrantTypeRepository;
	@Autowired
	private GrantedAuthorityClientRepository grantedAuthorityClientRepository;
	@Autowired
	private ResourceIdRepository resourceIdRepository;
	@Autowired
	private ScopeRepository scopeRepository;

	@GetMapping
	public String dashboard()
	{
		User user;
		if ((user = userSecurityUtil.getAuthenticatedUser()) == null)
			return "redirect:/";
		return "dashboard";
	}

	@GetMapping("/create")
	public String createClientForm(Model model)
	{
		User user;
		if ((user = userSecurityUtil.getAuthenticatedUser()) == null)
			return "redirect:/login";
		model.addAttribute("client", new Client());
		return "create";
	}

	@PostMapping("/create")
	public String createClient(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult)
	{
		User user;
		if ((user = userSecurityUtil.getAuthenticatedUser()) == null)
			return "redirect:/login";
		if (clientServiceDb.loadClientByClientId(client.getClientId()) != null)
			bindingResult.rejectValue("clientId", null, "There is already an App with this client_id");
		if (bindingResult.hasErrors())
			return "redirect:/dashboard/create";
		client
				.getAuthGrantTypes()
				.add(authGrantTypeRepository.findAuthGrantTypeByType("client_credentials"));
		client
				.getGrantedAuthorities()
				.add(grantedAuthorityClientRepository.findGrantedAuthorityClientByAuthority("granted_auth_1"));
		client
				.getResIds().add(resourceIdRepository.findResourceIdByResourceId("res_1"));
		client
				.getScopes().add(scopeRepository.findScopeByScope("scope_1"));
		client = clientServiceDb.save(client);

		user.getClients().add(client);
		userServiceDb.update(user);
		return "redirect:/dashboard";
	}
}
