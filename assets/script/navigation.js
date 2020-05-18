var navMenu = document.getElementById('navMenu');

function peekDropdown() {
  if (navMenu.className === "menu") {
    navMenu.className += " responsive";
  } else {
    navMenu.className = "menu";
  }
}
