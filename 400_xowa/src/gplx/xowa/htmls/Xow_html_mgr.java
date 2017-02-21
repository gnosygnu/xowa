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
package gplx.xowa.htmls; import gplx.*; import gplx.xowa.*;
import gplx.gfui.kits.core.*;
import gplx.xowa.langs.*;
import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.xtns.gallery.*;	
import gplx.xowa.parsers.xndes.*;
import gplx.xowa.htmls.portal.*; import gplx.xowa.addons.htmls.tocs.*; import gplx.xowa.wikis.modules.*; import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.htmls.tidy.*; import gplx.xowa.htmls.js.*;
import gplx.langs.htmls.encoders.*;
import gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.*; import gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.singles.*;
public class Xow_html_mgr implements Gfo_invk {
	private final    Gfo_url_encoder fsys_lnx_encoder = Gfo_url_encoder_.New__fsys_lnx().Make();
	public Xow_html_mgr(Xowe_wiki wiki) {
		this.wiki = wiki;
		html_wtr = new Xoh_html_wtr(wiki, this);
		Xoae_app app = wiki.Appe();
		page_wtr_mgr = new Xoh_page_wtr_mgr(app.Gui_mgr().Kit().Tid() != Gfui_kit_.Swing_tid);	// reverse logic to handle swt,drd but not mem
		img_xowa_protocol = fsys_lnx_encoder.Encode_to_file_protocol(app.Fsys_mgr().Bin_xowa_file_dir().GenSubFil_nest("app.general", "xowa_exec.png"));
		portal_mgr = new Xow_portal_mgr(wiki);
		module_mgr = new Xow_module_mgr(wiki);
		this.js_cleaner = new Xoh_js_cleaner(app);
	}
	public void Init_by_wiki(Xowe_wiki wiki) {
		html_wtr.Init_by_wiki(wiki);
		module_mgr.Init_by_wiki(wiki);
		tidy_mgr.Init_by_wiki(wiki);
		portal_mgr.Init_by_wiki(wiki);
		page_wtr_mgr.Init_by_wiki(wiki);
	}
	public void Init_by_lang(Xol_lang_itm lang) {
		portal_mgr.Init_by_lang(lang);
	}
	public Xowe_wiki				Wiki() {return wiki;} private Xowe_wiki wiki;
	public Xoh_html_wtr				Html_wtr() {return html_wtr;} private Xoh_html_wtr html_wtr;
	public Xoh_page_wtr_mgr			Page_wtr_mgr() {return page_wtr_mgr;} private Xoh_page_wtr_mgr page_wtr_mgr;
	public Xow_tidy_mgr				Tidy_mgr()	{return tidy_mgr;} private final    Xow_tidy_mgr tidy_mgr = new Xow_tidy_mgr();
	public Xoh_js_cleaner			Js_cleaner() {return js_cleaner;} private final    Xoh_js_cleaner js_cleaner;
	public Xop_xatr_whitelist_mgr	Whitelist_mgr() {return whitelist_mgr;} private final    Xop_xatr_whitelist_mgr whitelist_mgr = new Xop_xatr_whitelist_mgr().Ini();
	public Xow_portal_mgr			Portal_mgr() {return portal_mgr;} private Xow_portal_mgr portal_mgr;
	public Xow_module_mgr			Head_mgr() {return module_mgr;} private Xow_module_mgr module_mgr;
	public boolean Importing_ctgs() {return importing_ctgs;} public void Importing_ctgs_(boolean v) {importing_ctgs = v;} private boolean importing_ctgs;
	public int Img_thumb_width() {return img_thumb_width;} private int img_thumb_width = 220;
	public byte[] Img_xowa_protocol() {return img_xowa_protocol;} private byte[] img_xowa_protocol;
	public boolean Img_suppress_missing_src() {return img_suppress_missing_src;} public Xow_html_mgr Img_suppress_missing_src_(boolean v) {img_suppress_missing_src = v; return this;} private boolean img_suppress_missing_src = true;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_article))							return page_wtr_mgr;
		else if	(ctx.Match(k, Invk_portal))								return portal_mgr;
		else if	(ctx.Match(k, Invk_modules))							return module_mgr;
		else	return Gfo_invk_.Rv_unhandled;
	}
	public static final String Invk_article = "article", Invk_portal = "portal", Invk_modules = "modules";
}
