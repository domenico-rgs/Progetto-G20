const floatField = document.getElementById('search');
const floatContainer = document.getElementById('searchBar');


floatField.addEventListener('focus', () => {
  floatContainer.classList.add('focus');
});
floatField.addEventListener('blur', () => {
  floatContainer.classList.remove('focus');
});
