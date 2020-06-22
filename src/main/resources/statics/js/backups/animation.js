// script per animazioni varie

$(window).on('load', animation() );

function animation (){
  $('#banner .inner h1').animate({
    "left": "0px"
  }, 700);

  $('#banner .inner h2').animate({
    "right": "0px"
  }, 700);

  $('#content .loginImg:first-child').animate({
    "left": "-10px"
  }, 700);


  $('#content .loginImg:nth-child(2)').animate({
    "right": "20px"
  }, 700);


}
