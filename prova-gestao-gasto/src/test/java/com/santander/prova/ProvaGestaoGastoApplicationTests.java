package com.santander.prova;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.santander.prova.model.CategoriaGasto;
import com.santander.prova.model.Gasto;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static groovy.json.JsonOutput.toJson;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProvaGestaoGastoApplicationTests {

	@Autowired
    protected WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    protected MockMvc mockMvc;
 
    @Before
    public void setupInicial() throws Exception {
    	
    	this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity(springSecurityFilterChain))
                .build();
                
    	
    	ArrayList<String> categorias = new ArrayList<String>();
    	
    	categorias.add("Alimentacao");
    	categorias.add("Moradia");
    	categorias.add("Vestuario");
    	categorias.add("Educacao");
    	categorias.add("Servicos");
    	categorias.add("Comunicacao");
    	
    	for(int i=0; i<categorias.size(); i++) {
    		CategoriaGasto categoriaGasto = new CategoriaGasto();
        	
        	categoriaGasto.setDescricao(categorias.get(i));
        	
        	mockMvc.perform(post("/categoria/adicionar").with(httpBasic("usuarioTeste","1234")).contentType(APPLICATION_JSON).content(toJson(categoriaGasto))).andExpect(status().isOk()).andReturn();
    	}
    }
 
    
    @Test
    public void testAdicionaGastoUsuarioValido() throws Exception {
    	 	
    	Gasto gasto = new Gasto();
    	
    	gasto.setDescricao("Almoço");
    	gasto.setValor(125.55);
    	gasto.setCodigoUsuario(1L);

    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	java.sql.Date data = new java.sql.Date(sdf.parse("2019-06-10").getTime());
    	gasto.setData(data);
    	 	
    	CategoriaGasto categoriaGasto = new CategoriaGasto();
    	categoriaGasto.setId(1L);
    	categoriaGasto.setDescricao("Alimentacao");
    	
    	gasto.setCategoriaGasto(categoriaGasto);
    	 
    	mockMvc.perform(post("/gasto/adicionar").with(httpBasic("adminTeste","1234")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();
    
    	
    	gasto.setDescricao("Condominio");
    	gasto.setValor(300.78);
    	gasto.setCodigoUsuario(1L);

    	sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	data = new java.sql.Date(sdf.parse("2019-06-11").getTime());
    	gasto.setData(data);
    	
    	categoriaGasto.setId(2L);
    	categoriaGasto.setDescricao("Moradia");
    	
    	gasto.setCategoriaGasto(categoriaGasto);
    
    	mockMvc.perform(post("/gasto/adicionar").with(httpBasic("adminTeste","1234")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();
    	
      	
    	gasto.setDescricao("Bolsa");
    	gasto.setValor(456.96);
    	gasto.setCodigoUsuario(2L);

    	sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	data = new java.sql.Date(sdf.parse("2019-06-09").getTime());
    	gasto.setData(data);
    	   
    	mockMvc.perform(post("/gasto/adicionar").with(httpBasic("adminTeste","1234")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();
    }
    
    
    @Test
    public void testAdicionaGastoUsuarioInvalido() throws Exception {
    	 	
    	Gasto gasto = new Gasto();
    	
    	gasto.setDescricao("Almoço");
    	gasto.setValor(125.55);
    	gasto.setCodigoUsuario(1L);

    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	java.sql.Date data = new java.sql.Date(sdf.parse("2019-06-10").getTime());
    	gasto.setData(data);
    	 	
    	CategoriaGasto categoriaGasto = new CategoriaGasto();
    	categoriaGasto.setId(1L);
    	categoriaGasto.setDescricao("Alimentacao");
    	
    	gasto.setCategoriaGasto(categoriaGasto);
    	 
    	mockMvc.perform(post("/gasto/adicionar").with(httpBasic("xptoUsr","1234")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();
    }
    
    
    @Test
    public void testAdicionaGastoSenhaUsuarioInvalida() throws Exception {
    	Gasto gasto = new Gasto();
    	
    	gasto.setDescricao("Condominio");
    	gasto.setValor(300.78);
    	gasto.setCodigoUsuario(1L);

    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	java.sql.Date data = new java.sql.Date(sdf.parse("2019-06-11").getTime());
    	gasto.setData(data);
    	
    	CategoriaGasto categoriaGasto = new CategoriaGasto();
    	categoriaGasto.setId(2L);
    	categoriaGasto.setDescricao("Moradia");
    	
    	gasto.setCategoriaGasto(categoriaGasto);
    
    	mockMvc.perform(post("/gasto/adicionar").with(httpBasic("adminTeste","123456")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();

    }
    
    
    @Test
    public void testListaGastoUsuarioValido() throws Exception {
    	 		 
    	mockMvc.perform(get("/gasto/listar/1").with(httpBasic("usuarioTeste","1234"))).andExpect(status().isOk()).andReturn();
    }
    
    
    @Test
    public void testListaGastoUsuarioInvalido() throws Exception {
    	 		 
    	mockMvc.perform(get("/gasto/listar/1").with(httpBasic("xptoUsr","1234"))).andExpect(status().isOk()).andReturn();
    }
    
    
    @Test
    public void testListaGastoSenhaUsuarioInvalida() throws Exception {
    	 		 
    	mockMvc.perform(get("/gasto/listar/1").with(httpBasic("usuarioTeste","123456"))).andExpect(status().isOk()).andReturn();
    }
    
    
    @Test
    public void testListaGastoSemParametroUsuario() throws Exception {
    	 		 
    	mockMvc.perform(get("/gasto/listar").with(httpBasic("usuarioTeste","1234"))).andExpect(status().isOk()).andReturn();
    }
	
    
    @Test
    public void testFiltraGastoUsuarioValido() throws Exception {
    	 		 
    	mockMvc.perform(get("/gasto/filtrar/1/2019-06-10").with(httpBasic("usuarioTeste","1234"))).andExpect(status().isOk()).andReturn();
    }
    
    
    @Test
    public void testFiltraGastoUsuarioInvalido() throws Exception {
    	 		 
    	mockMvc.perform(get("/gasto/filtrar/1/2019-06-10").with(httpBasic("xptoUsr","1234"))).andExpect(status().isOk()).andReturn();
    }
    
    
    @Test
    public void testFiltraGastoSenhaUsuarioInvalida() throws Exception {
    	 		 
    	mockMvc.perform(get("/gasto/filtrar/1/2019-06-10").with(httpBasic("usuarioTeste","123456"))).andExpect(status().isOk()).andReturn();
    }
    
    
    @Test
    public void testFiltraGastoSemParametroData() throws Exception {
    	 		 
    	mockMvc.perform(get("/gasto/filtrar/1").with(httpBasic("usuarioTeste","1234"))).andExpect(status().isOk()).andReturn();
    }
    
    
    @Test
    public void testAlteraGastoCategorizacao() throws Exception {
    	
    	Gasto gasto = new Gasto();
    	
    	gasto.setDescricao("Almoço");
    	gasto.setValor(125.55);
    	gasto.setCodigoUsuario(1L);

    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	java.sql.Date data = new java.sql.Date(sdf.parse("2019-06-10").getTime());
    	gasto.setData(data);
    	 	
    	CategoriaGasto categoriaGasto = new CategoriaGasto();
    	categoriaGasto.setId(1L);
    	categoriaGasto.setDescricao("Alimentacao");
    	
    	gasto.setCategoriaGasto(categoriaGasto);
    	 
    	mockMvc.perform(post("/gasto/adicionar").with(httpBasic("adminTeste","1234")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();
   
    	
    	gasto.setDescricao("Condominio");
    	gasto.setValor(300.78);
    	gasto.setCodigoUsuario(1L);

    	sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	data = new java.sql.Date(sdf.parse("2019-06-11").getTime());
    	gasto.setData(data);
    	
    	categoriaGasto.setId(2L);
    	categoriaGasto.setDescricao("Moradia");
    	
    	gasto.setCategoriaGasto(categoriaGasto);
    
    	mockMvc.perform(post("/gasto/adicionar").with(httpBasic("adminTeste","1234")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();
    	
      	
    	gasto.setDescricao("Bolsa");
    	gasto.setValor(456.96);
    	gasto.setCodigoUsuario(2L);

    	sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	data = new java.sql.Date(sdf.parse("2019-06-09").getTime());
    	gasto.setData(data);
    	   
    	mockMvc.perform(post("/gasto/adicionar").with(httpBasic("adminTeste","1234")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();

    	 	     	
    	gasto.setId(3L);
    	gasto.setDescricao("Bolsa");
    	gasto.setValor(456.96);
    	gasto.setCodigoUsuario(2L);

    	sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	data = new java.sql.Date(sdf.parse("2019-06-09").getTime());
    	gasto.setData(data);
    	
    	categoriaGasto.setId(3L);
    	categoriaGasto.setDescricao("Vestuario");
    	
    	gasto.setCategoriaGasto(categoriaGasto);
    	   
    	mockMvc.perform(put("/gasto/alterar").with(httpBasic("usuarioTeste","1234")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();
    }
    
    
    @Test
    public void testAlteraGastoCategorizacaoUsuarioInvalido() throws Exception {
    	 	
    	Gasto gasto = new Gasto();
    	
    	gasto.setDescricao("Almoço");
    	gasto.setValor(125.55);
    	gasto.setCodigoUsuario(1L);

    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	java.sql.Date data = new java.sql.Date(sdf.parse("2019-06-10").getTime());
    	gasto.setData(data);
    	 	
    	CategoriaGasto categoriaGasto = new CategoriaGasto();
    	categoriaGasto.setId(1L);
    	categoriaGasto.setDescricao("Alimentacao");
    	
    	gasto.setCategoriaGasto(categoriaGasto);
    	 
    	mockMvc.perform(post("/gasto/adicionar").with(httpBasic("adminTeste","1234")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();
   
    	
    	gasto.setDescricao("Condominio");
    	gasto.setValor(300.78);
    	gasto.setCodigoUsuario(1L);

    	sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	data = new java.sql.Date(sdf.parse("2019-06-11").getTime());
    	gasto.setData(data);
    	
    	categoriaGasto.setId(2L);
    	categoriaGasto.setDescricao("Moradia");
    	
    	gasto.setCategoriaGasto(categoriaGasto);
    
    	mockMvc.perform(post("/gasto/adicionar").with(httpBasic("adminTeste","1234")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();
    	
      	
    	gasto.setDescricao("Bolsa");
    	gasto.setValor(456.96);
    	gasto.setCodigoUsuario(2L);

    	sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	data = new java.sql.Date(sdf.parse("2019-06-09").getTime());
    	gasto.setData(data);
    	   
    	mockMvc.perform(post("/gasto/adicionar").with(httpBasic("adminTeste","1234")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();

      	
    	gasto.setId(3L);
    	gasto.setDescricao("Bolsa");
    	gasto.setValor(456.96);
    	gasto.setCodigoUsuario(2L);

    	sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	data = new java.sql.Date(sdf.parse("2019-06-09").getTime());
    	gasto.setData(data);
    	
    	categoriaGasto.setId(3L);
    	categoriaGasto.setDescricao("Vestuario");
    	
    	gasto.setCategoriaGasto(categoriaGasto);
    	 
    	mockMvc.perform(put("/gasto/alterar").with(httpBasic("xptoUsr","1234")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();
    }
    
    
    @Test
    public void testAlteraGastoCategorizacaoSenhaUsuarioInvalida() throws Exception {
    	
    	Gasto gasto = new Gasto();
    	
    	gasto.setDescricao("Almoço");
    	gasto.setValor(125.55);
    	gasto.setCodigoUsuario(1L);

    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	java.sql.Date data = new java.sql.Date(sdf.parse("2019-06-10").getTime());
    	gasto.setData(data);
    	 	
    	CategoriaGasto categoriaGasto = new CategoriaGasto();
    	categoriaGasto.setId(1L);
    	categoriaGasto.setDescricao("Alimentacao");
    	
    	gasto.setCategoriaGasto(categoriaGasto);
    	 
    	mockMvc.perform(post("/gasto/adicionar").with(httpBasic("adminTeste","1234")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();
   
    	
    	gasto.setDescricao("Condominio");
    	gasto.setValor(300.78);
    	gasto.setCodigoUsuario(1L);

    	sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	data = new java.sql.Date(sdf.parse("2019-06-11").getTime());
    	gasto.setData(data);
    	
    	categoriaGasto.setId(2L);
    	categoriaGasto.setDescricao("Moradia");
    	
    	gasto.setCategoriaGasto(categoriaGasto);
    
    	mockMvc.perform(post("/gasto/adicionar").with(httpBasic("adminTeste","1234")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();
    	
      	
    	gasto.setDescricao("Bolsa");
    	gasto.setValor(456.96);
    	gasto.setCodigoUsuario(2L);

    	sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	data = new java.sql.Date(sdf.parse("2019-06-09").getTime());
    	gasto.setData(data);
    	   
    	mockMvc.perform(post("/gasto/adicionar").with(httpBasic("adminTeste","1234")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();
      	
    	gasto.setId(3L);
    	gasto.setDescricao("Bolsa");
    	gasto.setValor(456.96);
    	gasto.setCodigoUsuario(2L);

    	sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	data = new java.sql.Date(sdf.parse("2019-06-09").getTime());
    	gasto.setData(data);
    	
    	categoriaGasto.setId(3L);
    	categoriaGasto.setDescricao("Vestuario");
    	
    	gasto.setCategoriaGasto(categoriaGasto);
    
    	mockMvc.perform(put("/gasto/alterar").with(httpBasic("usuarioTeste","123456")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();

    }
    
    
    
    @Test
    public void testAlteraGastoCategorizacaoCategoriaInvalida() throws Exception {
    	
    	Gasto gasto = new Gasto();
      	
    	gasto.setId(3L);
    	gasto.setDescricao("Bolsa");
    	gasto.setValor(456.96);
    	gasto.setCodigoUsuario(2L);

    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	java.sql.Date data = new java.sql.Date(sdf.parse("2019-06-09").getTime());
    	gasto.setData(data);
    	
    	CategoriaGasto categoriaGasto = new CategoriaGasto();
    	categoriaGasto.setId(8L);
    	categoriaGasto.setDescricao("Outros");
    	
    	gasto.setCategoriaGasto(categoriaGasto);
    
    	mockMvc.perform(post("/gasto/alterar").with(httpBasic("usuarioTeste","1234")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();

    }
    
    
    @Test
    public void testSugestaoCategoriaUsuarioValido() throws Exception {
    	 		 
    	mockMvc.perform(get("/categoria/listar/cao").with(httpBasic("usuarioTeste","1234"))).andExpect(status().isOk()).andReturn();
    }
    
    
    @Test
    public void testSugestaoCategoriaUsuarioInvalido() throws Exception {
    	 		 
    	mockMvc.perform(get("/categoria/listar/cao").with(httpBasic("xptoUsr","1234"))).andExpect(status().isOk()).andReturn();
    }
    
    
    @Test
    public void testSugestaoCategoriaSenhaUsuarioInvalida() throws Exception {
    	 		 
    	mockMvc.perform(get("/categoria/listar/cao").with(httpBasic("usuarioTeste","123456"))).andExpect(status().isOk()).andReturn();
    }
    
    
    @Test
    public void testSugestaoCategoriaSemParametroSearch() throws Exception {
    	 		 
    	mockMvc.perform(get("/categoria/listar").with(httpBasic("usuarioTeste","1234"))).andExpect(status().isOk()).andReturn();
    }
    
    
    @Test
    public void testCategorizacaoAutomaticaGastoUsuarioValido() throws Exception {
    	 	
    	Gasto gasto = new Gasto();
    	
    	gasto.setDescricao("Almoço");
    	gasto.setValor(125.55);
    	gasto.setCodigoUsuario(1L);

    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	java.sql.Date data = new java.sql.Date(sdf.parse("2019-06-10").getTime());
    	gasto.setData(data);
    	 	
    	CategoriaGasto categoriaGasto = new CategoriaGasto();
    	categoriaGasto.setId(1L);
    	categoriaGasto.setDescricao("Alimentacao");
    	
    	gasto.setCategoriaGasto(categoriaGasto);
    	 
    	mockMvc.perform(post("/gasto/adicionar").with(httpBasic("adminTeste","1234")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();
   
    	
    	gasto.setDescricao("Condominio");
    	gasto.setValor(300.78);
    	gasto.setCodigoUsuario(1L);

    	sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	data = new java.sql.Date(sdf.parse("2019-06-11").getTime());
    	gasto.setData(data);
    	
    	categoriaGasto.setId(2L);
    	categoriaGasto.setDescricao("Moradia");
    	
    	gasto.setCategoriaGasto(categoriaGasto);
    
    	mockMvc.perform(post("/gasto/adicionar").with(httpBasic("adminTeste","1234")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();
    	
      	
    	gasto.setDescricao("Bolsa");
    	gasto.setValor(456.96);
    	gasto.setCodigoUsuario(2L);

    	sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	data = new java.sql.Date(sdf.parse("2019-06-09").getTime());
    	gasto.setData(data);
    	   
    	mockMvc.perform(post("/gasto/adicionar").with(httpBasic("adminTeste","1234")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();
    
    
    	gasto.setDescricao("Lanche");
    	gasto.setValor(25.30);
    	gasto.setCodigoUsuario(1L);

    	sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	data = new java.sql.Date(sdf.parse("2019-06-15").getTime());
    	gasto.setData(data);
    	
    	categoriaGasto.setId(0L);
    	categoriaGasto.setDescricao("Alimentacao");
    	
    	gasto.setCategoriaGasto(categoriaGasto);
    
    	mockMvc.perform(post("/gasto/adicionarCategoriaAuto").with(httpBasic("adminTeste","1234")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();
    	
    }
    
    
    @Test
    public void testCategorizacaoAutomaticaGastoUsuarioInvalido() throws Exception {
    	
    	Gasto gasto = new Gasto();
    	
    	gasto.setDescricao("Almoço");
    	gasto.setValor(125.55);
    	gasto.setCodigoUsuario(1L);

    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	java.sql.Date data = new java.sql.Date(sdf.parse("2019-06-10").getTime());
    	gasto.setData(data);
    	 	
    	CategoriaGasto categoriaGasto = new CategoriaGasto();
    	categoriaGasto.setId(1L);
    	categoriaGasto.setDescricao("Alimentacao");
    	
    	gasto.setCategoriaGasto(categoriaGasto);
    	 
    	mockMvc.perform(post("/gasto/adicionar").with(httpBasic("adminTeste","1234")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();
      
    
    	gasto.setDescricao("Lanche");
    	gasto.setValor(25.30);
    	gasto.setCodigoUsuario(1L);

    	sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	data = new java.sql.Date(sdf.parse("2019-06-15").getTime());
    	gasto.setData(data);
    	
    	categoriaGasto.setId(0L);
    	categoriaGasto.setDescricao("Alimentacao");
    	
    	gasto.setCategoriaGasto(categoriaGasto);
    
    	mockMvc.perform(post("/gasto/adicionarCategoriaAuto").with(httpBasic("xptoUsr","1234")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();
    		 	
    }
    
    
    @Test
    public void testCategorizacaoAutomaticaGastoSenhaUsuarioInvalida() throws Exception {
    	
    	Gasto gasto = new Gasto();
    	
    	gasto.setDescricao("Almoço");
    	gasto.setValor(125.55);
    	gasto.setCodigoUsuario(1L);

    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	java.sql.Date data = new java.sql.Date(sdf.parse("2019-06-10").getTime());
    	gasto.setData(data);
    	 	
    	CategoriaGasto categoriaGasto = new CategoriaGasto();
    	categoriaGasto.setId(1L);
    	categoriaGasto.setDescricao("Alimentacao");
    	
    	gasto.setCategoriaGasto(categoriaGasto);
    	 
    	mockMvc.perform(post("/gasto/adicionar").with(httpBasic("adminTeste","1234")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();
      
    
    	gasto.setDescricao("Lanche");
    	gasto.setValor(25.30);
    	gasto.setCodigoUsuario(1L);

    	sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	data = new java.sql.Date(sdf.parse("2019-06-15").getTime());
    	gasto.setData(data);
    	
    	categoriaGasto.setId(0L);
    	categoriaGasto.setDescricao("Alimentacao");
    	
    	gasto.setCategoriaGasto(categoriaGasto);
    
    	mockMvc.perform(post("/gasto/adicionarCategoriaAuto").with(httpBasic("adminTeste","123456")).contentType(APPLICATION_JSON).content(toJson(gasto))).andExpect(status().isOk()).andReturn();

    }

}
