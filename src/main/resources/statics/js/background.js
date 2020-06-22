// automatic way to change background img on index.HTML
//every 5 o 10 seconds

var imgList = ["../statics/images/background/back0.jpg",
                "../statics/images/background/back1.jpg",
                "../statics/images/background/back2.jpg",
                "../statics/images/background/back3.jpg",
                "../statics/images/background/back4.jpg"];

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

setInterval("changeImage()",5000);
