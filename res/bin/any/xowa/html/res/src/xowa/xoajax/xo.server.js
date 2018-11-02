(function (xo) {
  xo.server = new function() {
    this.send_by_bridge = function(cmd, proc, args) {
      xo.server.send(
      { cmd: cmd
      , data:
        { proc: proc
        , args: args
        }
      }
      );
    }
    this.send = function(msg) {
      var msg_str = JSON.stringify(msg);
      xo.log.add(1, 'xo.server:send', msg_str);
      if      (xo.app.mode == 'drd')          {xowa_exec.exec_json(msg_str);}           // SEE: bundle.js
      else if (xo.app.mode == 'http_server')  {xo.http_server.send_and_poll(msg_str);}  // SEE: xo.app.http_server.js
      else                                    {xowa_exec('exec_json', msg_str);}        // SEE: Xoh_js_cbk.java
    }
    this.redirect__recv = function(msg_str) {
      var msg = JSON.parse(msg_str);
      window.location = msg.url;
      return true;
    }
    
  };
}(window.xo = window.xo || {}));
