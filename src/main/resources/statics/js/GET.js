//metodo get al click di un film

$(document).on('click', '.item', function() {
  var title = $(this).find('p').text()
  window.location.href = "/movie?title=" + title
});


$('#searchMovie').on('keypress', function(e) {
  if (e.which == 13) {
    var title = $(this).val()
    window.location.href = "/catalog?search=" + title
  }
});

$('#searchImg').on('click', function(e) {
  var title = $('#searchMovie').val()
  window.location.href = "/catalog?search=" + title
});