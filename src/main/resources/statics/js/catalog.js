$(window).on('load', function() {
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

var startPoint = 0
var finalPoint = 5
var scrollPoint = 100
var scrollAdd = $(window).height / 3

// mostra piu film al click del pulsante
$(window).on('scroll', function() {
  if ($(window).scrollTop() > scrollPoint) {
    scrollPoint += scrollAdd
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
}
});
