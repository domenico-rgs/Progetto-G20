var loadMess = $('#loadMess #mess')
var loader = $('#loadMess .loader')

$(window).on('load', function() {

  //svuoto per evitare duplicazioni
  $('filmMenu').empty()

  var ajax = $.ajax({
    type: "GET",
    url: "/movieList",
    success: function(response) {
      loadMess.remove()
      loader.remove()

      $('.filmMenu').append(response);
    }
  })
});

var scrollPoint = 100
var scrollAdd = $(window).height / 1

// mostra piu film allo scroll
$(window).on('scroll', function() {

  if ($(window).scrollTop() > scrollPoint) {
    scrollPoint += scrollAdd
    $('#loadMess').append(loader)
    $('#loadMess').append(loadMess)

    var ajax = $.ajax({
      type: "GET",
      url: "/movieList",
      success: function(response) {
        loadMess.remove()
        loader.remove()

        $('.filmMenu').append(response);
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

//get requests nelle richieste di ricerca nella barra
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

//ricarica la pagina al back del browser
window.addEventListener("pageshow", function(event) {
  var historyTraversal = event.persisted ||
    (typeof window.performance != "undefined" &&
      window.performance.navigation.type === 2);
  if (historyTraversal) {
    // Handle page restore.
    window.location.reload();
  }
});