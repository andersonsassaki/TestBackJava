package com.santander.prova.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.santander.prova.model.CategoriaGasto;

@Repository
public interface CategoriaGastoRepository extends JpaRepository<CategoriaGasto, Long> {
	
	public List<CategoriaGasto> findByDescricaoContainingIgnoreCase(String searchCategoriaGasto);
	
}
