package SoprAjc.ProjetAstroSpringBoot.restController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import SoprAjc.ProjetAstroSpringBoot.model.CorpsCeleste;
import SoprAjc.ProjetAstroSpringBoot.model.JsonViews;
import SoprAjc.ProjetAstroSpringBoot.model.Position;
import SoprAjc.ProjetAstroSpringBoot.repositories.PositionsRepository;
import SoprAjc.ProjetAstroSpringBoot.repositories.SystemeInitRepository;

@RestController
@RequestMapping("/api/position")
@CrossOrigin(origins = "*")
public class PositionRestController {

	@Autowired
	PositionsRepository posRepo;
	
	@Autowired
	SystemeInitRepository sysIRepo;
	
	@GetMapping("")
	@JsonView(JsonViews.Common.class)
	public List<Position> getAll() {
		return posRepo.findAll();
	}
	
	@GetMapping("/order")
	@JsonView(JsonViews.Common.class)
	public List<List<Position>> getAllOrderByIdCorps() {
		List<CorpsCeleste> systeme = sysIRepo.findAll();
		List<List<Position>> positions=new ArrayList<List<Position>>();
		for (CorpsCeleste corpsCeleste : systeme) {
			positions.add(posRepo.findByIdCorpsCeleste(corpsCeleste.getId()));
		}
		return positions;
	}
}
