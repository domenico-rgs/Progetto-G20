//metodo get al click di un film
var title

$(document).on('click','.item', function() {
    title = $(this).find('p').text()
    window.location.href = "/movie?title=" + title
});

//////////////////////////////////////////

//CATALOGO (in futuro lo rendo scrollabile)


$('#searchMovie').on('keypress', function(e) {
  if (e.which == 13) {
    title = $(this).val()
    window.location.href = "/movie?title=" + title
  }
});

$('#searchImg').on('click', function(e) {
  title = $('#searchMovie').val()
  window.location.href = "/movie?title=" + title
});

//////////////////////////////////////////////////

// INDEX

$('#goToCatalog').on('click', function() {
    window.location.href = "/catalog?search=all";
});
