//evitare problemi nel caso dal carrello tornassi indietro

$('#total .sconto button').on('click', function() {
  discoFunc()
});
$('#total .sconto input').on('keypress', function(e) {
  if (e.which == 13) {
    discoFunc();
  }
});

$('#total .card button').on('click', function() {
  buyFunc()
});


function discoFunc() {
  var ajax = $.ajax({
    type: "POST",
    url: "/shopCard",
    data: {
      action: "discount",
      code: $('#total .sconto input').val()
    },
    success: function(response) {

      //nel caso non funziona ritorno 0 dal server
      if (response == '0.0') {
        $('#total .sconto p').text("Code not avaliable")
      }
      if (response == '-1.0') {
        $('#total .sconto p').text("Code already used")
      } else {
        $('#total .sconto p').text("Code successfully applied")
        //cambio il prezzo visualizzato
        var price = $('#total .card #totalPrice').attr('price')
        price = parseFloat(price) - parseFloat(response)
        $('#total .card #totalPrice').text(price + " Euro")
        $('#total .card #discount').text("-" + response + " Euro discount applied")
      }

    }
  })
}

function buyFunc() {
  var ajax = $.ajax({
    type: "POST",
    url: "/shopCard",
    data: {
      action: "buy",
    },
    success: function(response) {

      //reindirizza da qualche parte
    }
  })
}