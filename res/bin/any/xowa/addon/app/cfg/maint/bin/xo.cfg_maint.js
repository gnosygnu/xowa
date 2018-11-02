(function (xo) {
  xo.cfg_maint = new function() {
    this.get_elem_val = function(id) {
      var elem = document.getElementById(id);
      return elem == null ? null : elem.value;
    }
    this.itm__save = function(e) {
      var data = xo.cfg_maint.get_elem_val('data_box');
      xo.server.send_by_bridge('xo.cfg_maint', 'upsert',
      { data:     data
      });
    }
  }
}(window.xo = window.xo || {}));
document.getElementById('data_box').focus();
document.getElementById('save_btn').addEventListener('click', xo.cfg_maint.itm__save);
