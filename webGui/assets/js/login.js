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
