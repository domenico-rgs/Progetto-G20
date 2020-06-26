$(".seatList .seat[value='true']").on('click', function() {

  var elem = $(this)

  if (elem.attr('select') == 'false') {
    elem.attr("select", "true");
    elem.css("background-color", "green");
  } else {
    elem.attr("select", "false");
    elem.css("background-color", "white");
  }

});


//richiesta di get

$('#buyTicket').on('click', function() {


  $(".seatList .seat[select='true']").each(function() {
    var ajax = $.ajax({
      type: "POST",
      url: "/theatre",
      data: {
        seat: $(this).attr("pos")
      }
      success: function() {
        window.location.href = "/shopCard?" + $('#buyTicket').attr("value")
      }
    })
  })
})