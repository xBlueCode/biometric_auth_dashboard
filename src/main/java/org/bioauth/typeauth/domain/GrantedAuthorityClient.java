package org.bioauth.typeauth.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Component
@Entity
@Data
@NoArgsConstructor
public class GrantedAuthorityClient implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotEmpty
	private String authority;

	public GrantedAuthorityClient(@NotEmpty String authority) {
		this.authority = authority;
	}
}
