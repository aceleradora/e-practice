function quandoPressionaTab(event){
    var eventCustom;

    if(event.keyCode == 9 || event.which == 9 )
    {
        event.preventDefault();
        pulaQuatroEspacos(event);
    }
}

function pulaQuatroEspacos(event){
    var element = event.target;
    var cursorEm = document.querySelector("#"+element.id).selectionStart;
    var finalTextarea = document.querySelector("#"+element.id).textLength;

    $(element).val( $(element).val().substring(0, cursorEm) + "    " + $(element).val().substring(cursorEm, finalTextarea) );
    document.querySelector("#"+element.id).selectionStart = cursorEm+4;
    document.querySelector("#"+element.id).selectionEnd = cursorEm+4;
}

$(document).ready(function(){
    $("#botaoDeEnviar").click(function(){
        $(this).attr("disabled", "disabled");
        $("#formCodigo").submit();
    });
});

$(document).ready(function(){
    $('ul.tabs').each(function(){
        var labelAbaAtiva, abaAQualDeveSerMostradoOConteudo;
        var links = $(this).find('a');
        labelAbaAtiva = $($("ul.tabs a.active")[0] || links[0]);

        abaAQualDeveSerMostradoOConteudo = $(labelAbaAtiva[0].hash);

        links.not(labelAbaAtiva).each(function ( ) {
            $(this.hash).hide();
        });

        $(this).on('click', 'a', function(e){
            disableActives(this);
            reloadActiveTabs(this);
        });
    });
});

function reloadActiveTabs(aba){
    active = $(aba);
    content = $(active[0].hash);

    active.addClass('active');
    content.show();

    e.defaultPrevented();
}

function disableActives(aba){
    $("#painel .tabs a").each(function(){
        $(this).removeClass('active');
        $(this.hash).hide();
    });
}

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

    function testaBotao() {

        $("#tabLink3").addClass('active');
        $("#tabLink1").removeClass('active');
        $("#tabLink2").removeClass('active');
        $("#tab1").hide();
        $("#tab2").hide();
        $("#tabLink3").click();
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

$(document).ready(function() {
    if($('#status').html() != '') {
        $('#status').val("")
        $('#status').fadeOut(5000);
        $('#mensagemDeTexto').append("<label id='status'></label>");
    }

    $("#painel .tabs a").click(function(){
        $.ajax({
            url: "solucoes/aba-default/" + this.id,
            xhrFields: {
                withCredentials: true
            }
        });
    });
});