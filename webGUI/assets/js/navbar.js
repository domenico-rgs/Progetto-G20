window.onscroll = function()
{
  scrollFunction()
};

var rgb = 'rgb(' + 51 + ',' + 51 + ',' + 51 + ',';
var element = document.getElementById("header");


// fa sicuramente schifo...lo sistemo in futuro
function scrollFunction() {

element.style.backgroundColor = rgb + 0 + ')';


switch (true) {
  case (document.documentElement.scrollTop > 380):
      element.style.backgroundColor = rgb + 1 + ')';
    break;

  case (document.documentElement.scrollTop > 190):
        element.style.backgroundColor = rgb + 0.7 + ')';
      break;

  case (document.documentElement.scrollTop > 130):
      element.style.backgroundColor = rgb + 0.5 + ')';
      break;

  case (document.documentElement.scrollTop > 50):
      element.style.backgroundColor = rgb + 0.2 + ')';
    break;
}
}
