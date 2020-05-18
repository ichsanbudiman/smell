function peekDropdown() {
  var x = document.getElementById("navMenu");
  if (x.className === "menu") {
    x.className += " responsive";
  } else {
    x.className = "menu";
  }
}
