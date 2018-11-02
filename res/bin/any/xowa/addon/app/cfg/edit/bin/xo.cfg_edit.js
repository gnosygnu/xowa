(function (xo) {
  xo.cfg_edit = new function() {
    this.debug = false;
    //{  util.elem
    this.elem__get = function(id) {
      var elem = document.getElementById(id);
      if (elem == null && xo.cfg_edit.debug) alert('elem__get failed: id=' + id);
      return elem;
    }
    this.elem__val = function(id) {
      var elem = document.getElementById(id);
      return elem == null ? null : elem.value;
    }    
    this.elem__val_ = function(id, val) {
      var elem = document.getElementById(id);
      if (elem == null && xo.cfg_edit.debug) alert('elem__val failed: id=' + id);
      elem.value = val;
    }    
    this.elems__get_by_atr = function(atr) {
      var rv = [];
      
      // get all elems
      var elems = document.getElementsByTagName('*');
      var len = elems.length;
      
      // loop all elems and add found to rv
      for (var i = 0; i < len; i++) {
        if (elems[i].getAttribute(atr) !== null)  // elem has atr
          rv.push(elems[i]);
      }
      return rv;
    }
    //}

    //{  util.xocfg
    this.err__clear = function()  {xo.cfg_edit.err__write('');}
    this.err__write = function(s) {
      var elem = xo.elem.get('xocfg_err');
      if (elem)
        elem.innerHTML = s;
    }
    
    this.cfg_val__set = function(type, key, val) {
      if      (type === 'bool') {
        xo.cfg_edit.elem__get(key).checked = val == "y";
      }
      else if (type === 'io.cmd') {
        var flds = val.split("|");
        xo.cfg_edit.elem__val_(key + '-exe', flds[0]);
        xo.cfg_edit.elem__val_(key + '-arg', flds.length <= 1 ? "" : flds[1]);
      }
      else if (type === 'gui.binding') {
        var flds = val.split("|");
        // xo.cfg_edit.elem__val_(key + '-box', flds[0]);
        var box = xo.cfg_edit.elem__get(key + '-box');
        var len = box.options.length;
        for (var i = 0; i < len; i++) {
          var opt = box.options[i];
          if (opt.text === flds[0]) {
            box.selectedIndex = i;
            break;
          }
        }
        xo.cfg_edit.elem__val_(key + '-ipt', flds.length <= 1 ? "" : flds[1]);
      }
      else {
        xo.cfg_edit.elem__get(key).value = val;
      }
    }
    this.cfg_val__bind_all = function() {
      var elems = xo.cfg_edit.elems__get_by_atr('data-xocfg-key');
      var len = elems.length;
      for (var i = 0; i < len; i++) {
        elems[i].addEventListener('change', xo.cfg_edit.update__send_by_event);
        if (elems[i].tagName === 'BUTTON') {
          elems[i].addEventListener('click', xo.cfg_edit.update__send_by_event);
        }
      }
    }

    this.io_cmd__select = function(io_type, elem_id, msg) {
      // call xowa to launch file_dialog
      var file_path = xowa_exec('scripts_exec', 'app.gui.kit.ask_file("' + msg + '");');
      if (file_path == null || file_path == '') return; // nothing selected; exit
      
      // update val
      var elem = xo.cfg_edit.elem__get(elem_id);
      elem.value = file_path;
      xo.cfg_edit.update__send(elem);
    }

    this.gui_binding__remap_send = function change_binding(key, name, bnd) {
      try {
        xowa_exec('scripts_exec', "app.cfg.run('app', 'xowa.gui.shortcuts.show_remap_win', '" + key + "\n" + name + "\n" + xo.cfg_edit.elem__val(key + '-ipt') + "');");
      } catch (err) {alert(err);}
    }
    this.gui_binding__remap_recv = function(msg_str) {
      try {
        // err.write
        var msg = JSON.parse(msg_str);

        // update
        var ipt_elem = xo.cfg_edit.elem__get(msg.key + '-ipt');
        ipt_elem.value = msg.bnd;
        xo.cfg_edit.update__send(ipt_elem);

        return true;
      } catch (err) {alert(err);}      
    }
    //}
    
    //{  update
    this.update__send_by_event = function(e) {xo.cfg_edit.update__send(e.target);}
    this.update__send = function(elem) {
      try {
        // init vars
        var key = elem.getAttribute("data-xocfg-key");
        var type = elem.getAttribute("data-xocfg-type");

        // read val from ui
        var val = '';
        if      (type === 'bool')            {val = elem.checked ? "y" : "n";}
        else if (type === 'io.cmd-exe')      {val = elem.value + '|' + xo.cfg_edit.elem__val(key + '-arg');}
        else if (type === 'io.cmd-arg')      {val = xo.cfg_edit.elem__val(key + '-exe') + '|' + elem.value;}
        // else if (type === 'gui.binding-box') {val = elem.value + '|' + xo.cfg_edit.elem__val(key + '-ipt');}
        else if (type === 'gui.binding-box') {var box = xo.cfg_edit.elem__get(key + '-box'); val = box_text = box.options[box.selectedIndex].text + '|' + xo.cfg_edit.elem__val(key + '-ipt');}
        else if (type === 'gui.binding-ipt') {var box = xo.cfg_edit.elem__get(key + '-box'); val = box_text = box.options[box.selectedIndex].text + '|' + elem.value;}
        else                                 {val = elem.value;}              

        // send to server
        xo.server.send_by_bridge('xo.cfg_edit', 'update',
        { ctx: xo.cfg_edit.elem__val(key + '__ctx_box')
        , key: key
        , val: val
        , type: type
        });
      } catch (err) {alert(err);}
    }
    this.update__fail = function(msg_str) {
      try {
        // err.write
        var msg = JSON.parse(msg_str);
        var name = xo.cfg_edit.elem__get(msg.key + '__name');
        xo.cfg_edit.err__write('<b>' + msg.new_val + '</b> is invalid for <b>' + name.textContent.trim() + '</b><br/>Error: ' + msg.err);

        // revert val
        xo.cfg_edit.elem__val_(msg.key, msg.old_val);
        return true;
      } catch (err) {alert(err);}
    }
    this.update__pass = function(msg_str) {
      try {
        // err.clear
        var msg = JSON.parse(msg_str);
        xo.cfg_edit.err__clear();
        if (msg.type === 'btn') return true;       // if button, exit
        
        // show undo button
        var undo_elem = xo.cfg_edit.elem__get(msg.key + '__undo');
        undo_elem.classList.remove("xocfg_itm_hide");

        // show confirm icon briefly
        var img = undo_elem.children[0];
        img.className = 'xoimg_btn_x16 xoimg_misc_ok xocfg_pulse';
        setTimeout(function() {
          img.className = 'xoimg_btn_x16 xoimg_list_undo';
        }, 1200);
        return true;
      } catch (err) {alert(err);}
    }
    //}
    
    //{  delete
    this.delete__send = function(key) {
      try {
        xo.cfg_edit.err__clear();

        // get type
        var undo_elem = xo.cfg_edit.elem__get(key + '__undo');
        var type = undo_elem.getAttribute("data-xocfg-type");
        
        // send to server
        xo.server.send_by_bridge('xo.cfg_edit', 'delete',
        { ctx: xo.cfg_edit.elem__val(key + '__ctx_box')
        , key: key
        , type: type
        });
      } catch (err) {alert(err);}
    }
    this.delete__recv = function(msg_str) {
      try {
        var msg = JSON.parse(msg_str);

        // hide icon
        var undo_elem = xo.cfg_edit.elem__get(msg.key + '__undo');
        var undo_img = undo_elem.children[0];
        undo_elem.classList.add("xocfg_itm_hide");      

        // set value
        var type = undo_elem.getAttribute("data-xocfg-type");
        xo.cfg_edit.cfg_val__set(type, msg.key, msg.val);
        return true;
      } catch (err) {alert(err);}
    }
    //}
    
    //{  select
    this.select__by_keypress = function(e, target) {
      switch (e.keyCode) {
        case 38: // up
        case 40: // down;
          if (!e.altKey) // do not fire for alt+down which drops down menu
            this.select__send(target);
          break;
        case 33: // page_up
        case 34: // page_down;
          this.select__send(target);
          break;
      }
    }
    this.select__send = function(elem) {
      try {
        if (elem == null) // occurs when View HTML for SWT Browser
          return;
        xo.cfg_edit.err__clear();
        var key = elem.options[elem.selectedIndex].value;
        xo.server.send_by_bridge('xo.cfg_edit', 'select',
        { ctx: 'app' //xo.cfg_edit.elem__val(key + '__ctx_box')
        , key: key
        });
        
        var options_elem = xo.elem.get('options_lnk');
        if (options_elem) // null on DRD
          options_elem.href = '/wiki/Special:XowaCfg?grp=' + key;
      } catch (err) {alert(err);}
    }
    this.select__recv = function(msg_str) {
      try {
        // write grp via template
        var msg = JSON.parse(msg_str);
        var grp = xo.elem.get('cfg_grps');
        xo.tmpl.fmt('xocfg.grps', grp, msg);
        
        // bind update and popups
        xo.cfg_edit.cfg_val__bind_all();
        try {
          xowa_popups_bind_to_owner(document);
        } catch (err) {}
        return true;
      } catch (err) {alert(err);}
    }    
    //}

    //{  help
    this.help__toggle_all = function() {
      var elems = document.getElementsByClassName('xohelp_div');
      var len = elems.length;
      for (var i = 0; i < len; i++) {
        var elem = elems[i] ;
        var id = elem.id.replace('_help_div', '');
        xo.help.toggle(id);
      }
    }
  //}
  }
}(window.xo = window.xo || {}));

//{  onpageload
xo.cfg_edit.cfg_val__bind_all();
xo.help.add_bottom_margin = false;
setTimeout(function() { // wait for mustache to load files async  
  var elem = xo.cfg_edit.elem__get('xocfg_nav_select');
  // auto-load group; deactivated because first group comes preloaded to have HTML
  xo.cfg_edit.select__send(elem);
  elem.focus();
}, 0);
//}