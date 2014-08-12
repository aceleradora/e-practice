   var linhaSemTop = 2;

   function criarLinhas(id)
   {
      var criaElemento = document.createElement('DIV');
      var setaCss = document.getElementById(id);
      setaCss.parentNode.insertBefore(criaElemento,setaCss);
      criaElemento.appendChild(setaCss);

      criaElemento.className='textAreaComLinhas';
      criaElemento.style.width = (setaCss.offsetWidth + 30) + 'px';
      setaCss.style.position = 'absolute';
      setaCss.style.left = '40px';
      criaElemento.style.height = (setaCss.offsetHeight + 2) + 'px';
      criaElemento.style.overflow='hidden';
      criaElemento.style.position = 'relative';
      criaElemento.style.width = (setaCss.offsetWidth + 30) + 'px';

      var objLinha = document.createElement('DIV');
      objLinha.style.position = 'absolute';
      objLinha.style.top = linhaSemTop + 'px';
      objLinha.style.left = '0px';
      objLinha.style.width = '27px';
      criaElemento.insertBefore(objLinha,setaCss);
      objLinha.style.textAlign = 'right';
      objLinha.className='linhaObj';
      var string = '';
      for(var no=1;no<1000;no++){
         if(string.length>0)string = string + '<br>';
         string = string + no;
      }

      setaCss.onkeydown = function() { setaPosicaoLinha(objLinha,setaCss); };
      setaCss.onmousedown = function() { setaPosicaoLinha(objLinha,setaCss); };
      setaCss.onscroll = function() { setaPosicaoLinha(objLinha,setaCss); };
      setaCss.onblur = function() { setaPosicaoLinha(objLinha,setaCss); };
      setaCss.onfocus = function() { setaPosicaoLinha(objLinha,setaCss); };
      setaCss.onmouseover = function() { setaPosicaoLinha(objLinha,setaCss); };
      objLinha.innerHTML = string;

   }

   function setaPosicaoLinha(obj,setaCss)
   {
      obj.style.top = (setaCss.scrollTop * -1 + linhaSemTop) + 'px';

   }

