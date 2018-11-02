/* search-suggest.js: Generates a list of search suggestions for a given string
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
/*
name        = 'search-suggest'
xowa_server = 'y'
jquery      = 'n'
mw          = 'n'
;
*/
(function () {
"use strict";

var	currentSearch = '',
	renderedSuggestions = [],
	currentSuggestion = 0,
	searchInput, suggestionList
	;

function receiveSuggestions (search, suggestions) {
	if (search === currentSearch) {
		showSuggestions(suggestions);
	}
}
window.receiveSuggestions = receiveSuggestions;
function fetchSuggestions () {
	if (xowa_mode_is_server)
		xowa_exec_async(function(){}, 'get_search_suggestions', currentSearch, 'receiveSuggestions');
	else
		xowa_exec('get_search_suggestions', currentSearch, 'receiveSuggestions');
}

function renderSuggestion (page_db, page_display) {
	var	textNode = document.createElement('span'),
		linkNode = document.createElement('a'),
		liNode = document.createElement('li');
	textNode.innerHTML = page_display;
	textNode.setAttribute('xowa_page_db', page_db);
	linkNode.href = '/wiki/' + page_db;
	linkNode.appendChild(textNode);
	liNode.appendChild(linkNode);
	return liNode;
}

function showSuggestions (suggestions) {
	if (!suggestions) {
		return;
	}
	suggestionList.style.display = '';
	var i, li;
	for (i = 0; i < suggestions.length; i += 2) {
		var page_db = suggestions[i];
		var page_display = suggestions[i + 1];
		li = renderSuggestion(page_db, page_display),
		renderedSuggestions.push([li, page_db]);
		suggestionList.appendChild(li);
	}
}

function removeSuggestions () {
	currentSuggestion = 0;
	renderedSuggestions = [];
	suggestionList.innerHTML = '';
	suggestionList.style.display = 'none';
}

function updateSuggestions () {
	removeSuggestions();
	currentSearch = searchInput.value;
	fetchSuggestions();
}

function moveSuggestion (d) {
	if (renderedSuggestions.length === 0) {
		return;
	}
	if (currentSuggestion !== 0) {
		renderedSuggestions[currentSuggestion - 1][0].className = '';
	}
	currentSuggestion = (currentSuggestion + d) % (renderedSuggestions.length + 1);
	if (currentSuggestion < 0) {
		currentSuggestion += renderedSuggestions.length + 1; //why doesn't % behave the way I want it to behave?
	}
	if (currentSuggestion !== 0) {
		renderedSuggestions[currentSuggestion - 1][0].className = 'current';
	}
	searchInput.value = currentSuggestion === 0 ? currentSearch : renderedSuggestions[currentSuggestion - 1][1];
}

function onKeyUp (e) {
	switch (e.keyCode) {
	case 27: //ESC
		removeSuggestions();
	case 38: //up
		moveSuggestion(-1);
		break;
	case 40: //down
		moveSuggestion(+1);
		break;
	case 33: // page up
	case 34: // page down
	case 35: // end
	case 36: // home
	case 37: // left
	case 39: // right
	case 16: // shift
	case 17: // ctrl
	case 18: // alt
		break;
	default:
		updateSuggestions();
	}
}

function onKeyDown (e) {
	if (e.keyCode === 13 && currentSuggestion !== 0) {
		renderedSuggestions[currentSuggestion - 1][0].firstChild.click();
		e.preventDefault();
		return false;
	}
}

function init () {
	searchInput = document.getElementById('searchInput');
	suggestionList = document.createElement('ul');
	suggestionList.id = 'xowa-search-suggestions';
	suggestionList.style.display = 'none';
	document.getElementById('simpleSearch').appendChild(suggestionList);
	searchInput.addEventListener('keyup', onKeyUp, false);
	searchInput.addEventListener('keydown', onKeyDown, false);
	searchInput.addEventListener('blur', function () {
		if (document.querySelectorAll('#xowa-search-suggestions:hover').length === 0) { //don't remove the list when the mouse hovers over it, the user probably just clicked on a suggestion
			removeSuggestions();
		}
	}, false);
}

document.addEventListener('DOMContentLoaded', init, false);

})();