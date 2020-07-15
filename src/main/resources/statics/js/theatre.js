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

  var seats = ""

  $(".seatList .seat[select='true']").each(function() {

    seats = seats + $(this).attr("pos") + "-"
  })

  if (seats == "") {
    $('#error').text("Please select valid seat")
    return
  }


  var ajax = $.ajax({
    type: "POST",
    url: "/theatre",
    data: {
      seat: seats,
      id: $('title').attr("idvalue")
    },
    success: function(resp) {

      if (resp == "Error") {
        $('#error').text("Error with server")
        return
      }

      window.location.href = "/shopCart?shopID=" + resp
    }
  })

})