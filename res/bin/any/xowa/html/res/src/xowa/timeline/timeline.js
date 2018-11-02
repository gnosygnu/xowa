/* timeline.js: A JavaScript implementation of the MediaWiki extension EasyTimeline
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
/*jshint forin:false, onevar:true*/
"use strict";

/**
 * helper functions
 **/

//extends an object with the values of a second one
function extend (d1, d2, dontoverride) {
	var v;
	for (v in d2) {
		if ((!d1[v] || d2[v]) && !(dontoverride && d1[v])) {
			d1[v] = d2[v];
		}
	}
}

/**
 * functions to parse the code into an object
 **/

//removes all comments (#>block<#, #line), and empty lines (only whitespace)
function removeCommentsAndEmptyLines (code) {
  // 1st regex works fine in xulrunner, but freezes firefox 38, chrome, and webview; replacing with remove_perl_comments; DATE:2015-07-09
  // return code.replace(/((?:[^"#]+|"[^"]*")*)#(?:>[\s\S]*?<#|.*)/g, '$1').replace(/\n(?:\s*\n)+/g, '\n').replace(/^\s+/, '');
  var rv = remove_perl_comments(code);
  rv = rv.replace(/\n(?:\s*\n)+/g, '\n').replace(/^\s+/, '');
	return rv;
}
function remove_perl_comments(str) {
  var rv = "";
  var prv_pos = 0; var len = str.length;
  while (prv_pos < len) {
    var bgn_pos = str.indexOf('#', prv_pos);
    if  (  bgn_pos == -1    // no more #
        || prv_pos == len   // last char
        ) {
      rv += str.substring(prv_pos); // append rest
      break;      
    }
    var end_pos = -1;
    var end_skip = -1;
    if (str[bgn_pos + 1] == '>') {  // #>block#<
      end_pos = str.indexOf('<#', bgn_pos + 2);   // +2: skip "#>"
      end_skip = 2; // skip "#<"
    }
    else {                          // #line\n
      end_pos = str.indexOf('\n', bgn_pos + 1);   // +1: skip "#"
      end_skip = 0; // do not skip \n; retain it; needed for lines like "code #comment\n"
    }
    if (end_pos == -1) {  // end not found; not a comment
      rv += str.substring(prv_pos); // append rest
      break;
    }
    rv += str.substring(prv_pos, bgn_pos);
    prv_pos = end_pos + end_skip;
  }
  console.log(rv);
  return rv;
}
//handles the "Define" and the "Preset" commands
function replaceDefinesAndPresets (code) {
	var match;
	/*jshint boss: true*/
	while (match = (/^define\s+(\$\w+)\s*=\s*(.*?) *$/mi).exec(code)) {
		code = code.replace(new RegExp(match[1].replace(/\$/g, '\\$') + '\\b', 'g'), match[2]);
	}
	/*jshint boss: false*/
	return code.replace(/preset\s*=\s*timevertical[_\s]+onebar[_\s]+unityear/i,
		'PlotArea   = left:45 right:10 top:10 bottom:10\n' +
		'TimeAxis   = orientation:vertical format:yyyy\n' +
		'DateFormat = yyyy\n' +
		'AlignBars  = early\n' +
		'ScaleMajor = unit:year\n' +
		'ScaleMinor = unit:year\n' +
		'PlotData   =\n' +
		'   mark:(line,white) align:left fontsize:S width:20 shift:(20,0)')
		.replace(/preset\s*=\s*timehorizontal[_\s]+autoplacebars[_\s]+unityear/i,
		'ImageSize = height:auto barincrement:20\n' +
		'PlotArea   = left:25 right:25 top:15 bottom:30\n' +
		'TimeAxis   = orientation:horizontal format:yyyy\n' +
		'Colors =\n' +
		'  id:canvas value:gray(0.7)\n' +
		'  id:grid1 value:gray(0.4)\n' +
		'  id:grid2 value:gray(0.2)\n' +
		'BackgroundColors = canvas:canvas\n' +
		'DateFormat = yyyy\n' +
		'AlignBars = justify\n' +
		'ScaleMajor = unit:year grid:grid1\n' +
		'ScaleMinor = unit:year\n' +
		'Legend = orientation:vertical left:35 top:130\n' +
		'PlotData =\n' +
		'       align:left anchor:from fontsize:M width:15 shift:(4,-6) textcolor:black');
}
//parses the arguments string to an object
function parseArguments (args) {
	function unescape (c) {
		switch (c) {
		case '~~': return '~';
		case '~': return '\n';
		case '_': return ' ';
		case '^': return '\t';
		}
	}
	var ret = {}, i, pos;
	args = args.trim();
	args = args.replace(/"[^"]+"|\([^()]+\)|:[^:]+$/g, function (s) {
		return s.replace(/ /g, '_').replace(/\t/g, '^');
	});
	args = args.replace(/:[ _]+/g, ':');
	args = args.split(/ +/);
	for (i = 0; i < args.length; i++) {
		pos = args[i].indexOf(':');
		if (pos === -1) {
			ret.val = args[i];
		} else {
			ret[args[i].substr(0, pos).toLowerCase()] = args[i].substr(pos + 1).replace(/^"(.*)"$/, '$1').replace(/(?:~~?|_|\^)/g, unescape);
		}
	}
	return ret;
}
//splits the code into an array of commands, parses the arguments
function splitIntoCommands (code) {
	var commands = [], lines = code.split('\n'), i, match;
	for (i = 0; i < lines.length; i++) {
		if (lines[i].charAt(0) === ' ' || lines[i].charAt(0) === '\t') {
			commands[commands.length - 1].data.push(parseArguments(lines[i]));
		} else {
			match = /(\S+)\s*([^\s=]*)=\s*(.*)/.exec(lines[i]);
			if (!match) {
				continue;
			}
			if (match[2]) {
				match[3] = 'key:' + match[2] + ' ' + match[3];
			}
			commands.push({name: match[1].toLowerCase(), data: []});
			if (match[3]) {
				commands[commands.length - 1].data.push(parseArguments(match[3]));
			}
		}
	}
	return commands;
}
//parses the arguments for the LineData command
function parseLineData (data, lastLineData) {
	extend(data, lastLineData, true);
	if (data.at) {
		data.type = 'perpendicular';
		return data;
	} else if (data.atpos) {
		data.type = 'parallel';
		return data;
	} else if (data.points) {
		data.type = 'any';
		return data;
	} else {
		extend(lastLineData, data);
		return false;
	}
}
//parses the arguments for the PlotData command
function parsePlotData (data, lastPlotData) {
	if (data.barset && data.barset.toLowerCase() === 'break') {
		lastPlotData.nextBarsetNumber = 0;
		return false;
	}
	if (data.barset && data.barset.toLowerCase() === 'skip') {
		lastPlotData.nextBarsetNumber++;
		return false;
	}
	if (data.bar) {
		lastPlotData.barset = undefined;
		lastPlotData.bar = data.bar;
	} else if (data.barset) {
		lastPlotData.barset = data.barset;
		lastPlotData.bar = undefined;
		lastPlotData.nextBarsetNumber = 0;
	}
	if (data.at || data.from || data.till) {
		extend(data, lastPlotData, true);
		if (data.barset) {
			data.bar = data.barset + '#' + lastPlotData.nextBarsetNumber;
			lastPlotData.nextBarsetNumber++;
		}
	} else {
		extend(lastPlotData, data);
		return false;
	}
	if (data.from && data.till) {
		data.type = 'bar';
		return data;
	} else /*if (data.at)*/ {
		data.type = 'at';
		return data;
	}
}
//parses the arguments for the TextData command
function parseTextData (data, lastTextData) {
	var lineheight;
	if (data.lineheight) {
		lineheight = parseLength(data.lineheight);
	} else if (lastTextData.lineheight) {
		lineheight = parseLength(lastTextData.lineheight);
	} else {
		lineheight = parseLineHeight(data.fontsize);
	}
	lastTextData.lineheight = String(lineheight); //Yes, when you don't specify the fontsize in the first line, the lineheight will be set to the default lineheight for SMALL text, even if you change it afterwards, unless you explicitely set the lineheight. For *every* line of definition the text will go down one line, as determined by the *current* lineheight. This is a complete mess, but that's the way EasyTimeline works.
	if (data.pos) {
		lastTextData.skipY = 0;
	} else {
		lastTextData.skipY += lineheight;
	}
	extend(data, lastTextData, true);
	if (data.text) {
		return data;
	} else {
		extend(lastTextData, data);
		return false;
	}
}
//parses the whole code into an object
function parseCode (code) {
	var	commands = splitIntoCommands(replaceDefinesAndPresets(removeCommentsAndEmptyLines(code))),
		ret = {
			alignBars: 'early',
			backgroundcolors: {canvas: 'white', bars: ''},
			barData: {},
			colors: {},
			dateFormat: 'x.y',
			imageSize: {width: '0', height: '0', barincrement: '0'},
			legend: {show: false, orientation: 'ver', position: 'bottom', columns: '0', columnwidth: '0', left: '0', top: '0'},
			lines: [],
			period: {from: '0', to: '1'},
			plotArea: {left: '0', top: '0', right: '0', bottom: '0'},
			plots: [],
			scaleMajor: {show: false, gridcolor: '', unit: 'year', increment: '1', start: 'start', major: true},
			scaleMinor: {show: false, gridcolor: '', unit: 'year', increment: '1', start: 'start'},
			textData: [],
			timeAxis: {format: 'yyyy', orientation: 'hor', order: ''}
		}, data, i, j,
		lastLineData, lastPlotData, lastTextData;
	for (i = 0; i < commands.length; i++) {
		lastLineData = {width: '2', color: 'black'};
		lastPlotData = {bar: '', anchor: 'middle', color: 'rgb(0,0.6,0)', fontsize: 'S', width: '25'}; //documentation says width is calculated if missing, but I don't see this
		lastTextData = {pos: '(0,0)', textcolor: 'black', fontsize: 'S', tabs: '', skipY: 0};
		for (j = 0; j < commands[i].data.length; j++) {
			data = commands[i].data[j];
			switch (commands[i].name) {
			case 'alignbars':
				if (['early', 'late', 'justify'].indexOf(data.val.toLowerCase()) !== -1) {
					ret.alignBars = data.val.toLowerCase();
				}
				break;
			case 'backgroundcolors':
				extend(ret.backgroundcolors, data);
				break;
			case 'bardata':
				if (data.bar) {
					ret.barData[data.bar] = {
						text: data.text,
						link: data.link
					};
				} else if (data.barset) {
					ret.barData[data.barset] = {
						text: data.text,
						link: data.link
					};
				}
				break;
			case 'colors':
				if (!data.id) {
					continue;
				}
				ret.colors[data.id.toLowerCase()] = {
					value: data.value,
					legend: data.legend
				};
				break;
			case 'dateformat':
				if (['dd/mm/yyyy', 'mm/dd/yyyy', 'yyyy', 'x.y'].indexOf(data.val.toLowerCase()) !== -1) {
					ret.dateFormat = data.val.toLowerCase();
				}
				break;
			case 'imagesize':
				extend(ret.imageSize, data);
				break;
			case 'legend':
				ret.legend.show = true;
				extend(ret.legend, data);
				ret.legend.orientation = ret.legend.orientation.toLowerCase().substr(0, 3);
				break;
			case 'linedata':
				data = parseLineData(data, lastLineData);
				if (data) {
					ret.lines.push(data);
				}
				break;
			case 'period':
				extend(ret.period, data);
				break;
			case 'plotarea':
				extend(ret.plotArea, data);
				break;
			case 'plotdata':
				data = parsePlotData(data, lastPlotData);
				if (data) {
					ret.plots.push(data);
				}
				break;
			case 'scalemajor':
				ret.scaleMajor.show = true;
				if (data.grid) {
					data.gridcolor = data.grid;
				}
				extend(ret.scaleMajor, data);
				break;
			case 'scaleminor':
				ret.scaleMinor.show = true;
				if (data.grid) {
					data.gridcolor = data.grid;
				}
				extend(ret.scaleMinor, data);
				break;
			case 'textdata':
				data = parseTextData(data, lastTextData);
				if (data) {
					ret.textData.push(data);
				}
				break;
			case 'timeaxis':
				extend(ret.timeAxis, data);
				ret.timeAxis.orientation = ret.timeAxis.orientation.toLowerCase().substr(0, 3);
				break;
			}
		}
	}
	return ret;
}

/**
 * functions to parse special formats
 **/

//parses a length (including different units)
function parseLength (l, rel) {
	var f = 1;
	l = String(l).trim();
	if (/in$/.test(l)) {
		f = 100;
	} else if (/cm$/.test(l)) {
		f = 254;
	} else if (/%$/.test(l)) {
		if (rel === undefined) {
			rel = 1;
		}
		f = rel / 100;
	}
	return Number(l.replace(/[^0-9.\-]/g, '')) * f;
}
//parses a color
function parseColor (color, data) {
	var namedColors = {
		tan1: '#e5d3c9', tan2: '#b29999',
		magenta: '#ff4c7f', claret: '#b24c4c', coral: '#ff9999', pink: '#ffcccc',
		orange: '#ff9e23', redorange: '#ff7f00', lightorange: '#ffcc99',
		yellow2: '#eaea00', dullyellow: '#ffe599', yelloworange: '#ffd800', limegreen: '#ccffb2',
		brightgreen: '#00ff00', green: '#00b200', kelleygreen: '#4c994c', teal: '#007f33', drabgreen: '#99cc99', yellowgreen: '#99e599',
		brightblue: '#0000ff', blue: '#0066cc', darkblue: '#000099', oceanblue: '#007fcc', skyblue: '#b2ccff',
		purple: '#770077', lavender: '#ccb2cc', lightpurple: '#aa4caa', powderblue: '#9999ff', powderblue2: '#b2b2ff'
	};
	color = color.toLowerCase();
	if (color in data.colors) {
		color = data.colors[color].value;
	}
	color = color.replace(/\s+/g, '');
	color = color.replace(/^gray\((.*)\)$/, 'rgb($1,$1,$1)');
	color = color.replace(/^rgb\(([\d.]+),([\d.]+),([\d.]+)\)$/, function (dummy, r, g, b) {
		r *= 255; g *= 255; b *= 255;
		r = Math.round(r); g = Math.round(g); b = Math.round(b);
		return 'rgb(' + r + ',' + g + ',' + b + ')';
	});
	color = color.replace(/^hsb\(([\d.]+),([\d.]+),([\d.]+)\)$/, function (dummy, h, s, v) { //documentation says it is HSV
		//see <https://de.wikipedia.org/wiki/HSV-Farbraum#Umrechnung HSV in RGB>
		var h_i, f, p, q, t, r, g, b;
		h_i = Math.floor(6 * h);
		f = 6 * h - h_i;
		if (h_i === 6) {
			h_i = 0;
		}
		p = v * (1 - s);
		q = v * (1 - s * f);
		t = v * (1 - s * (1 - f));
		switch (h_i) {
		case 0: r = v; g = t; b = p; break;
		case 1: r = q; g = v; b = p; break;
		case 2: r = p; g = v; b = t; break;
		case 3: r = p; g = q; b = v; break;
		case 4: r = t; g = p; b = v; break;
		case 5: r = v; g = p; b = q;
		}
		r *= 255; g *= 255; b *= 255;
		r = Math.round(r); g = Math.round(g); b = Math.round(b);
		return 'rgb(' + r + ',' + g + ',' + b + ')';
	});
	if (color in namedColors) {
		color = namedColors[color];
	}
	return color;
}
//parses a date, returns x or y coordinate
function parseTime (time, data) {
	time = data.parseTime(time);
	if (data.timeAxis.order === 'reverse') {
		time = 1 - time;
	}
	if (data.xAxisIsTime) {
		return time * data.canvas.innerWidth + data.canvas.left;
	} else {
		return data.canvas.height - time * data.canvas.innerHeight - data.canvas.bottom;
	}
}
//parses a textsize
function parseTextSize (size) {
	switch ((size || 's').toLowerCase()) {
	case 'xs': return 8;
	case 's': return 10;
	case 'm': return 13;
	case 'l': return 18;
	case 'xl': return 24;
	default:
		if (/^\d+$/.test(size)) {
			return Math.round(Number(size) * 4 / 3);
		}
		return parseLength(size);
	}
}
//parses a textsize for lineheight
function parseLineHeight (size) {
	switch ((size || 's').toLowerCase()) {
	case 'xs': return 11;
	case 's': return 13;
	case 'm': return 16;
	case 'l': return 19;
	case 'xl': return 24;
	default:
		if (/^\d+$/.test(size)) {
			return Math.round(Number(size) * 1.2);
		}
		return parseLength(size) + 2;
	}
}
/**
 * functions to extract useful information from the data (to store them there again)
 **/

//gets the names of all bars from the data
function getBarNames (data) {
	var names = [], allNames = [], allNamesCorr = [], i, j, n, hasBarData = false, c;
	for (n in data.barData) {
		names.push(n);
	}
	if (names.length) {
		hasBarData = true;
	}
	for (i = 0; i < data.plots.length; i++) {
		n = data.plots[i].bar;
		if (allNames.indexOf(n) === -1) {
			allNames.push(n);
		}
		n = n.replace(/#\d+$/, '');
		if (names.indexOf(n) === -1) {
			names.push(n);
		}
	}
	if (hasBarData) {
		for (i = 0; i < names.length; i++) {
			n = names[i]; c = 0;
			for (j = 0; j < allNames.length; j++) {
				if (allNames[j] === n) {
					break;
				}
				if (allNames[j].indexOf(n) === 0 && allNames[j].charAt(n.length) === '#') {
					c++;
				}
			}
			if (c === 0) {
				allNamesCorr.push(n);
			} else {
				for (j = 0; j < c; j++) {
					allNamesCorr.push(n + '#' + j);
				}
			}
		}
		allNames = allNamesCorr;
	}
	return {names: names, allNames: allNames};
}
//gets the canvas size and inner size from the data
function getCanvasSize (data) {
	var ret = {}, barCount = 0, i;
	for (i = 0; i < data.barNames.allNames.length; i++) { //barsets seem to count one bar less than they contain
		if (data.barNames.allNames[i].indexOf('#0') === -1) {
			barCount++;
		}
	}
	data.xAxisIsTime = (data.timeAxis.orientation === 'hor');
	if (data.imageSize.width === 'auto') {
		ret.innerWidth = barCount * parseLength(data.imageSize.barincrement);
		ret.left = parseLength(data.plotArea.left, ret.innerWidth);
		ret.right = parseLength(data.plotArea.right, ret.innerWidth);
		ret.width = ret.left + ret.innerWidth + ret.right;
	} else {
		ret.width = parseLength(data.imageSize.width);
		ret.left = parseLength(data.plotArea.left, ret.width);
		if (data.plotArea.width) {
			ret.innerWidth = parseLength(data.plotArea.width, ret.width);
			ret.right = ret.width - ret.left - ret.innerWidth;
		} else {
			ret.right = parseLength(data.plotArea.right, ret.width);
			ret.innerWidth = ret.width - ret.left - ret.right;
		}
	}
	if (data.imageSize.height === 'auto') {
		ret.innerHeight = barCount * parseLength(data.imageSize.barincrement);
		ret.top = parseLength(data.plotArea.top, ret.innerHeight);
		ret.bottom = parseLength(data.plotArea.bottom, ret.innerHeight);
		ret.height = ret.top + ret.innerHeight + ret.bottom;
	} else {
		ret.height = parseLength(data.imageSize.height);
		ret.bottom = parseLength(data.plotArea.bottom, ret.height);
		if (data.plotArea.height) {
			ret.innerHeight = parseLength(data.plotArea.height, ret.height);
			ret.top = ret.height - ret.bottom - ret.innerHeight;
		} else {
			ret.top = parseLength(data.plotArea.top, ret.height);
			ret.innerHeight = ret.height - ret.top - ret.bottom;
		}
	}
	return ret;
}
//gets the positions of the bars
function getBarPositions (data) {
	var i, j, n, c, l, d, barData = {};
	for (i = 0; i < data.barNames.names.length; i++) {
		barData[data.barNames.names[i]] = {count: 1, width: 0};
	}
	for (i = 0; i < data.plots.length; i++) {
		n = data.plots[i].bar;
		if (/#\d+$/.test(n)) {
			c = Number(n.replace(/^.*#/, '')) + 1;
			n = n.replace(/#\d+$/, '');
		} else {
			c = 0;
		}
		if (n in barData) {
			if (data.plots[i].width) {
				l = parseLength(data.plots[i].width);
				if (l > barData[n].width) {
					barData[n].width = l;
				}
			}
			if (c > barData[n].count) {
				barData[n].count = c;
			}
		}
	}
	l = 0;
	for (n in barData) {
		if (barData[n].width === 0) {
			barData[n].width = 25;
		}
		l += barData[n].width * barData[n].count;
	}
	if (data.xAxisIsTime) {
		l = data.canvas.innerHeight - l;
	} else {
		l = data.canvas.innerWidth - l;
	}
	if (data.alignBars === 'justify') {
		if (data.barNames.allNames.length === 1) {
			d = l / 2;
		} else {
			d = l / (data.barNames.allNames.length - 1);
		}
	} else {
		d = l / data.barNames.allNames.length;
	}
	if ((data.xAxisIsTime && data.alignBars === 'early') ||
		(!data.xAxisIsTime && data.alignBars === 'late') ||
		(data.alignBars === 'justify' && data.barNames.allNames.length === 1)) {
		l = d;
	} else {
		l = 0;
	}
	for (i = 0; i < data.barNames.names.length; i++) {
		n = data.barNames.names[i];
		if (barData[n].count === 1) {
			barData[n].pos = l + barData[n].width / 2;
			l += (barData[n].width + d);
		} else {
			barData[n].pos = l + barData[n].width / 2;
			for (j = 0; j < barData[n].count; j++) {
				barData[n + '#' + j] = {pos: l + barData[n].width / 2};
				l += (barData[n].width + d);
			}
		}
	}
	return barData;
}
//gets the parser for data format
function getTimeParser (data) {
	var ddmmyyyy = function (s) {
		if (s.indexOf('/') === -1) {
			s = '01/01/' + s;
		}
		return (new Date(Number(s.substr(6, 4)), Number(s.substr(3, 2)) - 1, Number(s.substr(0, 2)))).getTime();
	}, mmddyyyy = function (s) {
		if (s.indexOf('/') === -1) {
			s = '01/01/' + s;
		}
		return (new Date(Number(s.substr(6, 4)), Number(s.substr(0, 2)) - 1, Number(s.substr(3, 2)))).getTime();
	}, yyyy = function (s) {
		return Number(s.replace(/[^0-9.\-]+/g, ''));
	}, parser, start, diff;
	switch (data.dateFormat) {
		case 'dd/mm/yyyy': parser = ddmmyyyy; break;
		case 'mm/dd/yyyy': parser = mmddyyyy; break;
		default: parser = yyyy;
	}
	start = parser(data.period.from);
	diff = parser(data.period.till) - start;
	return function (d) {
		if (d === 'start') {
			return 0;
		} else if (d === 'end') {
			return 1;
		} else {
			return (parser(d) - start) / diff;
		}
	};
}

/**
 * functions to draw different things
 */

//draws a link
function drawLink (ctx, target, x, y, w, h) {
	var a;
	target = target.replace(/\s+/g, '_');
	if (!(/^https?:\/\//).test(target)) {
		target = '/wiki/' + target;
	}
	a = document.createElement('a');
	a.style.position = 'absolute';
	a.style.display = 'block';
	a.style.left = x + 'px';
	a.style.top = y + 'px';
	a.style.width = w + 'px';
	a.style.height = h + 'px';
	a.href = target;
	ctx.canvas.parentNode.appendChild(a);
}

//draws text at some position
function drawTextAtPos (ctx, text, x, y, options) {
	var pre, linked, post, link, match, xLink, yLink, wLink, hLink;
	if (options.link) {
		pre = '';
		linked = text;
		post = '';
		link = options.link;
	} else {
		match = /([^\[]*)\[\[?((?:[^\]|]+\|)?)([^\]]+)\]\]?(.*)/.exec(text);
		if (match) {
			pre = match[1];
			linked = match[3];
			post = match[4];
			link = match[2].replace(/\|$/, '') || linked;
		} else {
			pre = text;
			linked = '';
			post = '';
			link = false;
		}
	}
	if (pre) {
		ctx.strokeStyle = options.color;
		ctx.fillStyle = options.color;
		ctx.fillText(pre, x, y);
		if (!linked && !post) {
			return;
		}
		x += ctx.measureText(pre).width;
	}
	if (linked) {
		xLink = x;
		yLink = y;
		wLink = ctx.measureText(linked).width;
		hLink = options.lineheight;
		switch (options.align) {
		case 'center': xLink -= wLink / 2; break;
		case 'right': xLink -= wLink;
		}
		switch (options.valign) {
		case 'middle': yLink -= hLink / 2; break;
		case 'top': break;
		case 'bottom': yLink -= hLink; break;
		default: yLink -= hLink * 0.75; //FIXME
		}
		ctx.strokeStyle = '#4545ed';
		ctx.fillStyle = '#4545ed';
		ctx.fillText(linked, x, y);
		drawLink(ctx, link, Math.round(xLink), Math.round(yLink), wLink, hLink);
		x += wLink;
	}
	if (post) {
		ctx.strokeStyle = options.color;
		ctx.fillStyle = options.color;
		ctx.fillText(post, x, y);
	}
}
//draws text which may contain tabs
function drawMultiTabText (ctx, text, options) {
	var tabChunks, i, pos, rawText, width, x, y;
	tabChunks = text.split('\t');
	y = options.y;
	for (i = 0; i < tabChunks.length; i++) {
		pos = (options.tabs[i - 1] || '0-left').split('-');
		x = options.x + Number(pos[0]);
		if (pos[1] !== 'left') {
			if (options.link) {
				rawText = tabChunks[i];
			} else {
				rawText = tabChunks[i].replace(/\[\[?(?:.*?\|)?([\s\S]*?)\]?\]/g, '$1');
			}
			width = ctx.measureText(rawText).width;
			if (pos[1] === 'right') {
				x -= width;
			} else {
				x -= width / 2;
			}
		}
		drawTextAtPos(ctx, tabChunks[i], x, y, options);
	}
}
//draws text at some position which may contain line breaks
function drawMultiLineText (ctx, data, text, pos) {
	var txt, tabs, color, fontsize, lineheight, lines, options, i;
	txt = text.text;
	if (!text.link) {
		//split links with line breaks inside
		txt = txt.replace(/(\[\[?[^|]+\|)([^\]]+)(\]\]?)/g, function (all, open, text, close) {
			return open + text.replace(/\n/g, close + '\n' + open) + close;
		});
	}
	if (text.tabs && text.tabs.charAt(0) === '(' && text.tabs.charAt(text.tabs.length - 1) === ')') {
		tabs = text.tabs.substr(1, text.tabs.length - 2).split(',');
	} else {
		tabs = [];
	}
	color = parseColor(text.textcolor || 'black', data);
	pos.y += (text.skipY || 0);
	fontsize = parseTextSize(text.fontsize);
	lineheight = parseLength(text.lineheight || '0') || parseLineHeight(text.fontsize);
	lines = txt.split('\n');
	ctx.font = fontsize + 'px sans-serif';
	ctx.textAlign = pos.align;
	ctx.textBaseline = pos.valign || 'alphabetic';
	options = {
		x: pos.x,
		y: pos.y,
		tabs: tabs,
		color: color,
		lineheight: lineheight,
		align: pos.align,
		valign: pos.valign
	};
	for (i = 0; i < lines.length; i++) {
		drawMultiTabText(ctx, lines[i], options);
		options.y += lineheight;
	}
}
//draws one time axis
function drawAxis (ctx, data, axis) {
	var pos, t, y, inc, d, color, textpos;
	ctx.lineWidth = 1;
	ctx.strokeStyle = 'black';
	ctx.fillStyle = 'black';
	ctx.textAlign = 'center';
	color = parseColor(axis.gridcolor, data);
	if (axis.major) {
		ctx.beginPath();
		ctx.moveTo(data.canvas.left, data.canvas.height - data.canvas.bottom);
		ctx.lineTo(data.xAxisIsTime ? data.canvas.width - data.canvas.right : data.canvas.left, data.xAxisIsTime ? data.canvas.height - data.canvas.bottom : data.canvas.top);
		ctx.closePath();
		ctx.stroke();
	}
	t = data.parseTime(axis.start);
	if (axis.start === 'start') {
		y = Number(data.period.from.replace(/^.*\//, ''));
	} else {
		y = Number(axis.start.replace(/^.*\//, ''));
	}
	inc = Number(axis.increment) || 1;
	d = data.parseTime((data.dateFormat === 'yyyy' || data.dateFormat === 'x.y' ? '' : '01/01/') + String(y + inc)) - data.parseTime(String(y));
	if (axis.unit === 'month') {
		inc /= 12;
		d /= 12;
	} else if (axis.unit === 'day') {
		inc /= 365.25;
		d /= 365.25;
	}
	while (t <= 1) {
		if (data.xAxisIsTime) {
			pos = {y: data.canvas.height - data.canvas.bottom};
			if (data.timeAxis.order === 'reverse') {
				pos.x = data.canvas.left + (1 - t) * data.canvas.innerWidth;
			} else {
				pos.x = data.canvas.left + t * data.canvas.innerWidth;
			}
		} else {
			pos = {x: data.canvas.left};
			if (data.timeAxis.order === 'reverse') {
				pos.y = data.canvas.height - data.canvas.bottom - (1 - t) * data.canvas.innerHeight;
			} else {
				pos.y = data.canvas.height - data.canvas.bottom - t * data.canvas.innerHeight;
			}
		}
		if (axis.major) {
			if (data.xAxisIsTime) {
				textpos = {x: pos.x, y: pos.y + 10, align: 'center', valign: 'top'};
			} else {
				textpos = {x: pos.x - 12, y: pos.y, align: 'right', valign: 'middle'};
			}
			drawMultiLineText(ctx, data, {text: String(y)}, textpos);
		}
		ctx.beginPath();
		ctx.moveTo(pos.x, pos.y);
		ctx.lineTo(pos.x - (data.xAxisIsTime ? 0 : (axis.major ? 5 : 3)), pos.y + (data.xAxisIsTime ? (axis.major ? 5 : 3) : 0));
		ctx.closePath();
		ctx.stroke();
		if (color && t !== 0) {
			ctx.strokeStyle = color;
			ctx.beginPath();
			ctx.moveTo(pos.x, pos.y);
			ctx.lineTo(data.xAxisIsTime ? pos.x : data.canvas.left + data.canvas.innerWidth, data.xAxisIsTime ? data.canvas.top : pos.y);
			ctx.closePath();
			ctx.stroke();
			ctx.strokeStyle = 'black';
		}
		y += inc;
		t += d;
	}
}
//draws the data axis
function drawDataAxis (ctx, data) {
	var pos, n;
	ctx.lineWidth = 1;
	ctx.strokeStyle = 'black';
	ctx.fillStyle = 'black';
	ctx.beginPath();
	ctx.moveTo(data.canvas.left, data.canvas.height - data.canvas.bottom);
	ctx.lineTo(data.xAxisIsTime ? data.canvas.left : data.canvas.width - data.canvas.right, data.xAxisIsTime ? data.canvas.top : data.canvas.height - data.canvas.bottom);
	ctx.closePath();
	ctx.stroke();
	for (n in data.barPositions) {
		if (n.indexOf('#') > -1) {
			continue;
		}
		if (data.xAxisIsTime) {
			pos = {x: data.canvas.left - 10, y: data.barPositions[n].pos + data.canvas.top, align: 'right', valign: 'middle'};
		} else {
			pos = {x: data.canvas.left + data.barPositions[n].pos, y: data.canvas.height - data.canvas.bottom + 2, align: 'center', valign: 'top'};
		}
		if (data.barData && data.barData[n]) {
			n = data.barData[n].text;
			if (!n) {
				continue;
			}
		}
		drawMultiLineText(ctx, data, {text: n || '', link: (data.barData && data.barData[n] && data.barData[n].link) || ''}, pos);
	}
}
//draws a line (LineData)
function drawLine (ctx, data, line) {
	var t0, t1, v0, v1, x0, x1, y0, y1, match;
	switch (line.type) {
	case 'parallel':
		t0 = parseTime(line.from || 'start', data);
		t1 = parseTime(line.till || 'end', data);
		v0 = parseLength(line.atpos, data.xAxisIsTime ? data.canvas.height : data.canvas.width);
		v1 = v0;
		if (data.xAxisIsTime) {
			x0 = t0; x1 = t1;
			y0 = data.canvas.height - v0; y1 = data.canvas.height - v1;
		} else {
			x0 = v0; x1 = v1;
			y0 = t0; y1 = t1;
		}
		break;
	case 'perpendicular':
		t0 = parseTime(line.at, data);
		t1 = t0;
		v0 = line.frompos ?
			parseLength(line.frompos, data.xAxisIsTime ? data.canvas.height : data.canvas.width) :
			(data.xAxisIsTime ? data.canvas.bottom : data.canvas.left);
		v1 = line.tillpos ?
			parseLength(line.tillpos, data.xAxisIsTime ? data.canvas.height : data.canvas.width) :
			(data.xAxisIsTime ? data.canvas.height - data.canvas.top : data.canvas.width - data.canvas.right);
		if (data.xAxisIsTime) {
			x0 = t0; x1 = t1;
			y0 = data.canvas.height - v0; y1 = data.canvas.height - v1;
		} else {
			x0 = v0; x1 = v1;
			y0 = t0; y1 = t1;
		}
		break;
	default:
		match = (/\((.+),(.+)\)\((.+),(.+)\)/).exec(line.points);
		if (match) {
			x0 = parseLength(match[1], data.canvas.width);
			y0 = data.canvas.height - parseLength(match[2], data.canvas.height);
			x1 = parseLength(match[3], data.canvas.width);
			y1 = data.canvas.height - parseLength(match[4], data.canvas.height);
		}
	}
	ctx.lineWidth = parseLength(line.width);
	ctx.strokeStyle = parseColor(line.color, data);
	ctx.beginPath();
	ctx.moveTo(x0, y0);
	ctx.lineTo(x1, y1);
	ctx.closePath();
	ctx.stroke();
}
//draws a bar (PlotData)
function drawBar (ctx, data, bar) {
	var t0, t1, v0, v1, x0, x1, y0, y1, color;

	if (!(bar.bar in data.barPositions)) {
		return;
	}
	t0 = parseTime(bar.from, data);
	t1 = parseTime(bar.till, data);
	v0 = data.barPositions[bar.bar].pos;
	v1 = v0;
	v0 -= parseLength(bar.width) / 2;
	v1 += parseLength(bar.width) / 2;

	if (data.xAxisIsTime) {
		x0 = t0; x1 = t1;
		y0 = v0 + data.canvas.top; y1 = v1 + data.canvas.top;
	} else {
		x0 = data.canvas.left + v0; x1 = data.canvas.left + v1;
		y0 = t0; y1 = t1;
	}

	color = parseColor(bar.color, data);

	ctx.lineWidth = 1;
	ctx.fillStyle = color;
	ctx.strokeStyle = color;
	ctx.beginPath();
	ctx.rect(x0, y0, x1 - x0, y1 - y0);
	ctx.closePath();
	ctx.fill();
}
//draws something on a bar (PlotData)
function drawMarkerOrText (ctx, data, bar) {
	var pos = {}, t0, t1, v0, v1, x0, x1, y0, y1, match;

	if (!(bar.bar in data.barPositions)) {
		return;
	}

	if (bar.from && bar.till) {
		t0 = parseTime(bar.from, data);
		t1 = parseTime(bar.till, data);
	} else {
		t0 = parseTime(bar.at, data);
		t1 = t0;
	}
	v0 = data.barPositions[bar.bar].pos;
	v1 = v0;
	v0 -= parseLength(bar.width) / 2;
	v1 += parseLength(bar.width) / 2;
	if (data.xAxisIsTime) {
		x0 = t0; x1 = t1;
		y0 = v0 + data.canvas.top; y1 = v1 + data.canvas.top;
	} else {
		x0 = data.canvas.left + v0; x1 = data.canvas.left + v1;
		y0 = t0; y1 = t1;
	}

	if (bar.text) {
		if (data.xAxisIsTime) {
			switch (bar.anchor.toLowerCase()) {
			case 'from': pos.x = x0; break;
			case 'till': pos.x = x1; break;
			default: pos.x = (x0 + x1) / 2;
			}
			pos.y = (y0 + y1) / 2;
		} else {
			switch (bar.anchor.toLowerCase()) {
			case 'from': pos.y = y0; break;
			case 'till': pos.y = y1; break;
			default: pos.y = (y0 + y1) / 2;
			}
			pos.x = (x0 + x1) / 2;
		}
		pos.align = bar.align || 'left';
		pos.valign = 'bottom';
		if (bar.shift) {
			match = (/^\(\s*([0-9.\-]*)\s*,\s*([0-9.\-]*)\s*\)$/).exec(bar.shift);
			if (match) {
				pos.x += parseLength(match[1], data.canvas.width);
				pos.y -= parseLength(match[2], data.canvas.height);
			}
		}
		drawMultiLineText(ctx, data, bar, pos);
	}
	if (bar.mark) {
		ctx.lineWidth = 1;
		ctx.strokeStyle = parseColor(bar.mark.replace(/^\(.*?,\s*|\s*\)$/g, ''), data);
		ctx.beginPath();
		ctx.moveTo(data.xAxisIsTime ? x1 : x0, data.xAxisIsTime ? y0 : y1);
		ctx.lineTo(x1, y1);
		ctx.closePath();
		ctx.stroke();
	}
}
//draws text (TextData)
function drawText (ctx, data, text) {
	var match = (/^\(\s*([0-9.\-]+)\s*,\s*([0-9.\-]+)\s*\)$/).exec(text.pos || '(0,0)');
	if (!match) {
		return;
	}
	drawMultiLineText(ctx, data, text, {x: Number(match[1]), y: data.canvas.height - Number(match[2]), align: 'left', valign: 'bottom'});
}

//draws all axes and backgrounds
function drawAxes (ctx, data) {
	var color;
	color = parseColor(data.backgroundcolors.canvas, data);
	ctx.fillStyle = color;
	ctx.strokeStyle = color;
	ctx.beginPath();
	ctx.rect(0, 0, data.canvas.width, data.canvas.height);
	ctx.closePath();
	ctx.fill();
	if (data.backgroundcolors.bars) {
		color = parseColor(data.backgroundcolors.bars, data);
		ctx.fillStyle = color;
		ctx.strokeStyle = color;
		ctx.beginPath();
		ctx.rect(data.canvas.left, data.canvas.top, data.canvas.innerWidth, data.canvas.innerHeight);
		ctx.closePath();
		ctx.fill();
	}
	if (data.scaleMinor.show) {
		drawAxis(ctx, data, data.scaleMinor);
	}
	if (data.scaleMajor.show) {
		drawAxis(ctx, data, data.scaleMajor);
	}
	if (data.barNames.names.length > 1) {
		drawDataAxis(ctx, data);
	}
}
//draws the legend
function drawLegend (ctx, data) {
	var i, color, legend = [], text, x, y, cols, entriesPerCol, oldY, textWidthLargest;
	if (!data.legend.show) {
		return;
	}
	for (color in data.colors) {
		if (!data.colors[color].legend || data.colors[color].legend === 'n' || data.colors[color].legend === 'N') {
			continue;
		}
		legend.push(color);
	}
	cols = Number(data.legend.columns);
	if (cols === 0) {
		if (legend.length <= 5) {
			cols = 1;
		} else if (legend.length <= 10) {
			cols = 2;
		} else {
			cols = 3;
		}
	}
	entriesPerCol = Math.ceil(legend.length / cols);
	if (data.legend.orientation === 'hor') {
		x = parseLength(data.legend.left, data.canvas.width) || 5;
		y = data.canvas.height - (parseLength(data.legend.top, data.canvas.height) || (data.legend.position === 'top' ? data.canvas.height - 5 : 22));
	} else {
		x = parseLength(data.legend.left, data.canvas.width) || (data.legend.position === 'right' ? data.canvas.width - data.canvas.right + 5 : 5);
		y = data.canvas.height - (parseLength(data.legend.top, data.canvas.height) || (data.legend.position !== 'bottom' ? data.canvas.height - 5 : 17 * entriesPerCol + 5));
	}
	oldY = y;
	textWidthLargest = 0;
	for (i = 0; i < legend.length; i++) {
		color = legend[i];
		text = data.colors[color].legend;
		if (text === 'y' || text === 'Y') {
			text = color;
		}
		color = parseColor(color, data);
		ctx.fillStyle = color;
		ctx.strokeStyle = color;
		ctx.beginPath();
		ctx.rect(x, y, 12, 12);
		ctx.closePath();
		ctx.fill();
		ctx.fillStyle = 'black';
		ctx.textAlign = 'left';
		ctx.textBaseline = 'top';
		ctx.fillText(text, x + 23, y);
		if (data.legend.orientation === 'hor') {
			x += Math.round(ctx.measureText(text).width * 1.5) + 26;
		} else {
			textWidthLargest = Math.max(textWidthLargest, ctx.measureText(text).width);
			if ((i + 1) % entriesPerCol) {
				y += 17;
			} else {
				x += Math.round(textWidthLargest * 1.5) + 26;
				y = oldY;
				textWidthLargest = 0;
			}
		}
	}
}
//draws all texts (TextData)
function drawTextData (ctx, data) {
	var i;
	for (i = 0; i < data.textData.length; i++) {
		drawText(ctx, data, data.textData[i]);
	}
}
//draws the lines behind the bars
function drawLines0 (ctx, data) {
	var i;
	for (i = 0; i < data.lines.length; i++) {
		if (data.lines[i].layer !== 'front') {
			drawLine(ctx, data, data.lines[i]);
		}
	}
}
//draws the lines in front of the bars
function drawLines1 (ctx, data) {
	var i;
	for (i = 0; i < data.lines.length; i++) {
		if (data.lines[i].layer === 'front') {
			drawLine(ctx, data, data.lines[i]);
		}
	}
}
//draws the bars and stuff on the bars (PlotData)
function drawBars (ctx, data) {
	var i;
	for (i = 0; i < data.plots.length; i++) {
		if (data.plots[i].type === 'bar') {
			drawBar(ctx, data, data.plots[i]);
		}
	}
	for (i = 0; i < data.plots.length; i++) { //draw text and markers *after* the bars, even if they are defined before them
		if (data.plots[i].type !== 'bar' || data.plots[i].text || data.plots[i].marker) {
			drawMarkerOrText(ctx, data, data.plots[i]);
		}
	}
}
//draws everything
function draw (ctx, data) {
	ctx.textBaseline = 'top';
	ctx.lineCap = 'round'; //For some strange reason, Firefox treats 'butt' and 'square' as 'square', and 'round' as 'butt'. We need 'butt', so set it to 'round'.
	drawAxes(ctx, data);
	drawLegend(ctx, data);
	drawTextData(ctx, data);
	drawLines0(ctx, data);
	drawBars(ctx, data);
	drawLines1(ctx, data);
}

/**
 * functions to handle DOM
 */

//draws the diagram from the given code on the given canvas
function drawOnCanvas (code, canvas) {
	var data = parseCode(code);
	data.barNames = getBarNames(data);
	data.canvas = getCanvasSize(data);
	canvas.width = data.canvas.width;
	canvas.height = data.canvas.height;
	data.barPositions = getBarPositions(data);
	data.parseTime = getTimeParser(data);
	draw(canvas.getContext('2d'), data);
}
//replaces the <pre> with the canvas and draws the diagramm
function replaceCodeWithCanvas (el) {
	var	code = el.textContent.replace(/\t/g, ' '),
		p = document.createElement('p'),
		canvas = document.createElement('canvas');
	p.style.position = 'relative'; //links will be placed with position: 'absolute'
	p.appendChild(canvas);
	el.parentNode.replaceChild(p, el);
	drawOnCanvas(code, canvas);
}
//draws all timelines
function replaceAllTimelines () {
	var timelines = document.querySelectorAll('pre.xowa-timeline'), i;
	for (i = 0; i < timelines.length; i++) {
		try {
			replaceCodeWithCanvas(timelines[i]);
		} catch (e) {
		}
	}
}
function init () {
  if (window.xowa)
    document.addEventListener('DOMContentLoaded', replaceAllTimelines, false);  
  else
    replaceAllTimelines();
}
init();
})();