package br.com.TresDevsPI4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContatoController {
	
	@GetMapping("/contato")
	public ModelAndView contato(){		
	ModelAndView mv = new ModelAndView("/contato");
		
	return mv;
	}

}
