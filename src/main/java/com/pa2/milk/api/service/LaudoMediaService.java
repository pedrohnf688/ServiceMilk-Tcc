package com.pa2.milk.api.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pa2.milk.api.model.LaudoMedia;
import com.pa2.milk.api.repository.LaudoMediaRepositorio;

@Service
public class LaudoMediaService {

	private static final Logger log = LoggerFactory.getLogger(LaudoMediaService.class);

	@Autowired
	private LaudoMediaRepositorio laudoMediaRepositorio;

	public LaudoMedia salvar(LaudoMedia l) {
		log.info("Salvando a Media do Laudo");
		return this.laudoMediaRepositorio.save(l);
	}

	public List<LaudoMedia> listarTodasMedias() {
		return this.laudoMediaRepositorio.findAll();
	}
	
	public LaudoMedia listarLaudoMediaPeloIddaSolicitacao(Integer solicitacaoId) {
		return this.laudoMediaRepositorio.findBySolicitacaoId(solicitacaoId);
	}

}
