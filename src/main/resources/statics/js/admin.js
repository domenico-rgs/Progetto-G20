var backItem;
var selectedItem;

function changeView() {

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
