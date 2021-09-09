package SoprAjc.ProjetAstroSpringBoot.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginUserDetails implements UserDetails {

	private Compte compte;
	
	public LoginUserDetails(Compte compte) {
		this.compte = compte;
	}
	
	public Compte getCompte() {
		return compte;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {		
		return null;
	}

	@Override
	public String getPassword() {		
		return compte.getPassword();
	}

	@Override
	public String getUsername() {		
		return compte.getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {	
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {	
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
