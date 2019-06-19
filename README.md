
********************************
* Incluir categorias de gastos *
********************************
M�todo: POST
Endpoint: http://localhost:8081/categoria/adicionar
Usu�rio: usuarioTeste
Senha: 1234

{"descricao":"Alimentacao"
}
{"descricao":"Moradia"
}
{"descricao":"Vestuario"
}
{"descricao":"Educacao"
}
{"descricao":"Servicos"
}
{"descricao":"Comunicacao"
}


********************************************************
* 1.) Incluir gastos - Integra��o de gastos por cart�o *
********************************************************
M�todo: POST
Endpoint: http://localhost:8081/gasto/adicionar
Usu�rio: adminTeste
Senha: 1234

{"descricao":"Almo�o","valor":125.55,"codigoUsuario":1,"data":"2019-06-10","categoriaGasto": {"id":1, "descricao":"Alimentacao"}}
{"descricao":"Condominio","valor":300.78,"codigoUsuario":1,"data":"2019-06-11","categoriaGasto": {"id":2, "descricao":"Moradia"}}
{"descricao":"Bolsa","valor":456.96,"codigoUsuario":2,"data":"2019-06-09"}
{"descricao":"Jantar","valor":150.0,"codigoUsuario":1,"data":"2019-06-10","categoriaGasto": {"id":1, "descricao":"Alimentacao"}}
{"descricao":"Aluguel","valor":1200.00,"codigoUsuario":2,"data":"2019-06-15","categoriaGasto": {"id":1, "descricao":"Alimentacao"}}


**************************
* 2.) Listagem de gastos *
************************** 
M�todo: GET
Endpoint: http://localhost:8081/gasto/listar/{codigoUsuario}
Usu�rio: usuarioTeste
Senha: 1234

http://localhost:8081/gasto/listar/1
 

************************
* 3.) Filtro de gastos *
************************ 
M�todo: GET
Endpoint: http://localhost:8081/gasto/filtrar/{codigoUsuario}/{data}
Usu�rio: usuarioTeste
Senha: 1234

http://localhost:8081/gasto/filtrar/1/2019-06-09
OBS: Teste no Browser


*******************************
* 4.) Categoriza��o de gastos *
*******************************
M�todo: PUT
Endpoint: http://localhost:8081/gasto/alterar
Usu�rio: usuarioTeste
Senha: 1234

{
"id": 3,
 "descricao": "Bolsa",
 "valor": 456.96,
 "codigoUsuario": 2,
 "data": "2019-06-09",
 "categoriaGasto": {
"id":3,
 "descricao":"Vestuario"
}
}


*****************************
* 5.) Sugest�o de categoria *
*****************************
M�todo: GET
Endpoint: http://localhost:8081/categoria/listar/{descricaoCategoria}
Usu�rio: usuarioTeste
Senha: 1234

http://localhost:8081/categoria/listar/cao


******************************************
* 6.) Categoriza��o autom�tica de gastos *
******************************************
M�todo: GET
Endpoint: http://localhost:8081/gasto/adicionarCategoriaAuto
Usu�rio: adminTeste
Senha: 1234

{"descricao":"Lanche","valor":25.3,"codigoUsuario":1,"data":"2019-06-15","categoriaGasto":{
"id":0,
 "descricao":"Alimentacao"
}}
