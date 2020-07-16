// animazioni dei click dei posti a sedere
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


//richiesta di get al click del "BUY"
$('#buyTicket').on('click', function() {

  var seats = ""

  $(".seatList .seat[select='true']").each(function() {

    seats = seats + $(this).attr("pos") + "-"
  })

  //se non seleziono niente, non eseguo la richiesta di get
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

//ricarica la pagina al back del browser
window.addEventListener("pageshow", function(event) {
  var historyTraversal = event.persisted ||
    (typeof window.performance != "undefined" &&
      window.performance.navigation.type === 2);
  if (historyTraversal) {
    // Handle page restore.
    window.location.reload();
  }
});