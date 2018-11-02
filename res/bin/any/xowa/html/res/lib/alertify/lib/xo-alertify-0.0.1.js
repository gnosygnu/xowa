(function (xo) {
  "use strict";
  xo.alertify = new function() {
    this.bind_href_to_confirm = function(elem_id, msg, ok_msg, cancel_msg, href) {
      var elem = document.getElementById(elem_id);
      elem.onclick = this.confirm_href;
      elem.data_alertify_msg    = msg;
      elem.data_alertify_ok     = ok_msg;
      elem.data_alertify_cancel = cancel_msg;
      elem.data_alertify_href = href;
    }
    this.confirm_href = function (elem) {      
      xo.alertify.confirm_show(elem.getAttribute('data_alertify_msg'), elem.getAttribute('data_alertify_ok'), elem.getAttribute('data_alertify_cancel')
      , function() {
        window.navigate_to(elem.getAttribute('data_alertify_href'));
      }
      );
      return false;
    }
    this.confirm_func = function (elem) {      
      xo.alertify.confirm_show(elem.getAttribute('data_alertify_msg'), elem.getAttribute('data_alertify_ok'), elem.getAttribute('data_alertify_cancel')
      , function() {        
        return eval(elem.getAttribute('data_alertify_func'));
      }
      );
      return false;
    }
    this.confirm_show = function(msg, ok_msg, cancel_msg, ok_cbk) {
			alertify.set({
				labels : {
					ok     : ok_msg,
					cancel : cancel_msg
				},
				buttonReverse : true,
				buttonFocus   : "cancel"
			});
			alertify.confirm(msg, function (e) {
        if (e) {
          ok_cbk();
        }
      });
      return false;
    }
    this.log_by_str = function(nde_str) {
      var nde = JSON.parse(nde_str);
      var msg = nde.msg || 'no message';
      var type = nde.type || 'success';
      var wait = nde.wait || 3000;
      alertify.log(msg, type, wait);
      return true;
    }
    
  }
}(window.xo = window.xo || {}));
