$('#addMovie #add').on('click', function() {
    var ajax = $.ajax({
       type: "POST",
       url: "/administrator",
       data: {
         requestPost: "addMovie",
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

$('#editMovie #searchList').on('change', function() {
  var ajax = $.ajax({
     type: "POST",
     url: "/administrator",
     data: {
       requestPost: "getMovieInf",
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
         requestPost: "removeMovie",
         title: $('#editMovie #title').val()
       },
       success : function(response)
       {
         $('#editMovie .message').text(response)
       }
  })
});
