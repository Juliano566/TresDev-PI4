function atualizarProduto() {
	var idProduto = $("#idProdutoAtualiza").val();
	console.log("atualizar produto cujo id é: ", idProduto);
	console.log("#idProdutoAtualiza");

	var id = $("#idProdutoAtualiza").val();
	var nome = $("#nomeProdutoAtualiza").val();
	var familia = $("#familiaProdutoAtualiza").val();
	var quantidade = $("#quantidadeProdutoAtualiza").val();
	var preco = $("#precoProdutoAtualiza").val();
	var descricao = $("#descricaoProdutoAtualiza").val();
	var filial = $("#filialProdutoAtualiza").val();



	$.post('AtualizarProduto', {
		id: id, nome: nome, familia: familia, quantidade: quantidade,
		preco: preco, descricao: descricao, filial: filial
	}, function() {

		$('#modalExclusao').modal('hide');
		window.location.reload();

	});


}




function test(){
	
	alert("Teste")
	
	
}
