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
@NamedQuery(name = "ArquivoRepository.findByFotoPerfilId", query = "SELECT a FROM Arquivo a WHERE a.cliente.id = :clienteId")
@NamedQuery(name = "ArquivoRepository.findAllByFotoPerfilId", query = "SELECT a FROM Arquivo a WHERE a.cliente.id = :clienteId")
public interface ArquivoRepository extends JpaRepository<Arquivo, String> {

	Arquivo findByFotoPerfilId(@Param("clienteId") Integer clienteId);

	List<Arquivo> findAllByFotoPerfilId(@Param("clienteId") Integer clienteId);

}