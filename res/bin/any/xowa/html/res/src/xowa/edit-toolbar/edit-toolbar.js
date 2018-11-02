/* edit-toolbar.js: Provides a toolbar for editing pages
Copyright (C) 2013 Schnark (<https://de.wikipedia.org/wiki/Benutzer:Schnark>)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
(function () {
"use strict";
var toolbar, editbox;

function i18n (id) {
	if (window.xowa_edit_i18n && window.xowa_edit_i18n[id]) {
		return window.xowa_edit_i18n[id]; //this line alone should work, but just for the case it doesn't ...
	}
	return id.replace(/_.*/, '');
}

function insertText (pre, sample, post, ownline, splitlines) {
	var start, end, scroll, text, prenl = '', postnl = '', insert, lines, i;
	editbox.focus();
	start = editbox.selectionStart;
	end = editbox.selectionEnd;
	scroll = editbox.scrollTop;
	text = editbox.value;
	text = [text.substring(0, start), text.substring(start, end), text.substring(end)];
	if (ownline && start > 0 && text[0].charAt(start - 1) !== '\n') {
		prenl = '\n';
	}
	if (ownline && text[2].charAt(0) !== '\n') {
		postnl = '\n';
	}
	if (start === end) {
		insert = prenl + pre + sample + post + postnl;
		start = start + prenl.length + pre.length;
		end = start + sample.length;
	} else {
		if (splitlines) {
			lines = text[1].split(/\n/);
			for (i = 0; i < lines.length; i++) {
				if (lines[i] !== '') {
					lines[i] = pre + lines[i] + post;
				}
			}
			insert = prenl + lines.join('\n') + postnl;
		} else {
			insert = prenl + pre + text[1] + post + postnl;
		}
		start = start + insert.length;
		end = start;
	}
	editbox.value = text[0] + insert + text[2];
	editbox.scrollTop = scroll;
	editbox.selectionStart = start;
	editbox.selectionEnd = end;
}

function insertButton (icon, title, pre, sample, post, ownline, splitlines) {
	var img = document.createElement('img');
	img.setAttribute('style', 'padding: 2px; cursor: pointer; border: 1px solid black; border-radius: 5px;');
	img.src = xowa_root_dir + '/bin/any/xowa/html/res/src/xowa/edit-toolbar/img/' + icon;
	img.title = title;
	img.onclick = function () {
		insertText(pre, sample, post, ownline, splitlines);
	};
	toolbar.appendChild(img);
}

function createBar (tools) {
	var i;
	toolbar = document.createElement('div');
	editbox.parentNode.insertBefore(toolbar, editbox);
	for (i = 0; i < tools.length; i++) {
		insertButton(tools[i][0], tools[i][1], tools[i][2], tools[i][3], tools[i][4], tools[i][5], tools[i][6]);
	}
}

function init () {
	editbox = document.getElementById('xowa_edit_data_box');
	createBar([
		['format-bold-A.png', i18n('bold_tip'), "'''", i18n('bold_sample'), "'''"],
		['format-italic-A.png', i18n('italic_tip'), "''", i18n('italic_sample'), "''"],
		['insert-link.png', i18n('link_tip'), '[[', i18n('link_sample'), ']]'],
		['High-contrast-format-text-underline.svg.png', i18n('headline_tip'), '== ', i18n('headline_sample'), ' ==', true],
		['format-ulist.png', i18n('ulist_tip'), '* ', i18n('ulist_sample'), '', true, true],
		['format-olist.png', i18n('olist_tip'), '# ', i18n('olist_sample'), '', true, true]	]);
}

document.addEventListener('DOMContentLoaded', init, false);

})();