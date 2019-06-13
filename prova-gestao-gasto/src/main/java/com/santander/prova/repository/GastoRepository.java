package com.santander.prova.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santander.prova.model.Gasto;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {
	public List<Gasto> findByCodigoUsuario(Long codigoUsuario);
	public List<Gasto> findByCodigoUsuarioAndData(Long codigoUsuario, Date data);
}
