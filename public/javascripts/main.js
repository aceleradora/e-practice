$(document).ready(function(){
    fadeOutStatusBotao();

    $("#botaoDeResultadoDoExercicio").click(function() {
        $("#resultadoDoExercicio").modal();
    });

    $("#botaoDeSolucaoDoProfessor").click(function() {
            $("#solucaoDoProfessor").modal();
        });

    $("#botao-de-limpar").click(function(){
        limpaASolucao();
    });

    var caixaDeTexto = $("#solucaoDoUsuario");
    if(caixaDeTexto[0].value == "") {
        $("#botao-de-limpar").attr("disabled", "disabled");
    }

    desabilitaBotaoDeProximoQuandoNaoExistemMaisExercicios();
    $("#botao-proximo-exercicio").click(function(){
        proximoExercicio();
    });


    $(document).keyup(function(evento){
        desabilitaBotaoDeLimparQuandoNaoHouverTextoNoConsole();
    });

    desabilitaBotaoDeEnviarQuandoNaoExistemMaisExercicios();
    $("#botao-de-enviar").click(function(){
        $("#formCodigo").submit();
    });

    $("#formCodigo").submit(function(){
        desabilitaBotao();
    });

    criarLinhas('solucaoDoUsuario');
});

function desabilitaBotaoDeEnviarQuandoNaoExistemMaisExercicios(){
    if($("#tab1 > .abas > pre").html() == "Você já resolveu todos os exercícios.") {
        $("#botao-de-enviar").attr("disabled", "disabled");
    }
}

function desabilitaBotaoDeProximoQuandoNaoExistemMaisExercicios(){
    if($("#tab1 > .abas > pre").html() == "Você já resolveu todos os exercícios.") {
        $("#botao-proximo-exercicio").attr("disabled", "disabled");
    }
}

function desabilitaBotaoDeLimparQuandoNaoHouverTextoNoConsole(){
   var caixaDeTexto = $("#solucaoDoUsuario");
    if(caixaDeTexto[0].value != "") {
        $("#botao-de-limpar").prop("disabled", false);
    }

    if(caixaDeTexto[0].value == "") {
        $("#botao-de-limpar").prop("disabled", true);
    }
}

function limpaASolucao() {
    var caixaDeTexto = $("#solucaoDoUsuario");
    if(caixaDeTexto[0].value != ""){
        var confirmacao = confirm("Você deseja apagar a solucao?");
        if (confirmacao) {
            $("#solucaoDoUsuario").val("");
            $("#botao-de-limpar").attr("disabled", "disabled");
        }
    }
}

function desabilitaBotao() {
    $("#botaoDeEnviar").attr("disabled");
};


function proximoExercicio(){
    var caixaDeTexto = $("#solucaoDoUsuario")

    if(caixaDeTexto[0].value != ""){
        var confirmacao = confirm("Você tem certeza que quer trocar de exercício? Sua solução anterior será apagada.");
        if (confirmacao) {
            caixaDeTexto.value = "";
            caixaDeTexto.empty();
            location.href = "/solucoes/proximo-exercicio";
        }
    } else {
        location.href = "/solucoes/proximo-exercicio";
    }
}

function fadeOutStatusBotao(){
    if($('#status').html() != '') {
        $('#status').val("").fadeOut(5000);
        $('#mensagemDeTexto').append("<label id='status'></label>");
    }
}