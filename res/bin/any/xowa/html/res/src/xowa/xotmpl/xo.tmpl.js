(function (xo) {
  xo.tmpl = new function () {
    this.fmt = function (tmpl_elem_id, elem, data) {
      var tmpl_elem = document.getElementById(tmpl_elem_id);
      var fmt = tmpl_elem.textContent;
      var html = Mustache.render(fmt, data);
      elem.insertAdjacentHTML('beforebegin', html);
      elem.parentNode.removeChild(elem);             
    }
    this.load_many = function () {
      var len = arguments.length;
      var dir = arguments[0];
      for (var i = 1; i < len; ++i) {
         this.load(dir, arguments[i]);
      }
    };
    this.load = function (dir, fmt_name) {
      var path = dir + fmt_name + ".mustache.html";
      var req = new XMLHttpRequest();
      req.onload = function (e) {
        var template = req.responseText;
        var elem = document.createElement('script');
        elem.id = fmt_name;
        elem.type = 'text/mustache';
        elem.textContent = template;
        document.head.appendChild(elem);
      }
      req.open("GET", path, true); // 'false': synchronous.
      req.send(null);
    };
  }
}(window.xo = window.xo || {}));
