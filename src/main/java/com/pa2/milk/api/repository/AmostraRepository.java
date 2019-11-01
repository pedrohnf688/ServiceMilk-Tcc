package com.pa2.milk.api.repository;

import java.util.List;

import javax.persistence.NamedQuery;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.pa2.milk.api.model.Amostra;

@Transactional(readOnly = true)
@NamedQuery(name = "AmostraRepository.findByAnaliseId", query = "SELECT a FROM Amostra a WHERE a.analise.id = :analiseId")
public interface AmostraRepository extends GenericRepository<Amostra, Integer> {

	List<Amostra> findByAnaliseId(@Param("analiseId") Integer analiseId);

	Amostra findByIdentificadorAmostra(@Param("identifAmostra") String identifAmostra);

	@Query(value = "SELECT * FROM Amostra t WHERE t.finalizada = true AND t.analise_id = :analiseId", nativeQuery = true)
	List<Amostra> findByAmostrasStatusColetadas(@Param("analiseId") Integer analiseId);

	@Query(value = "SELECT * FROM Amostra t WHERE t.analise_id = :analiseId AND t.identificador_amostra = :identifAmostra", nativeQuery = true)
	Amostra findByQrCodeAmostras(@Param("analiseId") Integer analiseId, @Param("identifAmostra") String identifAmostra);

	
	
	
}
   


