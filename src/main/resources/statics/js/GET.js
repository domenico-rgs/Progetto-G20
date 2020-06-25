//metodo get al click di un film
var title

$(document).on('click', '.item', function() {
  title = $(this).find('p').text()
  window.location.href = "/movie?title=" + title
});

//////////////////////////////////////////

//CATALOGO

$('#searchMovie').on('keypress', function(e) {
  if (e.which == 13) {
    title = $(this).val()
    window.location.href = "/catalog?search=" + title
  }
});

$('#searchImg').on('click', function(e) {
  title = $('#searchMovie').val()
  window.location.href = "/catalog?search=" + title
});

//////////////////////////////////////////////////

// INDEX

$('#goToCatalog').on('click', function() {
  window.location.href = "/catalog?search=all";
});

/////////////////////////////////////////////////////

// MOVIE INFORMATION

$('.showingTable #buy').on('click', function() {
  title = $('.description #title').text()
  var id = $(this).attr("value")
  window.location.href = "/theatre?id=" + id + "&title=" + title;
})

////////////////////////////////