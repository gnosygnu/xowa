/*
Based on Schnark's javascript for Reference tooltips
*/
(function($){
var	cfg = {
		show_delay          : xowa.cfg.get('popups-win-show_delay'),
		hide_delay          : xowa.cfg.get('popups-win-hide_delay'),
		max_w               : xowa.cfg.get('popups-win-max_w'),
		max_h               : xowa.cfg.get('popups-win-max_h'),
		show_all_max_w      : xowa.cfg.get('popups-win-show_all_max_w'),
		bind_focus_blur     : xowa.cfg.get('popups-win-bind_focus_blur'),
		bind_hover_area     : xowa.cfg.get('popups-win-bind_hover_area'),
	};
var fudge_size = 8;
  
if (window.xowa_popups_show_update == null) {
  window.xowa_popups_show_update = xowa_popups_show_update;
}
if (window.xowa_popups_hide_all == null) {
  window.xowa_popups_hide_all = xowa_popups_hide_all;
}
if (window.xowa_popups_bind_doc == null) {
  window.xowa_popups_bind_doc = bind_hover_to_doc;
}
if (window.xowa_popups_bind_to_owner == null) {
  window.xowa_popups_bind_to_owner = bind_hover_to_owner;
}
if (window.xowa_popups_bind_to_owner_js == null) {
  window.xowa_popups_bind_to_owner_js = bind_hover_to_owner_js;
  
  // subscribe to elem_add notifications
  if (window.xo != null && window.xo.elem != null) {
    xo.elem.elem_add__sub(window.xowa_popups_bind_to_owner_js);
  }
}
if (window.xowa_popups_bind_elem == null) {
  window.xowa_popups_bind_elem = bind_hover_to;
}
var popup_cache = {};
var popup_next_id = 1;
var protocol_pattern = /^(((http|https|ftp):\/\/)|(javascript:|xowa-cmd:)|#cite_)/;
function show_init(elem, popup_itm, anchor_x, anchor_y) {
  var elem_is_area = elem.prop('tagName') === 'AREA';
  var popup_tooltip = elem_is_area ? elem.data('title') : ''; // only show tooltip if area
  if (elem_is_area) {
    window.status = popup_tooltip;
  }
  if (!popup_itm.html || popup_itm.href === '/wiki/Special:XowaPopupHistory') {
    var html = xowa_exec('popups_get_html', popup_next_id, popup_itm.href, popup_tooltip);
    if (!html) { // html is null; occurs for protocols such as http: and xowa-cmd:
      elem.attr('title', elem.data('title')); // restore tooltip
      return;  
    }
    popup_itm.html = html;
  }
  var popup_id = 'popup_' + (popup_next_id++).toString();
  popup_itm.id = popup_id;
  /*
  */
  // insert popup to body else Read / Edit / View HTML will show on top; use multiple div wrappers to inherit same text styles from MW style sheet; DATE:2015-07-31
  // var $wrapper_1 = $('<div/>',{'class':'mw-body'})        .appendTo('body'); // TOMBSTONE: do not dynamically add element; just use pre-existing item; DATE:2016-12-13
  var $wrapper_1 = $('#xo-popup-div-id');
  var $wrapper_2 = $('<div/>',{'class':'mw-body-content'}).appendTo($wrapper_1);
  var $wrapper_3 = $('<div/>',{'class':'mw-content-ltr'}) .appendTo($wrapper_2);
  var $popup = $('<div/>').attr('id', popup_id).addClass('xowa_popup').append(popup_itm.html).appendTo($wrapper_3);
  // var $popup = $('<div>').attr('id', popup_id).addClass('xowa_popup').append(popup_itm.html).appendTo($('#mw-content-text'));
  var window_max_w = cfg.max_w;
  if (popup_itm.show_all_anchor_x) {
    anchor_x      = popup_itm.show_all_anchor_x;
    window_max_w  = popup_itm.show_all_max_w;
  }
  return show_popup(popup_itm, $popup, anchor_x, anchor_y, window_max_w);
}
function xowa_popups_show_update(mode, href, html) {
  var popup_itm = popup_cache[href];
  if (!popup_itm) return; // shouldn't happen
  var $popup = $("#" + popup_itm.id);
  $popup.html(html);
  popup_itm.html = html;
  var $window = $(window);
  var $anchor = $(popup_itm.anchor);
  var anchor_x = $popup.offset().left;
  var popup_max_w = cfg.max_w;
  if (mode == 'all' && cfg.show_all_max_w != -1) {
    popup_max_w = cfg.show_all_max_w;
    var window_width = $window.width();
    if (anchor_x + popup_max_w > window_width)
      anchor_x = window_width - popup_max_w;
    if (anchor_x < 0) anchor_x = 0;
    popup_itm.show_all_anchor_x = anchor_x;
    popup_itm.show_all_max_w    = popup_max_w;
  }
  var anchor_y = $anchor.offset().top - $window.scrollTop();
  if (anchor_y < 0) anchor_y = 0; // sometimes < 0; not sure why
  show_popup(popup_itm, $popup, anchor_x - fudge_size, anchor_y, popup_max_w);  // need to subtract fudge_size, or else popup drifts rightwards
}
function xowa_popups_hide_all() {
  var now_time = new Date().getTime();
  for (var popup_key in popup_cache) {
    var popup_itm = popup_cache[popup_key];
    if (now_time - popup_itm.show_time < cfg.show_delay) // hide popup only if shown recently; allows popups that were showing to still show while page is loaded
      popup_itm.popup.hide();
  }
}
function show_focus ($this) {
  var ev = {
    clientX : $this.offset().left - $(window).scrollLeft(),
    clientY : $this.offset().top - $(window).scrollTop(),
  }
  show($this, ev);
}
function show ($this, ev) {
  var href = $this.attr('href');
  if (!href || href.length == 0) return;
  if (href.charAt(0) === '#') return;   // ignore "#" which is used for javascript; DATE:2014-08-21
  if (protocol_pattern.test(href)) return;  // ignore hrefs with absolute protocol of "http:", etc. which won't point to XOWA content
  if ($this.hasClass('xowa-hover-off')) return; // ignore href if "xowa-hover-off" (for sidebar itms)
  var popup_itm = popup_cache[href];
  if (!popup_itm) {
    popup_itm = {
      href          : href,
      anchor        : $this,
    };
    popup_cache[href] = popup_itm;
  }  
	data = $this.data();
	if (data.willShow) {
		return;
	}
	if (data.willHide) {
		$this.data('willHide', false);
		window.clearTimeout(data.hideTimer);
		return;
	}
	if (data.popupVisible) {
		return;
	}
  if (!$this.data('title')) {
    $this.data('title', $this.attr('title'));
    $this.attr('title', '');
  }
	$this.data({
		willShow: true,
		showTimer: window.setTimeout(function () {
			$this.data({
				willShow: false,
				popupVisible: true,
				popupRef: show_init($this, popup_itm, ev.clientX, ev.clientY).hover(
					function () {
						show($this, ev);
					}, function () {
						hide($this);
					})
			});
		}, cfg.show_delay)
	});
}
function show_popup(popup_itm, $popup, anchor_x, anchor_y, popup_max_w) {
	var $window = $(window);
	var popup_pos = calc_popup_pos(anchor_x, anchor_y, $popup.outerWidth(), $popup.outerHeight(), $window.width(), $window.height());  
  if (window.xowa.js.mathJax == null)
    window.xowa.js.load_lib(xowa.root_dir + 'bin/any/javascript/xowa/mathjax/xowa_mathjax.js', xowa_mathjax_run); // note that this will only load mathjax if math items are on page
  else
    xowa_mathjax_run();
  bind_hover_to($('a', $popup));  
	$popup.css({
		left: popup_pos.x,
		top : popup_pos.y,
	});
  if (popup_max_w > 0)
    $popup.css({maxWidth: popup_max_w});
  else
    $popup.css({maxWidth  : cfg.max_w});
  var max_h = $window.height() - 20; //margin, border, padding + noch ein bisschen Sicherheit
  if (cfg.max_h > 0 && cfg.max_h < max_h)
    max_h = cfg.max_h;
  $popup.css({maxHeight: max_h});
  popup_itm.popup = $popup;
  popup_itm.show_time = new Date().getTime();
  return $popup.hide().fadeIn('fast');
}
function xowa_mathjax_run() { // NOTE: need indirection via function else null ref when window.xowa.js.mathJax == null
  window.xowa.js.mathJax.run();
}
function calc_popup_pos (x, y, w, h, W, H) {
	var d = fudge_size;  // increase distance to prevent popup from overlapping with link
	if (x + d + w > W) {
		x = W - w - 2;
		d = 20;
		if (x < 0) {
			x = 0;
		}
	} else {
		x = x + d;
	}
	if (y < h + d) {
		if (y + d + h < H) {
			y = y + d;
		} else {
			y = 0;
		}
	} else {
		y = y - h - d;
	}
	return {x: x, y: y};
}
function hide ($this) {
	var data = $this.data();
	if (data.willHide) {
		return;
	}
	if (data.willShow) {
		$this.data('willShow', false);
		window.clearTimeout(data.showTimer);
		return;
	}
	if (!data.popupVisible) {
		return;
	}
	$this.data({
		willHide: true,
		hideTimer: window.setTimeout(function () {
			$this.data({
				willHide: false,
				popupVisible: false
			});
			reallyHide(data.popupRef);
		}, cfg.hide_delay)
	});
}

function reallyHide ($popup) {
	$popup.fadeOut('fast', function () {
		if ($popup.find('a').length) {
			$popup.detach();
		} else {
			$popup.remove();
		}
	});
}
function bind_hover_to_doc() {
  bind_hover_to($('a'));
  if (cfg.bind_hover_area)
    bind_hover_to($('area'));
}
function bind_hover_to_owner_js(owner) {
  bind_hover_to_owner(owner);
}
function bind_hover_to_owner(owner) {  
  bind_hover_to($(owner).find('a'));
  if (cfg.bind_hover_area)
    bind_hover_to($(owner).find('a'));
}
function bind_hover_to(elems) {
	elems.hover(
		function (e) {
			show($(this), e);
		}, function () {
			hide($(this));
		});
  if (cfg.bind_focus_blur) {
    elems.focus(
      function (e) {
        show_focus($(this), e);
      }
    );
    elems.blur(
      function (e) {
        hide($(this), e);
      }
    );
  }
}

$(bind_hover_to_doc);

})(jQuery);
