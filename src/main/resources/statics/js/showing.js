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