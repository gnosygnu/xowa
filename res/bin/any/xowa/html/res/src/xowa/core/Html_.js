(function (Html_) {
  Html_.decode = function(input) {
    var doc = new DOMParser().parseFromString(input, "text/html");
    return doc.documentElement.textContent;
  };
}(window.Html_ = window.Html_ || {}));