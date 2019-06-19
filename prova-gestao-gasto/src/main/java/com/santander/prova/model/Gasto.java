package com.santander.prova.model;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="gasto")
public class Gasto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String descricao;
	private double valor;
	private Long codigoUsuario;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date data;
	
	@OneToOne 
    private CategoriaGasto categoriaGasto;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public CategoriaGasto getCategoriaGasto() {
		return categoriaGasto;
	}

	public void setCategoriaGasto(CategoriaGasto categoriaGasto) {
		this.categoriaGasto = categoriaGasto;
	}

	public Long getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(Long codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	@Override
	public String toString() {
		return "Gasto [id=" + id + ", descricao=" + descricao + ", valor=" + valor
				+ ", codigoUsuario=" + codigoUsuario + ", data=" + data + ", categoriaGasto=" + categoriaGasto + "]";
	}
}
