$(document).ready(function(){
    fadeOutStatusBotao();

    $("#botaoDeEnviar").click(function(){
        desabilitaBotao();
        $("#formCodigo").submit();
    });

    $("#botaoDeLimpar").click(function () {
        var btn = this;
        setTimeout(function () { $(btn).attr('disabled', 'disabled'); }, 1);
        return true;
    });

    $("#botao-de-limpar").click(function(){
        limpaASolucao();
    });

    $("#botao-proximo-exercicio").click(function(){
        proximoExercicio();
    });

    $("#formCodigo").submit(function(){
        desabilitaBotao();
    });
});

function limpaASolucao() {
    var caixaDeTexto = $("#solucaoDoUsuario");
    if(caixaDeTexto[0].value != ""){
        var confirmacao = confirm("Você deseja apagar a solucao?");
        if (confirmacao) {
            $("#solucaoDoUsuario").val("");
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