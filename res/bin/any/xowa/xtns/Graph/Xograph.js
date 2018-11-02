xo.xtns = xo.xtns || {};
xo.xtns.graph = new function() {
  var xolog = new xo.logs.logger('xo.xtns.graph');  
  var vega_cbks = {} // map of callbacks for tabular
  // var page_cache = {}; // TOMBSTONE: do not try to cache requests; multiple requests for same page may start at same time

  this.load_xowa = function(url, opt, callback) {
    this.init_xowa_props(opt);
    
    // cache callback
    var vega_cbk_guid = Uuid_.make();
    vega_cbks[vega_cbk_guid] = callback;

    // call xo_server
    var page_guid = xowa.page.guid;
    xolog.info("get_page.bgn", "page", opt.xowa_page);
    xo.server.send_by_bridge('xowa.app.util.misc', 'page_get', 
    { 'page_get_cbk':'xo.xtns.graph.load_xowa_recv'
    , 'protocol':opt.xowa_protocol
    , 'wiki':opt.xowa_wiki
    , 'page':opt.xowa_page
    , 'page_guid':page_guid
    , 'vega_cbk_guid':vega_cbk_guid
    });
  }

  this.load_xowa_recv = function(msg_str) {
    // parse msg
    var msg = JSON.parse(msg_str);
    xolog.info("get_page.end", "page", msg.page);

    // get page_text
    var page_text = msg.page_text;
    var protocol = msg.protocol;
    switch (protocol) {
      case 'map:':
      case 'tabular:':
        page_text = "{\"jsondata\":" + page_text + "}"; // format as "{jsondata:page_text}"; note that this is done by JsonConfig api
        break;      
    }
    
    // get callback and process page_text
    var callback = vega_cbks[msg.vega_cbk_guid];    
    callback(null, page_text);

    return "pass:load_xowa_recv"; // return something for xoajax
  }
  
  this.init_xowa_props = function(opt) {
    // get protocol
    var url = opt.url;
    var protocol_end = url.indexOf(':/');
    var protocol = 'unknown';
    if (protocol_end != -1) {
      protocol = url.substring(0, protocol_end + 1).toLowerCase(); // +1 to capture ":"; note that graph2.compiled.js compares to "wikiraw:", "map:", etc.
    }

    // get page
    var page = '';
    var wiki = 'commons.wikimedia.org';
    switch (protocol) {
      case 'map:':
        page = replace_at_bgn_or_null(url, "map:///", "Data:");
        break;
      case 'tabular:':
        page = replace_at_bgn_or_null(url, "tabular:///", "Data:");
        break;
      case 'wikiraw:':
        page = replace_at_bgn_or_null(url, "wikiraw:///", "");
        wiki = xowa.page.wiki;
        break;      
    }
    if (page == null) {
      xolog.warn("unknown page protocol", "protocol", protocol, "url", url);
    }
    
    // set xowa_props
    opt.xowa_protocol = protocol;
    opt.xowa_wiki = wiki;
    opt.xowa_page = page;    
  }

  // XOWA: from /modules/graph1.js|graph2.js; modified b/c MW use mw.hook('wikipage.content'); DATE:2018-02-25
	this.drawVegaGraph = function(version, elem, data, callback)
  {
    if (version == 1)
    {
      vg.parse.spec
      ( data
      , function (chart) 
        {
          if (chart)
          {
            chart({el: elem}).update();
          }
        } 
      );      
    }
    else
    {
      vg.parse.spec
      ( data
      , function (error, chart) 
        {
          if (!error) 
          {
            chart({el:elem}).update();
          }
          if (callback)
          {
            callback(error);
          }
        }
      );      
    }
	};
  
  this.exec = function() 
  {
    xolog.info("running graphs");

    var $ = jQuery;
    var $content = $('#content');
    // from graph2.compiled.js; modified b/c MW saves data in mw.config.get('wgGraphSpecs')
		$content.find( '.mw-graph' ).each
    ( function () 
      {
        var elem = this;
        var version = $(this).attr('xo-graph-version');
        var html = $(this).html();
        var spec = JSON.parse(Html_.decode(html));
        xolog.info("running graph", "version", version); //, "data", html);

        // blank out html; run graph
        $(this).html('');
				xo.xtns.graph.drawVegaGraph
        ( version
        , this
        , spec
        , function (error)
          {
            if (error)
              console.log(error);
          }
        );
      }
    );
  }
  var replace_at_bgn_or_null = function(str, find, repl) {
    if (str.startsWith(find))
      return repl + str.substring(find.length, str.length);
    else
      return null;
  }
}
