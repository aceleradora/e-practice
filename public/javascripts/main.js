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