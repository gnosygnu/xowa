xowa.select_node = function(nde, bgn, end) {  
  // create rng; set bgn, end
  var rng = document.createRange();
  if (nde.childNodes.length > 0) nde = nde.childNodes[0];
  rng.selectNodeContents(nde);
  rng.setStart(nde, bgn);
  rng.setEnd(nde, end);

  // set selection
  var sel = window.getSelection();
  sel.removeAllRanges();
  sel.addRange(rng);
}
xowa.scroll_into_view = function(t) {
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

xowa.js.win.find_in_hdoc_ctx = {};
xowa.js.win.find_in_hdoc = function(find_text, dir_fwd, case_match, wrap_find, highlight_matches) {
  var IGNORE_NODES = ',head,style,title,meta,script,object,iframe,input,';    // list of nodes whose text will be ignored during highlight
  var ctx = xowa.js.win.find_in_hdoc_ctx;
  function find_main(find_text, dir_fwd, case_match, wrap_find, highlight_matches) {
    // highlight all matching nodes
    highlight__clear();
    if (find_text == '') return; // exit else infinite loop
    if (!case_match) find_text = find_text.toLowerCase();
    init_ctx(ctx, highlight_matches);
    var found_nodes_len = highlight__exec(ctx, find_text, case_match);
    if (found_nodes_len == 0) return; // nothing found; exit

    // select current node
    if (ctx.highlight_matches) {  // if !highlight_matches, then use cur_selection_idx; needed for exiting find_box
      ctx.cur_selection_idx = cur_selection_idx__calc(ctx.cur_selection_idx, find_text != ctx.prv_find_text, dir_fwd, wrap_find, found_nodes_len);
    }
    var node = ctx.found_nodes[ctx.cur_selection_idx]; 
    xowa.select_node(node, 0, find_text.length);
    xowa.scroll_into_view(node);
    ctx.prv_find_text = find_text;
  }
  function init_ctx(ctx, highlight_matches) {
    if (ctx.cur_selection_idx == null) ctx.cur_selection_idx = 0; // set to 0 if ctx is just created; do not reset for each find, b/c cur_selection_idx needs to be preserved to search thru document
    ctx.highlight_matches = highlight_matches;
    ctx.manual_selection_nde = manual_selection_nde__calc();
    ctx.manual_selection_idx = null;
    ctx.found_nodes = {}; // reset found nodes
  }
  function manual_selection_nde__calc() { // get manual selection; note that a selection created by find_main's xowa.select_node will be "destroyed" by highlight__clear
    var sel = window.getSelection();
    if (sel.rangeCount == 0) return null; // nothing selected
    if (sel.getRangeAt) {
      var rng = sel.getRangeAt(0);  // handle only 1st range (can multiple ranges ever be selected?)
      if (!ctx.manual_selection_nde) return null;
      return (ctx.manual_selection_nde == rng.startContainer) ? null : rng.startContainer;
    }
    else {
      return rng.anchorNode;
    }
  }
  function cur_selection_idx__calc(cur_select_idx, find_text_is_new, dir_fwd, wrap_find, found_nodes_len) {
    if (ctx.manual_selection_idx != null) { // manual selection; use ctx.manual_selection_idx
      return ctx.manual_selection_idx;
    }
    if (find_text_is_new)  // if find_text_is_new, always pick 1st
      return 0;
    // if find_text_is_same, get next
    var new_select_idx = cur_select_idx;
    if (dir_fwd) {
      new_select_idx = cur_select_idx + 1;
      if (new_select_idx == found_nodes_len) {
        new_select_idx = wrap_find ? 0 : cur_select_idx;
      }
    }
    else {
      new_select_idx = cur_select_idx - 1;
      if (new_select_idx == -1) {
        new_select_idx = wrap_find ? found_nodes_len - 1 : cur_select_idx;
      }        
    }
    return new_select_idx;
  }
  function highlight__exec(ctx, find_text, case_match) {
    var found_idx = 0;
    function visit_nodes_recurse(ctx, node, find_text, case_match) {
      var found = 0;
      if (node.nodeType == 3) { // 3=textNode
        if (ctx.manual_selection_nde == node) {    // set manual_selection_idx if something was selected; this handles situation when user manually selects text on page, and find needs to occur after manual selection
          ctx.manual_selection_idx = found_idx;
        }
        // search node for find_text
        var node_data = node.data;
        if (!case_match) node_data = node_data.toLowerCase();
        var find_pos = node_data.indexOf(find_text);
        if (find_pos > -1) {  // find_text found; create node with find_text only and highlight it
          var new_node = document.createElement('span');
          if (ctx.highlight_matches) // set class if highlight_matches enabled; note that highlight_matches == false when exiting find_box
            new_node.className = 'xowa_find_highlight';
          var new_node_bgn = node.splitText(find_pos);
          var new_node_end = new_node_bgn.splitText(find_text.length);
          var new_node_mid = new_node_bgn.cloneNode(true);
          new_node.appendChild(new_node_mid);
          new_node_bgn.parentNode.replaceChild(new_node, new_node_bgn);
          ctx.found_nodes[found_idx] = new_node;
          found = 1;
          ++found_idx;
        }
      }
      else if (    node.nodeType == 1 // 1=element
               &&  IGNORE_NODES.indexOf(',' + node.nodeName.toLowerCase() + ',') == -1
               ) {
        for (var i = 0; i < node.childNodes.length; ++i) {
          i += visit_nodes_recurse(ctx, node.childNodes[i], find_text, case_match);        
        }
      }
      return found;
    }
    $('body').each(function() {
      visit_nodes_recurse(ctx, this, find_text, case_match);
    });
    return found_idx;
  };
  function highlight__clear() {
    $('body').find('span.xowa_find_highlight').each(function() {
      with (this.parentNode) {
        replaceChild(this.firstChild, this);
        normalize();
      }
    }).end();
  };

  find_main(find_text, dir_fwd, case_match, wrap_find, highlight_matches);
}
