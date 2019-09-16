package com.pa2.milk.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pa2.milk.api.model.Analise;
import com.pa2.milk.api.service.AnaliseService;

@RestController
@RequestMapping(value = "/analise")
@CrossOrigin(origins = "*")
public class AnaliseController {
	
	private static final Logger log = LoggerFactory.getLogger(AnaliseController.class);
	
	@Autowired
	private AnaliseService analiseService;
	
	
	@PreAuthorize("hasAnyRole('ADMINISTRADOR','BOLSISTA','CLIENTE')")
	@GetMapping(value = "{solicitacaoId}")
	public List<Analise> buscarAnalisePorSolicitacaoId(@PathVariable("solicitacaoId") Integer solicitacaoId) {
		log.info("Listando Analises pelo Id da Solicitação");
		List<Analise> analises = this.analiseService.listarAnalisesPorSolicitacaoId(solicitacaoId);
		return analises;

	}


}
