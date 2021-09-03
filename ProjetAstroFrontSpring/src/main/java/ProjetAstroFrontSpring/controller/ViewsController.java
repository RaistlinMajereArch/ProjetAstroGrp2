package ProjetAstroFrontSpring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;





@Controller
@RequestMapping("")
public class ViewsController {
	
	
//	@GetMapping("/produit")
//	public String produit(@RequestParam(name = "id", required = true) int id, Model model) {
//		// id=id*2;
//		model.addAttribute("id", id);
//		return "produit";
//	}

	@GetMapping("")
	public String connect(Model model) {
		System.out.println("entree");
		return "";
		//return "redirect:/produit
	}
	
	@GetMapping("/views/menu")
	public String menu(Model model) {
		return "views/menu";
		//return "redirect:/produit
	}
	
	@GetMapping("modification")
	public String modification(Model model) {
		return "views/Modification";
		//return "redirect:/produit
	} 
	
	@GetMapping("create")
	public String create(Model model) {
		return "views/create";
		//return "redirect:/produit
	}
	
	@GetMapping("wait")
	public String wait(Model model) {
		return "views/wait";
		//return "redirect:/produit
	}
	
	@GetMapping("result")
	public String result(Model model) {
		return "views/result";
		//return "redirect:/produit
	}
	
	
}
