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
       url: "/movieItem",
       success : function(response)
       {
           $('.filmMenu').append(response);
       }
  })
});
