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
package gplx.xowa.xtns.template_styles;

import gplx.Bry_;
import gplx.String_;
import gplx.langs.javascripts.util.regex.JsPattern_;
import gplx.xowa.htmls.minifys.XoCssMin;

import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XoCssTransformer {
    private final static ConcurrentHashMap<String, Pattern> patterns = new ConcurrentHashMap<>();
    private String css;
    public XoCssTransformer(String css) {
        this.css = css;
    }

    public XoCssTransformer Minify() {
        XoCssMin minifier = new XoCssMin();
        this.css = minifier.cssmin(css, -1);
        return this;
    }
    public XoCssTransformer Url(String src, String trg) {
        // change some url(...) entries
        css = css.replace("//" + src, "//" + trg);
        return this;
    }
    public XoCssTransformer Prepend(String selector) {
        // prepend any classes to all declarations; primarily for '.mw-parser-output ' selector
        // regex means match anything before `{` which is not `}{`
        css = PrependSelector(css, "([^\\}\\{]*)\\{", selector);
        return this;
    }
    public byte[] ToBry() {return Bry_.new_u8(css);}
    public String ToStr() {return css;}

	private String PrependSelector(String orig, String regx, String selector) {
	    // init regex
        Pattern pattern = JsPattern_.getOrCompile(patterns, regx);
		Matcher matcher = pattern.matcher(orig);

        // init temp variables for replacements
		StringBuffer sb = null;
		int previous = 0;

		// find "match" which is everything before `{`
		while (matcher.find()) {
		    // lazy-instantiate buffer
            if (sb == null) sb = new StringBuffer();

            // add everything between previousPosition and match.start
            sb.append(orig, previous, matcher.start(1));

            // get match, as well as trimmed version; need `.Trim()` to check for things like "\s@media"
            String match = matcher.group(1);
            String matchTrimmed = match.trim();
            if (matchTrimmed.length() > 0) { // should not happen b/c CSS shouldn't have "empty" class (`}{}`) but check anyway b/c of `match.charAt(0)`
                if (matchTrimmed.charAt(0) != '@') { // ignore any css conditional group rules (`@`) rules like @media, @supports, etc
                    // split match by nested class selectors; EX: "a,b" -> ["a", "b"]
                    String[] items = match.split(",");
                    for (int i = 0; i < items.length; i++) {
                        // put `,` back between items, unless it's the first
                        if (i > 0)
                            sb.append(",");

						String item = items[i].trim();
						if (item.length() > 0) { // ignore empty items
                            // prepend the selector, except if it starts with 'body'
							if (!String_.MidByLenSafe(item, 0, 4).equals("body")) {
                                sb.append(selector);
                                sb.append(' ');
                            }

                            // put back the item
                            sb.append(item);
                        }
    				}
				}
                else {
                    // EX: `@media`
                    sb.append(match);
                }
            }
            previous = matcher.end(1);
		}

		// finish generating string, or return orig
		if (sb != null) {
            sb.append(orig, previous, orig.length());
			return sb.toString();
		}
		else
			return orig;
	}
}
