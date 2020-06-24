$('#addTheatre #add').on('click', function() {
    var ajax = $.ajax({
       type: "POST",
       url: "/administrator",
       data: {
         requestPost: "AddTheatre",
         name: $('#addTheatre #movie').val(),
         configuration: $('#addTheatre #configuration').val(),
         },
       success : function(response)
       {
         $('#addTheatre.message').text(response)
       }
  })
});


$('#addShowing #add').on('click', function() {
    var ajax = $.ajax({
       type: "POST",
       url: "/administrator",
       data: {
         requestPost: "AddShowing",
         movie: $('#addShowing #movie').find(":selected").text(),
         theatre: $('#addShowing #theatre').find(":selected").text(),
         date: $('#addShowing #date').val(),
         hour: $('#addShowing #hour').val(),
         price: $('#addShowing #price').val()
       },
       success : function(response)
       {
         $('#addShowing.message').text(response)
       }
  })
});

$('#editShowing #editS').on('click', function() {
    var ajax = $.ajax({
       type: "POST",
       url: "/administrator",
       data: {
         requestPost: "AddShowing",
         movie: $('#addShowing #movie').find(":selected").text(),
         theatre: $('#addShowing #theatre').find(":selected").text(),
         date: $('#addShowing #date').val(),
         hour: $('#addShowing #hour').val(),
         price: $('#addShowing #price').val()
       },
       success : function(response)
       {
         $('#editShowing .message').text(response)
       }
  })
});

$('#editShowing .searchDiv #remove').on('click', function() {
    var ajax = $.ajax({
       type: "POST",
       url: "/administrator",
       data: {
         requestPost: "RemoveShowing",
         title: $('#editShowing #id').val()
       },
       success : function(response)
       {
         $('#editShowing .message').text(response)
       }
  })
});


$('#searchShowing').on('keypress', function(e) {
  if (e.which == 13) {
    var ajax = $.ajax({
       type: "POST",
       url: "/administrator",
       data: {
         requestPost: "GetShowingInf",
         id: $('#editShowing #searchList').val(),
       },
       success : function(response)
       {
         // per ora cosi, sicuramente c'è di meglio (invio parametri splittando la stringa)
         // 0: theatre
         // 1: price

         var params = response.split("@")

         if (params[0] == "Error") {
           $('#editShowing .message').text(params[1])
         }

         else {
           $('#editShowing #theatre').val(params[0])
           $('#editShowing #price').val(params[1])
         }


  }
});

$('#addMovie #add').on('click', function() {
    var ajax = $.ajax({
       type: "POST",
       url: "/administrator",
       data: {
         requestPost: "AddMovie",
         title: $('#addMovie #title').val(),
         duration: $('#addMovie #duration').val(),
         plot: $('#addMovie #plot').val(),
         cover: $('#addMovie #cover').val(),
         category: $('#addMovie #category').find(":selected").text()
       },
       success : function(response)
       {
         $('#addMovie .message').text(response)
       }
  })
});





$('#editMovie #editM').on('click', function() {
    var ajax = $.ajax({
       type: "POST",
       url: "/administrator",
       data: {
         requestPost: "EditMovie",
         title: $('#editMovie #title').val(),
         duration: $('#editMovie #duration').val(),
         plot: $('#editMovie #plot').val(),
         cover: $('#editMovie #cover').val(),
         category: $('#editMovie #category').find(":selected").text()
       },
       success : function(response)
       {
         $('#editMovie .message').text(response)
       }
  })
});

$('#editMovie #searchList').on('change', function() {
  var ajax = $.ajax({
     type: "POST",
     url: "/administrator",
     data: {
       requestPost: "GetMovieInf",
       title: $('#editMovie #searchList').val(),
     },
     success : function(response)
     {
       // per ora cosi, sicuramente c'è di meglio (invio parametri splittando la stringa)
       // 0: titolo
       // 1: Durata
       // 2: Plot
       var params = response.split("@")

       if (params[0] == "Error") {
         $('#editMovie .message').text(params[1])
       }

       else {
         $('#editMovie #title').val(params[0])
         $('#editMovie #duration').val(params[1])
         $('#editMovie #plot').val(params[2])
       }

     }
})
});

$('#editMovie .searchDiv #remove').on('click', function() {
    var ajax = $.ajax({
       type: "POST",
       url: "/administrator",
       data: {
         requestPost: "RemoveMovie",
         title: $('#editMovie #title').val()
       },
       success : function(response)
       {
         $('#editMovie .message').text(response)
       }
  })
});
