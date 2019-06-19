package com.santander.prova.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.santander.prova.model.Gasto;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {
	public List<Gasto> findByCodigoUsuarioOrderByDataDesc(Long codigoUsuario);
	public List<Gasto> findByCodigoUsuarioAndData(Long codigoUsuario, Date data);
	
	@Query("SELECT c.id FROM Gasto g JOIN g.categoriaGasto c WHERE g.codigoUsuario = :codigoUsuario AND c.descricao = :descricao")
	public Long findByCodigoUsuario(@Param("codigoUsuario") Long codigoUsuario, @Param("descricao") String descricao);
}
