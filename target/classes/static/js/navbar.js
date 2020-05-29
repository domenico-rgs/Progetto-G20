window.onscroll = function()
{
  scrollFunction()
};

window.onload = function() {
  navOnLoad()
  navPagination();
};

var rgb = 'rgb(' + 30 + ',' + 63 + ',' + 97 + ',';
var element = document.getElementById("header");

// rende transparente la barra una volta entrato nel centro
function scrollFunction() {

if (document.documentElement.scrollTop >300) {
  element.style.backgroundColor = rgb + 1 + ')';
}
else {
  element.style.backgroundColor = rgb + 0 + ')';
}
};

// se la pagina si avvia in un posto differente dalla testa, rende la barra opaca

function navOnLoad() {

  if (document.documentElement.scrollTop >300) {
    element.style.backgroundColor = rgb + 1 + ')';
  }
};
