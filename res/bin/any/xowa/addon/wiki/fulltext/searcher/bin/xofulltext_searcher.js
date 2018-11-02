(function (xo) {
  xo.fulltext_searcher = new function() {
    //{ SECTION:util
    this.send = function(proc, args) {
      args.page_guid = xo_page_guid;
      try {xo.server.send_by_bridge('xowa.wiki.fulltext.searcher', proc, args);}
      catch (err) {alert(err);}
    }
    
    this.alerts_enabled = false;
    this.handle_err = function(s) {
      if (this.alerts_enabled) {
        alert(s);
      }
      else {
        console.log(s);
      }
    }
    //}
    
    //{ SECTION:document.onload
    this.loaded = false;    
    this.onload = function(evt) {
      // onload fires multiple times on drd; only run once
      if (this.loaded) return;
      this.loaded = true;
      
      // init notify anchor; focus search_btn
      xo.notify.elem_anchor = '#main_div';
      xo.elem.get('search_txt').focus();

      // run search      
      var query = xo.elem.get_val_or_null('search_txt');
      if (query && query.length > 0) {
        this.search_run();
      }
    }
    //}
    
    //{ SECTION:search_run
    this.search_is_running = false;
    this.search_run = function() {    
      /*
      * runs search automatically when page is loaded
      * uses data from doc_elems...
      ** visible; EX: search_txt
      ** hidden; EX: qarg_wikis      
      * Doc_elems are filled in when page is generated from...
      ** query args; EX: &wikis=a|b
      ** cfg; EX: xowa.addon.search.fulltext.special.show_all_snips
      
      Note that code is involved b/c
      * need to have each search generate its own url b/c...
      ** http_server
      ** moving bwd / fwd through history)
      * want to minimize unnecessary qargs based on ...
      ** implied scope; EX: &wikis=en.wikipedia.org when current wiki is en.wikipedia.org
      ** cfg; EX: &show_all_snips=1
      */
      try {
        // get search_text
        var search_text = xo.elem.get_val_or_null('search_txt');
        search_text = search_text.replace(/ /g, "_");    // replace " " with "_"; this replacement is done automatically in drd.WebView, but is done manually here with swt.swtbrowser for consistency
        
        // get data specified by user
        var msg = 
        { search:                 xo.elem.get_val_or_null('search_txt')
        , cur_wiki:               xo.elem.get_val_or_null('cur_wiki')
        };

        // get data specified by qargs
        this.search_run__add_qarg_data(msg, this.qarg_keys);

        // clear results; activate cancel; focus search box
        this.results__clear();
        this.search_cxl__show(true);
        xo.elem.get('search_btn').focus();

        // send msg
        this.send('search_run', msg);
        this.search_is_running = true;
      } catch (err) {
        this.handle_err(err);
      }
    }
    this.search_run__add_qarg_data = function(msg, keys) {
      // adds qarg data to search_run msg 
      var len = keys.length;
      for (var i = 0; i < len; i++) {
        var key = 'qarg_' + keys[i];
        msg[key] = xo.elem.get_val_or_null(key); // relies on msg.key == elem.id
      }      
    }
    //}

    //{ SECTION:search_redirect
    this.qarg_keys = ['wikis', 'ns_ids', 'limits', 'offsets', 'expand_pages', 'expand_snips', 'show_all_snips'];
    this.search_redirect = function() {
      // redirects to new search page with new query args when search is pressed
      try {
        // get search_text
        var search_text = xo.elem.get_val_or_null('search_txt');

        // if search_text has changed, reset offsets; EX: on 21-40 of 'earth'; changing search to 'moon' should start from 1, not 21
        if (search_text !== xo.elem.get_val_or_null('qarg_search')) {
          xo.elem.get('qarg_offsets').value = '0';
        }
        
        // build url
        var path_prefix = xowa.app.mode == 'http_server'
          ? '/'
          : '/site/';
        var url = path_prefix + xo.elem.get_val_or_null('cur_wiki') + '/wiki/Special:XowaSearch?search=' + search_text;
        var len = this.qarg_keys.length;
        for (var i = 0; i < len; i++) {
          url = this.search_redirect__add_to_url(url, this.qarg_keys[i]);
        }

        // redirect
        window.navigate_to(url);
      } catch (err) {
        this.handle_err(err);
      }
    }
    this.search_redirect__add_to_url = function(url, key) {
      // only add qarg to url if it's been specified by user
      // get qarg / dflt values
      var qarg_val = xo.elem.get_val_or_null('qarg_' + key);
      var dflt_val = xo.elem.get_val_or_null('dflt_' + key);
      
      // if qarg === dflt, return url
      if (qarg_val === dflt_val) {
        return url;
      }
      // else, qarg changed; append it to url
      else {
        return url + '&' + key + '=' + qarg_val;
      }
    }
    this.search_keydown = function(e) {
      if(e.keyCode === 13){
        e.preventDefault(); // squash further notifications
        this.search_redirect();
      }
    }
    //}
    
    //{ SECTION:search_cxl
    this.search_cxl = function() {
      if (this.search_is_running) {
        this.send('search_cxl', {page_guid: xo.elem.get_val_or_null('page_guid')});      
        xo.elem.get('search_cxl_msg').style.display = 'block';
      }
    }
    this.search_cxl__show = function(show) {
      if (show) {
        xo.elem.get('search_cxl_btn').classList.add('search_cxl_pulse');
      }
      else {
        xo.elem.get('search_cxl_btn').classList.remove('search_cxl_pulse');
        xo.elem.get('search_cxl_msg').style.display = 'none';
      }
    }
    //}

    //{ SECTION:paging
    this.paging_fwd = function(wiki) {this.paging_dir(wiki, true);}
    this.paging_bwd = function(wiki) {this.paging_dir(wiki, false);}
    this.paging_dir = function(wiki, fwd) {
      try {
        // get wiki_idx for wiki from qarg_wikis; needed for offset / limit; EX: wikis=a|b offsets=0|20
        var wiki_idx = -1;
        var qarg_wikis_str = xo.elem.get_val_or_null('qarg_wikis');
        var qarg_wikis_ary = qarg_wikis_str.split('|');
        var qarg_wikis_len = qarg_wikis_ary.length;
        for (var i = 0; i < qarg_wikis_len; i++) {
          var qarg_wiki = qarg_wikis_ary[i];
          if (wiki === qarg_wiki) {
            wiki_idx = i;
            break;
          }
        }
        if (wiki_idx == -1) {
          this.handle_err('could not find wiki_idx; wiki=' + wiki + ' qargs=' + qarg_wikis_str);
          return;
        }

        // get offset / limit
        var limit      = this.paging_dir__get_val_or_0th(wiki_idx, 'qarg_limits');
        var old_offset = this.paging_dir__get_val_or_0th(wiki_idx, 'qarg_offsets');
        
        // calc new_offset
        var new_offset = old_offset + limit * (fwd ? 1 : -1);
        if (new_offset < 0) {
          new_offset = 0;      // handle negative offsets; EX: old_offset=20 -> limit changed to 30 -> new_offset=-10 -> new_offset=0
          if (old_offset == 0) // ignore noops at BOS; EX: old_offset=0 -> limit changed to 30 -> new_offset = -30 -> new_offset=0 -> don't reload page since new_offset = old_offset
            return;
        }
        
        // set it and redirect
        this.paging_dir__set_val('qarg_offsets', wiki_idx, qarg_wikis_len, new_offset);
        this.search_redirect();
      } catch (err) {
        this.handle_err(err);
      }
    }
    this.paging_dir__get_val_or_0th = function(wiki_idx, key) {
      // get ary; EX: offset=0|20
      var ary = xo.elem.get_val_or_null(key).split('|');
      var ary_len = ary.length;
      
      // get nth val based on wiki_idx
      var rv = wiki_idx < ary_len 
        ? ary[wiki_idx]     // nth exists;  EX: wikis=a|b offsets=0 wiki=a -> wiki_idx=0 -> return i
        : ary[ary_len - 1]  // nth missing; EX: wikis=a|b offsets=0 wiki=b -> wiki_idx=1 -> return nth
      ;
      return parseInt(rv);
    }
    this.paging_dir__set_val = function(key, wiki_idx, wikis_len, val) {
      // get ary; EX: offset=0|20
      var ary = xo.elem.get_val_or_null(key).split('|');
      var ary_len = ary.length;
      var s = '';
      
      // concat new_val by iterating over old_val
      for (var i = 0; i < wikis_len; i++) {
        var item = '';
        // item is cur_wiki; use it
        if (i == wiki_idx) {
          item = val.toString();
        }
        else {
          // item is other_wiki; use nth if available, or else 0th
          item = i < ary_len ? ary[i] : ary[0];
        }
        if (i != 0) s+= '|';
        s += item;
      }
      
      // set new val
      var elem = document.getElementById(key);
      elem.value = s;
    }
    //}
    
    //{ SECTION:results
    //{ SECTION:results_misc
    this.results__clear = function() {
      var elem = xo.elem.get('results_wikis');
      elem.innerHTML = "<div id='results_wikis_last'>&nbsp;</div>";      
    }
    this.results__done__recv = function(msg) {return this.results__done(JSON.parse(msg));}
    this.results__done = function(msg) {
      this.search_cxl__show(false);
      this.search_is_running = false;
      return true;
    }
    //}
    
    //{ SECTION:add procs
    this.results__wiki__add__recv = function(msg) {return this.results__wiki__add(JSON.parse(msg));}
    this.results__wiki__add = function(msg) {
      try {
        // set expand_pages vars
        var expand_pages = msg.expand_pages;
        msg.expand_pages_icon  = expand_pages ? 'xoimg_toggle_hide' : 'xoimg_toggle_show';
        msg.expand_pages_style = expand_pages ? 'block' : 'none';

        // create wiki-grp elem
        var div = xo.elem.make('results_wikis', 'div');
        xo.tmpl.fmt('xofts.wiki', div, msg);
        
        // hide '(searching ###)' if lucene (only show for wikitext searches)
        if (msg.type_is_lucene) {
          elem = document.getElementById("results_wiki_" + msg.wiki + "_searched_div");
          elem.style.display = 'none';
        }
      } catch (err) {
        this.handle_err(err + " proc=results__wiki__add; msg=" + JSON.stringify(msg));
      }
      return true;
    }
    this.results__page__add__recv = function(msg) {return this.results__page__add(JSON.parse(msg));}
    this.results__page__add = function(msg) {
      try {
        // get vars
        var wiki = msg.wiki;
        
        // get vars for href.page
        var page_ttl = msg.page_ttl;
        msg.page_href = page_ttl.replace(/\'/g, '%27'); // escape apos, else will break "href=''"
        msg.page_name = page_ttl.replace(/_/g, " ");    // replace _ for display purposes

        // set expand_snips vars
        var expand_snips = msg.expand_snips;
        msg.expand_snips_icon  = expand_snips ? 'xoimg_toggle_hide' : 'xoimg_toggle_show';
        msg.expand_snips_style = expand_snips ? 'block' : 'none';

        // create wiki_div html
        var wiki_div_key = "results_wiki_" + wiki + "_content";        
        // wiki_div sometimes doesn't exist when (a) XOWA-desktop is first starting; and (b) restoring a Search in 1st tab; if it doesn't exist, create it
        if (!xo.elem.get(wiki_div_key)) {
          this.results__wiki__add({wiki: wiki});
        }
        var div = xo.elem.make(wiki_div_key, 'div');
        xo.tmpl.fmt('xofts.page', div, msg);
        
        // publish elem_add event for popups
        var page_elem_key = 'results_wiki_' + wiki + '_page_' + msg.page_id;
        var page_elem = xo.elem.get(page_elem_key);        
        xo.elem.elem_add__pub(page_elem);        
      } catch (err) {
        this.handle_err(err + " proc=results__page__add; msg=" + JSON.stringify(msg));
      }
      return true;
    }
    this.results__line__add__recv = function(msg) {return this.results__line__add(JSON.parse(msg));}
    this.results__line__add = function(msg) {
      try {
        // make page_div key
        var wiki = msg.wiki;
        var page_id = msg.page_id;        
        var div_key = 'results_wiki_' + wiki + '_page_' + page_id + '_content';
        
        // generate html
        var div = xo.elem.make(div_key, 'div');
        xo.tmpl.fmt('xofts.snip', div, msg);
      } catch (err) {
        this.handle_err(err + " msg=" + JSON.stringify(msg));
      }
      return true;
    }
    //}
    
    //{ SECTION:update procs
    this.results__wiki__update__recv = function(msg) {return this.results__wiki__update(JSON.parse(msg));}
    this.results__wiki__update = function(msg) {
      try {
        elem = document.getElementById("results_wiki_" + msg.wiki + "_searched");
        elem.textContent = msg.searched;
        
      } catch (err) {
        this.handle_err(err + " proc=results__wiki__update msg=" + JSON.stringify(msg));
      }
      return true;      
    }
    this.results__page__update__recv = function(msg) {return this.results__page__update(JSON.parse(msg));}
    this.results__page__update = function(msg) {
      try {
        var wiki = msg.wiki;
        var page_id = msg.page_id;
        var found = msg.found;
        var show_all_snips = msg.show_all_snips;
        
        var elem = document.getElementById("results_wiki_" + wiki + "_page_" + page_id + "_found");
        elem.textContent = found;
        
        if (found > 1 && !show_all_snips) {
          var elem = document.getElementById('results_wiki_' + wiki + '_page_' + page_id + '_rest');          
          elem.style.display = 'initial';
        }
      } catch (err) {
        this.handle_err(err + " msg=" + JSON.stringify(msg));
      }
      return true;      
    }
    //}
    //}
    
    //{ SECTION:snips_show_all
    this.snips_show_all = function(qry_id, wiki, page_id) {
      try {
        // hide snips_show_allbutton
        var elem = document.getElementById('results_wiki_' + wiki + '_page_' + page_id + '_rest');
        elem.style.display = 'none';
        
        // always expand toggle_page indicator
        this.show_div
        ( xo.elem.get('results_wiki_' + wiki + '_page_' + page_id + '_content')
        , xo.elem.get('results_wiki_' + wiki + '_page_' + page_id + '_img')
        , true
        );

        // remove focus to button
        document.activeElement.blur();
        
        // send message
        var data = 
        { qry_id:                 qry_id
        , wiki:                   wiki
        , page_id:                page_id
        };
        this.send('snips_show_all', data);        
      } catch (err) {
        this.handle_err(err);
      }
    }
    //}

    //{ SECTION:options_save
    this.options_save = function() {
      try {
        this.send('options_save', 
        { 'expand_options':  xo.elem.get('xo_options_list_container').style.display === 'block'
        });
      } catch (err) {
        this.handle_err(err);
      }
    }
    //}
    
    //{ SECTION:toggle_div
    this.toggle_div = function(div_id, img_id) {
      var div_elem = document.getElementById(div_id);
      var img_elem = document.getElementById(img_id);
      var show = div_elem.style.display === "block";
      return this.show_div(div_elem, img_elem, !show);
    }
    this.show_div = function(div_elem, img_elem, show) {
      if (show) {
        div_elem.style.display = "block";
        img_elem.classList.remove("xoimg_toggle_show");
        img_elem.classList.add   ("xoimg_toggle_hide");
      }
      else {
        div_elem.style.display = "none";
        img_elem.classList.remove("xoimg_toggle_hide");
        img_elem.classList.add   ("xoimg_toggle_show");
      }
      return true;
    }
    //}
  }
}(window.xo = window.xo || {}));
/*
TOMBSTONE: onload does not work on droid where WM overrides onload
if(window.onload) {
    var onload_old = window.onload;
    var onload_new = function(evt) {
        onload_old(evt);
        xo.fulltext_searcher.onload(evt);
    };
    window.onload = onload_new;
} else {
    window.onload = xo.fulltext_searcher.onload;
}
*/
window.onbeforeunload = function(event) {  
  xo.fulltext_searcher.search_cxl();
};
setTimeout(function() { // wait for mustache to load files async
  xo.fulltext_searcher.onload({});
}, 0);  // was 400
