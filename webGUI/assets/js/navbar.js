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

//per i tasti di pagination numerati
$('.pagination a').click(function() {
  $(this).addClass('active').siblings().removeClass('active');
});

//effeto di paginazione nei tasti della navabar
function navPagination() {

  var path = window.location.pathname;
  var page = path.split("/").pop();

  switch (page) {
    case "index":

      break;
    case "faq":

    break;
    default:

  }
}
