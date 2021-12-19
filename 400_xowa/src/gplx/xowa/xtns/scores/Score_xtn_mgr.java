/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.scores;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.GfsCtx;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.types.custom.brys.fmts.fmtrs.*;
public class Score_xtn_mgr extends Xox_mgr_base {
	@Override public byte[] Xtn_key() {return XTN_KEY;} public static final byte[] XTN_KEY = BryUtl.NewA7("score");
	@Override public Xox_mgr Xtn_clone_new() {return new Score_xtn_mgr();}
	public BryFmtr Html_img() {return html_img;} private BryFmtr html_img = BryFmtr.New(StringUtl.ConcatLinesNl
		(	""
		,	"<p>"
		,	"  <a id=\"~{a_id}\" href=\"~{a_href}\" xowa_title=\"~{a_xowa_title}\">"
		,	"    <img id=\"~{img_id}\" src=\"~{img_src}\"  />"
		,	"  </a>"
		,	"</p>"
		), "a_id", "a_href", "a_xowa_title", "img_id", "img_src", "img_alt");
	public BryFmtr Html_txt() {return html_txt;} private BryFmtr html_txt = BryFmtr.New(StringUtl.ConcatLinesNl
		(	""
		,	"<div id=\"~{div_id}\" class=\"~{div_class}\">"
		,	"  <pre style=\"overflow:auto\">~{code}"
		,	"</pre>"
		,	"</div>"
		), "div_id", "div_class", "code");
	public BryFmtr Lilypond_fmtr() {return lilypond_fmtr;} private BryFmtr lilypond_fmtr = BryFmtr.New(StringUtl.ConcatLinesNl
		(	"\\header {"
		,	"  tagline = ##f"
		,	"}"
		,	"\\paper {"
		,	"  raggedright = ##t"
		,	"  raggedbottom = ##t"
		,	"  indent = 0\\mm"
		,	"}"
		,	"\\version \"~{version}\""
		,	"\\score {"
		,	"  ~{code}"
		,	"  \\layout { }"
		,	"  \\midi {"
		,	"    \\context {"
		,	"      \\Score"
		,	"      tempoWholesPerMinute = #(ly:make-moment 100 4)"
		,	"    }"
		,	"  }"
		,	"}"), "version", "code");
	@Override public void Xtn_init_by_wiki(Xowe_wiki wiki) {
		wiki.App().Cfg().Bind_many_wiki(this, wiki, Cfg__enabled);
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_html_img))			return html_img.Fmt();
		else if	(ctx.Match(k, Invk_html_img_))			html_img.FmtSet(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_html_txt))			return html_txt.Fmt();
		else if	(ctx.Match(k, Invk_html_txt_))			html_txt.FmtSet(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_lilypond_fmt))		return lilypond_fmtr.Fmt();
		else if	(ctx.Match(k, Invk_lilypond_fmt_))		lilypond_fmtr.FmtSet(m.ReadBry("v"));
		else if	(ctx.Match(k, Cfg__enabled))			Enabled_(m.ReadYn("v"));
		else											return super.Invk(ctx, ikey, k, m);
		return this;
	}
	private static final String Invk_html_img = "html_img", Invk_html_img_ = "html_img_", Invk_html_txt = "html_txt", Invk_html_txt_ = "html_txt_", Invk_lilypond_fmt = "lilypond_fmt", Invk_lilypond_fmt_ = "lilypond_fmt_";
	private static final String Cfg__enabled = "xowa.addon.score.enabled";
	public static byte[] Lilypond_version = null;
}
