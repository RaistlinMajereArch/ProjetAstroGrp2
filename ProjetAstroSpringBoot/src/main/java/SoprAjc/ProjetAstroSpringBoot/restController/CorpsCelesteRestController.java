package SoprAjc.ProjetAstroSpringBoot.restController;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import SoprAjc.ProjetAstroSpringBoot.exceptions.CorpsCelesteException;
import SoprAjc.ProjetAstroSpringBoot.model.CorpsCeleste;
import SoprAjc.ProjetAstroSpringBoot.model.Etoile;
import SoprAjc.ProjetAstroSpringBoot.model.JsonViews;
import SoprAjc.ProjetAstroSpringBoot.model.Planete;
import SoprAjc.ProjetAstroSpringBoot.model.Satellite;
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
	@JsonView(JsonViews.Common.class)
	public CorpsCeleste getCorpsById(@PathVariable Integer id) {
		Optional<CorpsCeleste> opt = sysIRepo.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new CorpsCelesteException();
	}
	
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	@JsonView(JsonViews.Common.class)
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
		
		if(corpsCeleste.getType()=="Planete") {
			return sysIRepo.save((Planete) corpsCeleste);
		}
		if(corpsCeleste.getType()=="Etoile") {
			return sysIRepo.save((Etoile) corpsCeleste);
		}
		if(corpsCeleste.getType()=="Satellite") {
			return sysIRepo.save((Satellite) corpsCeleste);
		}
		return sysIRepo.save(corpsCeleste);//a faire pour planete, etoile et satellite !!!!!!!!!!!!!!!!!!!!!!!!!a
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@JsonView(JsonViews.Common.class)
	public void delete(@PathVariable Integer id) {
		Optional<CorpsCeleste> opt = sysIRepo.findById(id);
		if (opt.isPresent()) {
			sysIRepo.deleteById(id);
		}
		else {
			throw new CorpsCelesteException();
		}
	}
	
	@PatchMapping("/{id}")
	@JsonView(JsonViews.Common.class)
	public CorpsCeleste modify(@RequestBody Map<String, Object> fields, @PathVariable Integer id){
		Optional<CorpsCeleste> opt = sysIRepo.findById(id);
		if (opt.isPresent()) {
			CorpsCeleste corpsCelesteEnBase = opt.get();
			fields.forEach((key,value)->{
				Field field = ReflectionUtils.findField(CorpsCeleste.class, key);
				ReflectionUtils.makeAccessible(field);
				ReflectionUtils.setField(field, corpsCelesteEnBase, value);
			});
			return sysIRepo.save(corpsCelesteEnBase);
		}
		throw new CorpsCelesteException();
	}
	
	@PutMapping("/{id}")
	@JsonView(JsonViews.Common.class)
	public CorpsCeleste replace(@Valid @RequestBody CorpsCeleste corpsCeleste, BindingResult br, @PathVariable Integer id) {
		if (br.hasErrors()) {
			corpsCeleste.setId(id);
			return sysIRepo.save(corpsCeleste);
		}
		throw new CorpsCelesteException();
	}
	
}
	
	
	