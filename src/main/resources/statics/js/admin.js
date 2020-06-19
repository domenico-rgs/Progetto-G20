var backItem;
var selectedItem;

window.onload = function() {
  changeView();
};

window.onresize = function() {
  changeView();
};

function changeView() {


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



  if (backItem != undefined) {
      $(backItem).css("height", "0");
  }

  $(selectedItem).css("height", "auto");

}

$('.sideMenu a').click(function() {
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
