package com.pa2.milk.api.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pa2.milk.api.model.Analise;
import com.pa2.milk.api.model.Solicitacao;
import com.pa2.milk.api.repository.AnaliseRepository;

@Service
public class AnaliseService {

	private static final Logger log = LoggerFactory.getLogger(AnaliseService.class);
	
	@Autowired
	private AnaliseRepository analiseRepository;
	
	public List<Analise> listarAnalisesPorSolicitacaoId(Integer solicitacaoId){
		log.info("Listando Analises pelo Id da Solicitação");
		return this.analiseRepository.findBySolicitacaoId(solicitacaoId);
	}
	
	public Optional<Analise> buscarAnalisePorId(Integer id) {
		log.info("Listando Analises por Id");
		return analiseRepository.findById(id);
	}
	
	public void deletarAnalisePorId(Integer id) {
		this.analiseRepository.deleteById(id);
	}
}
