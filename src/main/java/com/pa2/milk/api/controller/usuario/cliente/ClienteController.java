package com.pa2.milk.api.controller.usuario.cliente;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

	
	@RequestMapping(method = RequestMethod.GET)
	public String findById() {
		return "REST está funcionando123";
	}
}
