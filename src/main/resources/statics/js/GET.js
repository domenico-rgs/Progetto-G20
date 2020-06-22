//metodo post al click in ogni elemento
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
    // Process success/failure here via ajax variable (http://api.jquery.com/jquery.ajax/)
});

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
