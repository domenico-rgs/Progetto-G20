window.onscroll = function()
{
  scrollFunction()
};

window.onload = function() {
  navOnLoad();
};

var rgb = 'rgb(' + 30 + ',' + 63 + ',' + 97 + ',';
var navbar = document.getElementById("header");

// rende transparente la barra una volta entrato nel centro
function scrollFunction() {

if (document.documentElement.scrollTop >100) {
  navbar.style.backgroundColor = rgb + 1 + ')';
}
else {
  navbar.style.backgroundColor = rgb + 0 + ')';
}
};

// se la pagina si avvia in un posto differente dalla testa, rende la barra opaca

function navOnLoad() {

  if (document.documentElement.scrollTop < 100) {
    navbar.style.backgroundColor = rgb + 0 + ')';
  }
};
