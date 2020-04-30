async function showingAppear() {

  document.getElementById('showing').style.animation = "300ms fadeIn";
  document.getElementById('showing').style.animationFillMode = "forwards";
  document.getElementById('showingTransparent').style.visibility = "visible";
}

function showingHidden() {

  document.getElementById('showing').style.animation = "300ms fadeOut";
  document.getElementById('showing').style.animationFillMode = "forwards";
  document.getElementById('showing').style.visibility = "visible";
  document.getElementById('showingTransparent').style.visibility = "hidden";
}
