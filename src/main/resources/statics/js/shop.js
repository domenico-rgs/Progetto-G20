var page = 1
var element
var container

window.onload = function() {
  startPage()
}

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
      codeCard: $('.cardNumber input').val(),
      date: $('.cardNumber input:first-child').val() + "/" + $('.cardNumber input:last-child').val(),
      cvv: $('.cvv input').val(),
      email: $('.insertEmail input').val()
    },
    success: function(response) {
      if (response == "true") {
        $('#total #buyMess').text("Acquistato con successo")
      }
      if (response == "false") {
        $('#total #buyMess').text("Acquistato non completato, verifica i dati")
      } else {
        $('#total #buyMess').text(response)
      }
      //faccio qualcosa
    }
  })
}

//getione delle pagine

function setPageValue() {
  element = "li[value='" + page + "']"
}

function setContainer() {
  container = '#' + $(element).attr('text')
}

function startPage() {
  setPageValue()
  setContainer()
  $(element).addClass('active')
  $(container).addClass('active')
}

$('.buttons button[name="back"]').on('click', function() {
  if (page == 1) {
    return
  }

  $(element).removeClass('active')
  $(container).removeClass('active')
  page -= 1
  setPageValue()
  setContainer()
  $(element).addClass('active')
  $(container).addClass('active')

})

$('.buttons button[name="continue"]').on('click', function() {
  if (page == 4) {
    return
  }

  $(element).removeClass('active')
  $(container).removeClass('active')
  page += 1
  setPageValue()
  setContainer()
  $(element).addClass('active')
  $(container).addClass('active')
})