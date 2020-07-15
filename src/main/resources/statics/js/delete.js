//animazioni input
$('#ticket input').on('focus', function() {
  $('#ticket label').addClass('active');
}).on('blur', function() {
  if ($('#ticket input').val() == "") {
    $('#ticket label').removeClass('active');
  }
})

$('#card input').on('focus', function() {
  $('#card label').addClass('active');
}).on('blur', function() {
  if ($('#card input').val() == "") {
    $('#card label').removeClass('active');
  }
})

//post request al click del pulsante "DELETE"
$('#deleteButton').on('click', function() {
  $('.loader').css("visibility", "visible")
  var ajax = $.ajax({
    type: "POST",
    url: "/delete",
    data: {
      cardNumber: $('#card input').val(),
      ticketCode: $('#ticket input').val(),
    },
    success: function(response) {
      $('.loader').css("visibility", "hidden")
      $('#errorMess').text(response)
    }
  })
});