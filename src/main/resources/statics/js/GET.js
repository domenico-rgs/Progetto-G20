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

$(document).on('click','#loginBtn', function() {
    var user = $('#user').val()
    var pass = $('#pass').val()
    var ajax = $.ajax({
        type: 'POST',
        url: "/index",
        async: false,
        data: {
          username: user,
          password: pass
        },
        success: function(response)
        {
          if(response == "FALSE") {
            $('#wrong').css("visibility", "visible");
          }
          else {
            
          }
        }
    });
    // Process success/failure here via ajax variable (http://api.jquery.com/jquery.ajax/)
});

$(document).on('click','#newBtn', function() {
  var ajax = $.ajax({
      type: 'POST',
      success: function(response)
      {
        window.location.href = "/createAccount"
      }
  });
});
