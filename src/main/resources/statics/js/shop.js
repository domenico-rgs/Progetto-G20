//evitare problemi nel caso dal carrello tornassi indietro

$('#total .sconto button').on('click', function() {
  discoFunc()
});
$('#total .sconto input').on('keypress', function(e) {
  if (e.which == 13) {
    discoFunc();
  }
});


function discoFunc() {
  var ajax = $.ajax({
    type: "POST",
    url: "/shopCard",
    data: {
      code: $('#total .sconto input').val()
    },
    success: function(response) {
      //faccio qualcosa per cambiare il prezzo restituito

      //nel caso non funziona ritorno 0 dal server
      if (response == '0') {
        $('#total .sconto p').text("Code not avaliable")
      } else {
        $('#total .sconto p').text("Succefully applied with -" + response)
      }

    }
  })
}