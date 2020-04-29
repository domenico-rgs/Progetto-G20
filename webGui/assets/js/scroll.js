async function addImageToDOM() {
  const imageDiv = document.createElement('div');
  imageDiv.className = 'one-fourth';

  const imgElement = document.createElement('img');
  imgElement.src = generateImageLinks();

  imageDiv.append(imgElement);
  document.querySelector('.container').append(imageDiv);
}

function generateImageLinks() {
  return `https://placekitten.com/${generateSize(200)}/${generateSize(200)}`;
}

function generateSize(min) {
  var randomNumber = Math.ceil(Math.random() * 100);
  return randomNumber + min;
}


function fetchRandomImages(number) {
  for (var i = 0; i < number; i++) {
    addImageToDOM();
  }
}

fetchRandomImages(100);

window.addEventListener('scroll', function() {
  if(window.scrollY + window.innerHeight + 100 >= document.documentElement.scrollHeight) {
    fetchRandomImages(5);
  }
})
