$(document).ready(function(){
	//alert("Document Ready está carregado!")
	
	const botao = document.getElementById("form:btn-criar-novo");
	botao.addEventListener("click", () => {
		resetFormulario();
		//document.getElementById("form:dialog").children[0].children[1].addEventListener('click',() => {resetFormulario()});
	});
});

/*$(window).on("load", function(){
   alert("Está carregado!")
   document.getElementById("form:dialog").children[0].children[1].addEventListener('click',() => {testeClique()});
});*/


/*document.onload(funcaoPaginaCarregada())
function funcaoPaginaCarregada() {
	alert('carrega apenas 1 vez');
}*/

function resetFormulario(){
	//alert('clicou');
	document.getElementById("form:dialog_title").innerText = "Cadastrar";
	let btn = document.getElementById("form:btn-atualizar");
	
	if(btn !== undefined && btn !== null){
		btn.classList.add("btn-primary");
		btn.classList.remove("btn-warning");
		btn.children[0].innerHTML = "Salvar";
		
		let formulario = document.getElementById("form:painelGrid");

		for(let i = 0; i < formulario.children[0].children.length; i++) {
			formulario.children[0].children[i].children[1].children[0].value ="";
		};
		
		let selectEmpresa = document.getElementById("form:empresa");
		if(selectEmpresa !== undefined && selectEmpresa !== null)
			selectEmpresa.children[2].innerHTML = "Selecione..."
		
		let selectFuncao = document.getElementById("form:funcao");
		if(selectFuncao !== undefined && selectFuncao !== null)
			selectFuncao.children[2].innerHTML = "Selecione..."
	}
}

function buscarCep(){
	debugger
	let cep = document.getElementById("form:cep").value;

	let url = "https://viacep.com.br/ws/" + cep + "/json/";
	
	fetch(url).then(response =>{
			
	  return response.json();
		
	}).then(data => {
		
		let logradouro = document.getElementById("form:logradouro");
		logradouro.value = data.logradouro;
		
		let bairro = document.getElementById("form:bairro");
		bairro.value = data.bairro;
		
		let cidade = document.getElementById("form:cidade");
		cidade.value = data.localidade;
		
		let uf = document.getElementById("form:uf");
		uf.value = data.uf;
	});
}