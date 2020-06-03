//metodo post al click in ogni elemento

$('.item').on('click', function() {
    var ajax = $.ajax({
        type: 'POST',
        url: '/movieInformation',
        data: { title: $(this).find('p').text(),
                saveMovie: "true"},
        success: function(html)
        {
          window.location.href = "/movieInformation"
        }
    });
    // Process success/failure here via ajax variable (http://api.jquery.com/jquery.ajax/)
});
