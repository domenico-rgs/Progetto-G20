$('#addTheatre #add').on('click', function() {
    var ajax = $.ajax({
       type: "POST",
       url: "/administrator",
       data: {
         requestPost: "AddTheatre",
         name: $('#addTheatre #name').val(),
         config: $('#addTheatre #config').val(),
         },
       success : function(response)
       {
         $('#addTheatre .message').text(response)
       }
  })
});


$('#addShowing #add').on('click', function() {
    var ajax = $.ajax({
       type: "POST",
       url: "/administrator",
       data: {
         requestPost: "AddShowing",
         movie: $('#addShowing #movie').val(),
         theatre: $('#addShowing #theatre').val(),
         date: $('#addShowing #date').val(),
         hour: $('#addShowing #hour').val(),
         price: $('#addShowing #price').val()
       },
       success : function(response)
       {
         $('#addShowing .message').text(response)
       }
  })
});

$('#editShowing #editS').on('click', function() {
    var ajax = $.ajax({
       type: "POST",
       url: "/administrator",
       data: {
         requestPost: "EditShowing",
         movie: $('#editShowing #movie').val(),
         theatre: $('#editShowing #theatre').val(),
         id: $('#editShowing #id').val(),
         price: $('#editShowing #price').val()
       },
       success : function(response)
       {
         $('#editShowing .message').text(response)
       }
  })
});


$('#editShowing #id').on('keypress', function(e) {
  if (e.which == 13) {
    var ajax = $.ajax({
       type: "POST",
       url: "/administrator",
       data: {
         requestPost: "GetShowingInf",
         movie: $('#editShowing #movie').val(),
         id: $('#editShowing #id').val()
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
           $('#editShowing #date').val(params[2])
         }


	}
	})
  }
});

$('#addMovie #add').on('click', function() {
    var ajax = $.ajax({
       type: "POST",
       url: "/administrator",
       data: {
         requestPost: "AddMovie",
         title: $('#addMovie #movie').val(),
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
         title: $('#editMovie #movie').val(),
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

$('#editMovie #movie').on('change', function() {
  var ajax = $.ajax({
     type: "POST",
     url: "/administrator",
     data: {
       requestPost: "GetMovieInf",
       title: $('#editMovie #movie').val(),
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
         $('#editMovie #cover').val(params[3])
         $('#editMovie #category').val(params[4])
       }

     }
	})
});
