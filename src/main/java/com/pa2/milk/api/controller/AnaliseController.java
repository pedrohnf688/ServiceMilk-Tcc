package com.pa2.milk.api.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pa2.milk.api.helper.Response;
import com.pa2.milk.api.model.Amostra;
import com.pa2.milk.api.model.Analise;
import com.pa2.milk.api.model.Cliente;
import com.pa2.milk.api.model.enums.EnumTipoPerfilUsuario;
import com.pa2.milk.api.repository.AmostraRepository;
import com.pa2.milk.api.service.AmostraService;
import com.pa2.milk.api.service.AnaliseService;

@RestController
@RequestMapping(value = "/analise")
@CrossOrigin(origins = "*")
public class AnaliseController {
	
	private static final Logger log = LoggerFactory.getLogger(AnaliseController.class);
	
	@Autowired
	private AnaliseService analiseService;
	
	@Autowired
	private AmostraService amostraService;
	
	@Autowired
	private AmostraRepository amostraRepository;
	
	@PreAuthorize("hasAnyRole('ADMINISTRADOR','BOLSISTA','CLIENTE')")
	@GetMapping(value = "{solicitacaoId}")
	public List<Analise> buscarAnalisePorSolicitacaoId(@PathVariable("solicitacaoId") Integer solicitacaoId) {
		log.info("Listando Analises pelo Id da Solicitação");
		List<Analise> analises = this.analiseService.listarAnalisesPorSolicitacaoId(solicitacaoId);
		return analises;

	}
	
	@PreAuthorize("hasAnyRole('ADMINISTRADOR','BOLSISTA','CLIENTE')")
	@DeleteMapping(value = "{id}")
	public ResponseEntity<Response<Analise>> deletarAnalisePorId(@PathVariable("id") Integer id) {

		log.info("Removendo Analise por Id: {}", id);

		Response<Analise> response = new Response<Analise>();

		Optional<Analise> analise = this.analiseService.buscarAnalisePorId(id);
		
		if (!analise.isPresent()) {
			log.info("Analise não encontrada");
			response.getErros().add("Analise não encontrada");
			ResponseEntity.badRequest().body(response);
		}

		List<Amostra> amostras = this.amostraService.listarAmostrasPorAnalise(analise.get().getId());
		
		response.setData(analise.get());
		
		this.analiseService.deletarAnalisePorId(id);
		this.amostraRepository.deleteAll(amostras);

		return ResponseEntity.ok(response);
	}
	

	
	@PreAuthorize("hasAnyRole('ADMINISTRADOR','BOLSISTA','CLIENTE')")
	@GetMapping(value = "{id}")
	public ResponseEntity<Response<Analise>> buscarAnalisePorId(@PathVariable("id") Integer id) {

		log.info("Buscar Cliente por Id");

		Response<Analise> response = new Response<Analise>();

		Optional<Analise> analise = this.analiseService.buscarAnalisePorId(id);

		if (!analise.isPresent()) {
			log.info("Analise não encontrada: {}", analise.get());
			response.getErros().add("Analise não encontrada");
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(analise.get());

		return ResponseEntity.ok(response);

	}
	

}
