async function addMovieToDOM() {
 const itemDiv = document.createElement('div');
  itemDiv.className = 'item';
  const imageDiv = document.createElement('div');
  imageDiv.className = 'img';
  const nameDiv = document.createElement('div');
  nameDiv.className = 'name';

  const imgElement = document.createElement('img');
  imgElement.src = 'images/unavaliable.png';
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

fetchRandomImages(100);

window.addEventListener('scroll', function() {
  if(window.scrollY + window.innerHeight + 100 >= document.documentElement.scrollHeight) {
    fetchRandomImages(5);
  }
})
