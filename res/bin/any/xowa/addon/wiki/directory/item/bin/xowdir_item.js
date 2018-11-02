(function (xo) {
  xo.wiki_directory = new function() {
    this.item_send = function(proc, args) {
      try {xo.server.send_by_bridge('wiki.directory.item', proc, args);}
      catch (err) {alert(err);}
    }
    this.get_elem_val = function(id) {
      var elem = document.getElementById(id);
      return elem == null ? null : elem.value;
    }
    this.item__save = function() {
      xo.wiki_directory.notify__clear();
      var data = 
      {
        id:       this.get_elem_val('id_lbl')
      , domain:   this.get_elem_val('domain_box')
      , name:     this.get_elem_val('name_box')
      , dir:      this.get_elem_val('dir_box')
      , mainpage: this.get_elem_val('mainpage_box')
      };
      xo.wiki_directory.item_send('save', data);
    }
    this.item__delete = function() {
      var data = 
      {
        id:     this.get_elem_val('id_lbl')
      , domain: this.get_elem_val('domain_box')
      };
      xo.wiki_directory.item_send('delete', data);
    }
    this.item__reindex_search = function() {
      xo.notify.elem_anchor = '#cur_help_div';  // moved here b/c of report of crash when New personal wiki is opened
      xo.wiki_directory.item_send('reindex_search', {domain:this.get_elem_val('domain_box')});
    }

    this.io_cmd__select = function() {
      // call xowa to launch file_dialog
      var file_path = xowa_exec('scripts_exec', 'app.gui.kit.ask_dir("Please select a folder");');
      if (file_path == null || file_path == '') return; // nothing selected; exit
      
      // update val
      var elem = document.getElementById('dir_box');
      elem.value = file_path;
    }

    this.notify__clear = function() {
      var elem = document.getElementById('xo_msg_box');
      elem.innerHTML = "";
    }
    this.notify__recv = function(msg_str) {
      try {
        var msg = JSON.parse(msg_str);
        var elem_id = msg.elem_id;
        if (!elem_id) elem_id = 'xo_msg_box';
        var msg_text = msg.msg_text;

        var elem = document.getElementById(elem_id);
        elem.innerHTML = elem.textContent + msg_text;
        return true;      
      } catch (err) {
        alert(err);
        return false;
      }
    }
  }
}(window.xo = window.xo || {}));
