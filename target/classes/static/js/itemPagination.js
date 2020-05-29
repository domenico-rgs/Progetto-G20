var itemIndex;

async function addMovieToDOM() {
 const itemDiv = document.createElement('div');
  itemDiv.className = 'item';
  const imageDiv = document.createElement('div');
  imageDiv.className = 'img';
  const nameDiv = document.createElement('div');
  nameDiv.className = 'name';

  const imgElement = document.createElement('img');
  imgElement.src = '../static/images/unavaliable.png';
  const titleElement = document.createTextNode("Test Title " + itemIndex);

itemDiv.appendChild(imageDiv).appendChild(imgElement);
itemDiv.appendChild(nameDiv).appendChild(titleElement);

  document.querySelector('.filmMenu').append(itemDiv);
}

itemIndex = 1;

function fetchRandomImages(number) {
  for (var i = 0; i < number; i++) {
    addMovieToDOM();
    itemIndex += 1;
  }
}

var elementForPage = 10;
fetchRandomImages(elementForPage);

//creo il numero di pagine

var nPage = 6 //temporeo, ci pensa il server

async function createPages(value) {

  if (typeof value === 'string' || value instanceof String) {
    const itemPage = document.createElement('a');
    const text = document.createTextNode(value);
    itemPage.appendChild(text);

    document.querySelector('.pagination').append(itemPage);
    return;
  }

  else {
  for (var i = 0; i < value; i++) {
    const itemPage = document.createElement('a');
    const text = document.createTextNode(i + 1);
    itemPage.appendChild(text);

    document.querySelector('.pagination').append(itemPage);
  }
}


}

createPages("Previous");
createPages(nPage);
createPages("Next");

//per i tasti di pagination numerati
$('.pagination a').click(function() {
  $(this).addClass('active').siblings().removeClass('active');
});
