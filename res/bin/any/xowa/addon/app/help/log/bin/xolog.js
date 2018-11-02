(function (xo) {
  xo.xolog = new function() {
    this.date_combo_show = function() {
      document.getElementById("date_combo_id").classList.toggle("date_combo_content_show");
    }
    window.onclick = function(event) {
      if (!event.target.matches('.date_combo_clicker')) {
        var dropdowns = document.getElementsByClassName("date_combo_content");
        var i;
        for (i = 0; i < dropdowns.length; i++) {
          var dropdown = dropdowns[i];
          if (dropdown.classList.contains('date_combo_content_show')) {
            dropdown.classList.remove('date_combo_content_show');
          }
        }
      }
    }
  }
}(window.xo = window.xo || {}));
