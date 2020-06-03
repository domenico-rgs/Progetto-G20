//metodo post al click in ogni elemento

$('.item').on('click', function() {
    var ajax = $.ajax({
        type: 'GET',
        url: '/movie',
        data: { title: $(this).find('p').text()},
        success: function(html)
        {
          window.location.href = "/movie"
        }
    });
    // Process success/failure here via ajax variable (http://api.jquery.com/jquery.ajax/)
});
