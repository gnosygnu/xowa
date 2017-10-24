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
package gplx.xowa.addons.bldrs.mass_parses.parses.utls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*; import gplx.xowa.addons.bldrs.mass_parses.parses.*;
import gplx.dbs.*;
import gplx.xowa.wikis.caches.*;
public class Xomp_tmpl_cache_bldr {
	public static Xow_page_cache New(Xowe_wiki wiki, boolean fill_all) {
		Xow_page_cache rv = new Xow_page_cache(wiki);
		if (fill_all) Fill_all(rv, wiki);
		return rv;
	}
	private static void Fill_all(Xow_page_cache cache, Xowe_wiki wiki) {
		String sql = String_.Concat_lines_nl_skip_last	// ANSI.Y
		( "SELECT  pp.page_id"
		, ",       pp.page_namespace"
		, ",       pp.page_title"
		, ",       pp.page_text_db_id"
		, ",       pp.page_redirect_id"
		, "FROM    page pp"
		, "WHERE   pp.page_namespace IN (8, 10, 828)"
		);

		Xomp_text_db_loader text_db_loader = new Xomp_text_db_loader(wiki);

		// load pages
		int count = 0;
		List_adp redirect_list = List_adp_.New();
		Ordered_hash page_regy = Ordered_hash_.New();
		Db_rdr rdr = wiki.Data__core_mgr().Db__core().Tbl__page().Conn().Stmt_sql(sql).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				// get ttl
				Xoa_ttl page_ttl = wiki.Ttl_parse(rdr.Read_int("page_namespace"), rdr.Read_bry_by_str("page_title"));

				// add to text_db_loader
				int page_id = rdr.Read_int("page_id"); 
				int page_redirect_id = rdr.Read_int("page_redirect_id");
				Xow_page_cache_itm itm = new Xow_page_cache_itm(true, page_ttl, null, null);	// NOTE: "null, null;" b/c GetContent in Scrib_title checks specifically for null, not empty String; DATE:2016-10-19
				itm.Set_page_ids(page_id, page_redirect_id);
				text_db_loader.Add(rdr.Read_int("page_text_db_id"), itm);
				
				// ignore duplicate page_titles in cache; EX:ru.n:Модуль:Weather/data DATE:2017-03-16
				if (cache.Get_or_null(page_ttl.Full_db()) == null) {
					cache.Add(page_ttl.Full_db(), itm);
				}
				else {
					Gfo_usr_dlg_.Instance.Warn_many("", "", "mass_parse: ignoring duplicate page title in page cache; title=~{0} id=~{1}", page_ttl.Full_db(), page_id);
				}

				page_regy.Add(page_id, itm);

				if (page_redirect_id != -1)
					redirect_list.Add(itm);
				if ((++count % 10000) == 0)
					Gfo_usr_dlg_.Instance.Prog_many("", "", "loading tmpls: ~{0}", count);
			}
		} finally {rdr.Rls();}

		// load wikitext
		text_db_loader.Load();

		// handle redirects
		int redirect_len = redirect_list.Len();
		for (int i = 0; i < redirect_len; ++i) {
			Xow_page_cache_itm src_itm = (Xow_page_cache_itm)redirect_list.Get_at(i);
			Xow_page_cache_itm trg_itm = (Xow_page_cache_itm)page_regy.Get_by(src_itm.Redirect_id());
			byte[] trg_itm_wtxt = null;
			Xoa_ttl trg_ttl = null;
			if (trg_itm == null) {	// template can redirect to non-template pages
				Xoa_ttl src_ttl = src_itm.Ttl();
				Xoae_page wpg = Xoae_page.New(wiki, src_ttl);
				wiki.Data_mgr().Load_from_db(wpg, src_ttl.Ns(), src_ttl, false);
				if (wpg.Db().Page().Exists()) {
					trg_itm_wtxt = wpg.Db().Text().Text_bry();
					trg_ttl = wpg.Ttl();
				}
				else {
					Gfo_usr_dlg_.Instance.Prog_many("", "", "missing redirect for tmpl: ~{0}", src_itm.Ttl().Full_db());
					continue;
				}
			}
			else {
				trg_itm_wtxt = trg_itm.Wtxt__direct();
				trg_ttl = trg_itm.Ttl();
			}
			src_itm.Set_redirect(trg_ttl, trg_itm_wtxt);	// NOTE: itm must have title of redirect, not original item; EX:Template:Ifempty -> Template:If_empty; DATE:2016-07-26
		}
	}
}
