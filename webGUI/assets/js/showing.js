var open = true;

$('button').on('click', function()
{
  if(open) {
  open = false;
  $('.prova').addClass('active');
}
  else {
  open = true;
  $('.prova').removeClass('active');
  }
});
