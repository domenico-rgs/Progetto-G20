var frame = document.getElementById('loginFrame');
var transp = document.getElementById('loginTransparent');


function loginAppear() {

  frame.style.visibility = "visible";
  document.getElementById('loginFrame').style.opacity = 1;
  document.getElementById('loginFrame').style.top = "5%";
  document.getElementById('loginTransparent').style.visibility = "visible";

}

const inputs = document.querySelectorAll(".input");


function addcl(){
	let parent = this.parentNode.parentNode;
	parent.classList.add("focus");
}

function remcl(){
	let parent = this.parentNode.parentNode;
	if(this.value == ""){
		parent.classList.remove("focus");
	}
}


inputs.forEach(input => {
	input.addEventListener("focus", addcl);
	input.addEventListener("blur", remcl);
});
