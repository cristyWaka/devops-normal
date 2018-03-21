package com.jenkins.practica.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jenkins.utils.DogsOperations;

import io.prometheus.client.Counter;

@Controller
public class DogsController {

	private static final Counter httpRequestsTotal = Counter
			.build("dogs_requests", "Total HTTP requests against the server to the main page")
			.labelNames("path")
			.register();

	@RequestMapping(value="/", method={RequestMethod.GET})
	public ModelAndView main(Model model) {
		ModelAndView mv = new ModelAndView("main");
		
		DogsOperations dogsOp = new DogsOperations();
		
		// First operation
        mv.addObject("random", dogsOp.getRandomDogImage());
        
        // Second operation
        mv.addObject("breed_list", dogsOp.getBreedList());
        
		httpRequestsTotal.labels("/main").inc();
		return mv;
	}
}
