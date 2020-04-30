async function addMovieToDOM() {
 const itemDiv = document.createElement('div');
  itemDiv.className = 'item';
  const imageDiv = document.createElement('div');
  imageDiv.className = 'img';
  const nameDiv = document.createElement('div');
  nameDiv.className = 'name';

  const imgElement = document.createElement('img');
  imgElement.src = 'assets/images/unavaliable.png';
  const titleElement = document.createTextNode("Test Title");

itemDiv.appendChild(imageDiv).appendChild(imgElement);
itemDiv.appendChild(nameDiv).appendChild(titleElement);

  document.querySelector('.filmMenu').append(itemDiv);
}

function fetchRandomImages(number) {
  for (var i = 0; i < number; i++) {
    addMovieToDOM();
  }
}

fetchRandomImages(10);

var oldScroll = 1800;
var element = 20;

window.onscroll = function() {

  //create automatic scroll item
  if(element < 100 &&(document.body.scrollTop > oldScroll || document.documentElement.scrollTop > oldScroll)) {
    oldScroll += 300;
    fetchRandomImages(5);
    element += 5;
  }
};
