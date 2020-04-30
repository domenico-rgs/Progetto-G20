window.onscroll = function()
{
  scrollFunction()
};

var rgb = 'rgb(' + 30 + ',' + 63 + ',' + 97 + ',';
var element = document.getElementById("header");


// fa sicuramente schifo...lo sistemo in futuro
//quando torna su non torna trasparente

//magari si potrebbe lasciare la trasparenza a 0.75 e quando ci si passa sopra
//col mouse farla diventare 1
function scrollFunction() {

element.style.backgroundColor = rgb + 0.75 + ')';


switch (true) {
  case (document.documentElement.scrollTop > 380):
      element.style.backgroundColor = rgb + 0.65 + ')';
    break;

  case (document.documentElement.scrollTop > 190):
        element.style.backgroundColor = rgb + 0.60 + ')';
      break;

  case (document.documentElement.scrollTop > 130):
      element.style.backgroundColor = rgb + 0.5 + ')';
      break;

  case (document.documentElement.scrollTop > 50):
      element.style.backgroundColor = rgb + 0.2 + ')';
    break;
}
}
