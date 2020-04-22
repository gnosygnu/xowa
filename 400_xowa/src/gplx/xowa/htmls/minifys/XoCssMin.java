/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.htmls.minifys;

import gplx.core.bits.Bitmask_;
import gplx.core.tooling.dataCollectors.GfoDataCollectorMgr;
import gplx.langs.javascripts.JsString_;
import gplx.langs.javascripts.util.regex.JsPattern_;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// XoCssMin based on:
// * Node.JS: https://github.com/jbleuzen/node-cssmin/blob/master/cssmin.js
// * YCSSMIN: https://github.com/yui/ycssmin/blob/master/cssmin.js
// * desb42 : https://github.com/desb42/myxowa/blob/master/400_xowa/src/gplx/xowa/htmls/Xoh_css_minify_v3.java
public class XoCssMin {
	private final static ConcurrentHashMap<String, Pattern> patterns = new ConcurrentHashMap<>();
	public static final int
	      MODE_NODEJS = 1
		, MODE_YCSS_MIN = 2
		, MODE_XOWA = 4
		, MODE_ALL = MODE_NODEJS | MODE_YCSS_MIN | MODE_XOWA
		;
	public void DataCollectorMgr_(GfoDataCollectorMgr v) {this.dataCollectorMgr = v;} private GfoDataCollectorMgr dataCollectorMgr;
	public String cssmin(String css, int linebreakpos) {return cssmin(css, linebreakpos, MODE_ALL);}
	public String cssmin(String css, int linebreakpos, int mode) {
		boolean isModeYcssMin = Bitmask_.Has_int(mode, MODE_YCSS_MIN);
		boolean isModeXowa = Bitmask_.Has_int(mode, MODE_XOWA);

		int startIndex = 0,
			endIndex = 0,
			i = 0, max = 0;
		List<String> preservedTokens = new ArrayList<>();
		List<String> comments = new ArrayList<>();
		String token = "";
		int totallen = css.length();
		String placeholder = "";

		if (isModeYcssMin) {
			css = _extractDataUrls(css, preservedTokens);
			if (dataCollectorMgr != null) {
				dataCollectorMgr.AddGrp("extractDataUrls").Add("css", css).Add("preservedTokens", preservedTokens);
			}
		}

		// collect all comment blocks...
		while ((startIndex = css.indexOf("/*", startIndex)) >= 0) {
			endIndex = css.indexOf("*/", startIndex + 2);
			if (endIndex < 0) {
				endIndex = totallen;
			}
			token = JsString_.slice(css, startIndex + 2, endIndex);
			comments.add(token);
			css = JsString_.slice(css, 0, startIndex + 2) + "___YUICSSMIN_PRESERVE_CANDIDATE_COMMENT_" + (comments.size() - 1) + "___" + JsString_.slice(css, endIndex);
			startIndex += 2;
		}
		if (dataCollectorMgr != null) {
			dataCollectorMgr.AddGrp("collectComments").Add("css", css).Add("comments", comments);
		}

		// preserve strings so their content doesn't get accidentally minified
		css = JsString_.replaceArg1(css, patterns
				, "(\"([^\\\\\"]|\\\\.|\\\\)*\")|('([^\\\\']|\\\\.|\\\\)*')"
				, (match) -> {
			int idx = 0, max2 = 0; String quote = match.substring(0, 1);

			match = JsString_.slice(match, 1, -1);

			// maybe the string contains a comment-like substring?
			// one, maybe more? put'em back then
			if (match.indexOf("___YUICSSMIN_PRESERVE_CANDIDATE_COMMENT_") >= 0) {
				for (idx = 0, max2 = comments.size(); idx < max2; idx = idx + 1) {
					match = match.replace("___YUICSSMIN_PRESERVE_CANDIDATE_COMMENT_" + idx + "___", comments.get(idx));
				}
			}

			// minify alpha opacity in filter strings
			match = JsString_.replace(match, patterns,
					"progid:DXImageTransform\\.Microsoft\\.Alpha\\(Opacity=", Pattern.CASE_INSENSITIVE,
					"alpha(opacity=");

			preservedTokens.add(match);

			return quote + "___YUICSSMIN_PRESERVED_TOKEN_" + (preservedTokens.size() - 1) + "___" + quote;
		});

		// strings are safe, now wrestle the comments
		for (i = 0, max = comments.size(); i < max; i = i + 1) {

			token = comments.get(i);
			placeholder = "___YUICSSMIN_PRESERVE_CANDIDATE_COMMENT_" + i + "___";

			// ! in the first position of the comment means preserve
			// so push to the preserved tokens keeping the !
			if (token.charAt(0) == '!') {
				preservedTokens.add(token);
				css = css.replace(placeholder,  "___YUICSSMIN_PRESERVED_TOKEN_" + (preservedTokens.size() - 1) + "___");
				continue;
			}

			// \ in the last position looks like hack for Mac/IE5
			// shorten that to /*\*/ and the next one to /**/
			if (token.charAt(token.length() - 1) == '\\') {
				preservedTokens.add("\\");
				css = css.replace(placeholder,  "___YUICSSMIN_PRESERVED_TOKEN_" + (preservedTokens.size() - 1) + "___");
				i = i + 1; // attn: advancing the loop
				preservedTokens.add("");
				css = css.replace("___YUICSSMIN_PRESERVE_CANDIDATE_COMMENT_" + i + "___",  "___YUICSSMIN_PRESERVED_TOKEN_" + (preservedTokens.size() - 1) + "___");
				continue;
			}

			// keep empty comments after child selectors (IE7 hack)
			// e.g. html >/**/ body
			if (token.length() == 0) {
				startIndex = css.indexOf(placeholder);
				if (startIndex > 2) {
					if (css.charAt(startIndex - 3) == '>') {
						preservedTokens.add("");
						css = css.replace(placeholder,  "___YUICSSMIN_PRESERVED_TOKEN_" + (preservedTokens.size() - 1) + "___");
					}
				}
			}

			// in all other cases kill the comment
			css = css.replace("/*" + placeholder + "*/", "");
		}


		// Normalize all whitespace strings to single spaces. Easier to work with that way.
		css = JsString_.replace(css, patterns, "\\s+", " ");

		// Remove the spaces before the things that should not have spaces before them.
		// But, be careful not to turn "p :link {...}" into "p:link{...}"
		// Swap out any pseudo-class colons with the token, and then swap back.
		css = JsString_.replaceArg1
				(css, patterns,
				"(^|\\})(([^\\{:])+:)+([^\\{]*\\{)"
				, s -> s.replace(":", "___YUICSSMIN_PSEUDOCLASSCOLON___"));
		if (dataCollectorMgr != null) {
			dataCollectorMgr.AddGrp("swapPseudo").Add("css", css);
		}

		// Preserve spaces in calc expressions;
		// XO:MODE_YCSS_MIN omits this
		css = JsString_.replaceArg2(css, patterns, "calc\\s*\\(\\s*(.*?)\\s*\\)", (m, c) -> {
			return m.replace(c, JsString_.replace(c, patterns, "\\s+", "___YUICSSMIN_SPACE_IN_CALC___"));
		});

		css = JsString_.replace(css, patterns, "\\s+([!{}:;>+\\(\\)\\[,])", "$1");
		css = css.replace("___YUICSSMIN_PSEUDOCLASSCOLON___", ":");

		// retain space for special IE6 cases
		css = JsString_.replace(css, patterns, ":first-(line|letter)(\\{|,)", ":first-$1 $2");

		// no space after the end of a preserved comment
		css = css.replace("*/ ", "*/");


		// If there is a @charset, then only allow one, and push to the top of the file.
		css = JsString_.replace(css, patterns, "^(.*)(@charset \"[^\"]*\";)", Pattern.CASE_INSENSITIVE, "$2$1");
		css = JsString_.replace(css, patterns, "/^(\\s*@charset [^;]+;\\s*)+/", Pattern.CASE_INSENSITIVE, "$1");

		// Put the space back in some cases, to support stuff like
		// @media screen and (-webkit-min-device-pixel-ratio:0){
		css = JsString_.replace(css, patterns, "\\band\\(", Pattern.CASE_INSENSITIVE, "and (");


		// Remove the spaces after the things that should not have spaces after them.
		css = JsString_.replace(css, patterns, "([!{}:;>+\\(\\[,])\\s+", "$1");

		// Restore preserved spaces in calc expressions
		// XO:MODE_YCSS_MIN omits this
		css = css.replace("___YUICSSMIN_SPACE_IN_CALC___", " ");

		// remove unnecessary semicolons
		css = JsString_.replace(css, patterns,";+\\}", "}");

		// Replace 0(px,em,%) with 0.
		css = JsString_.replace(css, patterns, "([\\s:])(0)(px|em|%|in|cm|mm|pc|pt|ex)", Pattern.CASE_INSENSITIVE,"$1$2");

		// Replace 0 0 0 0; with 0.
		css = JsString_.replace(css, patterns, ":0 0 0 0(;|\\})",":0$1");
		css = JsString_.replace(css, patterns, ":0 0 0(;|\\})",":0$1");
		css = JsString_.replace(css, patterns, ":0 0(;|\\})",":0$1");

		// Replace background-position:0; with background-position:0 0;
		// same for transform-origin
		css = JsString_.replaceArg3(css, patterns,
				"(background-position|transform-origin|webkit-transform-origin|moz-transform-origin|o-transform-origin|ms-transform-origin):0(;|\\})", Pattern.CASE_INSENSITIVE
				, (all, prop, tail)-> prop.toLowerCase() + ":0 0" + tail);

		// Replace 0.6 to .6, but only when preceded by : or a white-space
		css = JsString_.replace(css, patterns, "(:|\\s)0+\\.(\\d+)", "$1.$2");

		// Shorten colors from rgb(51,102,153) to #336699
		// This makes it more likely that it'll get further compressed in the next step.
		css = JsString_.replaceMatcher(css, patterns,
				"rgb\\s*\\(\\s*([0-9,\\s]+)\\s*\\)", Pattern.CASE_INSENSITIVE
				, m -> {
					String[] rgbcolors = m.group(1).split(",");
					String rgb = "";
					for (int idx = 0; idx < rgbcolors.length; idx = idx + 1) {
						rgbcolors[idx] = Integer.toHexString(Integer.parseInt(rgbcolors[idx], 10));
						if (rgbcolors[idx].length() == 1) {
							rgbcolors[idx] = "0" + rgbcolors[idx];
						}
						rgb += rgbcolors[idx];
					}
					return '#' + rgb;
				});

		// Shorten colors from #AABBCC to #ABC. Note that we want to make sure
		// the color is not preceded by either ", " or =. Indeed, the property
		//     filter: chroma(color="#FFFFFF");
		// would become
		//     filter: chroma(color="#FFF");
		// which makes the filter break in IE.
		// XO:MODE_YCSS_MIN replaces this with its own function
		// XO:desb42 comments this
		css = JsString_.replaceMatcher(css, patterns
				, "([^\"'=\\s])(\\s*)#([0-9a-f])([0-9a-f])([0-9a-f])([0-9a-f])([0-9a-f])([0-9a-f])", Pattern.CASE_INSENSITIVE
				, match-> {
					if (
						match.group(3).toLowerCase().equals(match.group(4).toLowerCase()) &&
						match.group(5).toLowerCase().equals(match.group(6).toLowerCase()) &&
						match.group(7).toLowerCase().equals(match.group(8).toLowerCase())
					) {
						return (match.group(1) + match.group(2) + '#' + match.group(3) + match.group(5) + match.group(7)).toLowerCase();
					} else {
						return match.group(0).toLowerCase();
					}
				});

		// border: none -> border:0
		css = JsString_.replaceArg3(css, patterns
				, "(border|border-top|border-right|border-bottom|border-right|outline|background):none(;|\\})", Pattern.CASE_INSENSITIVE
				, (all, prop, tail) -> prop.toLowerCase() + ":0" + tail
		);

		// shorter opacity IE filter
		css = JsString_.replace(css, patterns, "progid:DXImageTransform\\.Microsoft\\.Alpha\\(Opacity=", Pattern.CASE_INSENSITIVE, "alpha(opacity=");

		// Remove empty rules.
		css = JsString_.replace(css, patterns, "[^\\};\\{\\/]+\\{\\}", "");

		if (linebreakpos >= 0) {
			// Some source control tools don't like it when files containing lines longer
			// than, say 8000 characters, are checked in. The linebreak option is used in
			// that case to split long lines after a specific column.
			startIndex = 0;
			i = 0;
			// XO: desb42 comments this
			while (i < css.length()) {
				i = i + 1;
				if (css.charAt(i - 1) == '}' && i - startIndex > linebreakpos) {
					css = JsString_.slice(css, 0, i) + '\n' + JsString_.slice(css, i);
					startIndex = i;
				}
			}
		}

		// Replace multiple semi-colons in a row by a single one
		// See SF bug #1980989
		css = JsString_.replace(css, patterns, ";;+", ";");

		// restore preserved comments and strings
		for (i = 0, max = preservedTokens.size(); i < max; i = i + 1) {
			css = css.replace("___YUICSSMIN_PRESERVED_TOKEN_" + i + "___", preservedTokens.get(i));
		}

		// Trim the final string (for any leading or trailing white spaces)
		css = JsString_.replace(css, patterns, "^\\s+|\\s+$", "");

		if (isModeXowa) {
			// add the '.mw-parser-output ' selector
			css = JsString_.replace(css, patterns, "\\}([^@}].{2})", "}.mw-parser-output $1");
			css = JsString_.replace(css, patterns, "(@media[^\\{]*\\{)", "$1.mw-parser-output ");
			if (css.charAt(0) != '@')
				css = ".mw-parser-output " + css;

			// change some url(...) entries
			css = css.replace("//upload.wikimedia.org", "//www.xowa.org/xowa/fsys/bin/any/xowa/upload.wikimedia.org");
		}

		return css;
	}
	private String _extractDataUrls(String css, List<String> preservedTokens) {

		// Leave data urls alone to increase parse performance.
		// XO: also ensures it is not touched by the rest of the cssmin
		int maxIndex = css.length() - 1,
				appendIndex = 0,
				startIndex,
				endIndex;
		String terminator;
		boolean foundTerminator;
		StringBuffer sb = new StringBuffer();
		Matcher m;
		String preserver,
				token;
		Pattern pattern = JsPattern_.getOrCompile(patterns,"url\\(\\s*([\"']?)data\\:");

		// Since we need to account for non-base64 data urls, we need to handle
		// ' and ) being part of the data string. Hence switching to indexOf,
		// to determine whether or not we have matching string terminators and
		// handling sb appends directly, instead of using matcher.append* methods.
		m = pattern.matcher(css);
		while (m.find()) {

			startIndex = m.start() + 4;  // "url(".length()
			terminator = m.group(1);     // ', " or empty (not quoted); XO:can be any of `'`, `"`, ``

			if (terminator.length() == 0) { // XO: this means that the regex matched nothing; (["']?) -> ``
				terminator = ")";
			}

			foundTerminator = false;

			endIndex = m.end() - 1;

			while(foundTerminator == false && endIndex+1 <= maxIndex) {// XO: search forward for next terminator
				endIndex = css.indexOf(terminator, endIndex + 1);

				// endIndex == 0 doesn't really apply here
				if ((endIndex > 0) && (css.charAt(endIndex - 1) != '\\')) {// XO:found terminator; check it isn't escaped; EX: `\'`
					foundTerminator = true;
					if (!(")".equals(terminator))) {// XO:cur terminator is either `'` or `"`; grab next `)`
						endIndex = css.indexOf(")", endIndex);
					}
				}
				// XO:NOTE: foundTerminator will always be true.
				// Specifically, if endIndex is -1:
				// * loop will start from top and do `endIndex = css.indexOf(terminator, -1 + 1)` which evaluates to the original endIndex
				// * since endIndex will always be > 0 and endIndex - 1 will never be \ (b/c of regex), foundTerminator will always be true
			}

			// Enough searching, start moving stuff over to the buffer
			sb.append(css.substring(appendIndex, m.start()));

			if (foundTerminator) {
				token = css.substring(startIndex, endIndex);
				token = JsString_.replace(token, patterns, "\\s+", "");
				preservedTokens.add(token);

				preserver = "url(___YUICSSMIN_PRESERVED_TOKEN_" + (preservedTokens.size() - 1) + "___)";
				sb.append(preserver);

				appendIndex = endIndex + 1;
			} else {
				// No end terminator found, re-add the whole match. Should we throw/warn here?
				// XO: as detailed above, foundTerminator will always be true
				sb.append(css.substring(m.start(), m.end()));
				appendIndex = m.end();
			}
		}

		sb.append(css.substring(appendIndex));

		return sb.toString();
	}
}
