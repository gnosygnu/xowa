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
import gplx.xowa.parsers.amps.*;
import gplx.xowa.htmls.core.htmls.*;
class Xofulltext_searcher_html extends Xow_special_wtr__base implements Mustache_doc_itm {
	private final    Hash_adp props = Hash_adp_.New();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	public Xofulltext_searcher_html(Xocfg_mgr cfg_mgr, Gfo_qarg_mgr url_args, Xow_wiki wiki, Guid_adp page_guid, boolean lucene_exists) {
		String search_text = url_args.Read_str_or("search", ""); 
		search_text = String_.Replace(search_text, "_", " "); // xofulltext_searcher.js chains multiple words with "_"; convert back to space
		props.Add("qarg_search", Xoh_html_wtr_escaper.Escape_str(Xop_amp_mgr.Instance, tmp_bfr, search_text));
		props.Add("page_guid", page_guid.To_str());
		props.Add("cur_wiki", wiki.Domain_str());

		// enabled
		boolean app_is_drd = gplx.core.envs.Op_sys.Cur().Tid_is_drd();
		props.Add("app_is_drd", app_is_drd); // for options button
		props.Add("disabled", app_is_drd && !lucene_exists);

		// options:strings
		props_Add_str(cfg_mgr, url_args, "wikis" , wiki.Domain_str(), false);
		props_Add_str(cfg_mgr, url_args, "ns_ids", "0");
		props_Add_str(cfg_mgr, url_args, "limits", "25");
		props_Add_str(cfg_mgr, url_args, "offsets", "0");
		props_Add_str(cfg_mgr, url_args, "expand_pages", "y");
		props_Add_str(cfg_mgr, url_args, "expand_snips", "n");
		props_Add_str(cfg_mgr, url_args, "show_all_snips", "y");

		// options:bools
		props_Add_bool(cfg_mgr, url_args, "xowa.addon.fulltext_search.options", "expand_options");
		props_Add_bool(cfg_mgr, url_args, "xowa.addon.fulltext_search.special", "case_match");
		props_Add_bool(cfg_mgr, url_args, "xowa.addon.fulltext_search.special", "auto_wildcard_bgn");
		props_Add_bool(cfg_mgr, url_args, "xowa.addon.fulltext_search.special", "auto_wildcard_end");
	}
	private void props_Add_str(Xocfg_mgr cfg_mgr, Gfo_qarg_mgr url_args, String key, String else_val) {
		props_Add_str(cfg_mgr, url_args, key, else_val, true);
	}
	private void props_Add_str(Xocfg_mgr cfg_mgr, Gfo_qarg_mgr url_args, String key, String else_val, boolean use_cfg) {
		String dflt_val = else_val;
		if (use_cfg) {
			String cfg_key = "xowa.addon.fulltext_search.special." + key;
			dflt_val = cfg_mgr.Get_str_app_or(cfg_key, else_val);
		}
		props.Add("dflt_" + key, dflt_val);
		props.Add("qarg_" + key, url_args.Read_str_or(key, dflt_val));
	}
	private void props_Add_bool(Xocfg_mgr cfg_mgr, Gfo_qarg_mgr url_args, String cfg_grp, String key) {
		String cfg_key = cfg_grp + "." + key;
		boolean cfg_val = cfg_mgr.Get_bool_app_or(cfg_key, false);
		props.Add(key, cfg_val);
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
		Xopg_tag_wtr_.Add__ooui     (head_tags, app.Fsys_mgr().Http_root());

		head_tags.Add(Xopg_tag_itm.New_css_file(addon_dir.GenSubFil_nest("bin", "xofulltext_searcher.css")));
		head_tags.Add(Xopg_tag_itm.New_js_file(addon_dir.GenSubFil_nest("bin", "xofulltext_searcher.js")));
		head_tags.Add(Xopg_tag_itm.New_htm_frag(addon_dir.GenSubFil_nest("bin", "xofulltext_searcher.wiki.template.html"), "xofts.wiki"));
		head_tags.Add(Xopg_tag_itm.New_htm_frag(addon_dir.GenSubFil_nest("bin", "xofulltext_searcher.page.template.html"), "xofts.page"));
		head_tags.Add(Xopg_tag_itm.New_htm_frag(addon_dir.GenSubFil_nest("bin", "xofulltext_searcher.snip.template.html"), "xofts.snip"));

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
		Object val_obj = props.Get_by(key);
		if (val_obj == null) {
			return Mustache_doc_itm_.Ary__empty;
		}
		else {
			return Mustache_doc_itm_.Ary__bool((boolean)val_obj);
		}
	}
	public static Io_url Addon_dir(Xoa_app app) {
		return app.Fsys_mgr().Http_root().GenSubDir_nest("bin", "any", "xowa", "addon", "wiki", "fulltext", "searcher");
	}
}
