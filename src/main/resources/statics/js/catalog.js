var loadMess = $('#loadMess #mess')
var loader = $('#loadMess .loader')

var startPoint = 0
var finalPoint = 20

$(window).on('load', function() {


  var ajax = $.ajax({
    type: "GET",
    url: "/movieList",
    data: {
      startPoint: startPoint,
      finalPoint: finalPoint
    },
    success: function(response) {
      loadMess.remove()
      loader.remove()

      $('.filmMenu').append(response);
      startPoint += 10
      finalPoint += 10
    }
  })
});

var scrollPoint = 100
var scrollAdd = $(window).height / 1

// mostra piu film al click del pulsante
$(window).on('scroll', function() {

  if ($(window).scrollTop() > scrollPoint) {
    scrollPoint += scrollAdd
    $('#loadMess').append(loader)
    $('#loadMess').append(loadMess)

    var ajax = $.ajax({
      type: "GET",
      url: "/movieList",
      data: {
        startPoint: startPoint,
        finalPoint: finalPoint
      },
      success: function(response) {
        loadMess.remove()
        loader.remove()

        $('.filmMenu').append(response);
        startPoint += 5
        finalPoint += 5
      }
    })
  }
});

// label animation
$('.searchBar input').on('focus', function() {
  $('.searchBar label').addClass('active');
}).on('blur', function() {
  if ($('.searchBar input').val() == "") {
    $('.searchBar label').removeClass('active');
  }
})

//get requests
$(document).on('click', '.item', function() {
  var title = $(this).find('p').text()
  window.location.href = "/movie?title=" + title
});

$('#searchMovie').on('keypress', function(e) {
  if (e.which == 13) {
    var title = $(this).val()
    window.location.href = "/catalog?search=" + title
  }
});

$('#searchImg').on('click', function() {
  var title = $('#searchMovie').val()
  window.location.href = "/catalog?search=" + title
});