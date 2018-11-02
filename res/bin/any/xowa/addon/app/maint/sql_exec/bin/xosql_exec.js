(function (xo) {
  xo.sql_exec = new function() {
    this.send = function(proc, args) {
      try {xo.server.send_by_bridge('xowa.app.maint.sql_exec', proc, args);}
      catch (err) {alert(err);}
    }
    this.run_sql = function() {
      try {
        xo.sql_exec.results__set('running');
        var data = 
        { domain:   xo.elem.get_val_or_null('domain_box')
        , db:       xo.elem.get_val_or_null('db_box')
        , sql:      xo.elem.get_val_or_null('sql_box')
        };
        xo.sql_exec.send('exec', data);
      } catch (err) {
        alert(err);
      }
    }

    this.results__set = function(msg) {
      var elem = document.getElementById('results_div');
      elem.innerHTML = msg;
    }
    this.results__recv = function(msg_str) {
      try {
        var msg = JSON.parse(msg_str);
        this.results__set(msg.msg_text);
        return true;      
      } catch (err) {
        alert(err);
        return false;
      }
    }
  }
}(window.xo = window.xo || {}));
