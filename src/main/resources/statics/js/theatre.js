$(".seatList .seat[value='free']").on('click', function() {

  var elem = $(this)

  if (elem.attr('select') == 'false') {

    elem.attr("select", "true");
    elem.css("background-color", "green");
  } else {
    elem.attr("select", "false");
    elem.css("background-color", "white");
  }

});