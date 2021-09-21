package SoprAjc.ProjetAstroSpringBoot.restController;

import java.awt.AWTException;
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
		
		if(corpsCeleste.getType().equals("Planete")) {
			Planete plan=new Planete(corpsCeleste.getMasseInit(), corpsCeleste.getDiametreInit(), corpsCeleste.getxInit(), corpsCeleste.getyInit(), corpsCeleste.getVxInit(), corpsCeleste.getVyInit(),
					corpsCeleste.getNomInit(), corpsCeleste.getParent());
			return sysIRepo.save(plan);
		}else if(corpsCeleste.getType().equals("Etoile")) {
			Etoile etoile=new Etoile(corpsCeleste.getMasseInit(), corpsCeleste.getDiametreInit(), corpsCeleste.getNomInit());
			return sysIRepo.save(etoile);
		}else if(corpsCeleste.getType().equals("Satellite")) {
			Satellite sat=new Satellite(corpsCeleste.getMasseInit(), corpsCeleste.getDiametreInit(), corpsCeleste.getxInit(), corpsCeleste.getyInit(), corpsCeleste.getVxInit(), corpsCeleste.getVyInit(),
					corpsCeleste.getNomInit(), corpsCeleste.getParent());
			return sysIRepo.save(sat);
		}else {
			
			return sysIRepo.save(corpsCeleste);//a faire pour planete, etoile et satellite !!!!!!!!!!!!!!!!!!!!!!!!!a
		}
	}
	
//	@PostMapping("/Planete")
//	@ResponseStatus(HttpStatus.CREATED)
//	@JsonView(JsonViews.Common.class)
//	public CorpsCeleste createPlanete(@Valid @RequestBody Planete plan,BindingResult br) {
//		
//		if (br.hasErrors()) {
//			throw new CorpsCelesteException();
//		}
//		if (plan.getParent() !=null) {
//			Optional<CorpsCeleste> opt = sysIRepo.findById(plan.getParent().getId());
//			if (!opt.isPresent()) {
//				throw new CorpsCelesteException();
//			}
//			plan.setParent(opt.get());		
//		}
//		return sysIRepo.save(plan);
//	}
//	
//	@PostMapping("/Satellite")
//	@ResponseStatus(HttpStatus.CREATED)
//	@JsonView(JsonViews.Common.class)
//	public CorpsCeleste createSatellite(@Valid @RequestBody Satellite sat,BindingResult br) {
//		
//		if (br.hasErrors()) {
//			throw new CorpsCelesteException();
//		}
//		if (sat.getParent() !=null) {
//			Optional<CorpsCeleste> opt = sysIRepo.findById(sat.getParent().getId());
//			if (!opt.isPresent()) {
//				throw new CorpsCelesteException();
//			}
//			sat.setParent(opt.get());		
//		}
//		return sysIRepo.save(sat);
//	}
//	
//	@PostMapping("/Etoile")
//	@ResponseStatus(HttpStatus.CREATED)
//	@JsonView(JsonViews.Common.class)
//	public CorpsCeleste createEtoile(@Valid @RequestBody Etoile etoile,BindingResult br) {
//		
//		if (br.hasErrors()) {
//			throw new CorpsCelesteException();
//		}
//		if (etoile.getParent() !=null) {
//			Optional<CorpsCeleste> opt = sysIRepo.findById(etoile.getParent().getId());
//			if (!opt.isPresent()) {
//				throw new CorpsCelesteException();
//			}
//			etoile.setParent(opt.get());		
//		}
//		return sysIRepo.save(etoile);
//	}
	
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
			throw new CorpsCelesteException();
		}
		if (corpsCeleste.getParent() !=null) {
			Optional<CorpsCeleste> opt = sysIRepo.findById(corpsCeleste.getParent().getId());
			if (!opt.isPresent()) {
				throw new CorpsCelesteException();
			}
			corpsCeleste.setParent(opt.get());
		}
		//sysIRepo.deleteById(id);
		if(corpsCeleste.getType().equals("Planete")) {
			Planete plan=new Planete(corpsCeleste.getMasseInit(), corpsCeleste.getDiametreInit(), corpsCeleste.getxInit(), corpsCeleste.getyInit(), corpsCeleste.getVxInit(), corpsCeleste.getVyInit(),
					corpsCeleste.getNomInit(), corpsCeleste.getParent());
			plan.setId(id);
			System.out.println(plan);
			return sysIRepo.save(plan);
		}else if(corpsCeleste.getType().equals("Etoile")) {
			Etoile etoile=new Etoile(corpsCeleste.getMasseInit(), corpsCeleste.getDiametreInit(), corpsCeleste.getNomInit());
			etoile.setId(id);
			return sysIRepo.save(etoile);
		}else if(corpsCeleste.getType().equals("Satellite")) {
			Satellite sat=new Satellite(corpsCeleste.getMasseInit(), corpsCeleste.getDiametreInit(), corpsCeleste.getxInit(), corpsCeleste.getyInit(), corpsCeleste.getVxInit(), corpsCeleste.getVyInit(),
					corpsCeleste.getNomInit(), corpsCeleste.getParent());
			sat.setId(id);
			return sysIRepo.save(sat);
		}else {
			
			return sysIRepo.save(corpsCeleste);//a faire pour planete, etoile et satellite !!!!!!!!!!!!!!!!!!!!!!!!!a
		}
	}
	
//	@PutMapping("/{id}/Planete")
//	@JsonView(JsonViews.Common.class)
//	public CorpsCeleste replacePlanete(@Valid @RequestBody Planete planete, BindingResult br, @PathVariable Integer id) {
//		if (br.hasErrors()) {
//			throw new CorpsCelesteException();
//		}
//		Optional<CorpsCeleste> opt = sysIRepo.findById(id);
//		System.out.println(opt);
//		if (opt.isPresent()) {
//				planete.setId(id);
//				return sysIRepo.save(planete);
//		}
//		throw new CorpsCelesteException();
//	}
//	
//	@PutMapping("/{id}/Satellite")
//	@JsonView(JsonViews.Common.class)
//	public CorpsCeleste replaceSatellite(@Valid @RequestBody Satellite sat, BindingResult br, @PathVariable Integer id) {
//		if (br.hasErrors()) {
//			throw new CorpsCelesteException();
//		}
//		Optional<CorpsCeleste> opt = sysIRepo.findById(id);
//		System.out.println(opt);
//		if (opt.isPresent()) {
//				sat.setId(id);
//				return sysIRepo.save(sat);
//		}
//		throw new CorpsCelesteException();
//	}
//	
//	@PutMapping("/{id}/Etoile")
//	@JsonView(JsonViews.Common.class)
//	public CorpsCeleste replaceEtoile(@Valid @RequestBody Etoile etoile, BindingResult br, @PathVariable Integer id) {
//		if (br.hasErrors()) {
//			throw new CorpsCelesteException();
//		}
//		Optional<CorpsCeleste> opt = sysIRepo.findById(id);
//		System.out.println(opt);
//		if (opt.isPresent()) {
//				etoile.setId(id);
//				return sysIRepo.save(etoile);
//		}
//		throw new CorpsCelesteException();
//	}
	
	
//	@PutMapping("/{id}")
//	@JsonView(JsonViews.Common.class)
//	public CorpsCeleste replace(@Valid @RequestBody CorpsCeleste corpsCeleste, BindingResult br, @PathVariable Integer id) {
//		System.out.println(corpsCeleste);
//		if (br.hasErrors()) {
//			throw new CorpsCelesteException();
//		}
//		Optional<CorpsCeleste> opt = sysIRepo.findById(id);
//		System.out.println(opt);
//		
//		if (opt.isPresent()) {
//			sysIRepo.deleteById(id);
//			if(corpsCeleste.getType().equals("Planete")) {
//				Planete plan=new Planete(corpsCeleste.getMasseInit(), corpsCeleste.getDiametreInit(), corpsCeleste.getxInit(), corpsCeleste.getyInit(), corpsCeleste.getVxInit(), corpsCeleste.getVyInit(),
//						corpsCeleste.getNomInit(), corpsCeleste.getParent());
//				plan.setId(id);
//				return sysIRepo.save(plan);
//			}else if(corpsCeleste.getType().equals("Etoile")) {
//				Etoile etoile=new Etoile(corpsCeleste.getMasseInit(), corpsCeleste.getDiametreInit(), corpsCeleste.getNomInit());
//				etoile.setId(id);
//				return sysIRepo.save(etoile);
//			}else if(corpsCeleste.getType().equals("Satellite")) {
//				Satellite sat=new Satellite(corpsCeleste.getMasseInit(), corpsCeleste.getDiametreInit(), corpsCeleste.getxInit(), corpsCeleste.getyInit(), corpsCeleste.getVxInit(), corpsCeleste.getVyInit(),
//						corpsCeleste.getNomInit(), corpsCeleste.getParent());
//				sat.setId(id);
//				return sysIRepo.save(sat);
//			}else {
//				
//				return sysIRepo.save(corpsCeleste);//a faire pour planete, etoile et satellite !!!!!!!!!!!!!!!!!!!!!!!!!a
//			}
//				
//		}
//		throw new CorpsCelesteException();
		
		
//		System.out.println(corpsCeleste);
//		if (!br.hasErrors()) {
//			System.out.println("dans if");
//			corpsCeleste.setId(id);
//			return sysIRepo.save(corpsCeleste);
//		}
//		throw new CorpsCelesteException();
//	}
	
}
