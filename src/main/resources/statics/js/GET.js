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

$('#showFilm').on('click', function() {
    var ajax = $.ajax({
       type: "GET",
       url: "/movieList",
       data: {
         startPoint: 0,
         finalPoint: 5
       },
       success : function(response)
       {
           $('.filmMenu').append(response);
       }
  })
});
