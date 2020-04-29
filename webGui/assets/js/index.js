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
