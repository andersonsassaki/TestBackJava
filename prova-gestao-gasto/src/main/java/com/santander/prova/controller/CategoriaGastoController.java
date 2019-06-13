package com.santander.prova.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santander.prova.model.CategoriaGasto;
import com.santander.prova.model.Gasto;
import com.santander.prova.repository.CategoriaGastoRepository;

@RestController
@RequestMapping("/categoria")
public class CategoriaGastoController {

	private CategoriaGastoRepository repository;
	
	CategoriaGastoController(CategoriaGastoRepository categoriaGastoRepository) {
		this.repository = categoriaGastoRepository;
	}
	
	
	@PostMapping("/adicionar")
	public CategoriaGasto adicionaCategoriaGasto(@RequestBody CategoriaGasto categoriaGasto){
	    return repository.save(categoriaGasto);
	}
	
	
	@GetMapping
	@RequestMapping("/listar/{searchCategoriaGasto}")
	public ResponseEntity<List<CategoriaGasto>> listaCategoriaGasto(@PathVariable String searchCategoriaGasto) {
		
		List<CategoriaGasto> categoriaGastos = (List<CategoriaGasto>) repository.findByDescricao(searchCategoriaGasto);
		return ResponseEntity.status(HttpStatus.OK).body(categoriaGastos);        
	} 
	
	
	@RequestMapping("/list")
	public ResponseEntity<List<CategoriaGasto>> listaCategoriaGastoAll() {
		
		List<CategoriaGasto> categoriaGastos = (List<CategoriaGasto>) repository.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(categoriaGastos);        
	} 

}
