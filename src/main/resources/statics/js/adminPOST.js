/* dentro questo script vengono eseguite tutte le richieste
  di POST da parte dei click degli elementi nella pagina di administrator,
  che tramite il parametro requestPost verranno reindirizzati verso
  gli Handler corrispondenti.
  La risposta ritorna se la richiesta è andata a buon fine o l'errore
  presentatosi
*/

// add theatre
$('#addTheatre #add').on('click', function() {
  $('#addTheatre .loader').css("visibility", "visible")
  var ajax = $.ajax({
    type: "POST",
    url: "/administrator",
    data: {
      requestPost: "AddItem",
      object: "theatre",
      name: $('#addTheatre #name').val(),
      config: $('#addTheatre #config').val(),
    },
    success: function(response) {
      $('#addTheatre .loader').css("visibility", "hidden")
      $('#addTheatre .message').text(response)
      reloadGeneral()
    }
  })
});

//remove theatre
$('#deleteTheatre #remove').on('click', function() {
  $('#deleteTheatre .loader').css("visibility", "visible")
  var ajax = $.ajax({
    type: "POST",
    url: "/administrator",
    data: {
      requestPost: "DeleteItem",
      object: "theatre",
      name: $('#deleteTheatre #theatre').val(),
    },
    success: function(response) {
      $('#deleteTheatre .loader').css("visibility", "hidden")
      $('#deleteTheatre .message').text(response)
      reloadGeneral()
    }
  })
});

//add showing
$('#addShowing #add').on('click', function() {
  $('#addShowing .loader').css("visibility", "visible")
  var ajax = $.ajax({
    type: "POST",
    url: "/administrator",
    data: {
      requestPost: "AddItem",
      object: "showing",
      movie: $('#addShowing #movie').val(),
      theatre: $('#addShowing #theatre').val(),
      date: $('#addShowing #date').val(),
      hour: $('#addShowing #hour').val(),
      price: $('#addShowing #price').val()
    },
    success: function(response) {
      $('#addShowing .loader').css("visibility", "hidden")
      $('#addShowing .message').text(response)
      reloadGeneral()
    }
  })
});

//add multiple Showings
$('#multiShowings #add').on('click', function() {
  $('#multiShowings .loader').css("visibility", "visible")
  var ajax = $.ajax({
    type: "POST",
    url: "/administrator",
    data: {
      requestPost: "AddItem",
      object: "multiShowing",
      movie: $('#multiShowings #movie').val(),
      theatre: $('#multiShowings  #theatre').val(),
      dateStart: $('#multiShowings  #dateStart').val(),
      dateFinal: $('#multiShowings  #dateFinal').val(),
      hour: $('#multiShowings  #hour').val(),
      price: $('#multiShowings  #price').val()
    },
    success: function(response) {
      $('#multiShowings .loader').css("visibility", "hidden")
      $('#multiShowings .message').text(response)
      reloadGeneral()
    }
  })
});


//edit showing
$('#editShowing #idS').on('change', function() {
  $('#editShowing .loader').css("visibility", "visible")
  var ajax = $.ajax({
    type: "POST",
    url: "/administrator",
    data: {
      requestPost: "GetShowingInf",
      action: "getINF",
      id: $('#editShowing #idS').val()
    },
    success: function(response) {
      // 0: theatre
      // 1: Price
      // 2: date
      var params = response.split("@")

      $('#editShowing .loader').css("visibility", "hidden")
      if (params.length == 1) {
        $('#editShowing .message').text(response)
      }
      if (params[0] == "Error") {
        $('#editShowing .message').text(params[1])
      } else {
        $('#editShowing #theatre').val(params[0])
        $('#editShowing #price').val(params[1])
        $('#editShowing #date').val(params[2])
      }
    }
  })
});

$('#editShowing #movie').on('change', function() {
  $('#editShowing .loader').css("visibility", "visible")
  var ajax = $.ajax({
    type: "POST",
    url: "/administrator",
    data: {
      requestPost: "GetShowingInf",
      action: "getID",
      title: $('#editShowing #movie').val(),
    },
    success: function(response) {
      $('#editShowing #idList').empty()
      $('#editShowing #idS').attr('placeholder', 'ID');

      //lista di id disponibili, da splittare
      var idList = response.split("@")
      var element
      $('#editShowing .loader').css("visibility", "hidden")

      if (idList.length == 0) {
        $('#editShowing #idS').attr('placeholder', 'Nothing ID');
        return;
      }

      idList.forEach(function(item, index) {
        //item è il mio elemento
        element = '<option value=' + item + '>' + item + '</option>'
        $('#editShowing #idList').append(element)
      })

    }
  })
});

$('#editShowing #remove').on('click', function() {
  $('#editShowing .loader').css("visibility", "visible")
  var ajax = $.ajax({
    type: "POST",
    url: "/administrator",
    data: {
      requestPost: "DeleteItem",
      object: "showing",
      id: $('#editShowing  #idS').val(),
    },
    success: function(response) {
      $('#editShowing .loader').css("visibility", "hidden")
      $('#editShowing .message').text(response)
      reloadGeneral()
    }
  })
});


//add movie
$('#addMovie #add').on('click', function() {
  $('#addMovie .loader').css("visibility", "visible")
  var ajax = $.ajax({
    type: "POST",
    url: "/administrator",
    data: {
      requestPost: "AddItem",
      object: "movie",
      title: $('#addMovie #movie').val(),
      duration: $('#addMovie #duration').val(),
      plot: $('#addMovie #plot').val(),
      cover: $('#addMovie #cover').val(),
      category: $('#addMovie #category').find(":selected").text()
    },
    success: function(response) {
      $('#addMovie .loader').css("visibility", "hidden")
      $('#addMovie .message').text(response)
      reloadGeneral()
    }
  })
});


//edit movie
$('#editMovie #editM').on('click', function() {
  $('#editMovie .loader').css("visibility", "visible")
  var ajax = $.ajax({
    type: "POST",
    url: "/administrator",
    data: {
      requestPost: "EditItem",
      object: "movie",
      title: $('#editMovie #movie').val(),
      plot: $('#editMovie #plot').val(),
      cover: $('#editMovie #cover').val(),
      category: $('#editMovie #category').find(":selected").text()
    },
    success: function(response) {
      $('#editMovie .loader').css("visibility", "hidden")
      $('#editMovie .message').text(response)
      reloadGeneral()
    }
  })
});

//edit movie
$('#editMovie #movie').on('change', function() {
  $('#editMovie .loader').css("visibility", "visible")
  var ajax = $.ajax({
    type: "POST",
    url: "/administrator",
    data: {
      requestPost: "GetMovieInf",
      title: $('#editMovie #movie').val(),
    },
    success: function(response) {
      // per ora cosi, sicuramente c'è di meglio (invio parametri splittando la stringa)
      // 0: titolo
      // 1: Durata
      // 2: Plot
      var params = response.split("@")
      $('#editMovie .loader').css("visibility", "hidden")

      if (params[0] == "Error") {
        $('#editMovie .message').text(params[1])
      } else {
        $('#editMovie #title').val(params[0])
        $('#editMovie #duration').val(params[1])
        $('#editMovie #plot').val(params[2])
        $('#editMovie #cover').val(params[3])
        $('#editMovie #category').val(params[4])
      }

    }
  })
});

$('#editMovie #remove').on('click', function() {
  $('#editMovie .loader').css("visibility", "visible")
  var ajax = $.ajax({
    type: "POST",
    url: "/administrator",
    data: {
      requestPost: "DeleteItem",
      object: "movie",
      title: $('#editMovie #movie').val(),
    },
    success: function(response) {
      $('#editMovie .loader').css("visibility", "hidden")
      $('#editMovie .message').text(response)
      reloadGeneral()
    }
  })
});

//discounts actions
$('#discounts #add').on('click', function() {
  $('#discounts .loader').css("visibility", "visible")
  var ajax = $.ajax({
    type: "POST",
    url: "/administrator",
    data: {
      requestPost: "Discount",
      action: "save",
      code: $('#discounts #code').val(),
      value: $('#discounts #value').val(),
    },
    success: function(response) {
      $('#discounts .loader').css("visibility", "hidden")
      $('#discounts .message').text(response)
    }
  })
});


$('#discounts #remove').on('click', function() {
  $('.loader').css("visibility", "visible")
  var ajax = $.ajax({
    type: "POST",
    url: "/administrator",
    data: {
      requestPost: "Discount",
      action: "remove",
      code: $('#discounts #discoCode').val(),
    },
    success: function(response) {
      $('.loader').css("visibility", "hidden")
      $('#discounts .message').text(response)
    }
  })
});

//password check
$('.passDiv #home').on('click', function(e) {
  window.location.href = "/home"
});

$('.passDiv input').on('keypress', function(e) {
  if (e.which == 13) {
    passCheck();
  }
});

$('.passDiv #check').on('click', function(e) {
  passCheck();
});

function passCheck() {
  var ajax = $.ajax({
    type: "POST",
    url: "/administrator",
    data: {
      requestPost: "PassCheck",
      passText: $('.passDiv input').val()
    },
    success: function(response) {
      if (response == "true") {

        // animazioni di entrata
        $('#passInsert').animate({
          "opacity": "0"
        }, 300).css("visibility", "hidden")
        $('#three').animate({
          "opacity": "1"
        }, 1000).css("visibility", "visible")
        entered = true

        //per il Menu$('.sideMenu').animate({
        $('.sideMenu').animate({
          "left": "0px"
        }, 500);
      }
      if (response == "false") {
        $('.passDiv p').text("Incorrect password. Try again")
      }
    }
  })
}


// ogni volta che modifico qualcosa aggiorno la pagina generale in backGround
function reloadGeneral() {
  $('#general').empty()
  var ajax = $.ajax({
    type: "POST",
    url: "/administrator",
    data: {
      requestPost: "LoadGeneral",
    },
    success: function(response) {
      $('#general').append(response)
    }
  })

}