var loadMess = $('#loadMess #mess')
var loader = $('#loadMess .loader')

var startPoint = 0
var finalPoint = 5

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
      startPoint += 5
      finalPoint += 5
    }
  })
});

var scrollPoint = 100
var scrollAdd = $(window).height / 3

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
  $('.searchBar label').removeClass('active');
  $('.searchBar input').val("");
})