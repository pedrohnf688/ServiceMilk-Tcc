package com.pa2.milk.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pa2.milk.api.model.Arquivo;

@Repository
public interface ArquivoRepository extends JpaRepository<Arquivo, String>{

}
