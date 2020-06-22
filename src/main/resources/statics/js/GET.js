//metodo get al click di un film
var title

$(document).on('click','.item', function() {
    title = $(this).find('p').text()
    var ajax = $.ajax({
        type: 'GET',
        success: function(html)
        {
          window.location.href = "/movie?title=" + title
        }
    });
});


//script del catalogo (in futuro lo rendo scrollabile)
var startPoint = 0
var finalPoint = 5

// mostra piu film al click del pulsante
$('#showFilm').on('click', function() {
    var ajax = $.ajax({
       type: "GET",
       url: "/movieList",
       data: {
         startPoint: startPoint,
         finalPoint: finalPoint
       },
       success : function(response)
       {
           $('.filmMenu').append(response);
           startPoint += 5
           finalPoint += 5
       }
  })
});

// metodo get nella pagina index.html

$('#goToCatalog').on('click', function() {
    window.location.replace("/catalog?search=all");
});
