var open = true;

$('button').on('click', function() {
  if (open) {
    open = false;
    $('.showingTable').addClass('active');
  } else {
    open = true;
    $('.showingTable').removeClass('active');
  }
});

//get requests


$('.showingTable #buy').on('click', function() {
  var title = $('.description #title').text()
  var id = $(this).attr("value")
  window.location.href = "/theatre?id=" + id + "&title=" + title;
})