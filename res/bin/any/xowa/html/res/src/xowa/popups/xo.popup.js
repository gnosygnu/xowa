(function($){ // self-invoking anonymous function to reduce pollution of global namespace

var XoPopupMode = {
  Hidden:         'hidden'
, ShowConfirming: 'confirmingShow'
, Shown:          'shown'
, HideConfirming: 'confirmingHide'
}
Object.freeze(XoPopupMode);

var XoPopupItm = (function(){
  XoPopupItm.prototype.Html = null;
  XoPopupItm.prototype.Mode = XoPopupMode.Hidden;
  XoPopupItm.prototype.AnchorElem = null;
  XoPopupItm.prototype.AnchorTitle = null;
  XoPopupItm.prototype.PopupElem = null;
  XoPopupItm.prototype.ShowConfirmedCbk = null;
  XoPopupItm.prototype.HideConfirmedCbk = null;
  XoPopupItm.prototype.HoverX = 0;
  XoPopupItm.prototype.HoverY = 0;
  
  function XoPopupItm(AnchorElem) {
    this.Id = 'popup_' + (XoPopupItm.IdNext++).toString();
    this.AnchorElem = AnchorElem;
  }

  XoPopupItm.IdNext = 1;
  return XoPopupItm;
}());

var XoPopupCfg = (function(){
  function XoPopupCfg() {
    this.WriteLogEnabled = false;
    this.ShowConfirmingDelay            = xowa.cfg.get('popups-win-show_delay');
    this.HideConfirmingDelay            = xowa.cfg.get('popups-win-hide_delay');
    this.MaxW                           = xowa.cfg.get('popups-win-max_w');
    this.MaxH                           = xowa.cfg.get('popups-win-max_h');
    this.ShowAllMaxW                    = xowa.cfg.get('popups-win-show_all_max_w');
    this.AllowPopupsForKeyboardTabbing  = xowa.cfg.get('popups-win-bind_focus_blur');
    this.BindHoverArea                  = xowa.cfg.get('popups-win-bind_hover_area');
  }
  return XoPopupCfg;
}());

var XoPopupMgr = (function(){
  // ----------------------------------
  // Ctor; props
  // ----------------------------------
  function XoPopupMgr() {
    this.BindHoverToDoc();

    // subscribe to callback
    var mgr = this;
    xowa.js.doc.evtElemAdd.sub(
      function(elem) {
        var anchs = $('a', $(elem));
        mgr.BindHoverTo(anchs);
      }
    );
  }
  XoPopupMgr.prototype.Cfg = new XoPopupCfg();
	XoPopupMgr.prototype.Cache = {};
  
  // ----------------------------------
  // Bind <a> / <area> to hover
  // ----------------------------------
  XoPopupMgr.prototype.BindHoverToDoc = function() {
    this.BindHoverTo($('a'));
    if (this.Cfg.BindHoverArea) { // <area> tags have href param; REF:en.w:Samuel_Johnson      
      this.BindHoverTo($('area'));
    }
  }
  XoPopupMgr.prototype.BindHoverTo = function(elems) {
    elems = this.BindHoverToFilter(elems);
    elems.hover
    ( function(ev) {XoPopupMgr.prototype.ShowConfirming($(this), ev);}
    , function()   {XoPopupMgr.prototype.HideConfirming($(this));}
    );
    if (this.Cfg.AllowPopupsForKeyboardTabbing){
      elems.focus(function(ev) {XoPopupMgr.prototype.ShowFocus($(this), ev);});
      elems.blur (function(ev) {XoPopupMgr.prototype.Hide($(this), ev);});
    }
  }
  
  // NOTE: ".*/wiki/File:" is for HTTP: ("/en.wikipedia.org/wiki/File:...") and SWT ("/wiki/File:...")
  XoPopupMgr.ProtocolPattern = /^(((http|https|ftp|mailto):)|(javascript:|xowa-cmd:|.*\/wiki\/(File|Image|Category|Talk|User|Special):)|#cite_|\/fsys)/;
  XoPopupMgr.prototype.BindHoverToFilter = function(elems) {
    var array = [];
    var len = elems.length;
    for (var i = 0; i < len; i++) {
      var $anch = $(elems[i]);
      var href = $anch.attr('href');
      if (!href || href.length == 0) continue; // ignore empty anch; EX: '<a>'
      if (href.charAt(0) === '#') continue;   // ignore "#" which is used for javascript; DATE:2014-08-21
      if (XoPopupMgr.ProtocolPattern.test(href)) continue;  // ignore hrefs with absolute protocol of "http:", etc. which won't point to XOWA content
      if ($anch.hasClass('xowa-hover-off')) continue; // ignore href if "xowa-hover-off" (for sidebar itms)  
      array.push($anch);
    }
    return $(array).map (function () {return this.toArray();}); // REF:https://stackoverflow.com/questions/6867184/turn-array-of-jquery-elements-into-jquery-wrapped-set-of-elements
  }

  // ----------------------------------
  // Show / hide
  // ----------------------------------
  XoPopupMgr.prototype.ShowConfirming = function($anch, ev) {
    if (this.Cfg.WriteLogEnabled) this.WriteLogByAnch($anch, 'XoPopupMgr.ShowConfirming.Bgn');

    // exit unless mode is hidden
    var popupItm = this.GetItmOrNew($anch);
    switch (popupItm.Mode) {
      case XoPopupMode.Hidden:
        popupItm.Mode = XoPopupMode.ShowConfirming;
        break;
      case XoPopupMode.HideConfirming:
        popupItm.Mode = XoPopupMode.Shown;
        window.clearTimeout(popupItm.HideConfirmedCbk);
        return;
      case XoPopupMode.ShowConfirming:
      case XoPopupMode.Shown:
        return;
      default:
        console.log('XoPopupMgr.ShowConfirming:unknown ' + popupItm.Mode);
        return;
    }
    
    // set title to '' b/c we don't want it to show before popup does
    if (!popupItm.AnchorTitle) {
      popupItm.AnchorTitle = $anch.attr('title');
      $anch.attr('title', '');
    }

    // cache X,Y of mouse pointer
    popupItm.HoverX = ev.clientX;
    popupItm.HoverY = ev.clientY;
    
    // set-up callback
    var mgr = this;
    popupItm.ShowConfirmedCbk = window.setTimeout(
      function() {mgr.ShowConfirmed($anch, popupItm);}
    , this.Cfg.ShowConfirmingDelay
    );

    if (this.Cfg.WriteLogEnabled) this.WriteLogByAnch($anch, 'XoPopupMgr.ShowConfirming.End');
  }

  XoPopupMgr.prototype.ShowConfirmed = function($anch, popupItm) {
    if (this.Cfg.WriteLogEnabled) this.WriteLogByAnch($anch, 'XoPopupMgr.ShowConfirmed.Bgn');
    
    // set mode to shown
    popupItm.Mode = XoPopupMode.Shown;

    // create popup
    var html = popupItm.Html;
    if (!html) html = 'retrieving data'
    this.CreatePopup(true, popupItm, popupItm.AnchorElem, html);
    
    // no cached html; call XOWA
    if (!popupItm.Html) {
      var href = $anch.attr('href');
      var mgr = this;
      var showMode = 'init';
      switch (xowa.app.mode) {
        case 'http_server':
          var req = new XMLHttpRequest();
          if (href.startsWith('/wiki/')) href = xowa.page.wiki + href; // Special:XowaCfg and other pages use AJAX to update page content which won't pass through Convert_page; DATE:2018-11-11
          var path = href + '?action=popup&popup_mode=init&popup_id=' + popupItm.Id;
          req.onload = function(e) {
            mgr.UpdatePopupHtml(popupItm.Id, showMode, req.responseText);
          }
          req.open("GET", path, true); // 'false': synchronous.
          req.send(null);
          break;
        case 'mock':
          xowa.popups.Mock_popups_get_html(mgr, 'init', popupItm, href); // NOTE: must be "xowa.popups", not "this", b/c "this" will call prototype first
          break;
        default:
          var elemIsArea = $anch.prop('tagName') === 'AREA';
          var popupTooltip = elemIsArea ? $anch.attr('title') : ''; // only show tooltip if area
          mgr.UpdatePopupHtml(popupItm.Id, showMode, xowa_exec('popups_get_html', "init", popupItm.Id, href, popupTooltip));
          break;
      }
    }
    
    if (this.Cfg.WriteLogEnabled) this.WriteLogByAnch($anch, 'XoPopupMgr.ShowConfirmed.End');
  }
  
  XoPopupMgr.prototype.HideConfirming = function($anch) {
    if (this.Cfg.WriteLogEnabled) this.WriteLogByAnch($anch, 'XoPopupMgr.HideConfirming.Bgn');
    
    // exit unless mode is Shown
    var popupItm = this.GetItmOrNew($anch);    
    switch (popupItm.Mode) {
      case XoPopupMode.Shown:
        popupItm.Mode = XoPopupMode.HideConfirming;
        break;
      case XoPopupMode.ShowConfirming:
        popupItm.Mode = XoPopupMode.Hidden;
        window.clearTimeout(popupItm.ShowConfirmedCbk);
        return;
      case XoPopupMode.HideConfirming:
      case XoPopupMode.Hidden:
        return;
      default:
        console.log('XoPopupMgr.HideConfirming:unknown ' + popupItm.Mode);
        return;
    }
       
    // set-up callback
    var mgr = this;
    popupItm.HideConfirmedCbk = window.setTimeout(function () {mgr.HideConfirmed($anch, popupItm);}, this.Cfg.HideConfirmingDelay);
    
    if (this.Cfg.WriteLogEnabled) this.WriteLogByAnch($anch, 'XoPopupMgr.HideConfirming.End');
  }

  XoPopupMgr.prototype.HideConfirmed = function($anch, popupItm) {
    if (this.Cfg.WriteLogEnabled) this.WriteLogByAnch($anch, 'XoPopupMgr.HideConfirmed.Bgn');
    
    // set to hidden
    popupItm.Mode = XoPopupMode.Hidden;
    
    // fade item out
    var $popup = popupItm.PopupElem;
    if ($popup) {
      $popup.fadeOut('fast', function () {
        $('#' + popupItm.Id + '_body').remove();
      });
    }
    
    if (this.Cfg.WriteLogEnabled) this.WriteLogByAnch($anch, 'XoPopupMgr.HideConfirmed.End');
  }

  // ----------------------------------
  // create / update popup html
  // ----------------------------------
  XoPopupMgr.prototype.CreatePopup = function(create, popupItm, $anch, html) {
    // create popup
    // insert popup to body, else "Read / Edit / View HTML" will show on top; use multiple div wrappers to inherit same text styles from MW style sheet; DATE:2015-07-31
    // var $wrapper_1 = $('<div/>',{'class':'mw-body'}).appendTo('body'); // TOMBSTONE: do not dynamically add element; just use pre-existing item; DATE:2016-12-13
    var $popup = null;
    
    if (create) {
      var $wrapper_1 = $('#xo-popup-div-id');
      var $wrapper_2 = $('<div/>',{'class':'mw-body-content', 'id':popupItm.Id + '_body'}).appendTo($wrapper_1);
      var $wrapper_3 = $('<div/>',{'class':'mw-content-ltr'}) .appendTo($wrapper_2);
      $popup = $('<div/>').attr('id', popupItm.Id).addClass('xowa_popup').append(html).appendTo($wrapper_3);
      // var $popup = $('<div>').attr('id', popup_id).addClass('xowa_popup').append(popupItm.html).appendTo($('#mw-content-text'));
      popupItm.PopupElem = $popup;
    }
    else {
      $popup = popupItm.PopupElem;
    }
    
    // calc position
    var popupX = popupItm.HoverX;
    var popupY = popupItm.HoverY;

    // set popup's left / top
    var $window = $(window);
    var popupPos = XoPopupMgr.AdjustPopupPos(popupX, popupY, $popup.outerWidth(), $popup.outerHeight(), $window.width(), $window.height());
    $popup.css({
      left: popupPos.X,
      top : popupPos.Y,
    });
    
    // default maxW to this.Cfg.MaxW which is ordinarily "-1" which means fit to html; if "all", default to window.width
    var popupMaxW = this.Cfg.MaxW;
    if (popupItm.ShowMode === 'all') {
      popupMaxW = $window.width() - 20; //margin, border, padding
    }
    $popup.css({maxWidth: popupMaxW});

    // default maxH to window.height(); can't be -1, else div won't scroll
    var popupMaxH = $window.height() - 20; //margin, border, padding + noch ein bisschen Sicherheit
    if (this.Cfg.MaxH > 0) { // NOTE: > 0 b/c defaults to -1
      popupMaxH = this.Cfg.MaxH;
    }
    $popup.css({maxHeight: popupMaxH});
    
    // allow popups for any anchor in popup
    this.BindHoverTo($('a', $popup));
    
    // bind popup's hover to anch; allows hovering over popup to keep popup open
    $popup.hover(
      function(ev) {XoPopupMgr.prototype.ShowConfirming($anch, ev);}
    , function()   {XoPopupMgr.prototype.HideConfirming($anch);}
    );
    
    $popup.fadeIn('fast');
  } 
  
  XoPopupMgr.prototype.UpdatePopupHtml = function(popupItmId, showMode, html) {
    // get $anch
    var popupItm = this.Cache[popupItmId];
    var $anch = popupItm.AnchorElem;
    if (this.Cfg.WriteLogEnabled) this.WriteLogByAnch($anch, 'XoPopupMgr.UpdatePopupHtml.Bgn');
    
    // set html
    if (!html) { // html is null; occurs for protocols such as http: and xowa-cmd:
      $anch.attr('title', popupItm.AnchorTitle); // restore tooltip
      return;  
    }
    var mgr = this;
    
    // update html
    var popupHtmlElem = $('#' + popupItm.Id);
    popupHtmlElem.fadeOut('fast', function() {
      popupItm.ShowMode = showMode;
      popupItm.Html = html;
      popupHtmlElem.html(html);
      mgr.CreatePopup(false, popupItm, popupItm.AnchorElem, html);
      popupHtmlElem.fadeIn('fast');
      xowa.js.doc.evtElemAdd.pub(popupItm.PopupElem[0]); // "[0]" -> REF:https://learn.jquery.com/using-jquery-core/faq/how-do-i-pull-a-native-dom-element-from-a-jquery-object/
    });
    
    // only show tooltip if area
    if ($anch.prop('tagName') === 'AREA') {
      window.status = $anch.data('title');
    }

    if (this.Cfg.WriteLogEnabled) this.WriteLogByAnch($anch, 'XoPopupMgr.UpdatePopupHtml.End');
  }

  XoPopupMgr.AdjustPopupPos = function(popupX, popupY, popupW, popupH, windowW, windowH) {    
    // if popupX causes popup to not show entirely on screen, right-align to window-right
    var offsetX = 8;
    if (popupX + popupW + offsetX > windowW) {
      popupX = windowW - popupW - offsetX;
      if (popupX < 0) {
        popupX = 0;
      }
    // else just nudge it by offset to prevent popup from overlapping with link
    } else {
      popupX = popupX + offsetX;
    }
    
    // if popupY causes popup to not show entirely on screen, bottom-align to window-bottom
    var offsetY = 20; // adjust for statusBar
    if (popupY + popupH + offsetY > windowH) {
      popupY = windowH - popupH  - offsetY;
      if (popupY < 0) {
        popupY = 0;
      }
    }
    // else just nudge it by offset to prevent popup from overlapping with link
    else {
      popupY = popupY + offsetY;
    }
    
    return {X: popupX, Y: popupY};
  }

  // ----------------------------------
  // Utility
  // ----------------------------------
  XoPopupMgr.prototype.GetItmOrNew = function($anch) {
    var popupId = $anch.attr('xo_popup_id');
    if (popupId) {
      popupItm = this.Cache[popupId];
    }
    else {
      popupItm = new XoPopupItm($anch);
      $anch.attr('xo_popup_id', popupItm.Id);
      this.Cache[popupItm.Id] = popupItm;
    }
    return popupItm;
  }

  XoPopupMgr.prototype.WriteLogByAnch = function($anch, message) {
    console.log(message + ':' + ' id=' + $anch.attr('xo_popup_id') + ' href=' + $anch.attr('href'));
  }
  
  XoPopupMgr.prototype.dbg = function() {
    var args_len = arguments.length;
    var elem = document.getElementById('siteSub');
    for (var i = 0; i < args_len; i++) {
      elem.innerHTML += '&nbsp;' + arguments[i];
    }
  }

  /*
  XoPopupMgr.prototype.FetchMore = function(popupItmId) {
    var popupItm = this.Cache[popupItmId];
    switch (xowa.app.mode) {
      case 'http_server':
        var href = popupItm.AnchorElem.attr('href');
        var req = new XMLHttpRequest();
        if (href.startsWith('/wiki/')) href = xowa.page.wiki + href; // Special:XowaCfg and other pages use AJAX to update page content which won't pass through Convert_page; DATE:2018-11-11
        var path = href + '?action=popup&popup_mode=more&popup_id=' + popupItmId;
        req.onload = function(e) {
          mgr.UpdatePopupHtml(popupItm.Id, req.responseText);
        }
        req.open("GET", path, true); // 'false': synchronous.
        req.send(null);
        break;
      default:
        this.UpdatePopupHtml(popupItm.Id, xowa_exec('popups_get_html', "more", popupItm.Id));
        break;
    }
  }
  */
  
  return XoPopupMgr;
}());

$(document).ready(function() {
  window.xowa.popups = new XoPopupMgr();
});
})(jQuery);

/*
# TESTING
## SWT
### en.w:Earth
Basic popup
* Hover over planet -> popup
* Hover over Sun -> popup
* Hover over planet -> cached, and still planet (not sun)
* Hover over Sun -> cached, and still planet (not sun)
* Double hover: hover over any link in Sun popup

Show more
* Show more a few times -> make sure vertical scroll bar
* Show all -> make sure popup widens

Ignored links
* xowa-hover-off links should not show
** any link in sidebar
** Article / Talk
** Read, Edit, View HTML
* Http links (scroll down to any link in references)
* Images 
* Reference links (should show Reference tool tip)

### en.w:Samuel_Johnson
Image map via area
(NOTE: this behavior only works in XULRunner; latest Chrome / Firefox no longer captures onmouseenter / onmouseexit for <area>)
* Scroll down to "A literary party... The Club" and hover over any of the figures

### Special:XowaSearch?search=test&fulltext=y
Full-text search
* Hover over any of the search results

----

## HTTP server (Firefox)
### http://localhost:8080/en.wikipedia.org/wiki/Earth

Basic popup
* Same as SWT/Basic_popup above

Hover over image -> should not show "retrieving data"

### http://localhost:8080/home/wiki/Special:XowaCfg?grp=xowa.addon.popups 
AJAX: b/c HTML comes from AJAX which doesn't pass through Convert_page
*/
