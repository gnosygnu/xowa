xo.log.add(1, 'bldr.central:init');
(function (xo) {
  xo.bldr = xo.bldr || {};
  xo.bldr.core = new function() {
    // list of all top-tasks and sub-tasks
    this.regy = {};    
    this.rates = {};
    this.STATUS = {
      INIT : 1,
      WORK : 2,
      DONE : 4,
      SUSPENDED : 16,
    }
    this.EXEC = {
      RUN  : 1,
      STOP : 2,
      REDO : 3,
    }
    /*
    this.rates = 
    { 'gplx.xowa.core.security.verify' : 1000000      
    }
    */

    // utility functions
    this.get_uid  = function(tbl, task_id, type) {return tbl + ':' + task_id + ':' + type;}
    this.get_elem = function(tbl, task_id, type) {return xo.elem.get(this.get_uid(tbl, task_id, type));}
    this.send = function(proc, args) {
      xo.server.send(
      { cmd: 'builder_central.exec'
      , data:
        { proc: proc
        , args: args
        }
      }
      );
    }
    
    // reload work, todo, done data
    this.reload__send = function()    {this.send('reload', {});};
    this.reload__recv = function(msg) {this.reload(JSON.parse(msg)); return true;};
    this.reload = function(msg) {
      xo.log.add(1, 'xo.bldr.core.reload:bgn');
      this.reload_list(msg, 'work');
      this.reload_list(msg, 'todo');
      this.reload_list(msg, 'done');
      this.init_filter(msg);
      xo.log.add(1, 'xo.bldr.core.reload:end');
    };
    this.reload_list__recv = function(msg) {
      var msg_as_json = JSON.parse(msg);
      this.reload_list(msg_as_json, msg_as_json.lists.list_name);
      return true;
    };
    this.reload_list = function(msg, list_name) {
      var list = msg.lists[list_name];
      var list_wkr = xo.bldr[list_name];
      if (!list) return;
      list_wkr.clear_list();
      this.delete_subs(list_name + '__tbl');
      var len = list.length;
      xo.log.add(1, 'xo.bldr.core.reload:list', list_name, len);
      for (var i = 0; i < len; ++i) {
        var task = list[i];
        this.regy[task.task_id] = task;
        list_wkr.add_task(task);
      }      
    }
    this.delete_subs = function(elem_id) {
      var elem = xo.elem.get(elem_id);
      while (elem.lastChild) {
        elem.removeChild(elem.lastChild);
      }      
    }

    this.init_filter = function(msg) {
      document.getElementById("filter_types").value = msg.filters.types.active;

      var filter_select = document.getElementById("filter_langs");
      this.clear_select(filter_select);
      var len = msg.langs.length;
      for (var i = 0; i < len; i++) {
        var lang_nde = msg.langs[i];
        var opt = document.createElement('option');
        opt.value = lang_nde.key;
        opt.text = lang_nde.name;
        filter_select.appendChild(opt);
      }
      
      return true;
    };
    this.clear_select = function(cbo) {
      var len = cbo.options.length;
      for (i = 0; i < len; i++) {
        cbo.options[0] = null;
      }      
    }

    // create a row based on type and nde
    this.create_row = function(type, nde) {
      var row;
      if (type == 'work') {
        row = xo.elem.make('work__tbl', 'div');
        nde.row_type = 'work';
        nde.row_is_head = false;
        nde.row_is_work = true;
        nde.row_is_todo = false;              
        nde.row_is_done = false;
      }
      else if (type == 'head') {
        row = xo.elem.make('head__row', 'div');
        nde.row_type = 'head';
        nde.row_is_head = true;
        nde.row_is_work = false;
        nde.row_is_todo = false;              
        nde.row_is_done = false;
        nde.task_id = '0';
      }
      else if (type == 'todo') {
        row = xo.elem.make('todo__tbl', 'div');
        nde.row_type = 'todo';
        nde.row_is_head = false;
        nde.row_is_work = false;
        nde.row_is_todo = true;
        nde.row_is_done = false;
      }
      else if (type == 'done') {
        row = xo.elem.make('done__tbl', 'div');
        nde.row_type = 'done';
        nde.row_is_head = false;
        nde.row_is_work = false;
        nde.row_is_todo = false;
        nde.row_is_done = true;
      }
      xo.tmpl.fmt('xobc.row', row, nde);
      return row;
    }

    // transfer node
    this.transfer__recv = function(msg_str) {
      var msg = JSON.parse(msg_str);
      this.transfer(msg.src, msg.trg, msg.task);
      return true;
    }
    this.transfer = function(src, trg, task) {
      var task_id = task.task_id;

      xo.log.add(1, 'xo.bldr.core.transfer:bgn', src, trg, task_id);

      // del src
      delete(xo.bldr[src].regy[task_id]);
      xo.elem.del(xo.bldr.core.get_uid(src, task_id, 'row'));
      xo.bldr[src].del_task(task);

      // add trg
      xo.bldr[trg].add_task(task);
      
      // add to correct sort-order      
      if (trg != 'work') {// do not reorder work table; work has a sort based on add-order
        // get trg row, trg_tbl
        var trg_row = xo.bldr.core.get_elem(trg, task_id, 'row');
        var trg_sort = parseInt(trg_row.getAttribute('data-sort'));
        var trg_tbl = xo.elem.get(trg + '__tbl');
        var trg_subs = trg_tbl.children;
        var trg_subs_len = trg_subs.length;        
        if (trg_subs_len > 1) { // do not reorder if only 1 item;
          // loop rows until trg_seqn found
          for (var i = 0; i < trg_subs_len; i++) {
            var tmp_row = trg_subs[i];
            var tmp_sort = parseInt(tmp_row.getAttribute('data-sort'));
            if (tmp_sort >= trg_sort) {
              try {trg_row.parentNode.insertBefore(trg_row, tmp_row);}
              catch (e) {alert(e);}
              break;
            }
          }
        }
      }

      xo.bldr.work.head__update();
      xo.log.add(1, 'xo.bldr.core.transfer:end');
    }
        
    // get next active sub nde; currently used by make function
    this.get_next_nde = function(nde) {
      for (var sub_key in nde.subs) {
        var sub = nde.subs[sub_key];
        if (  sub.task_status == xo.bldr.core.STATUS.INIT
           || sub.task_status == xo.bldr.core.STATUS.WORK
           || sub.task_status == xo.bldr.core.STATUS.SUSPENDED
           ) {
          return sub;
        }
      }
      return null;
    }    
    this.get_elem_height_in_px = function(elem_id) {
      var elem = xo.elem.get(elem_id);
      if (elem.offsetHeight) {
          return elem.offsetHeight;
      } else if (elem.style.pixelHeight) {
          return elem.style.pixelHeight;
      }
      return -1;
    }
    this.count = function(o) {
      var rv = 0;
      for (k in o) {
        if (o.hasOwnProperty(k)) {
          ++rv;
        }
      }
      return rv;      
    }    
  };
  
  xo.bldr.todo = new function() {
    this.regy = {};
    this.clear_list = function() {for (var itm in this.regy) delete this.regy[itm];}
    this.add_task = function(task) {
      xo.bldr['todo'].regy[task.task_id] = task;
      xo.bldr.core.create_row('todo', task);      
    }
    this.del_task = function(task) {}
    this.add_work__send = function(task_id) {
      var task = xo.bldr['todo'].regy[task_id];
      // if (xo.app.mode === 'drd') {
        // xo.alertify.confirm_show('This task will download a lot of data!<br/><br/>Please make sure you are on Wi-Fi,<br/>or you will incur data charges.<br/><br/>Are you sure you want to continue?', 'Continue', 'Cancel'
        // , function() {xo.bldr.todo.add_work__send__confirm_y(task_id);});
      // }
      // else {
        xo.bldr.todo.add_work__send__confirm_y(task_id);
      // }
    }
    this.add_work__send__confirm_y = function(task_id) {
      xo.log.add(1, 'xo.bldr.todo.add_work', task_id); 
      xo.bldr.core.send('add_work', {task_id: task_id});
    };
    
    this.del_todo__send = function(task_id) {
      var task = xo.bldr['todo'].regy[task_id];
      xo.bldr.core.send('del_todo', {task_id: task_id});
    }
    
    this.download_db__send = function() {
      xo.bldr.core.send('download_db', {});
    };
    
    this.filter__send = function() {
      var lang_key = document.getElementById("filter_langs").value;
      var type_key = document.getElementById("filter_types").value;
      xo.log.add(1, 'xo.bldr.todo.filter', lang_key); 
      xo.bldr.core.send('filter_todo', {'lang_key': lang_key, 'type_key': type_key});
    };
  };
  
  xo.bldr.done = new function() {
    this.regy = {};
    this.clear_list = function() {for (var itm in this.regy) delete this.regy[itm];}
    this.add_task = function(task) {
      xo.bldr['done'].regy[task.task_id] = task;
      xo.bldr.core.create_row('done', task);
    }
    this.del_task = function(task) {}
    this.del_done__send = function(task_id) {
      // xo.alertify.confirm_show('Are you sure you want to move this task back into the available list?', 'Yes', 'No'
      // , function() {xo.bldr.done.del_done__send__confirm_y(task_id);});
      xo.log.add(1, 'xo.bldr.done.del_done', task_id); 
      xo.bldr.core.send('del_done', {task_id: task_id});    
    }
    this.del_done__send__confirm_y = function(task_id) {
      // xo.log.add(1, 'xo.bldr.done.del_done', task_id); 
      // xo.bldr.core.send('del_done', {task_id: task_id});    
    };
  };

  xo.bldr.work = new function() {
    this.regy = {};
    
    // called by transfer
    this.clear_list = function() {for (var itm in this.regy) delete this.regy[itm];}
    this.add_task = function(task) {      
      xo.bldr['work'].regy[task.task_id] = task;
      
      // var nde_nxt = xo.bldr.core.get_next_nde(nde);
      var step = task.step;
      var cmd = step.cmd;
      var pct = (cmd.prog_data_cur * 100) / cmd.prog_data_end;
      task.prog_msg = xo.bldr.work.prog__msg_make(cmd.cmd_name, pct, xo.time.to_dhms(cmd.prog_time_end), cmd);
      xo.bldr.core.create_row('work', task);
      xo.bldr.core.get_elem('work', task.task_id, 'pbar-bar').style.width = pct + '%';
      this.run_btn__update(true, xo.bldr.core.EXEC.RUN);
    }
    this.del_task = function(task) {
      if (xo.bldr.core.count(this.regy) === 0) {
        this.run_btn__update(false, xo.bldr.core.EXEC.RUN);
      }
    }
    
    // called by server when item is first added
    this.init__recv = function(msg_str) {
      var msg = JSON.parse(msg);
      var task_id = msg.task_id;
      var nde = xo.bldr.core.regy[task_id];
      nde.task_status = xo.bldr.core.STATUS.INIT;
      nde.prog_data_cur = 0;
      return true;
    }

    // called when "-" clicked
    this.del_work__send = function(task_id) {
      var task = xo.bldr.work.regy[task_id];
      if (task.task_status != xo.bldr.core.STATUS.INIT) {
        xo.alertify.confirm_show('Are you sure you want to remove the task and lose all work?', 'Yes', 'No'
        , function() {xo.bldr.work.del_work__send__confirm_y(task, task_id);});
      }
      else {
        this.del_work__send__confirm_y(task, task_id);
      }
    }
    this.del_work__send__confirm_y = function(task, task_id) {
      task.task_status = xo.bldr.core.STATUS.INIT;
      xo.bldr.core.send('del_work', {task_id: task_id});      
    }

    this.run_next__send = function() {xo.bldr.core.send('run_next', {});}

    this.redo_cur__send = function() {
      xo.bldr.core.send('redo_cur', {});
    }

    this.stop_cur__send = function() {xo.bldr.core.send('stop_cur', {});}
    this.stop_cur__recv = function(msg) {xo.bldr.work.stop_cur(JSON.parse(msg)); return true;}
    this.stop_cur = function(args) {
      var task_id = args.task_id;
      this.run_btn__update(true, xo.bldr.core.EXEC.RUN);
      this.del_btn__update(task_id, true);
    }
    
    // called when task started
    this.prog__start__recv = function(task_str) {
      var task = JSON.parse(task_str);
      xo.bldr.work.regy[task.task_id] = task;  // overwrite task with new cmd
      xo.bldr.core.get_elem('work', task.task_id, 'step-name').innerHTML = task.step.step_name;
      var msg_elem = xo.bldr.core.get_elem('work', task.task_id, 'pbar-txt');
      msg_elem.style.backgroundColor = 'initial';
      
      this.run_btn__update(task.step.cmd.cmd_suspendable, xo.bldr.core.EXEC.STOP);
      this.del_btn__update(task.task_id, false);
      return true;
    }
    this.run_btn__update = function(enabled, exec_type) {
      var btn = xo.bldr.core.get_elem('head', '0', 'run_next');
      btn.style.opacity = enabled ? '1.0' : '0.2';
      
      switch (exec_type) {
        case xo.bldr.core.EXEC.RUN:
          btn.className = 'xoimg_btn_x24 xoimg_media_play';
          btn.onclick = function() {return xo.bldr.work.run_next__send();};
          break;
        case xo.bldr.core.EXEC.STOP:
          btn.className = 'xoimg_btn_x24 xoimg_media_pause';
          btn.onclick = function() {return xo.bldr.work.stop_cur__send();};                
          break;
        case xo.bldr.core.EXEC.REDO:
          btn.className = 'xoimg_btn_x24 xoimg_list_refresh';
          btn.onclick = function() {return xo.bldr.work.redo_cur__send();};                
          break;
      }
    }
    this.del_btn__update = function(task_id, enabled) {
      var btn = xo.bldr.core.get_elem('work', task_id, 'del_work');
      btn.disabled = !enabled;
      btn.style.opacity = enabled ? '1.0' : '0.2';
    }

    // update head row
    this.head__update = function() {
      // calculate stats
      var head_item_len = 0;
      var head_data_end = 0;
      var head_time_cur = 0;
      var head_time_end = 0;
      for (var nde_key in this.regy) {
        var nde = this.regy[nde_key];
        head_item_len += 1;
        head_data_end += nde.prog_data_end;
        head_time_cur += nde.prog_time_end - nde.prog_time_cur;
        head_time_end += nde.prog_time_end;
      }
      
      // update ui
      xo.elem.get('head:0:name').textContent = head_item_len + ' item(s)';
      // xo.elem.get('head:0:size').textContent = xo.iosize.to_str(head_data_end);
      // xo.elem.get('head:0:time').textContent = xo.time.to_dhms(head_time_cur);
    }
    this.prog__update__recv = function(msg) {xo.bldr.work.prog__update(JSON.parse(msg)); return true;}
    this.prog__update = function(args) {
      // calc time_til
      var prog_data_cur = args.prog_data_cur;
      var prog_data_end = args.prog_data_end;
      var cur_rate = args.prog_rate;
      var time_til = (prog_data_end - prog_data_cur) / cur_rate;
      var time_til_str = xo.time.to_dhms(Math.ceil(time_til));

      // update cmd
      var task_id = args.task_id;
      var task = xo.bldr.work.regy[task_id];
      var cmd = task.step.cmd;
      cmd.prog_data_cur = prog_data_cur;
      cmd.prog_time_cur = prog_data_cur / cur_rate;      

      // update pbar
      var pct = (prog_data_cur / prog_data_end);
      var prog_msg = xo.bldr.work.prog__msg_make(cmd.cmd_name, (pct * 100), time_til_str, cmd);
      xo.bldr.core.get_elem('work', task_id, 'pbar-txt').innerHTML = prog_msg;
      xo.bldr.core.get_elem('work', task_id, 'pbar-bar').style.width = (pct * 100) + '%';
      
      /*
      // update top_nde time;
      var top_subs = top_nde.subs;
      var top_subs_len = top_subs.length;
      var top_time_cur = 0, top_time_end = 0;
      for (var i = 0; i < top_subs_len; ++i) {
        var sub_nde = top_subs[i];
        top_time_cur += sub_nde.prog_time_cur;
        top_time_end += sub_nde.prog_time_end;
      }
      top_nde.prog_time_cur = top_time_cur;
      top_nde.prog_time_end = top_time_end;
      */

      xo.bldr.work.head__update(); 
    }
    this.prog__msg_make = function(name, percent, time_til_str, cmd) {
      var prog_data_cur_str = xo.iosize.to_str(cmd.prog_data_cur);
      var prog_data_end_str = xo.iosize.to_str(cmd.prog_data_end);
      return name + ': ' + (percent | 0) + '% &middot; ' + time_til_str + ' &middot; ' + prog_data_cur_str + ' / ' + prog_data_end_str;
    }
    this.prog__done__recv = function(msg) {xo.bldr.work.prog__done(JSON.parse(msg)); return true;}
    this.prog__done = function(args) {
      xo.log.add(1, 'prog__done.bgn', args);
      var task_id = args.task_id;
      var task_is_done = args.task_is_done;
      var task = xo.bldr.work.regy[task_id];
      var cmd = task.step.cmd;
      task.task_status = xo.bldr.core.STATUS.INIT;

      xo.notify.show(cmd.cmd_name + ' done');
      
      // if last, show extra notification
      if (task_is_done) {
        xo.notify.show(task.task_name + ' done');
        // this.run_btn__update(false, true);
      }
      xo.log.add(1, 'prog__done.end', args);
    }
    this.prog__fail__recv = function(msg) {xo.bldr.work.prog__fail(JSON.parse(msg)); return true;}
    this.prog__fail = function(args) {
      var task_id = args.task_id;
      var err = args.err;
      var resume = args.resume;
      var task = xo.bldr.work.regy[task_id];
            
      // update msg
      var msg_elem = xo.bldr.core.get_elem('work', task.task_id, 'pbar-txt');
      msg_elem.innerHTML = task.step.cmd.cmd_name + ' failed: ' + err;
      
      if (resume)
        this.run_btn__update(true, xo.bldr.core.EXEC.RUN);
      else
        this.run_btn__update(true, xo.bldr.core.EXEC.REDO);
      this.del_btn__update(task.task_id, true);
    };
    this.prog__stat__recv = function(msg) {xo.bldr.work.prog__stat(JSON.parse(msg)); return true;}
    this.prog__stat = function(args) {
      var task_id = args.task_id;
      var msg = args.msg;
      var task = xo.bldr.work.regy[task_id];
            
      // update msg
      var msg_elem = xo.bldr.core.get_elem('work', task.task_id, 'pbar-txt');
      msg_elem.innerHTML = task.step.cmd.cmd_name + ' status: ' + msg;
    };
    this.rate__update__recv = function(msg) {xo.bldr.work.rate__update(JSON.parse(msg)); return true;}
    this.rate__update = function(args) {
      xo.bldr.core.rates[args.task_type] = args.task_rate;
      xo.bldr.work.head__update();
    }      
  }

  xo.admin = new function() {
    this.add_work = function() {
      for (var nde_key in xo.bldr.todo.regy) {
        xo.bldr.todo.add_work__send(nde_key);
        break;
      }
    }
    this.del_work = function() {
      for (var task_id in xo.bldr.work.regy) {
        xo.bldr.work.del_work__send(task_id);
        break;
      }
    }
    this.run_next = function() {
      xo.bldr.work.run_next__send();
    }
    this.stop_cur = function() {
      xo.bldr.work.stop_cur__send();
    }
  }
}(window.xo = window.xo || {}));

// general init
setTimeout(function() { // wait for mustache to load files async
  // initialize buttons
  xo.log.add(2, 'bldr.central:init buttons');
  xo.elem.bind_onclick(xo.admin.add_work, 'admin__add_work__btn');
  xo.elem.bind_onclick(xo.admin.del_work, 'admin__del_work__btn');
  xo.elem.bind_onclick(xo.admin.run_next, 'admin__run_next__btn');
  xo.elem.bind_onclick(xo.admin.stop_cur, 'admin__stop_cur__btn');
  
  // load head; note: must go before reload b/c reload updates head elements
  xo.log.add(2, 'bldr.central:init head');
  xo.bldr.core.create_row('head', {});

  // request initialization
  xo.log.add(2, 'bldr.central:init reload');
  xo.bldr.core.reload__send();
  
  xo.bldr.work.head__update();

  // initialize notify
  xo.log.add(2, 'bldr.central:init notify');
  xo.notify.elem_anchor = '#head__row';
}, 0);  // was 400
