(function (xo) {
  "use strict";
  xo.help = new function() {
    this.add_bottom_margin = true;
    this.toggle = function(elem_prefix) {// assumes 3 elements with suffix of _btn,_div,_msg
      // get elems
      var div_elem = document.getElementById(elem_prefix + '_help_div');
      var msg_elem = document.getElementById(elem_prefix + '_help_msg');

      // if active, disable and exit
      if (div_elem.active) {
        div_elem.active = false;
        div_elem.style.height = '0px';
        div_elem.style.opacity = 0;
        if (xo.help.add_bottom_margin)
          div_elem.style['margin'] = '0px 0px 0px 0px';
        return;
      }
      
      // activate
      div_elem.active = true;
      
      // measure height
      var msg_rect = msg_elem.getBoundingClientRect();
      var msg_h = msg_rect.bottom - msg_rect.top;

      // set height
      div_elem.style.height = msg_h + 'px';
      div_elem.style.opacity = 1;
      if (xo.help.add_bottom_margin)
        div_elem.style['margin'] = '0px 0px 10px 0px';  // add bottom margin to give it space over table
    }
    this.auto_expand = function() {
      var elems = document.getElementsByClassName("xohelp_div_expand");
      var i;
      for (i = 0; i < elems.length; i++) {
          var elem = elems[i];          
          var elem_id = elem.id;
          var elem_prefix = elem_id.substring(0, elem_id.indexOf('_')); 
          xo.help.toggle(elem_prefix);
      }      
    }
  }
}(window.xo = window.xo || {}));
xo.help.auto_expand();
