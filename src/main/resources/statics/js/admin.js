var backItem;
var selectedItem;
var entered = false

$(window).on('load', function() {

  $('.passDiv').animate({
    "opacity": "1"
  }, 700);

  // selectedItem = '#introduzione';
  //
  // changeView();

  sideMenuSwitch();
});
//ho levato window rezise per motivi di performance
// e perche non funzionava....cercare un'implementazione migliore

// comparsa e scomparsa menu laterale

function sideMenuSwitch() {

  if ($(window).width() > 600) {
    $('.sideMenu').animate({
      "left": "0px"
    }, 500);
  }

  if ($(window).width() < 600) {
    $('.sideMenu').animate({
      "left": "-200px"
    }, 500);
  }

  selectedItem = '#general';
  $('.sideMenu .menu ul li:first-child').addClass('active')

  changeView();

}

function changeView() {

  if (backItem != undefined) {
    $(backItem).css("height", "0");
    $(backItem).css("border-bottom", "none");
  }

  $(selectedItem).css("height", "auto").slideDown(1000);
  $(selectedItem).css("border-bottom", "1px solid rgb(30,63,97)");

}

$('.sideMenu li').click(function() {
  $(this).addClass('active').siblings().removeClass('active');

  backItem = selectedItem;
  selectedItem = '#' + $(this).attr("value");

  changeView();
});



$('#sideButton').click(function() {
  $('.sideMenu').animate({
    "left": "0px"
  }, 500);
});

$('#closeButton').click(function() {
  $('.sideMenu').animate({
    "left": "-15%"
  }, 500);
});