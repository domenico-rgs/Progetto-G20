var open = true;

//listener class "AVALIABLE SHOW"
$('button').on('click', function() {
  if (open) {
    open = false;
    $('.showingTable').addClass('active');
  } else {
    open = true;
    $('.showingTable').removeClass('active');
  }
});

//listener del pulsante BUY nella scelta delle proiezioni dei film
$('.showingTable #buy').on('click', function() {
  var title = $('.description #title').text()
  var id = $(this).attr("value")
  window.location.href = "/theatre?id=" + id + "&title=" + title;
})