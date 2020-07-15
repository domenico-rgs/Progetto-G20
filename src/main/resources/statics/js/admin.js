var backItem;
var selectedItem;
var entered = false

$(window).on('load', function() {

  loadGeneralPage();

  $('.passDiv').animate({
    "opacity": "1"
  }, 700);

  // selectedItem = '#introduzione';
  //
  // changeView();

  sideMenuSwitch();
});
//funzione per caricare lapagina generale dinamica
function loadGeneralPage() {
  var ajax = $.ajax({
    type: "POST",
    url: "/administrator",
    data: {
      requestPost: "LoadGeneral",
    },
    success: function(response) {
      $('#general').append(response)
    }
  })
}

// comparsa e scomparsa menu laterale

function sideMenuSwitch() {

  if ($(window).width() > 600) {
    $('.sideMenu').animate({
      "left": "0px"
    }, 500);
  }

  if ($(window).width() < 600) {
    $('.sideMenu').animate({
      "left": "-200px"
    }, 500);
  }

  selectedItem = '#general';
  $('.sideMenu .menu ul li:first-child').addClass('active')

  changeView();

}

function changeView() {

  if (backItem != undefined) {
    $(backItem).css("height", "0");
    $(backItem).css("border-bottom", "none");
  }

  $(selectedItem).css("height", "auto").slideDown(1000);
  $(selectedItem).css("border-bottom", "1px solid rgb(30,63,97)");

}

$('.sideMenu li').click(function() {
  $(this).addClass('active').siblings().removeClass('active');

  backItem = selectedItem;
  selectedItem = '#' + $(this).attr("value");

  changeView();
});



$('#sideButton').click(function() {
  $('.sideMenu').animate({
    "left": "0px"
  }, 500);
});

$('#closeButton').click(function() {
  $('.sideMenu').animate({
    "left": "-15%"
  }, 500);
});



$('.stat #movie').click(function() {
  $('.table .movieT').addClass('active')
  $('.table .theatreT').removeClass('active')
  $('.table .showingT').removeClass('active')
})

$('.stat #theatre').click(function() {
  $('.table .theatreT').addClass('active')
  $('.table .movieT').removeClass('active')
  $('.table .showingT').removeClass('active')
})

$('.stat #showing').click(function() {
  $('.table .showingT').addClass('active')
  $('.table .movieT').removeClass('active')
  $('.table .theatreT').removeClass('active')
})


//per mostrare i showing per film
$('.showingT #movie').on('change', function() {
  $('.showingT .tableSh').empty()
  $('.showingT .tableSh .loader').css("visibility", "visible")

  var ajax = $.ajax({
    type: "POST",
    url: "/administrator",
    data: {
      requestPost: "LoadShowStat",
      title: $(this).val()
    },
    success: function(response) {
      $('.showingT .tableSh .loader').css("visibility", "hidden")
      $('.showingT .tableSh').append(response)
    }
  })

});