$(document).ready(function(){
    $("textarea#solucaoDoUsuario").keydown(function(event){
        quandoPressionaTab(event, this);
    });
});

function quandoPressionaTab(event, textarea){
    if(event.keyCode == 9 || event.which == 9 )
    {
        event.preventDefault();
        pulaQuatroEspacos(event, textarea);
    }
}

function pulaQuatroEspacos(event, textarea){
    var cursorEm = textarea.selectionStart;
    var finalTextarea = textarea.textLength;

    $(textarea).val( $(textarea).val().substring(0, cursorEm) + "    " + $(textarea).val().substring(cursorEm, finalTextarea));
    textarea.selectionStart = cursorEm + 4;
    textarea.selectionEnd = cursorEm + 4;
}