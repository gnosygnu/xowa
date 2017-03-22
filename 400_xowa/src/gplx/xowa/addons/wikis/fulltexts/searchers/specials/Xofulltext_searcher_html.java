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
package gplx.xowa.addons.wikis.fulltexts.searchers.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*; import gplx.xowa.addons.wikis.fulltexts.searchers.*;
import gplx.core.net.qargs.*;
import gplx.dbs.*;
import gplx.xowa.specials.*; import gplx.langs.mustaches.*; import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.pages.tags.*;
import gplx.xowa.addons.apps.cfgs.*;
class Xofulltext_searcher_html extends Xow_special_wtr__base implements Mustache_doc_itm {
	private final    boolean case_match, auto_wildcard_bgn, auto_wildcard_end, expand_matches_section, show_all_matches;
	private final    Hash_adp props = Hash_adp_.New();
	public Xofulltext_searcher_html(Xocfg_mgr cfg_mgr, Gfo_qarg_mgr url_args, Xow_wiki wiki, Guid_adp page_guid) {
		props.Add("page_guid", page_guid.To_str());
		props.Add("cur_wiki", wiki.Domain_str());
		props.Add("search", url_args.Read_str_or("search", ""));
		props_Add(cfg_mgr, url_args, "wikis" , wiki.Domain_str());
		props_Add(cfg_mgr, url_args, "ns_ids", "0");
		props_Add(cfg_mgr, url_args, "limits", "100");
		props_Add(cfg_mgr, url_args, "offsets", "0");

		this.case_match = cfg_mgr.Get_bool_app_or("xowa.addon.search.fulltext.special.case_match", false);
		this.auto_wildcard_bgn = cfg_mgr.Get_bool_app_or("xowa.addon.search.fulltext.special.auto_wildcard_bgn", false);
		this.auto_wildcard_end = cfg_mgr.Get_bool_app_or("xowa.addon.search.fulltext.special.auto_wildcard_end", false);
		this.expand_matches_section = cfg_mgr.Get_bool_app_or("xowa.addon.search.fulltext.special.expand_matches_section", false);
		this.show_all_matches = cfg_mgr.Get_bool_app_or("xowa.addon.search.fulltext.special.show_all_matches", false);
	}
	private void props_Add(Xocfg_mgr cfg_mgr, Gfo_qarg_mgr url_args, String key, String dflt_val) {
		String cfg_key = "xowa.addon.search.fulltext.special.dflt_" + key;
		String cfg_val = cfg_mgr.Get_str_app_or(cfg_key, dflt_val);
		props.Add("dflt_" + key, cfg_val);
		props.Add("qarg_" + key, url_args.Read_str_or(key, cfg_val));
	}
	@Override protected Io_url Get_addon_dir(Xoa_app app)			{return Addon_dir(app);}
	@Override protected Io_url Get_mustache_fil(Io_url addon_dir)	{return addon_dir.GenSubFil_nest("bin", "xofulltext_searcher.main.template.html");}
	@Override protected Mustache_doc_itm Bld_mustache_root(Xoa_app app) {return this;}
	@Override protected void Bld_tags(Xoa_app app, Io_url addon_dir, Xopage_html_data page_data) {
		Xopg_tag_mgr head_tags = page_data.Head_tags();
		Xopg_tag_wtr_.Add__xoelem	(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xocss	(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xohelp	(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xolog	(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xoajax	(head_tags, app.Fsys_mgr().Http_root(), app);
		Xopg_tag_wtr_.Add__xotmpl	(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__jquery	(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xonotify (head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__mustache	(head_tags, app.Fsys_mgr().Http_root());
		Xopg_alertify_.Add_tags	    (head_tags, app.Fsys_mgr().Http_root());

		head_tags.Add(Xopg_tag_itm.New_css_file(addon_dir.GenSubFil_nest("bin", "xofulltext_searcher.css")));
		head_tags.Add(Xopg_tag_itm.New_js_file(addon_dir.GenSubFil_nest("bin", "xofulltext_searcher.js")));
		head_tags.Add(Xopg_tag_itm.New_htm_frag(addon_dir.GenSubFil_nest("bin", "xofulltext_searcher.wiki.template.html"), "xofts.wiki"));
		head_tags.Add(Xopg_tag_itm.New_htm_frag(addon_dir.GenSubFil_nest("bin", "xofulltext_searcher.page.template.html"), "xofts.page"));
		head_tags.Add(Xopg_tag_itm.New_htm_frag(addon_dir.GenSubFil_nest("bin", "xofulltext_searcher.line.template.html"), "xofts.line"));

		page_data.Js_enabled_y_();
	}

	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		String val = (String)props.Get_by(key);
		if (val == null)
			return false;
		else {
			bfr.Add_str_u8(val);
			return true;
		}
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		if		(String_.Eq(key, "case_match"))              return Mustache_doc_itm_.Ary__bool(case_match);
		else if	(String_.Eq(key, "auto_wildcard_bgn"))       return Mustache_doc_itm_.Ary__bool(auto_wildcard_bgn);
		else if	(String_.Eq(key, "auto_wildcard_end"))       return Mustache_doc_itm_.Ary__bool(auto_wildcard_end);
		else if	(String_.Eq(key, "expand_matches_section"))  return Mustache_doc_itm_.Ary__bool(expand_matches_section);
		else if	(String_.Eq(key, "show_all_matches"))        return Mustache_doc_itm_.Ary__bool(show_all_matches);
		return Mustache_doc_itm_.Ary__empty;
	}
	public static Io_url Addon_dir(Xoa_app app) {
		return app.Fsys_mgr().Http_root().GenSubDir_nest("bin", "any", "xowa", "addon", "wiki", "fulltext", "searcher");
	}
}
