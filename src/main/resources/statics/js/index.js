// NAVBAR SCRIPTS

window.onscroll = function()
{
  scrollFunction()
};

window.onload = function() {
  navOnLoad();
  animation();
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

// se la pagina si avvia in testa, rende la pagina transparente
function navOnLoad() {

  if (document.documentElement.scrollTop < 100) {
    navbar.style.backgroundColor = rgb + 0 + ')';
  }
};

// BACKGROUND IMAGE SCRIPT

// automatic way to change background img on index.HTML
//every 5 o 10 seconds

var imgList = ["../statics/images/background/back0.jpg",
                "../statics/images/background/back1.jpg",
                "../statics/images/background/back2.jpg",
                "../statics/images/background/back3.jpg",
                "../statics/images/background/back4.jpg"];

var currentImage;
var index = 1;
var element = document.getElementById('banner')


function changeImage(){
  if(index == 5) {
    index = 0;
  }

  currentImage = imgList[index];

  element.style.backgroundImage = "url(" + currentImage + ")";
  index++;

};

setInterval("changeImage()",5000);


// ANIMAZIONI

function animation (){
  $('#banner .inner h1').animate({
    "left": "0px"
  }, 700);

  $('#banner .inner h2').animate({
    "right": "0px"
  }, 700);
}
