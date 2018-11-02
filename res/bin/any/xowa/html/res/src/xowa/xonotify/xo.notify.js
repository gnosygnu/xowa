(function (xo, $) {
  xo.notify = new function() {
    this.elem_anchor;
    this.show = function(msg) {
      try {
        if (!this.elem_anchor) {
          alert('elem_anchor is null; must set xo.notify.elem_anchor');
          return;
        }
        $(this.elem_anchor).notify(msg, {className:'success', elementPosition:'top center', globalPosition:'top center', style:'xostyle'
        , autoHideDelay: 3000
        , showDuration: 500
        , hideDuration: 500
        });
      } catch (err) {
        alert(err);
      }
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
    this.show__recv = function(msg_str) {      
      var msg = JSON.parse(msg_str);
      xo.notify.show(msg.text);
      return true;
    }
  };
  xo.notify.init();
}(window.xo = window.xo || {}, jQuery));
