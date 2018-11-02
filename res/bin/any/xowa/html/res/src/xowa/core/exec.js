function obj__merge(lhs, rhs) {
  var rv = lhs;
  for (prop in rhs) {
    lhs[prop] = rhs[prop];
  }
  return rv;
}
function Xocmds() {
  this.cmds = {};
  this.add = function(key, cmd) {
    cmd.key = key;
    this.cmds[key] = cmd;
  }
  this.get = function(key) {
    var rv = this.cmds[key];
    if (rv == null) throw 'cmd not found: ' + key;
    if (rv.exec == null) throw 'cmd does not have function "exec": ' + key;
    return rv;
  }
  this.exec_by_str = function(cmd_key, data_as_str) {
    var data = null;
    try {data = JSON.parse(data_as_str);}
    catch(err) {alert(err);}
    this.exec(cmd_key, data);
  }
  this.exec = function(cmd_key, data) {
    var cmd = this.get(cmd_key);
    cmd.exec(data);
  }
  this.recv = function(orig_data, cbk_key, msg_str) {
    // deserialize msg
    var msg = null;
    try         {msg = JSON.parse(msg_str);}
    catch (err) {throw 'parse err: msg=' + msg_str + ' stack=' + err.stack;} 
    // do any notifications
    if (msg.notify) {
      this.exec('xowa.notify', {text:msg.notify.text, status:msg.notify.status});
    }
    if (msg.rslt && !msg.rslt.pass) return;
    // identify cmd to run; prefer server cmd over gui
    var cmd_key = msg.cmd;
    if (!cmd_key) {
      cmd_key = cbk_key;
      if (!cmd_key) return; // note that some msgs may not ever have anything to execute
    }
    // run cmd
    var msg_data = obj__merge(orig_data, msg.data);
    var cmd = this.get(cmd_key);
    cmd.exec(msg_data);
  }
  this.send = function(fmt, cmd, data, cbk) {
    try {
      var send_msg_str = cmd;     // if 'gfs', then msg == cmd; note that gfs which doesn't use data
      if (fmt == 'json') {
        var send_msg_obj = 
        { 'client' : xowa.client  // NOTE: values set by http_server in <head>
        , 'cmd'    : cmd
        , 'data'   : data
        };
        send_msg_str = JSON.stringify(send_msg_obj);
      }
      if (xowa.app.mode == 'gui') {
        var response = xowa_exec('exec_' + fmt, send_msg_str);
        try {xowa.cmds.recv(data, cbk, response);}
        catch (err) {throw Err_.msg(err, 'exec.send', 'gui callback failed', 'response', JSON.parse_safe(response));}
      }
      else {
        var xreq = new XMLHttpRequest();
        xreq.onreadystatechange = function() {
          if (xreq.readyState == 4 && xreq.status == 200) {
            var response = xreq.responseText;
            try {xowa.cmds.recv(data, cbk, response);}
            catch (err) {Err_.print(err, 'exec.send', 'async callback failed', 'cmd', cmd, 'data', data, 'response', JSON.parse_safe(response));}
          }
        };      
        var post_url = 'http://' + xowa.client.server_host + '/exec/' + fmt; // EX:http://localhost:8080/exec/json
        xreq.open('POST', post_url, true);
        var form_data = new FormData();
        form_data.append('msg', send_msg_str);
        form_data.append('app_mode', xowa.app.mode);
        xreq.send(form_data);
      }
    } catch (err) {Err_.print(err, 'exec.send', 'send failed', 'cmd', cmd, 'data', data);}
  };
};
JSON.parse_safe = function(s) {
  try {return JSON.parse(s);}
  catch (err) {return s;}
}
function Err_() {
  var frame_list = [];
  var frame;
  this.add_frame = function(type, hdr) {
    frame = {};
    frame_list.push(frame);
    frame.type = type;
    frame.hdr = hdr;
    frame.args = [];
  }
  this.add_args = function(args) {
    var args_len = args.length;
    for (var i = 0; i < args_len; i += 2) {
      var key = args[i];
      var val = i + 1 < args_len ? args[i+1] : "NULL_VAL";
      frame.args.push({key: key, val: val});
    }
  }
  this.to_json = function() {
    var rv = {};
    var len = frame_list.length;
    for (var i = 0; i < len; ++i) {
      var frame = frame_list[i];
      var sub = {};
      rv[i] = sub;
      sub.msg = '[' + frame.type + '] ' + frame.hdr;
      var args = frame.args;
      for (var arg_idx in args) {
        var arg = args[arg_idx];
        sub[arg.key] = arg.val;
      }
    }
    return JSON.stringify(rv, null, 2);
  }
}
Err_.msg = function(err, type, hdr) {
  var err_data = Err_.get_data(err);
  err_data.add_frame(type, hdr);
  err_data.add_args(Array.prototype.slice.call(arguments, 3));
  return err;
}
Err_.get_data = function(err) {
  var data = err.data;
  if (data == null) {
    data = new Err_();
    err.data = data;
  }
  return data;
}
function html__escape(s) {
  return s
       .replace(/&/g, "&amp;")
       .replace(/</g, "&lt;")
       .replace(/>/g, "&gt;")
       .replace(/"/g, "&quot;")
       .replace(/'/g, "&#039;");
}
Err_.print = function(err, type, hdr) {
  err_data = Err_.get_data(err);
  err_data.add_frame(type, hdr);
  err_data.add_args(Array.prototype.slice.call(arguments, 3));
  err__add
  ( err.message 
  + '\n' + html__escape(err_data.to_json())
  + '\n' + err.stack
  );
}

window.xowa.cmds = new Xocmds();

function Xonotify() {
  var loaded = false;
  this.exec = function(data) {
     if (!loaded) {      
      loaded = true;
      var notify_cmd = this;
      xowa.js.jquery.init();
      xowa.js.load_lib(xowa_root_dir + 'bin/any/xowa/html/res/lib/notifyjs/notifyjs-0.3.1.js'
      , function delayed_notify_exec() {
        notify_cmd.notify(data.text, data.status);
      }
      );
    }
    else
      this.notify(data.text, data.status);
  }
  this.notify = function(msg, type) {
    try {$.notify(msg, {className:type, globalPosition:'top center'});}
    catch (err) {Err_.print(err, 'xonotify', 'notify failed', 'msg', msg, 'type', type);}
  }
}
window.xowa.cmds.add('xowa.notify', new Xonotify());
