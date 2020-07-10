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

  var seats

  $(".seatList .seat[select='true']").each(function() {

    seats = seats + $(this).attr("pos") + "-"
  })


  var ajax = $.ajax({
    type: "POST",
    url: "/theatre",
    data: {
      seat: seats,
      id: $('title').attr("idvalue")
    },
    success: function() {
      window.location.href = "/shopCard"
    }
  })

})