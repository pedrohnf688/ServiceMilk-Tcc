package com.pa2.milk.api.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pa2.milk.api.model.Fazenda;
import com.pa2.milk.api.repository.FazendaRepository;

@Service
public class FazendaService {

	private static final Logger log = LoggerFactory.getLogger(FazendaService.class);

	@Autowired
	private FazendaRepository fazendaRepository;

	public Fazenda buscarPorId(Integer id) {
		Optional<Fazenda> objFazenda = fazendaRepository.findById(id);
		return objFazenda.orElse(null);
	}

	public void salvar(Fazenda fazenda) {
		log.info("Salvando fazenda ");
		fazendaRepository.save(fazenda);
	}

	public void remover(Integer id) {
		log.info("Removendo fazenda pelo Id: {}", id);
		this.fazendaRepository.deleteById(id);
	}

	public List<Fazenda> listarFazenda() {
		log.info("Listando todas as fazendas");
		return this.fazendaRepository.findAll();
	}

	public Optional<Fazenda> buscarPorCnpj(String cnpj) {
		log.info("Buscando fazenda pelo Cnpj: {}", cnpj);
		return Optional.ofNullable(this.fazendaRepository.findByCnpj(cnpj));
	}
	
	public List<Fazenda> buscarFazendaClienteId(Integer clienteId){
		return this.fazendaRepository.findByClienteId(clienteId);
	}

}
