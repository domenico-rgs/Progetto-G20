window.onscroll = function()
{
  scrollFunction()
};

var rgb = 'rgb(' + 30 + ',' + 63 + ',' + 97 + ',';
var element = document.getElementById("header");


function scrollFunction() {

if (document.documentElement.scrollTop >300) {
  element.style.backgroundColor = rgb + 1 + ')';
}
else {
  element.style.backgroundColor = rgb + 0 + ')';
}
};
