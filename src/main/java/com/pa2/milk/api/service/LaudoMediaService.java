package com.pa2.milk.api.service;

import java.util.List;
import java.util.Optional;

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
	
	public List<LaudoMedia> laudoMediaPeloIddaSolicitacao(Integer solicitacaoId) {
		return this.laudoMediaRepositorio.findBySolicitacaoId(solicitacaoId);
	}

	public Optional<LaudoMedia> buscarPorId(Integer id) {
		log.info("Buscando Laudo Media por id");
		return this.laudoMediaRepositorio.findById(id);
	}

	public void deletaLaudoMediaPorId(LaudoMedia lm) {
		log.info("Deletando Laudo Media por Id");
	     this.laudoMediaRepositorio.delete(lm);
	}

}
