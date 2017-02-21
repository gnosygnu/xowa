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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*;
import gplx.xowa.wikis.dbs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*; import gplx.xowa.htmls.core.htmls.*; import gplx.core.intls.ucas.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.fmts.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.dbs.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.urls.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.langs.*;
public class Xoctg_catpage_mgr implements Gfo_invk {
	private final    Xow_wiki wiki;
	private final    Hash_adp_bry cache = Hash_adp_bry.cs();
	private final    Xoctg_catpage_loader loader = new Xoctg_catpage_loader();
	private final    Xoctg_fmt_grp fmt_subcs = Xoctg_fmt_grp.New__subc(), fmt_pages = Xoctg_fmt_grp.New__page(), fmt_files = Xoctg_fmt_grp.New__file();
	private final    Uca_ltr_extractor ltr_extractor = new Uca_ltr_extractor(true);
	private String missing_cls = Str__missing_cls__red;
	public int Grp_max() {return grp_max;} private int grp_max = Grp_max_dflt;
	public Xoctg_catpage_mgr(Xow_wiki wiki) {
		this.wiki = wiki;
		this.collation_mgr = new Xoctg_collation_mgr(wiki);
	}
	public Xoctg_collation_mgr Collation_mgr() {return collation_mgr;} private Xoctg_collation_mgr collation_mgr;
	public Xoctg_fmt_grp Fmt(byte tid) {
		switch (tid) {
			case Xoa_ctg_mgr.Tid__subc: return fmt_subcs;
			case Xoa_ctg_mgr.Tid__page: return fmt_pages;
			case Xoa_ctg_mgr.Tid__file: return fmt_files;
			default: throw Err_.new_unhandled(tid);
		}
	}
	public byte[] Missing_ctg_cls_css() {
		if		(String_.Eq(missing_cls, Str__missing_cls__normal))		return Css__missing_cls__normal;
		else if (String_.Eq(missing_cls, Str__missing_cls__hide))		return Css__missing_cls__hide;
		else if (String_.Eq(missing_cls, Str__missing_cls__red))		return Css__missing_cls__red;
		else															return Bry_.Empty;	// NOTE: do not throw error, else fatal error when regen'ing cfg db; DATE:2016-12-27
	}
	public void Init_by_wiki(Xow_wiki wiki) {
		wiki.App().Cfg().Bind_many_wiki(this, wiki, Cfg__missing_class);
	}
	public void Free_mem_all() {cache.Clear();}
	public Xoctg_catpage_ctg Get_or_load_or_null(byte[] page_ttl, Xoctg_catpage_url catpage_url, Xoa_ttl cat_ttl, int limit) {
		// load categories from cat dbs; exit if not found
		Xoctg_catpage_ctg ctg = (Xoctg_catpage_ctg)cache.Get_by(cat_ttl.Full_db());
		if (ctg == null) {
			if (gplx.core.envs.Env_.Mode_testing()) return null;	// needed for dpl test
			synchronized (thread_lock) {	// LOCK:used by multiple wrks; DATE:2016-09-12
				ctg = loader.Load_ctg_or_null(wiki, page_ttl, this, catpage_url, cat_ttl, limit);
			}
			if (ctg == null) return null;	// not in cache or db; exit
			if (limit == Int_.Max_value)	// only add to cache if Max_val (DynamicPageList); for regular catpages, always retrieve on demand
				cache.Add(cat_ttl.Full_db(), ctg);
		}
		return ctg;
	}
	public void Write_catpage(Bry_bfr bfr, Xoa_page page) {
		try	{
			// get catpage_url
			Xoctg_catpage_url catpage_url = Xoctg_catpage_url_parser.Parse(page.Url());

			// load categories from cat dbs; exit if not found
			Xoctg_catpage_ctg ctg = Get_or_load_or_null(page.Ttl().Page_db(), catpage_url, page.Ttl(), grp_max);
			if (ctg == null) return;

			// write html
			Xol_lang_itm lang = page.Lang();
			fmt_subcs.Write_catpage_grp(bfr, wiki, lang, ltr_extractor, ctg, grp_max);
			fmt_pages.Write_catpage_grp(bfr, wiki, lang, ltr_extractor, ctg, grp_max);
			fmt_files.Write_catpage_grp(bfr, wiki, lang, ltr_extractor, ctg, grp_max);
		}
		catch (Exception e) {
			Xoa_app_.Usr_dlg().Warn_many("", "", "failed to generate category: title=~{0} err=~{1}", page.Url_bry_safe(), Err_.Message_gplx_log(e));
		}
	}
	public void Cache__add(byte[] ttl, Xoctg_catpage_ctg ctg) {
		cache.Del(ttl);
		cache.Add(ttl, ctg);
	}
	public void Grp_max_(int v) {grp_max = v;}	// TEST:
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__collation_))		collation_mgr.Collation_name_(m.ReadStr("v"));
		else if (ctx.Match(k, Cfg__missing_class))		missing_cls = m.ReadStr("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk__collation_ = "collation_";

	public static int Grp_max_dflt = 200;
	private static final    Object thread_lock = new Object();

	private static final String Cfg__missing_class = "xowa.addon.category.catpage.missing_class";
	private static final String Str__missing_cls__normal = "normal", Str__missing_cls__hide = "hide", Str__missing_cls__red = "red_link";
	private static final    byte[] Css__missing_cls__normal = Bry_.new_a7(".xowa-missing-category-entry {}"), Css__missing_cls__hide = Bry_.new_a7(".xowa-missing-category-entry {display: none;}"), Css__missing_cls__red = Bry_.new_a7(".xowa-missing-category-entry {color: red;}");
}
