package mx.com.admoninmuebles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.admoninmuebles.service.CorreoService;

@RestController
@RequestMapping("/api")
public class CorreoController {
	
	 @Autowired
	 private CorreoService correoService;
	
	 @GetMapping("/sendMail")
	 public String sendMail() {
		 return correoService.sendMail();
	 }

}
