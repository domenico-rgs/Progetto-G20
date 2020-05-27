// automatic way to change background img on index.HTML
//every 5 o 10 seconds

var imgList = ["../static/images/background/back0.jpg",
                "../static/images/background/back1.jpg",
                "../static/images/background/back2.jpg",
                "../static/images/background/back3.jpg",
                "../static/images/background/back4.jpg"];

var currentImage;
var index = 1;
var element = document.getElementById('banner')


function changeImage(){
  if(index == 5) {
    index = 0;
  }

  currentImage = imgList[index];

  element.style.backgroundImage = "url(" + currentImage + ")";
  index++;

};


setInterval("changeImage()",10000);
