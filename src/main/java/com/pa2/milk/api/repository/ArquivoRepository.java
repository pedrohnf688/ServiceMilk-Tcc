package com.pa2.milk.api.repository;

import java.util.List;

import javax.persistence.NamedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pa2.milk.api.model.Arquivo;

@Repository
@Transactional(readOnly = true)
public interface ArquivoRepository extends JpaRepository<Arquivo, String> {

	Arquivo findByFotoPerfilId(@Param("clienteId") Integer clienteId);

	List<Arquivo> findAllByFotoPerfilId(@Param("clienteId") Integer clienteId);

	Arquivo findByFotoSolicitacaoId(@Param("clienteId") Integer clienteId);

	List<Arquivo> findAllByFotoSolicitacaoId(@Param("clienteId") Integer clienteId);

	Arquivo findByComprovanteSolicitacaoId(@Param("clienteId") Integer clienteId);

	List<Arquivo> findAllByComprovanteSolicitacaoId(@Param("clienteId") Integer clienteId);

	Arquivo findByFotoFazendaId(@Param("clienteId") Integer clienteId);

	List<Arquivo> findAllByFotoFazendaId(@Param("clienteId") Integer clienteId);

	
}