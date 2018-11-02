(function (xo) {
  xo.server.send = function(root) {
    xo.log.add(1, 'xo.server', JSON.stringify(root));
    var proc_name = root.data.proc;
    if (proc_name == 'reload') {
      xo.bldr.core.reload__recv(JSON.stringify(
      { lists:
        { todo:
          [
            { task_type: 'gplx.xowa.bldr.wikis.copy_part'
            , task_name: 'simple.wikipedia.org: file'
            , task_id : 's.w-2016.03-file'
            , prog_status: 1
            , prog_data_cur : 0
            , prog_data_end : 1819264175
            , subs:
              [
                { task_type : 'xowa.core.http.download'
                , task_name : 'download'
                , task_id  : 's.w-2016.03-file-download'
                , task_top_uid : 's.w-2016.03-file'
                , task_suspendable: true
                , prog_status: 1
                , prog_data_cur : 0
                , prog_data_end : 1819264175
                }
              , { task_type : 'gplx.xowa.core.security.verify'
                , task_name : 'verify'
                , task_id  : 's.w-2016.03-file-verify'
                , task_top_uid : 's.w-2016.03-file'
                , task_suspendable: false
                , prog_status: 1
                , prog_data_cur : 0
                , prog_data_end : 1819264175
                }
              ]
            }
          , { task_type: 'gplx.xowa.bldr.wikis.copy_part'
            , task_name: 'simple.wikipedia.org: html'
            , task_id : 's.w-2016.03-html'
            , prog_status: 1
            , prog_data_cur : 0
            , prog_data_end : 1819264175
            , subs:
              [
                { task_type : 'xowa.core.http.download'
                , task_name : 'download'
                , task_id  : 's.w-2016.03-html-download'
                , prog_status: 1
                , prog_data_cur : 0
                , prog_data_end : 1819264175
                }
              , { task_type : 'gplx.xowa.core.security.verify'
                , task_name : 'verify'
                , task_id  : 's.w-2016.03-html-unzip'
                , prog_status: 1
                , prog_data_cur : 0
                , prog_data_end : 1819264175
                }
              ]
            }
          ]
        , done:
          [
            { task_type: 'gplx.xowa.bldr.wikis.copy_part'
            , task_name: 'simple.wikipedia.org: file'
            , task_id : 's.w-2016.03-file'
            , prog_status: 1
            , prog_data_cur : 0
            , prog_data_end : 1819264175
            , subs:
              [
                { task_type : 'xowa.core.http.download'
                , task_name : 'download'
                , task_id  : 's.w-2016.03-file-download'
                , task_top_uid : 's.w-2016.03-file'
                , task_suspendable: true
                , prog_status: 1
                , prog_data_cur : 0
                , prog_data_end : 1819264175
                }
              , { task_type : 'gplx.xowa.core.security.verify'
                , task_name : 'verify'
                , task_id  : 's.w-2016.03-file-verify'
                , task_top_uid : 's.w-2016.03-file'
                , task_suspendable: false
                , prog_status: 1
                , prog_data_cur : 0
                , prog_data_end : 1819264175
                }
              ]
            }
          ]
        }
      }
      ));
    }
    else if (proc_name == 'add_work') {
      xo.bldr.core.transfer__recv(JSON.stringify(
      { src:'todo'
      , trg:'work'
      , task:
        { task_id:root.data.args.task_id
        , task_name: 'test queue'
        , step:
          {
            step_name: 'step name'
          , cmd:
            { cmd_name: 'download'
            , prog_data_cur: 0
            , prog_data_end: 100
            , prog_time_end: 0
            }
          }
        }
      }));
    }
    else if (proc_name == 'del_work') {
      xo.server.wkr.remove();
      xo.bldr.core.transfer__recv(JSON.stringify({src:'work',trg:'todo',uid:root.data.args.task_id}));
    }
    else if (proc_name == 'run_next') {
      var task_id = root.data.args.task_id;
      var nde = xo.bldr.work.regy['s.w-2016.03-file'];
      if (nde.started) {
        xo.server.wkr.resume();
      }
      else {
        nde.started = true;
        var sub_nde = nde.subs[0];  // TODO: get next active node
        
        var msgs = [];
        msgs = msgs.concat(
        [
          { proc:'xo.bldr.work.pause__visibility'
          , args:
            { task_id:'s.w-2016.03-file-download'
            }
          }
        ]
        );
        msgs = msgs.concat(xo.server.msgs.make
        ( 5
        , { proc:'xo.bldr.work.prog__update'
          , args:
            { task_id: sub_nde.task_id
            , prog_data_cur: function(ctx, nde) {
              return (((ctx.idx + 1) * nde.prog_data_end) / ctx.max) | 0;
            }
            , prog_data_end: sub_nde.prog_data_end
            }
          }
        ));
        /*
        msgs = msgs.concat(
        [
        { proc:'xo.bldr.work.prog__error'
            , args:
              { task_id: sub_nde.task_id
              , err: 'hash failed: expd=ff3d064ddd8373d1bada6f1c666cd501 actl=ff3d064ddd8373d1bada6f1c666cd501z'
              }
            }
        ]
        );
        */
        msgs = msgs.concat(
        [
          { proc:'xo.bldr.work.prog__finished'
          , args:
            { task_id: sub_nde.task_id
            , prog_data_cur: sub_nde.prog_data_end
            , prog_data_end: sub_nde.prog_data_end
            }
          }
        ]
        );
        msgs = msgs.concat(
        [
          { proc:'xo.bldr.work.pause__visibility'
          , args:
            { task_id:'s.w-2016.03-file-verify'
            }
          }
        ]
        );
        sub_nde = nde.subs[1];  // TODO: get next active node
        msgs = msgs.concat(
          xo.server.msgs.make
          ( 10
          , { proc:'xo.bldr.work.prog__update'
            , args:
              { task_id: sub_nde.task_id
              , prog_data_cur: function(ctx, nde) {
                return (((ctx.idx + 1) * nde.prog_data_end) / ctx.max) ;
              }
              , prog_data_end: sub_nde.prog_data_end
              }
            }
          )
        );
        /*
        */
        xo.server.wkr.start(msgs)
      }
      xo.bldr.work.run_next__recv(JSON.stringify({task_id:task_id}));
    }
    else if (proc_name == 'work__pause__send') {
      xo.server.wkr.pause();
      xo.bldr.work.pause__recv(JSON.stringify({task_id:root.data.args.task_id}));
    }
  };
  
  xo.server.wkr = new function() {
    this.time_interval = 500;
    this.msg_idx = 0;
    this.msg_end = 0;
    this.run_handle = null;
    this.paused = false;
    this.msgs = null;

    this.on_run = function() {
      var wkr = xo.server.wkr;
      var msg = wkr.msgs[wkr.msg_idx];
      xo.server.msgs.get_by_path(this, msg.proc)(msg.args);
      ++wkr.msg_idx;
      if (wkr.paused || wkr.msg_idx >= wkr.msg_end) {
        this.clearInterval(wkr.run_handle);
      }
    }
    this.start = function(msgs) {
      this.msg_idx = 0;
      this.msgs = msgs;
      this.msg_end = msgs.length;
      this.resume();
    }
    this.resume = function() {
      this.paused = false;
      this.run_handle = window.setInterval(this.on_run, this.time_interval);      
    }
    this.pause = function() {
      this.paused = true;      
    }
    this.remove = function() {
      this.paused = true;
      this.msg_idx = 0;
    }
  }
  xo.server.msgs = new function() {
    this.get_by_path = function(obj, path){
        for (var i = 0, path = path.split('.'), len = path.length; i < len; ++i){
            obj = obj[path[i]];
        };
        return obj;
    };  
    this.make = function(max, proto) {
      var rv = [];
      for (var idx = 0; idx < max; ++idx) {
        var clone = clone_deep({idx:idx,max:max}, proto, {});
        rv.push(clone);
      }
      return rv;
    }
    function clone_deep(ctx, proto, clone) {
      for (var proto_key in proto) {
        var proto_val = proto[proto_key];
        if (proto_val instanceof Function) {
          clone[proto_key] = proto_val(ctx, proto);
        }
        else if (proto_val instanceof Object) {
          clone[proto_key] = clone_deep(ctx, proto_val, {});
        }
        else {
          clone[proto_key] = proto_val;
        }
      }
      return clone;
    }
  }
}(window.xo = window.xo || {}))
