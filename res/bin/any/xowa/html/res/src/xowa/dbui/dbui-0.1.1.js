"use strict"
// lang
function inherit(child, parent) {
  child.prototype = Object.create(parent.prototype);
};

// util
function dbg() {
  var s = '';
  var arguments_len = arguments.length;
  for (var i = 0; i < arguments_len; ++i) {
    if (i != 0) s += ' ';
    var argument = arguments[i];
    if (argument == null)
      argument = 'NULL';
    else {
      var arg_outer_html = argument.outerHTML;
      if (arg_outer_html == null)
        argument = JSON.stringify(argument);
      else
        argument = arg_outer_html;
    }
    s += "'" + argument + "'";
  }
  console.log(s);
}
function Xo_list() {
  this.length = 0;
  this.ary = {};
  this.add = function(o) {
    this.ary[this.length] = o;
    ++this.length;
  }
  this.get_at = function(i) {
    return this.ary[i];
  }
}


// html dom
function atr__get(elem, key) {
  var rv = null;
  if (elem) {
    var elem_atrs = elem.attributes;
    if (elem_atrs) {
      var atr = elem.attributes[key];
      if (atr)
        rv = atr.value;
    }
  }
  return rv;
}
function atr__set(elem, key, val) {
  var atr = elem.attributes[key];
  if (!atr) {
    atr = document.createAttribute(key);
    elem.setAttributeNode(atr);
  }
  atr.value = val;
}
function atr__get_upwards(elem, key) {
  if (elem) {
    var elem_atrs = elem.attributes;
    if (elem_atrs) {
      var atr = elem.attributes[key];
      if (atr)
        return atr.value;
      else
        return atr__get_upwards(elem.parentNode, key);
    }
  }
  return null;
}
function elem__get(id) {
  return document.getElementById(id);
}
function elem__get_by_class_subs(owner, cls) {
  var rv = new Xo_list();
  elem__get_by_class_subs_recurse(rv, owner, cls);
  return rv;
}
function elem__get_by_class_subs_recurse(rv, owner, cls) {
  var subs = owner.childNodes;
  var len = subs.length;
  var found = 0;
  for (var i = 0; i < len; ++i) {
    var sub = subs[i];
    if (sub.classList && sub.classList.contains(cls)) {
      rv.add(sub);
    }
    found += elem__get_by_class_subs_recurse(rv, sub, cls);
  }
  return found;
}
function elem__del_by_elem(elem) {
  elem.parentNode.removeChild(elem);
}
function err__add(msg) {
  var err_div = document.getElementById('xowa_err_div');
  err_div.style.display = 'block';
  err_div.innerHTML += msg;
}

// dbui
function Dbui_cmd_pair_reg(key_stub, bgn_cmd, end_cmd) {
  var bgn_key = key_stub + '_bgn';
  window.xowa.cmds.add(bgn_key, bgn_cmd);
  if (end_cmd) {
    var end_key = key_stub + '_end';
    window.xowa.cmds.add(end_key, end_cmd);
    bgn_cmd.cbk_key = end_key;
  }
}

function Dbui__bgn() {};
Dbui__bgn.prototype.cbk_key = '';
Dbui__bgn.prototype.exec = function(data) {
  var row_id = data.row_id; if (row_id == null) throw 'no row_id found';
  var elem = elem__get(row_id);
  data.tbl_key  = atr__get_upwards(elem, 'data-dbui-tbl_key');
  data.row_pkey = atr__get(elem, 'data-dbui-row_pkey');
  xowa.cmds.send('json', this.key, this.fill_data(data, elem), this.cbk_key);
};
Dbui__bgn.prototype.fill_data = function(data, elem) {return data;};

function Dbui__delete__bgn() {
  this.exec = function(data) {
    var row_id = data.row_id; if (row_id == null) throw 'no row_id found';
    var elem = elem__get(row_id);
    var delete_confirm_msg = atr__get_upwards(elem, 'data-dbui-delete_confirm_msg');
    if (delete_confirm_msg) {
      if (!confirm(delete_confirm_msg)) return;
    }    
    Dbui__bgn.prototype.exec.call(this, data);
  }
};
inherit(Dbui__delete__bgn, Dbui__bgn);

function Dbui__cancel__bgn() {
  this.exec = function(data) {
    var elem = elem__get(data.row_id);
    elem.innerHTML = elem.dbui_cancel_html;
    xowa.js.doc.process_new_elem(elem);
  }
}
inherit(Dbui__cancel__bgn, Dbui__bgn);

function Dbui__save__bgn() {}
inherit(Dbui__save__bgn, Dbui__bgn);
Dbui__save__bgn.prototype.fill_data = function(data, elem) {    
  var rv = Dbui__bgn.prototype.fill_data(data, elem);
  var cells = elem__get_by_class_subs(elem, 'dbui_cell');
  var len = cells.length;
  var vals = {};
  rv.vals = vals;        
  for (var i = 0; i < len; ++i) {
    var cell = cells.get_at(i);
    vals[i] = {
      key  : atr__get(cell, 'dbui_col'),
      val  : cell.value,
    };
  }
  return rv;
}

function Dbui__reorder__bgn() {}
inherit(Dbui__reorder__bgn, Dbui__bgn);
Dbui__reorder__bgn.prototype.exec = function(data) {
  xowa.cmds.send('json', this.key, data, null);
};

function Dbui__edit__end() {
  this.exec = function(data) {
    var row = elem__get(data.row_id);
    row.dbui_cancel_html = row.innerHTML;
    data.html = data.html.replace(/\\n/g, '\n')
    row.innerHTML = data.html;
  };
}
function Dbui__save__end() {
  this.exec = function(data) {
    var row = elem__get(data.row_id);
    data.html = data.html.replace(/\\n/g, '\n')
    row.innerHTML = data.html;
    xowa.js.doc.process_new_elem(row);
  };
}

function Dbui__delete__end() {
  this.exec = function(data) {
    var row = elem__get(data.row_id);
    elem__del_by_elem(row);
  };
}

function rows__delete(row_id) {
  xowa.cmds.exec('xowa.dbui.delete_bgn' , {'row_id':row_id});
}
function rows__edit(row_id) {
  xowa.cmds.exec('xowa.dbui.edit_bgn'    , {'row_id':row_id});
}
function rows__save(row_id) {
  xowa.cmds.exec('xowa.dbui.save_bgn'   , {'row_id':row_id});
}
function rows__cancel(row_id) {
  xowa.cmds.exec('xowa.dbui.cancel_bgn' , {'row_id':row_id});
}
function rows__reorder(ids) {
  var elem_nth = elem__get(ids[ids.length - 1]);
  var tbl_key = atr__get_upwards(elem_nth, 'data-dbui-tbl_key');  
  var pkeys_str = to_pkeys_str(ids);
  xowa.cmds.exec('xowa.dbui.reorder_bgn' , {'tbl_key':tbl_key, 'pkeys':pkeys_str});
}
function to_pkeys_str(elem_ids) {
  var pkeys = '';
  var len = elem_ids.length;
  for (var i = 0; i < len; ++i) {
    var elem_id = elem_ids[i];
    if (elem_id === '') continue;
    var elem = elem__get(elem_id);
    var pkey = atr__get(elem, 'data-dbui-row_pkey');
    var spr = (pkeys === '') ? '' : '|';
    pkeys += spr + pkey;
  }
  return pkeys;
}
function Dbui__edit__keyup(ev, row_id) {
  var keycode = (typeof ev.which === "number") ? ev.which : ev.keyCode;
  switch (keycode) {
    case 13:
      // if textarea, only "save" if ctrl+enter, not enter
      if (  ev.target.nodeName.toUpperCase() === 'TEXTAREA'
         && !ev.ctrlKey)
         return;
      xowa.cmds.exec('xowa.dbui.save_bgn' , {'row_id':row_id});    
      break;
    case 27:
      xowa.cmds.exec('xowa.dbui.cancel_bgn' , {'row_id':row_id});    
      break;
  }
}
Dbui_cmd_pair_reg('xowa.dbui.delete'  , new Dbui__delete__bgn() , new Dbui__delete__end())
Dbui_cmd_pair_reg('xowa.dbui.edit'    , new Dbui__bgn()         , new Dbui__edit__end())
Dbui_cmd_pair_reg('xowa.dbui.save'    , new Dbui__save__bgn()   , new Dbui__save__end())
Dbui_cmd_pair_reg('xowa.dbui.cancel'  , new Dbui__cancel__bgn() , null);
Dbui_cmd_pair_reg('xowa.dbui.reorder' , new Dbui__reorder__bgn(), null);
