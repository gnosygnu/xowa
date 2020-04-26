package gplx.xowa.xtns.template_styles;

import gplx.Bry_;
import gplx.langs.javascripts.JsString_;
import gplx.xowa.htmls.minifys.XoCssMin;

import java.util.concurrent.ConcurrentHashMap;
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
    public XoCssTransformer Prepend(String prepend) {
        // prepend any classes to all declarations; primarily for '.mw-parser-output ' selector
        css = JsString_.replace(css, patterns, "\\}([^@}].{2})", "} " + prepend + " $1");
        css = JsString_.replace(css, patterns, "(@media[^\\{]*\\{)", "$1" + prepend + " ");
        if (css.charAt(0) != '@')
            css = prepend + " " + css;
        return this;
    }
    public XoCssTransformer Url(String src, String trg) {
        // change some url(...) entries
        css = css.replace("//" + src, "//" + trg);
        return this;
    }
    public byte[] ToBry() {return Bry_.new_u8(css);}
    public String ToStr() {return css;}
}
