package com.pa2.milk.api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pa2.milk.api.model.Laudo;

public interface LaudoRepositorio extends JpaRepository<Laudo, Integer> {

	List<Laudo> findByBatchId(String batchId);

	List<Laudo> findAllByDate(LocalDate date);

	Laudo findAllByBatchId(String batchId);

}
