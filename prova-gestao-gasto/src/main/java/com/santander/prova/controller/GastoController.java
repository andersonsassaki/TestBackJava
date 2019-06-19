package com.santander.prova.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santander.prova.model.Gasto;
import com.santander.prova.repository.GastoRepository;


@RestController
@RequestMapping("/gasto")
public class GastoController {
	
	private GastoRepository repository;	
	
	
	GastoController(GastoRepository gastoRepository) {
		this.repository = gastoRepository;
	}
		
	
	// Funcionalidade: Integra��o de gastos por cart�o
	@PostMapping("/adicionar")
	public Gasto adicionaGasto(@RequestBody Gasto gasto){
	    return repository.save(gasto);
	}
	
	
	// Funcionalidade: Listagem de gastos
	@GetMapping
	@RequestMapping("/listar/{codigoUsuario}")
	public ResponseEntity<List<Gasto>> listaGastoUsuario(@PathVariable Long codigoUsuario) {
		
		List<Gasto> gastos = (List<Gasto>) repository.findByCodigoUsuarioOrderByDataDesc(codigoUsuario);
		return ResponseEntity.status(HttpStatus.OK).body(gastos);        
	}
	
	
	// Funcionalidade: Filtro de gastos
	@GetMapping
	@RequestMapping("/filtrar/{codigoUsuario}/{data}")
    public ResponseEntity<List<Gasto>> filtraGastoUsuario(@PathVariable Long codigoUsuario, @PathVariable String data) throws ParseException {

	 	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	 	Date date = sdf.parse(data);
		
		List<Gasto> gastos = (List<Gasto>) repository.findByCodigoUsuarioAndData(codigoUsuario, date);
		return ResponseEntity.status(HttpStatus.OK).body(gastos);
    }
	
	
	// Funcionalidade: Categoriza��o de gastos
	@PutMapping
	@RequestMapping("/alterar")
    public ResponseEntity<Gasto> alteraGastoUsuario(@RequestBody Gasto gasto) {

        return repository.findById(gasto.getId()).map(recordGasto -> {       	
        	recordGasto.setCategoriaGasto(gasto.getCategoriaGasto());
           
        	Gasto updatedGasto = repository.save(recordGasto);
        	
        	return ResponseEntity.ok().body(updatedGasto);
        }).orElse(ResponseEntity.notFound().build());
    }
	
	
	// Funcionalidade: Integra��o de gastos por cart�o - Inclus�o autom�tica da categoria
	@PostMapping("/adicionarCategoriaAuto")
	public Gasto adicionaGastoAutomatico (@RequestBody Gasto gasto) { 
	    		
		Long codigoCategoria = repository.findByCodigoUsuario(gasto.getCodigoUsuario(), gasto.getCategoriaGasto().getDescricao());
		
		if(!codigoCategoria.toString().contentEquals("")) {
			gasto.getCategoriaGasto().setId(codigoCategoria);
		}
		
		return repository.save(gasto);
	}
}
