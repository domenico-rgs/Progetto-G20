window.onscroll = function()
{
  scrollFunction()
};

function scrollFunction()
{
  if (document.body.scrollTop > 380 || document.documentElement.scrollTop > 380)
  {
    document.getElementById("navbar").style.top = "0";
  }
  else
  {
    document.getElementById("navbar").style.top = "-60px";
  }
}


function loginAppear() {

  document.getElementById('login').style.animation = "200ms fadeIn";
  document.getElementById('login').style.visibility = "visible";
  document.getElementById('loginTransparent').style.visibility = "visible";
}

function loginHidden() {

  document.getElementById('login').style.visibility = "hidden";
  document.getElementById('login').style.animation = "none";
  document.getElementById('loginTransparent').style.visibility = "hidden";
}
