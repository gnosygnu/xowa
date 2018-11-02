(function (xo, $) {
  xo.notify = new function() {
    this.elem_anchor = '';
    this.show = function(msg) {
      $(this.elem_anchor).notify(msg, {className:'success', elementPosition:'top center', globalPosition:'top center', style:'xostyle'
      , autoHideDelay: 3000
      , showDuration: 500
      , hideDuration: 500
      });      
    }
    this.init = function() {
      $.notify.addStyle('xostyle', {
        html: "<div><span data-notify-html/></div>",
        classes: {
          base: {
            "margin-top":"40px",
            "white-space": "nowrap",
            "background-color": "#5cb85c",
            "padding": "5px",
            "color":"white",
            "text-align": "center",
          },
        }
      });
    }
  };
  xo.notify.init();
}(window.xo = window.xo || {}, jQuery));
