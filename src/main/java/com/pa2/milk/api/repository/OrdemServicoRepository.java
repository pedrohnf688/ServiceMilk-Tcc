package com.pa2.milk.api.repository;

import org.springframework.data.repository.query.Param;

import com.pa2.milk.api.model.OrdemServico;

public interface OrdemServicoRepository extends GenericRepository<OrdemServico, Integer> {
	
	OrdemServico findBySolicitacaoId(@Param("solicitacaoId") Integer solicitacaoId);
}
