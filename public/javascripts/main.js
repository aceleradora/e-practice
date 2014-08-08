$(document).ready(function(){
    $("#botaoDeEnviar").click(function(){
        $(this).attr("disabled", "disabled");
        $("#formCodigo").submit();
    });
});

$("#botaoDeLimpar").click(function () {
    var btn = this;
    setTimeout(function () { $(btn).attr('disabled', 'disabled'); }, 1);
    return true;
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

$(document).ready(alttela);

$(window).resize(alttela);
function alttela() {
    if ($(window).width() > $('#terminal').width()){
        $(document).css('position', 'fixed');
    }
    else{
        $(document).css('position', 'relative');
    };
};

function desabilitaBotao() {
    $("#botaoDeEnviar").attr("disabled");
};

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) != -1) return c.substring(name.length,c.length);
    }
    return "";
}