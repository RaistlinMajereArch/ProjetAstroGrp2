package SoprAjc.ProjetAstroSpringBoot.restController;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import SoprAjc.ProjetAstroSpringBoot.exceptions.CompteException;
import SoprAjc.ProjetAstroSpringBoot.model.Compte;
import SoprAjc.ProjetAstroSpringBoot.repositories.CompteRepository;

@RestController
@RequestMapping("/api/compte")
@CrossOrigin(origins = "*")
public class CompteRestController {
	
	@Autowired
	private CompteRepository compteRepo;

	@GetMapping("")
	//@JsonView(JsonViews.AchatWithClientAndProduit.class)
	public List<Compte> getAll() {
		return compteRepo.findAll();
	}

	@GetMapping("/{id}")
	//@JsonView(JsonViews.AchatWithClientAndProduit.class)
	public Compte get(@PathVariable Integer id) {
		return compteRepo.findById(id).get();
	}

	@PostMapping("")
	//@JsonView(JsonViews.Common.class)
	@ResponseStatus(HttpStatus.CREATED)
	public Compte create(@Valid @RequestBody Compte compte, BindingResult br) {
		if (br.hasErrors()) {
			throw new CompteException();
		}
		return compteRepo.save(compte);
	}


	@PutMapping("/{id}")
	//@JsonView(JsonViews.Common.class)
	public Compte put(@Valid @RequestBody Compte compte, BindingResult br, @PathVariable Integer id) {
		return compteRepo.save(compte);
	}

	@PatchMapping("/{id}")
	//@JsonView(JsonViews.Common.class)
	public Compte patch(@RequestBody Map<String, Object> fields, @PathVariable Integer id) {
		Optional<Compte> opt = compteRepo.findById(id);
		if (opt.isPresent()) {
			Compte compte = opt.get();
			fields.forEach((key, value) -> {
				Field field = ReflectionUtils.findField(Compte.class, key);
				ReflectionUtils.makeAccessible(field);
				ReflectionUtils.setField(field, compte, value);
			});
			return compteRepo.save(compte);
		}
		throw new CompteException();
	}
}
