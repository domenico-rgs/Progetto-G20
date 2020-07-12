$('#ticket input').on('focus', function() {
  $('#ticket label').addClass('active');
}).on('blur', function() {
  $('#ticket label').removeClass('active');
  $('#ticket input').val("");
})

$('#card input').on('focus', function() {
  $('#card label').addClass('active');
}).on('blur', function() {
  $('#card label').removeClass('active');
  $('#card input').val("");
})

$('#deleteButton').on('click', function() {
  var ajax = $.ajax({
    type: "POST",
    url: "/delete",
    data: {
      cardNumber: $('#card input').val(),
      ticketCode: $('#ticket input').val(),
    },
    success: function(response) {
      $('#errorMess').text(response)
    }
  })
});