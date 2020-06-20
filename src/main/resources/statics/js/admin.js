var backItem;
var selectedItem;

$(window).on('load', sideMenuSwitch() );
//ho levato window rezise per motivi di performance
// e perche non funzionava....cercare un'implementazione migliore

// comparsa e scomparsa menu laterale

function sideMenuSwitch() {

  if ($(window).width() > 600){
    $('.sideMenu').animate({
      "left": "0px"
    }, 500);
  }

  if ($(window).width() < 600){
    $('.sideMenu').animate({
      "left": "-200px"
    }, 500);
  }

}
function changeView() {

  if (backItem != undefined) {
      $(backItem).css("height", "0");
  }

  $(selectedItem).css("height", "auto");

}

$('.sideMenu li').click(function() {
  $(this).addClass('active').siblings().removeClass('active');

  backItem = selectedItem;
  selectedItem = '.' + $(this).attr("value");

  changeView();
});



$('#sideButton').click(function()
{
  $('.sideMenu').animate({
    "left": "0px"
  }, 500);
});

$('#closeButton').click(function()
{
  $('.sideMenu').animate({
    "left": "-200px"
  }, 500);
});
