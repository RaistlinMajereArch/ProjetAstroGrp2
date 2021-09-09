package SoprAjc.ProjetAstroSpringBoot.restController;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import SoprAjc.ProjetAstroSpringBoot.exceptions.CorpsCelesteException;
import SoprAjc.ProjetAstroSpringBoot.model.CorpsCeleste;
import SoprAjc.ProjetAstroSpringBoot.model.JsonViews;
import SoprAjc.ProjetAstroSpringBoot.repositories.SystemeInitRepository;

@RestController
@RequestMapping("/api/CorpsCeleste")
@CrossOrigin(origins = "*")
public class CorpsCelesteRestController {
	
	@Autowired
	SystemeInitRepository sysIRepo;
	
	@GetMapping("")
	@JsonView(JsonViews.Common.class)
	public List<CorpsCeleste> getAllCorps() {
		return sysIRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public CorpsCeleste getCorpsById(@PathVariable Integer id) {
		Optional<CorpsCeleste> opt = sysIRepo.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new CorpsCelesteException();
	}
	
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public CorpsCeleste create(@Valid @RequestBody CorpsCeleste corpsCeleste,BindingResult br) {
		if (br.hasErrors()) {
			throw new CorpsCelesteException();
		}
		if (corpsCeleste.getParent() !=null) {
			Optional<CorpsCeleste> opt = sysIRepo.findById(corpsCeleste.getParent().getId());
			if (!opt.isPresent()) {
				throw new CorpsCelesteException();
			}
			corpsCeleste.setParent(opt.get());
		}
		return sysIRepo.save(corpsCeleste);
	}
}
