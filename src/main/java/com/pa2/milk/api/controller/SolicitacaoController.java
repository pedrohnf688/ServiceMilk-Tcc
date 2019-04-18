package com.pa2.milk.api.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pa2.milk.api.helper.Response;
import com.pa2.milk.api.model.Solicitacao;
import com.pa2.milk.api.model.dto.SolicitacaoDto;
import com.pa2.milk.api.service.SolicitacaoService;

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/solicitacao")
@CrossOrigin(origins = "*")
public class SolicitacaoController {

	private static final Logger log = LoggerFactory.getLogger(FazendaController.class);

	@Autowired
	private SolicitacaoService solicitacaoService;

	@PostMapping
	public ResponseEntity<Response<Solicitacao>> cadastrarSolicitacao(@RequestBody SolicitacaoDto solicitacao,
			BindingResult result) throws NoSuchAlgorithmException, NotFoundException {
		log.info("Cadastrando solicitacao: {}", solicitacao.toString());

		Response<Solicitacao> response = new Response<Solicitacao>();

		if (result.hasErrors()) {
			log.info("Erro validando dados de cadastro da Solicitacao: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		solicitacaoService.salvarSolicitacao(solicitacao.getCnpj());

		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "{id}")
	public ResponseEntity<Response<Solicitacao>> buscarSolicitacaoPorID(@PathVariable("id") Integer id) {

		log.info("Buscar Solicitacao por Id");

		Response<Solicitacao> response = new Response<Solicitacao>();

		Optional<Solicitacao> solicitacao = solicitacaoService.buscarSolicitacaoPorId(id);

		response.setData(solicitacao.get());

		return ResponseEntity.ok(response);
	}
}
