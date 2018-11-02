if (!window.xowa) {
  window.xowa = {
    root_dir : xowa_root_dir,
    js : {
      jquery : {
        init_done: false,
      },
      mediaWiki: {
        init_done: false,
      },
      doc: {
      },
      win: {
      },
      selection : {
      },
      xtn : {
      },
    },
    css : {},
    cfg : {},
    cookie: {},
    gfs : {},
    app : {},
    page : {},
    server : {},
  };

  xowa.css.add = function(css) {
    var s = document.createElement('style');
    document.getElementsByTagName('head')[0].appendChild(s);
    s.appendChild(document.createTextNode(css));
  };

  xowa.cfg.get = function(key) {
    return window.xowa_global_values[key] || null;
  };
  xowa.cfg.set = function(key, val) {
    window.xowa_global_values[key] = val;
  };

  xowa.cookie = function(key, val, cookieData) {
    if (val == null)  // accessor; EX: $.cookie('key');
        return xowa.cfg.get(key);
    else              // mutator;  EX: $.cookie('key', 'val');
        xowa.cfg.set(key, val);
  };

  xowa.js.load_lib = function(file, callback) {
    var script = document.createElement('script');
    if (callback) {
      script.onload = callback;
    }
    script.async = false;
    script.setAttribute('src', file);
    document.getElementsByTagName('head')[0].appendChild(script);
  };

  xowa.js.jquery.init_callback = function() {
    jQuery.cookie = xowa.cookie;
    jQuery.ready(); //fire the ready event
  };
  xowa.js.jquery.init = function(document) {
    if (xowa.js.jquery.init_done) return;
    xowa.js.load_lib(xowa.root_dir + 'bin/any/xowa/html/res/lib/jquery/jquery-1.11.3.min.js', xowa.js.jquery.init_callback);
    xowa.js.jquery.init_done = true;
  };
    
  xowa.js.mediaWiki.init = function(){
    if (xowa.js.mediaWiki.init_done) return;
    window.mediaWiki = {
        msg: xowa.cfg.get,
        log: function () {},
        config: {
            get: xowa.cfg.get
        },
        //simulate mediaWiki.hook: Execute functions queued for 'wikipage.content' directly, and ignore anything else
        hook: function (name) {
            return name === 'wikipage.content' ? {
              add: function (f) {
                     f(window.jQuery ? jQuery('#mw-content-text') : null);
              },
              remove: function () {},
              fire: function () {}
            } : {
              add: function () {},
              remove: function () {},
              fire: function () {}
            };
        }
    };
    xowa.js.mediaWiki.init_done = true;
  }

  xowa.gfs.arg_yn = function(v) {return v ? "'y'" : "'n'";}
  xowa.gfs.arg_str = function(v) {return "'" + v.replace("'", "''") + "'";}
  xowa.gfs.http_cmd_url = null;
  xowa.gfs.exec = function(cbk, cmd) {
    if (xowa.cfg.get('mode_is_gui')) {
      var rv = xowa_exec('cmd', cmd);
      if (cbk != null)
        cbk(rv);
    }
    else {
      if (xowa.cfg.get('mode_is_http')) {
        var http_cmd_url = xowa.gfs.http_cmd_url;
        if (http_cmd_url == null) {
          xowa.js.jquery.init();
          http_cmd_url = 'http://localhost:' + xowa.cfg.get('http-port') + '/xowa-cmd:';
          xowa.gfs.http_cmd_url = http_cmd_url;
        }
        $.get(http_cmd_url + cmd).done(function(data) {
          if (cbk != null)
            cbk(rv);
        });
      }
      else
        throw 'xowa offline';
    }
  };

  // PURPOSE: focus body so that keyboard up / down will scroll html content after page loads
  xowa.js.win.focus_body = function() {
    var body_elems = document.getElementsByTagName('body');
    if (body_elems == null || body_elems.length == 0) {return false;} // no body found; shouldn't happen
    var body_elem = body_elems[0];
    if (body_elem.contentEditable == 'true') {
      body_elem.selectionStart = 0;
      body_elem.selectionEnd = 0;
      window.setTimeout(function(){body_elem.focus();}, 250);
    }
    else {
      var active_element_name = document.activeElement.nodeName.toLowerCase();
      if (active_element_name == 'html' || active_element_name == 'body') { // no anchor selected
        var elems = document.getElementsByTagName("a");
        if (elems != null && elems.length > 0) {
          elems[0].focus(); // focus first
        }
      }
    }
  };

  // PURPOSE: get vpos for restoring page position when moving forward / backward through pages
  xowa.js.win.vpos_get = function() {
    var getIndex = function(node) {
     var parent = node.parentNode, i = -1, child;
     while (parent && (child = parent.childNodes[++i]))
      if (child == node) return i;
     return -1;
    }
    var getPath = function(node) {
     var parent, path = [], index = getIndex(node);
     (parent = node.parentNode) && (path = getPath(parent));
     index > -1 && path.push(index);
     return path;
    }
    var sel = window.getSelection();
    if (sel.rangeCount == 0) return document.documentElement.scrollTop + '|0';  // occurs during view-only mode
    var rng = sel.getRangeAt(0);
    var rng_bgn = rng.startContainer;
    var pos = getPath(rng_bgn);
    return document.documentElement.scrollTop + '|' + pos.toString();
  };

  // PURPOSE: set vpos for restoring page position when moving forward / backward through pages
  xowa.js.win.vpos_set = function(node_path, scroll_top) {
    var getNode = function(path) {
      var node = document.documentElement, i = 0, index = 0;
      while((index = path[++i]) > -1) {
        node = node.childNodes[index];
      }
      return node;
    }
    var sel = window.getSelection();
    var rng = document.createRange();
    var path = new Array();
    path = [node_path];
    var nde = getNode(path);

    rng.selectNodeContents(nde);
    rng.setEnd(nde, 0); // removes selection
    sel.removeAllRanges();
    sel.addRange(rng);
    document.documentElement.scrollTop = scroll_top;
    return true;
  };

  // PURPOSE: find text in textarea (Edit / HTML modes)
  xowa.js.win.find_in_textarea_ctx = {};
  xowa.js.win.find_in_textarea = function(find_text, dir_fwd, case_match, wrap_find) {
    var find_in_textarea_main = function(find_text, dir_fwd, case_match, wrap_find) {
      var text_area = document.getElementById('xowa_edit_data_box');
      var full_txt = text_area.value;
      var ctx = xowa.js.win.find_in_textarea_ctx;
      var find_text_is_same = find_text == ctx.prv_find_text;
      ctx.prv_find_text = find_text;
      if (!case_match) {
        find_text = find_text.toLowerCase();
        full_txt = full_txt.toLowerCase();
      }
      var find_bgn = dir_fwd ? 0 : 9999999;
      var sel_bgn = text_area.selectionStart; 
      if (sel_bgn > -1 && find_text_is_same)  // selection active and find_text_is same; move pos forward to select next item
        find_bgn = sel_bgn + (dir_fwd ? 1 : -1);
      var found = find_text_in_textarea(text_area, dir_fwd, find_text, full_txt, find_bgn);
      if (!found && wrap_find) {
        find_bgn = dir_fwd ? 0 : 9999999;
        found = find_text_in_textarea(text_area, dir_fwd, find_text, full_txt, find_bgn);
      }  
      return find_bgn;
    }
    var find_text_in_textarea = function(text_area, dir_fwd, find_text, full_txt, find_bgn) {
      if (dir_fwd)
        find_bgn = full_txt.indexOf(find_text, find_bgn);
      else
        find_bgn = full_txt.lastIndexOf(find_text, find_bgn);
      if (find_bgn == -1) return false;
      var find_end = find_bgn + find_text.length;

      text_area.setSelectionRange(find_bgn, find_bgn + 1);  // select first character of find
      var ev = document.createEvent('KeyEvents');
      ev.initKeyEvent('keypress', true, true, window, false, false, false, false, 0, text_area.value.charCodeAt(find_bgn));  // simulate keypress to replace firstchar with itself;
      text_area.dispatchEvent(ev); // scrolls
      text_area.setSelectionRange(find_bgn, find_end);
      return true;
    }
    return find_in_textarea_main(find_text, dir_fwd, case_match, wrap_find);
  };
  
  // PURPOSE: scroll elem with id into view; used when navigating to Page#Header and scrolling page to #Header
  xowa.js.win.scroll_elem_into_view = function(elem_id) {
    var select_by_id_proc = function(id) {
      var sel = window.getSelection();
      sel.removeAllRanges();

      var nde = document.getElementById(id);
      if (nde == null) return false;
      highlight_nde(nde);
      return true;
    }
    var highlight_nde = function(nde) {  
      var rng = document.createRange();
      if (nde.childNodes.length > 0) nde = nde.childNodes[0];
      rng.selectNodeContents(nde);
      rng.setStart(nde, 0); // removes selection
      rng.setEnd(nde, 0); // removes selection

      // set selection
      var sel = window.getSelection();
      sel.removeAllRanges();
      sel.addRange(rng);

      // scroll to selection NOTE: this will not work for <p>'s that are taller than the window
      // window.setTimeout(function(){scrollIntoView_proc(nde);}, 100);
      scrollIntoView_proc(nde);
    }
    var scrollIntoView_proc = function (t) {
      if (typeof(t) != 'object') return;

      if (t.getRangeAt) { // t is selection
        if (t.rangeCount == 0) return;
        t = t.getRangeAt(0);
      }

      // if t is not an element, then move up hierarchy until element which accepts scrollIntoView()
      o = t;
      while (o && o.nodeType != 1) o = o.previousSibling;
      t = o || t.parentNode;
      if (t) t.scrollIntoView(true);
    }
    return select_by_id_proc(elem_id);
  };
  
  // PURPOSE: print preview
  xowa.js.win.print_preview = function() {
    window.print();
  };

  // PURPOSE: copy
  xowa.js.selection.get_text_or_href = function() {
    var sel = window.getSelection();            // check if anything is highlighted
    if (sel != null) {
      var sel_str = sel.toString();
      if (sel_str != "")
        return "1|" + sel;
    }
    var nde = document.activeElement;           // default to active anchor; typically this will be whatever is "clicked"
    if (nde == null) return "0|";
    return (nde.tagName == "A")                 // nothing clicked
      ? "2|" + nde.href
      : "0|"
      ;  
  };

  // PURPOSE: open selected text in new tab; Ctrl+G,Ctrl+F
  xowa.js.selection.get_href_or_text = function() {
    var sel_obj = window.getSelection(); if (sel_obj == null) return "0|";
    var sel_text = "1|" + sel_obj.toString();
    if (sel_obj.rangeCount == 0) return sel_text;
    var range = sel_obj.getRangeAt(0); if (range == null) return sel_text;
    var count = 0;
    var node = range.commonAncestorContainer;
    while (count < 8) { // NOTE: needs to be at least 3 to handle nested html inside anchor; EX.WP:[[Portal:Baseball/Categories and Main topics|<span style="color: black;">'''Categories & Topics'''</span>]]; also EX.WP:Wikipedia:Featured topics/Battlecruisers of Germany;
      if (node == null) return sel_text;
      if (node.attributes != null) {
        var atr = node.attributes['href'];
        if (atr != null) return "2|" + atr.value;
      }
      count = count + 1;
      node = node.parentNode;
    }
    return sel_text;
  };
  
  // PURPOSE: (1) middle-click on link to open in new tab; (2) highlight text and open in new tab
  xowa.js.selection.get_active_or_selection = function() {
    var nde = document.activeElement;           // default to active anchor; typically this will be whatever is "clicked"
    if (nde != null && nde.tagName == "A")      // anchor clicked
      return "2|" + nde.href;                   // return anchor's href
    var sel = window.getSelection();            // check if anything is highlighted
    if (sel != null) {
      var sel_str = sel.toString();
      if (sel_str != "")
        return "1|" + sel;                      // return highlighted text
    }
    return "0|";                                // return nothing
  };
  
  // PURPOSE: save file as
  xowa.js.selection.get_src_or_empty = function() {
    var nde = document.activeElement;           // default to active anchor; typically this will be whatever is "right-clicked"
    if (nde == null) return '';
    if (nde.tagName != 'A') return '';
    var subElements = nde.getElementsByTagName('IMG');
    if (subElements == null) return '';
    var subElement = subElements[0];
    if (subElement == null) return '';
    return subElement.src;
  };
  
  // PURPOSE: keyboard command: Ctrl+G,Ctrl+G
  xowa.js.selection.toggle_focus_for_anchor = function() {
    // selects node with blue background around text (same as highlighting text with mouse)
    var select_nde = function(nde2) {  
      // create rng
      var rng = document.createRange();
      rng.selectNodeContents(nde2);
      var child_len = nde2.childNodes.length;
      if (child_len == 0) {   // no children; shouldn't happen
        rng.setStart(nde2, 0);
        rng.setEnd(nde2, nde2.textContent.length);
      }
      else {                  // children exists; select 1st and nth;
        rng.setStart(nde2.childNodes[0], 0);
        var nth_nde = nde2.childNodes[child_len - 1];
        rng.setEnd(nth_nde, nth_nde.textContent.length);
      }

      // set selection
      var sel = window.getSelection();
      sel.removeAllRanges();
      sel.addRange(rng);    
    }
    var sel = window.getSelection();
    if (sel.rangeCount == 0) return;  // nothing selected; exit
    var rng = sel.getRangeAt(0);
    var nde1 = rng.startContainer;
    var count = 0;
    while (count < 8) {         // traverse up dom from selected node; 8 is arbitrary limit
      if (nde1 == null) return; // null-check, but also guards against not finding <a> in current selection;
      if (nde1.attributes != null) {
        var href = nde1.attributes['href'];
        if (href != null) {     // nde1 has href
          if (count == 0) {     // nde1 was <a> when count == 0; indicates that <a> was selected
            select_nde(nde1);   // select "internal text"
            nde1.blur();
            document.getElementsByTagName('body')[0].focus();
          }
          else {                // nde1 was "internal text" when count == 0; note that nde1 is now <a> at count == 1
            nde1.focus();       // focus nde1 which is now an <a>
          }
          return;
        }
      }
      count = count + 1;
      nde1 = nde1.parentNode;
    }
  }

  // PURPOSE: get active link when in editable mode (needs changes to Swt_browser) DATE:2015-06-23
  xowa.js.selection.get_active_for_editable_mode = function(atr_key, or_val) {
    var sel_obj = window.getSelection(); if (sel_obj == null) return or_val;
    if (sel_obj.rangeCount == 0) return or_val;
    var range = sel_obj.getRangeAt(0); if (range == null) return or_val;
    var count = 0;
    var node = range.commonAncestorContainer;
    // NOTE: needs to be at least 3 to handle nested html inside anchor
    // PAGE:en.w:Portal:Baseball/Categories and Main topics|<span style="color: black;">'''Categories & Topics'''</span>]]
    // PAGE:en.w:Wikipedia:Featured topics/Battlecruisers of Germany
    while (count < 8) { 
      if (node == null) return or_val;
      if (node.attributes != null) {
        var atr = node.attributes[atr_key];
        if (atr != null) return atr.value;
      }
      count = count + 1;
      node = node.parentNode;
    }
    return or_val;
  };

  // PURPOSE: used when clicking on file to get xowa_title
  xowa.js.doc.root_html_get = function() {
    return document.getElementsByTagName("html")[0].innerHTML;
  };
  
  // PURPOSE: focuses html_box when leaving other gui widgets (escape key)
  xowa.js.doc.elem_focus = function(elem_id) {
    var nde = document.getElementById(elem_id);
    if (nde != null)
      nde.focus();
  };

  // PURPOSE: Math and Score to delete <pre> after replacing with content
  xowa.js.doc.elem_delete = function(elem_id) {
    var elem = document.getElementById(elem_id);
    elem.parentNode.removeChild(elem);
    return true;
  };

  // PURPOSE: async search
  xowa.js.doc.elem_append_above = function(elem_id, html) {
    var elem = document.getElementById(elem_id);
    elem.insertAdjacentHTML('beforebegin', html);
    
    xowa.js.doc.process_new_elem(elem.parentNode);  // NOTE: elem is placeholder item; html is inserted after it; need to call process_new_elem on parentNode; DATE:2015-08-03
  };

  // PURPOSE: process new element such as adding bindings; DATE:2015-07-09
  xowa.js.doc.process_new_elem = function(elem) {
    if (elem == null) elem = document;
    xowa_popups_bind_to_owner($(elem));
  }
  
  // PURPOSE: async search; gallery; imap
  xowa.js.doc.elem_replace_html = function(elem_id, html) {
    var elem = document.getElementById(elem_id);
    elem.insertAdjacentHTML('beforebegin', html);
    elem.parentNode.removeChild(elem); 
  };

  // PURPOSE: show image on page
  xowa.js.doc.elem_img_update = function(elem_id, elem_src, elem_width, elem_height) {
    if (document == null) return false;
    var elem = document.getElementById(elem_id);
    if (elem == null) return false;
    elem.src = elem_src;
    elem.width = elem_width;
    elem.height = elem_height;
    return true;
  };

  // PURPOSE: prefs_mgr; get edit_box val
  xowa.js.doc.atr_get_as_obj = function(elem_id, atr_key) {
    return document.getElementById(elem_id)[atr_key];
  };
  
  // PURPOSE: prefs_mgr; note that toString() is needed for bool which somehow comes back strangely through SWT
  xowa.js.doc.atr_get_to_str = function(elem_id, atr_key) {
    return document.getElementById(elem_id)[atr_key].toString();
  };

  // PURPOSE: search; set cancel_icon
  xowa.js.doc.atr_set = function(elem_id, atr_key, atr_val) {
    document.getElementById(elem_id).setAttribute(atr_key, atr_val);
    return true;
  };

  // PURPOSE: append val or set it if empty; used by redlink
  xowa.js.doc.atr_append_or_set = function(elem_id, atr_key, val) {
    var elem = document.getElementById(elem_id);
    var atr_val = elem[atr_key];
    if (atr_val && atr_val.length > 0)
      atr_val = atr_val + ' ' + val;
    else
      atr_val = val;
    elem.setAttribute(atr_key, atr_val);
    return true;
  };

  // PURPOSE: called by packed gallery. EX: en.w:National_Gallery_of_Art
  xowa.js.xtn.gallery_packed_exec = function() {
    gallery_packed_exec(jQuery, mediaWiki);
  };
  
  // PURPOSE: get or create object by path; EX: "obj__get('xowa', 'usr', 'bookmarks');" will create window.xowa.usr.bookmarks; DATE:2015-07-19
  obj__get = function(owner) {
    var len = arguments.length;
    for (var i = 1; i < len; ++i) {
      var arg = arguments[i];
      var next_owner = owner[arg];
      if (!next_owner) {
        next_owner = {};
        owner[arg] = next_owner;
      }
      owner = next_owner;
    }
    return owner;
  }

  function xowa_toggle_visible(prefix, visible) {
    var icon = document.getElementById(prefix + '-toggle-icon');
    var elem = document.getElementById(prefix + '-toggle-elem');
    if (visible == null)                       // visible not passed
      visible = !(elem.style.display === '');  // default to reverse of current visible state
    if (visible) {
      elem.style.display = '';
      icon.title = xowa_global_values.show;      
      icon.src = xowa_root_dir + 'bin/any/xowa/file/app.general/twisty_down.png';
    }
    else {
      elem.style.display = 'none';
      icon.title = xowa_global_values.hide;
      icon.src = xowa_root_dir + 'bin/any/xowa/file/app.general/twisty_right.png';
    }
    // xowa.gfs.exec(null, 'xowa.api.html.page.toggles.get(' + xowa.gfs.arg_str(prefix) + ').visible = ' + xowa.gfs.arg_yn(visible) + ';');
    xowa.gfs.exec(null, "app.cfg.set('app', 'xowa.html.toggles." + prefix + "', " + xowa.gfs.arg_yn(visible) + ");");
  };
  
  function xowa_elem_select(elem) {
    var rng = document.createRange();
    rng.selectNodeContents(elem);// select entire range
    rng.setEnd(elem, 0); // remove selection
    var sel = window.getSelection();
    sel.removeAllRanges();
    sel.addRange(rng);
  }
  window.navigate_to = function(href) { // XOWA: expose publicly for alertify
    window.location = href;
  };
}