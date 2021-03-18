package br.com.TresDevsPI4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController {
	
	@RequestMapping("/teste")
	public String index() {
		return "index";
	}

}
