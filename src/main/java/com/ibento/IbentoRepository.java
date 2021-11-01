package com.ibento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IbentoRepository extends JpaRepository<Ibento, Long> {

	List<Ibento> findByNameContainingIgnoreCase(String name);
}
