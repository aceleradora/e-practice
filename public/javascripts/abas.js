$(document).ready(function() {
	ajaxSetandoAbaAtual();
	eventoDeClickNasAbas();
});

function eventoDeClickNasAbas () {
    $('ul.tabs').each(function(){
        var labelAbaAtiva;
        var links = $(this).find('a');
        labelAbaAtiva = $($("ul.tabs a.active")[0] || links[0]);

        links.not(labelAbaAtiva).each(function ( ) {
            $(this.hash).hide();
        });

        $(this).on('click', 'a', function(event){
            disableActives(this);
            reloadActiveTabs(this, event);
        });
    });
}

function reloadActiveTabs(aba, event){
    var active = $(aba);
    var content = $(active[0].hash);

    active.addClass('active');
    content.show();

    event.preventDefault();
}

function disableActives(aba){
    $("#painel .tabs a").each(function(){
        $(this).removeClass('active');
        $(this.hash).hide();
    });
}

function ajaxSetaRota(abaAtiva){
    $.ajax({
        url: "solucoes/aba-default/" + abaAtiva,
        xhrFields: {
            withCredentials: true
        }
    });
}

function ajaxSetandoAbaAtual() {
	$("#painel .tabs a").click(function(){
        ajaxSetaRota(this.id);        
    });
}