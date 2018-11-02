//copied from https://de.wikipedia.org/wiki/Benutzer:Schnark/js/popuprefs.js, four lines commented out for XOWA
/* Dokumentation unter [[Benutzer:Schnark/js/popuprefs]] <nowiki>
<ref>s werden beim Überfahren mit der Maus als kleines Popup angezeigt. */

/*global mediaWiki*/

(function($, mw){
var	config = {
		showDelay: 200,
		hideDelay: 200,
		//style: 'position: fixed; z-index: 1000; background-color: InfoBackground; border: 1px solid InfoText; padding: 2px;'
		style: 'position: fixed; z-index: 1000; padding: 5px; border: 2px solid #00f; background-color: #def; max-width: 500px; overflow: auto;'
		//style: 'position: fixed; z-index: 1000; padding: 5px; border: 2px solid #080086; background-color: #f7f7f7; max-width: 500px; overflow: auto; box-shadow: 2px 4px 2px rgba(0,0,0,0.3); -moz-box-shadow: 2px 4px 2px rgba(0,0,0,0.3); -webkit-box-shadow: 2px 4px 2px rgba(0,0,0,0.3);'
	};

function show ($this, e) {
	var footnote = document.getElementById(($this.attr('href') || '').replace(/^#/, '')), data;
	footnote = getPopupContent(footnote);
	if (!footnote) {
		return;
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
	$this.data({
		willShow: true,
		showTimer: window.setTimeout(function () {
			$this.data({
				willShow: false,
				popupVisible: true,
				popupRef: reallyShow(footnote, e.clientX, e.clientY).hover(
					function () {
						show($this, e);
					}, function () {
						hide($this);
					})
			});
		}, config.showDelay)
	});
}

function getPopupContent (el) {
	if (!el) {
		return false;
	}
	var	$footnote = $(el).clone(true), //FIXME sortierbare Tabellen, klappbare Inhalte
		$back = $footnote.find('.mw-cite-backlink');
	if ($back.length !== 1) {
		$back = $footnote.find('a').eq(0);
	}
	$back.remove();
	if ($.trim($footnote.text()) === '') {
		return false; //FIXME en hat einige Fußnoten-Vorlagen, bei denen der Inhalt nach dem angesprungenen Element folgt, eine Nicht-Anzeige ist nur eine Notlösung.
	}
	return $footnote;
}

function getPos (x, y, w, h, W, H) {
	var d = 5;
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

function reallyShow ($footnote, x, y) {
	var $window = $(window), $popup, pos;
	$popup = $('<div>').addClass('popupref').append($footnote).appendTo($('#mw-content-text'));
	pos = getPos(x, y, $popup.outerWidth(), $popup.outerHeight(), $window.width(), $window.height());
	return $popup.css({
		left: pos.x,
		top: pos.y,
		maxHeight: $window.height() - 20 //margin, border, padding + noch ein bisschen Sicherheit
	}).hide().fadeIn('fast');
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
		}, config.hideDelay)
	});
}

function reallyHide ($popup) {
	$popup.fadeOut('fast', function () {
		if ($popup.find('sup.reference a, .reference sup a').length) {
			$popup.detach();
		} else {
			$popup.remove();
		}
	});
}

function init ($content) {
	$content.find('sup.reference a, .reference sup a').hover(
		function (e) {
			show($(this), e);
		}, function () {
			hide($(this));
		});
}

//$(document).trigger('loadWikiScript', ['Benutzer:Schnark/js/popuprefs.js', {version: 2.7}]);

//mw.loader.using('mediawiki.util', function () {
//	mw.util.addCSS('.popupref {' + config.style + '}');
	mw.hook('wikipage.content').add(init);
//});

})(jQuery, mediaWiki);
//</nowiki>