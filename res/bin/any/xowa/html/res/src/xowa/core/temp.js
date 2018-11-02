/*</pre>
=== DOM creation ===
<pre>*/
/**
 * Create a new DOM node for the current document.
 *    Basic usage:  var mySpan = newNode('span', "Hello World!")
 *    Supports attributes and event handlers*: var mySpan = newNode('span', {style:"color: red", focus: function(){alert(this)}, id:"hello"}, "World, Hello!")
 *    Also allows nesting to create trees: var myPar = newNode('p', newNode('b',{style:"color: blue"},"Hello"), mySpan)
 *
 * *event handlers, there are some issues with IE6 not registering event handlers on some nodes that are not yet attached to the DOM,
 * it may be safer to add event handlers later manually.
**/
var newNode = window.newNode = function newNode(tagname) {
	var node = document.createElement(tagname);

	for (var i = 1; i < arguments.length; ++i){
		if (typeof arguments[i] === 'string') { // text
			node.appendChild(document.createTextNode(arguments[i]));
		} else if (typeof arguments[i] === 'object') {
			if (arguments[i].nodeName) { //If it is a DOM Node
				node.appendChild(arguments[i]);
			} else { // Attributes (hopefully)
				for (var j in arguments[i]){
					if (j === 'class') { //Classname different because...
						node.className = arguments[i][j];
					} else if (j === 'style') { //Style is special
						node.style.cssText = arguments[i][j];
					} else if (typeof arguments[i][j] === 'function') { //Basic event handlers
						newNode.addEventHandler(node, j, arguments[i][j]);
					} else {
						node.setAttribute(j, arguments[i][j]); //Normal attributes
					}
				}
			}
		}
	}

	node.addEventHandler = function(eventName, handler) {
		newNode.addEventHandler(this, eventName, handler);
	};

	return node;
};

newNode.addEventHandler = function(node, eventName, handler) {
	try{ node.addEventListener(eventName,handler,false); //W3C
	}catch(e){try{ node.attachEvent('on'+eventName,handler,"Language"); //MSIE
	}catch(e){ node['on'+eventName]=handler; }} //Legacy
};

/*</pre>
== Visibility toggling ==
<pre>*/
var VisibilityToggles = window.VisibilityToggles = {
	// toggles[category] = [[show, hide],...]; statuses[category] = [true, false,...]; buttons = <li>
	toggles: {}, statuses: {}, buttons: null,

	// Add a new toggle, adds a Show/Hide category button in the toolbar,
	// and will call show_function and hide_function once on register, and every alternate click.
	register: function (category, show_function, hide_function) {
		var id = 0;
		if (!this.toggles[category]) {
			this.toggles[category] = [];
			this.statuses[category] = [];
		} else {
			id = this.toggles[category].length;
		}
		this.toggles[category].push([show_function, hide_function]);
		this.statuses[category].push(this.currentStatus(category));
		this.addGlobalToggle(category);

		(this.statuses[category][id] ? show_function : hide_function)();

		return function () {
			var statuses = VisibilityToggles.statuses[category];
			statuses[id] = !statuses[id];
			VisibilityToggles.checkGlobalToggle(category);
			return (statuses[id] ? show_function : hide_function)();
		};
	},

	// Add a new global toggle to the side bar
	addGlobalToggle: function(category) {
		if (document.getElementById('p-visibility-'+category))
			return;
		if (!this.buttons) {
			this.buttons = newNode('ul');
			var collapsed = jQuery.cookie("vector-nav-p-visibility") === "false",
				toolbox = newNode('div', {'class': "portal portlet "+(collapsed?"collapsed":"expanded"), 'id': 'p-visibility'},
					newNode('h3', 'Visibility'),
					newNode('div', {'class': "pBody body"}, collapsed ? void(0) : {'style':'display:block;'}, this.buttons)
				);
			var sidebar = document.getElementById('mw-panel') || document.getElementById('column-one');
			var insert = null;
			if ((insert = (document.getElementById('p-lang') || document.getElementById('p-feedback'))))
				sidebar.insertBefore(toolbox, insert);
			else
				sidebar.appendChild(toolbox);
		}
		var status = this.currentStatus(category);
		var newToggle = newNode('li', newNode('a', {
			id: 'p-visibility-' + category, 
			style: 'cursor: pointer',
			href: '#visibility-' + category,
			click: function(e)
			{
				VisibilityToggles.toggleGlobal(category); 
				if (e && e.preventDefault)
					e.preventDefault();
				else 
					window.event.returnValue = false;
				return false; 
			}},
			(status ? 'Hide ' : 'Show ') + category));
		for (var i = 0; i < this.buttons.childNodes.length; i++) {
			if (this.buttons.childNodes[i].id < newToggle.id) {
				this.buttons.insertBefore(newToggle, this.buttons.childNodes[i]);
				return;
			}
		}
		this.buttons.appendChild(newToggle);
	},

	// Update the toggle-all buttons when all things are toggled one way
	checkGlobalToggle: function(category) {
		var statuses = this.statuses[category];
		var status = statuses[0];
		for (var i = 1; i < statuses.length; i++) {
			if (status != statuses[i])
				return;
		}
		document.getElementById('p-visibility-' + category).innerHTML = (status ? 'Hide ' : 'Show ') + category;
	},

	// Toggle all un-toggled elements when the global button is clicked
	toggleGlobal: function(category) {
		var status = document.getElementById('p-visibility-' + category).innerHTML.indexOf('Show ') === 0;
		for (var i = 0; i < this.toggles[category].length; i++ ) {
			if (this.statuses[category][i] != status) {
				this.toggles[category][i][status ? 0 : 1]();
				this.statuses[category][i] = status;
			}
		}
		document.getElementById('p-visibility-' + category).innerHTML = (status ? 'Hide ' : 'Show ') + category;
		var current = jQuery.cookie('Visibility');
		if (!current)
			current = ";";
		current = current.replace(';' + category + ';', ';');
		if (status)
			current = current + category + ";";
		window.setCookie('Visibility', current);
	},

	currentStatus: function(category) {
		if (location.hash.toLowerCase().split('_')[0] == '#' + category.toLowerCase())
			return true;
		if (location.href.search(/[?](.*&)?hidecats=/) > 0)
		{
			var hidecats = location.href;
			hidecats = hidecats.replace(/^[^?]+[?]((?!hidecats=)[^&]*&)*hidecats=/, '');
			hidecats = hidecats.replace(/&.*/, '');
			hidecats = hidecats.split(',');
			for (var i = 0; i < hidecats.length; ++i)
				if (hidecats[i] == category || hidecats[i] == 'all')
					return false;
				else if (hidecats[i] == '!' + category || hidecats[i] == 'none')
					return true;
		}
		if (jQuery.cookie('WiktionaryPreferencesShowNav') == 'true')
			return true;
		if ((jQuery.cookie('Visibility') || "").indexOf(';' + category + ';') >= 0)
			return true;
		// TODO check category-specific cookies
		return false;
	}
};


/*</pre>
===Hidden Quotes===
<pre>*/

function setupHiddenQuotes(li) {
	var HQToggle, liComp, dl;
	var HQShow = 'quotations ▼';
	var HQHide = 'quotations ▲';
	for (var k = 0; k < li.childNodes.length; k++) {
		// Look at each component of the definition.
		liComp = li.childNodes[k];
		if ( liComp.nodeName.toLowerCase() === "dl" && !dl ) {
			dl = liComp;
		}
		// If we find a ul or dl, we have quotes or example sentences, and thus need a button.
		if (/^(ul|UL)$/.test(liComp.nodeName)) {
			HQToggle = newNode('a', {href: 'javascript:(function(){})()'}, '');
			li.insertBefore(newNode('span', {'class': 'HQToggle'}, ' [', HQToggle, ']'), dl || liComp);
			HQToggle.onclick = VisibilityToggles.register('quotations',
				function show() {
					HQToggle.innerHTML = HQHide;
					for (var child = li.firstChild; child; child = child.nextSibling) {
						if (/^(ul|UL)$/.test(child.nodeName)) {
							child.style.display = 'block';
						}
					}
				},
				function hide() {
					HQToggle.innerHTML = HQShow;
					for (var child = li.firstChild; child; child = child.nextSibling) {
						if (/^(ul|UL)$/.test(child.nodeName)) {
							child.style.display = 'none';
						}
					}
				});

			break;
		}
	}
}

jQuery(document).ready(function () {
//	if (mw.config.get('wgNamespaceNumber') === 0) {
		var ols, lis, li;
		// First, find all the ordered lists, i.e. all the series of definitions.
		var ols = document.getElementsByTagName('ol');
		for(var i = 0; i < ols.length; i++) {
		 // Then, for every set, find all the individual definitions.
			for (var j = 0; j < ols[i].childNodes.length; j++) {
				li = ols[i].childNodes[j];
				if (li.nodeName.toUpperCase() == 'LI') {
					setupHiddenQuotes(li);
				}
			}
		}
//	}
});
