$(document).ready(function(){
    fadeOutStatusBotao();

    $("#botao-de-limpar").click(function(){
        limpaASolucao();

    });

    $("#botao-de-enviar").click(function(){
            if($("#tab1 > .abas > pre").html() == "Você já resolveu todos os exercícios.") {
                  $("#botao-de-enviar").attr("disabled", "disabled");
            }
            else {
                $("#formCodigo").submit();
            }
        });

     var caixaDeTexto = $("#solucaoDoUsuario");
     if(caixaDeTexto[0].value == "") {
        $("#botao-de-limpar").attr("disabled", "disabled");
     }

    $("#botao-proximo-exercicio").click(function(){
        proximoExercicio();
    });

    $("#formCodigo").submit(function(){
        desabilitaBotao();
    });

    criarLinhas('solucaoDoUsuario');
});

document.onkeyup=function(e){
 var caixaDeTexto = $("#solucaoDoUsuario");
    if(caixaDeTexto[0].value != "") {

        $("#botao-de-limpar").prop("disabled", false);
    }

    var key = e.which || e.keyCode;



    if(key == 8) {

        if(caixaDeTexto[0].value == "") {

            $("#botao-de-limpar").prop("disabled", true);
        }
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