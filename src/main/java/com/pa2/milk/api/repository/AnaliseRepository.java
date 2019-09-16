package com.pa2.milk.api.repository;

import java.util.List;

import javax.persistence.NamedQuery;

import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.pa2.milk.api.model.Analise;

@Transactional(readOnly = true)
@NamedQuery(name = "AnaliseRepository.findBySolicitacaoId", query = "SELECT a FROM Analise a WHERE a.solicitacao.id = :solicitacaoId")
public interface AnaliseRepository extends GenericRepository<Analise, Integer> {

	List<Analise> findBySolicitacaoId(@Param("solicitacaoId") Integer solicitacaoId);
}
