(function (xo) {
    "use strict";
    xo.mode_is_debug = false;
    
    // NOTE: xo.elem is now init'd by default on all pages
    // it will then be init'd a 2nd time for special pages
    // ignore 2nd init else elem_add_subs will be cleared
    if (xo.elem) return;
    
    // standard creation
    xo.elem = new function () {
      this.get = function (elem_id) {
          var rv = document.getElementById(elem_id);
          if (!rv && xo.mode_is_debug) {
              alert('elem is null: ' + elem_id);
          }
          return rv;
      };
      
      this.get_val_or_null = function(id) {
        var elem = document.getElementById(id);
        return elem == null ? null : elem.value;
      }
      this.get_checked_or_fail = function(id) {
        var elem = document.getElementById(id);
        if (elem == null && xo.mode_is_debug) {
          alert('checkbox elem is null: ' + id);
        }
        if (elem.type !== 'checkbox' && xo.mode_is_debug) {
          alert('elem is not a checkbox: ' + id);          
        }
        return elem.checked;
      }

      this.make = function (owner_id, elem_type, elem_id) {
          var rv = document.createElement(elem_type);
          if (elem_id) {rv.id = elem_id; }
          var owner_elem = owner_id ? this.get(owner_id) : document.documentElement;
          if (!owner_elem && xo.mode_is_debug) {
              alert('owner elem is null: ' + owner_id);
          }
          owner_elem.appendChild(rv);
          return rv;
      };
      
      this.del = function (elem_id) {
          xo.log.add(1, 'elem.del.bgn', 'elem_id', elem_id);
          var elem = document.getElementById(elem_id);
          elem.parentNode.removeChild(elem);
          return true;
      };

      this.bind_onclick = function (func_obj, elem_id) {
          this.bind(func_obj, 'onclick', elem_id);
      }
      this.bind = function (func_obj, func_name, elem_id) {
          var elem = this.get(elem_id);
          elem[func_name] = func_obj;
      };
      
      this.insert_html_above = function(elem_id, html) {
        var elem = document.getElementById(elem_id);
        elem.insertAdjacentHTML('beforebegin', html);          
        xo.elem.elem_add__pub(elem.parentNode);
      };
      this.elem_add__subs = [];
      this.elem_add__pub = function(elem) {
        if (elem == null) elem = document;
        var len = xo.elem.elem_add__subs.length;
        for (var i = 0; i < len; i++) {
          xo.elem.elem_add__subs[i](elem);
        }
      }
      this.elem_add__sub = function(func) {
        xo.elem.elem_add__subs.push(func);
      }
      this.selectbox__selected_set = function(sel_id) {
        try {
          var sel = document.getElementById(sel_id);
          var sel_val = sel.getAttribute('data-select-selected');
          var opts = sel.options;
          var opts_len = opts.length;
          for (var i = 0; i < opts_len; i++) {
            var opt = opts[i];
            if (opt.value === sel_val) {
              opt.selected = 'selected';
              break;
            }
          }
        } catch (err) {
          alert(err);
        }
      }        
      this.selectbox__selected_get = function(sel_id) {
        try {
          var sel = document.getElementById(sel_id);
          var opt = sel.options[sel.selectedIndex];
          return opt.value;
        }
        catch (err) {
          alert(err);
        }
      }
    };
}(window.xo = window.xo || {}));
