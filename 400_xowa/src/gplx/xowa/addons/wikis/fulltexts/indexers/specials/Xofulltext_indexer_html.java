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
package gplx.xowa.addons.wikis.fulltexts.indexers.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*; import gplx.xowa.addons.wikis.fulltexts.indexers.*;
import gplx.xowa.specials.*; import gplx.langs.mustaches.*; import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.pages.tags.*;
import gplx.dbs.*;
class Xofulltext_indexer_html extends Xow_special_wtr__base implements Mustache_doc_itm {
	private final    String wikis_bry, ns_ids, idx_opt;
	public Xofulltext_indexer_html(String wikis_bry, String ns_ids, String idx_opt) {
		this.wikis_bry = wikis_bry;
		this.ns_ids = ns_ids;
		this.idx_opt = idx_opt;
	}
	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		if		(String_.Eq(key, "wikis"))    bfr.Add_str_u8(wikis_bry);
		else if	(String_.Eq(key, "ns_ids"))   bfr.Add_str_u8(ns_ids);
		else if	(String_.Eq(key, "idx_opt"))  bfr.Add_str_u8(idx_opt);
		else                                  return false;
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		return Mustache_doc_itm_.Ary__empty;
	}

	@Override protected Io_url Get_addon_dir(Xoa_app app)			{return Addon_dir(app);}
	@Override protected Io_url Get_mustache_fil(Io_url addon_dir)	{return addon_dir.GenSubFil_nest("bin", "xofulltext_indexer.template.html");}
	@Override protected Mustache_doc_itm Bld_mustache_root(Xoa_app app) {
		return this;
	}
	@Override protected void Bld_tags(Xoa_app app, Io_url addon_dir, Xopage_html_data page_data) {
		Xopg_tag_mgr head_tags = page_data.Head_tags();
		Xopg_tag_wtr_.Add__xoelem	(head_tags, app.Fsys_mgr().Http_root());

		Xopg_tag_wtr_.Add__xocss	(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xohelp	(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xolog	(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xoajax	(head_tags, app.Fsys_mgr().Http_root(), app);
		Xopg_tag_wtr_.Add__jquery	(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xonotify (head_tags, app.Fsys_mgr().Http_root());
		Xopg_alertify_.Add_tags	    (head_tags, app.Fsys_mgr().Http_root());

		head_tags.Add(Xopg_tag_itm.New_css_file(addon_dir.GenSubFil_nest("bin", "xofulltext_indexer.css")));
		head_tags.Add(Xopg_tag_itm.New_js_file(addon_dir.GenSubFil_nest("bin", "xofulltext_indexer.js")));

		page_data.Js_enabled_y_();
	}
	public static Io_url Addon_dir(Xoa_app app) {
		return app.Fsys_mgr().Http_root().GenSubDir_nest("bin", "any", "xowa", "addon", "wiki", "fulltext", "indexer");
	}
}
