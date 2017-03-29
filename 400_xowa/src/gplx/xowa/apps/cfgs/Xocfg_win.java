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
package gplx.xowa.apps.cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.core.brys.fmtrs.*;
import gplx.gfui.draws.*;
import gplx.xowa.guis.langs.*;
public class Xocfg_win implements Gfo_invk {
	public Xol_font_info Font() {return font;} private Xol_font_info font = new Xol_font_info("Arial", 8, FontStyleAdp_.Plain);
	public Bry_fmtr Search_box_fmtr() {return search_box_fmtr;} private Bry_fmtr search_box_fmtr = Bry_fmtr.new_("Special:Search?search=~{search}", "search");
	public Bry_fmtr Allpages_box_fmtr() {return allpages_box_fmtr;} private Bry_fmtr allpages_box_fmtr = Bry_fmtr.new_("Special:AllPages?from=~{search}&namespace=0&hideredirects=0", "search");
	public void Init_by_app(Xoae_app app) {
		font.Init_by_app(app);
		app.Cfg().Bind_many_app(this, Cfg__search__default_to_fulltext, Cfg__search__fallback_to_title);			
	}
	public String Search_url() {return search_url;} private String search_url = "Special:Search";
	public void Search_url_(boolean default_to_fulltext) {
		search_url = default_to_fulltext ? "Special:XowaSearch" : "Special:Search?fulltext=y&search=enter_search_in_url_bar";
		search_box_fmtr = Bry_fmtr.new_(search_url + "?fulltext=y&search=~{search}", "search");
	}
	public boolean Search_fallsback_to_title() {return search_fallsback_to_title;} private boolean search_fallsback_to_title;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_font))                             return font;
		else if	(ctx.Match(k, Invk_search_box_fmt_))                  search_box_fmtr.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, "search_url"))                          return search_url;
		else if	(ctx.Match(k, Cfg__search__default_to_fulltext))      Search_url_(m.ReadBool("v"));
		else if	(ctx.Match(k, Cfg__search__fallback_to_title))        search_fallsback_to_title = m.ReadBool("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_search_box_fmt_ = "search_box_fmt_", Invk_font = "font";
	private static final String 
	  Cfg__search__default_to_fulltext = "xowa.addon.fulltext_search.compatibility.default_to_fulltext"
	;
	public static final String 
	  Cfg__search__fallback_to_title   = "xowa.addon.fulltext_search.compatibility.fallback_to_title";

	public static final float Font_size_default = 8;
}
