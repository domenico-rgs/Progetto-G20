//metodo post al click in ogni elemento
var title

$('.item').on('click', function() {
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


$('img.searchImg').on('click', function() {
    title = $('#search').val()
    var ajax = $.ajax({
        type: 'GET',
        success: function(html)
        {
          window.location.href = "/catalog?search=" + title
        }
    });
    // Process success/failure here via ajax variable (http://api.jquery.com/jquery.ajax/)
});

$('#search').keyup(function(e){
    if(e.keyCode == 13)
    {
      title = $(this).val()
      var ajax = $.ajax({
          type: 'GET',
          success: function(html)
          {
            window.location.href = "/catalog?search=" + title
          }
      });
    }
});
