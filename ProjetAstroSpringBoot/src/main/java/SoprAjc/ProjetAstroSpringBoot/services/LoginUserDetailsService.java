package SoprAjc.ProjetAstroSpringBoot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import SoprAjc.ProjetAstroSpringBoot.model.Compte;
import SoprAjc.ProjetAstroSpringBoot.model.LoginUserDetails;
import SoprAjc.ProjetAstroSpringBoot.repositories.CompteRepository;

@Service
public class LoginUserDetailsService implements UserDetailsService {

	@Autowired
	private CompteRepository compteRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Compte> opt = compteRepo.findByLogin(username);
		if (opt.isPresent()) {
			return new LoginUserDetails(opt.get());
		}
		throw new UsernameNotFoundException("");
	}

}
