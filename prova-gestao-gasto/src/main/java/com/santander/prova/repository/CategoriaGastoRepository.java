package com.santander.prova.repository;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.santander.prova.model.CategoriaGasto;

@Repository
@NamedQueries({
	@NamedQuery(name="CategoriaGastoRepository.findByDescricao",
	query="SELECT cat FROM categoriaGasto cat WHERE cat.descricao LIKE LOWER(CONCAT('%', ?1, '%'))")}) //%:searchCategoriaGasto%")})
public interface CategoriaGastoRepository extends JpaRepository<CategoriaGasto, Long> {
	
	List<CategoriaGasto> findByDescricao(String searchCategoriaGasto); //(@Param("searchCategoriaGasto") String searchCategoriaGasto);
}
