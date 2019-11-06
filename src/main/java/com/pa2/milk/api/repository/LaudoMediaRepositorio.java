package com.pa2.milk.api.repository;

import java.util.List;

import javax.persistence.NamedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.pa2.milk.api.model.LaudoMedia;

@Transactional(readOnly = true)
@NamedQuery(name = "LaudoMediaRepositorio.findBySolicitacaoId", query = "SELECT l FROM LaudoMedia l WHERE l.solicitacao.id = :solicitacaoId")
public interface LaudoMediaRepositorio extends JpaRepository<LaudoMedia, Integer> {

	List<LaudoMedia> findBySolicitacaoId(@Param("solicitacaoId") Integer solicitacaoId);

	LaudoMedia findAllBySolicitacaoId(@Param("solicitacaoId") Integer solicitacaoId);

}
