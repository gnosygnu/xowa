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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.langs.*;
class Xoctg_catlink_loader {
	private byte version;
	private int link_dbs_len;
	private Db_attach_mgr attach_mgr;
	private final    Bry_bfr sortkey_val_bfr = Bry_bfr_.New();
	public void Run(Xoctg_catpage_ctg rv, Xow_wiki wiki, Xoctg_catpage_mgr catpage_mgr, Xowd_page_tbl page_tbl, int cat_id, byte grp_tid, boolean url_is_from, byte[] url_sortkey, int limit) {
		String sql = Bld_sql(catpage_mgr, cat_id, grp_tid, url_is_from, url_sortkey, limit);
		Load_catlinks(rv, wiki, page_tbl, rv.Grp_by_tid(grp_tid), sql, url_is_from, limit);
	}
	public void Make_attach_mgr__v2(Xow_db_mgr db_mgr, int cat_link_db_idx) {
		this.version = 2;
		this.link_dbs_len = 1;
		Xow_db_file cat_link_db = db_mgr.Dbs__get_by_id_or_fail(cat_link_db_idx);
		this.attach_mgr = new Db_attach_mgr(cat_link_db.Conn(), new Db_attach_itm("link_db_1", cat_link_db.Conn()));
	}
	public void Make_attach_mgr__v3_v4(Xow_db_mgr db_mgr, Db_conn cat_core_conn) {
		// init db vars
		List_adp db_list = List_adp_.New();
		Db_conn db_1st = null;
		int db_idx = 0;

		// fill db_list by looping over each db unless (a) cat_link_db or (b) core_db (if all or few)
		int len = db_mgr.Dbs__len();
		for (int i = 0; i < len; ++i) {
			Xow_db_file cl_db = db_mgr.Dbs__get_at(i);
			switch (cl_db.Tid()) {
				case Xow_db_file_.Tid__cat_link:	// always use cat_link db
					break;
				case Xow_db_file_.Tid__core:		// only use core if all or few
					if (db_mgr.Props().Layout_text().Tid_is_lot()) 
						continue;
					else
						break;
				default:							// skip all other files
					continue;
			}

			// add to db_list
			if (db_1st == null) db_1st = cl_db.Conn();
			db_list.Add(new Db_attach_itm("link_db_" + ++db_idx, cl_db.Conn()));
		}

		// make attach_mgr
		this.version = 4;
		this.link_dbs_len = db_list.Len();
		if (cat_core_conn.Meta_tbl_exists("cat_sort")) {
			version = 3;
			db_1st = cat_core_conn;
		}
		this.attach_mgr = new Db_attach_mgr(db_1st, (Db_attach_itm[])db_list.To_ary_and_clear(Db_attach_itm.class));
	}
	private String Bld_sql		(Xoctg_catpage_mgr catpage_mgr, int cat_id, byte grp_tid, boolean url_is_from, byte[] url_sortkey, int limit) {
		Bry_bfr bfr = Bry_bfr_.New();
		for (int i = 0; i < link_dbs_len; ++i)
			Bld_sql_by_db(catpage_mgr, cat_id, grp_tid, url_is_from, url_sortkey, i + List_adp_.Base1, bfr);
		bfr.Add_str_u8_fmt
		( "\nORDER BY cl_to_id, cl_type_id, cl_sortkey {0}"
		+ "\nLIMIT {1}"
		, url_is_from ? "ASC" : "DESC", limit + 1);
		return bfr.To_str_and_clear();
	}
	private void Bld_sql_by_db	(Xoctg_catpage_mgr catpage_mgr, int cat_id, byte grp_tid, boolean url_is_from, byte[] url_sortkey, int link_db_id, Bry_bfr bfr) {
		if (link_db_id != List_adp_.Base1) bfr.Add_str_a7("\nUNION\n");

		// change sortkey vars per version; note that v3 differs from v2 and v4 b/c of cat_sort tbl
		String sortkey_col  = "cl_sortkey";
		String sortkey_join = "";
		if (version == 3) {	 // NOTE: version 3 takes sortkey from cat_sort
			sortkey_col = "cs.cs_key";
			sortkey_join = "\n        JOIN cat_sort cs ON cl.cl_sortkey_id = cs.cs_id";
		}

		// sortkey_val
		byte[] sortkey_val = null;
		String sortkey_prefix_fld = version == 4 ? "cl_sortkey_prefix" : "''";
		sortkey_val = Build_sortkey_val(sortkey_val_bfr, version, catpage_mgr.Collation_mgr(), url_sortkey);

		// bld sql; NOTE: building sql with args embedded b/c (a) UNION requires multiple Crt_arg for each ?; (EX: 4 unions, 3 ? require 12 .Crt_arg); (b) easier to debug
		String sql = Db_sql_.Make_by_fmt(String_.Ary
		( "SELECT  cl_to_id"
		, ",       cl_from"
		, ",       cl_type_id"
		, ",       {3} AS cl_sortkey"
		, ",       {7} AS cl_sortkey_prefix"
		, "FROM    <link_db_{0}>cat_link cl{6}"
		, "WHERE   cl_to_id = {1}"
		, "AND     cl_type_id = {2}"
		, "AND     {3} {4} {5}"
		), link_db_id, cat_id, grp_tid, sortkey_col, url_is_from ? ">=" : "<", sortkey_val, sortkey_join, sortkey_prefix_fld);
		bfr.Add_str_u8(sql);
	}
	private void Load_catlinks(Xoctg_catpage_ctg rv, Xow_wiki wiki, Xowd_page_tbl page_tbl, Xoctg_catpage_grp grp, String sql, boolean url_is_from, int limit) {
		// init; prep sql
		Xoctg_page_loader catlink_loader = new Xoctg_page_loader(wiki);
		Ordered_hash hash = catlink_loader.Hash();
		sql = attach_mgr.Resolve_sql(sql);

		// run sql and create itms based on cat_link
		Xoctg_catpage_itm zth_itm = null;
		Db_rdr rdr = Db_rdr_.Empty;
		int row_idx = 0;
		try {
			attach_mgr.Attach();
			rdr = attach_mgr.Conn_main().Stmt_sql(sql).Exec_select__rls_auto();
			while (rdr.Move_next()) {
				Xoctg_catpage_itm itm = Xoctg_catpage_itm.New_by_rdr(rdr, version);
				if (row_idx++ < limit)	// row_idx >= limit for last row; EX: 200 < 200
					hash.Add(itm.Page_id(), itm);
				else {					// last row; EX: 201
					if (url_is_from)	// from=some_key; 201st row is sort_key for "(Next 200)"
						zth_itm = itm;
					else				// until=some_key; 201st row means that 200th row is not 1st row; show prev link
						grp.Prev_disable_(false);
				}
			}
		}
		finally {
			rdr.Rls();
			attach_mgr.Detach();
		}

		// load ns / ttl from page
		page_tbl.Select_in__id(catlink_loader);
		grp.Itms_((Xoctg_catpage_itm[])hash.To_ary_and_clear(Xoctg_catpage_itm.class));

		// set data for Next 200 / Previous 200
		if (zth_itm != null) {
			if (version == 4) {
				Load_sortkey(wiki, grp, zth_itm);
				grp.Next_sortkey_(zth_itm.Sortkey_handle());
			}
			else
				grp.Next_sortkey_(zth_itm.Sortkey_handle());
		}
	}
	private static void Load_sortkey(Xow_wiki wiki, Xoctg_catpage_grp grp, Xoctg_catpage_itm zth_itm) {
		// load page_ttl from db
		Xowd_page_itm tmp_pg = new Xowd_page_itm();
		wiki.Data__core_mgr().Tbl__page().Select_by_id(tmp_pg, zth_itm.Page_id());

		// set ttl; skip if page is missing (Talk: ns) else null-ref; PAGE:en.wCategory:Disambig-Class_Comics_articles_of_NA-importance DATE:2016-10-12
		if (tmp_pg.Exists()) {
			Xoa_ttl zth_ttl = wiki.Ttl_parse(tmp_pg.Ns_id(), tmp_pg.Ttl_page_db());
			zth_itm.Page_ttl_(zth_ttl);
		}

		// make sortkey
		byte[] prv_sortkey = grp.Itms__len() == 0 ? Bry_.Empty : grp.Itms__get_at(grp.Itms__len() - 1).Sortkey_handle();
		zth_itm.Sortkey_handle_make(Bry_bfr_.New(), prv_sortkey);
	}
	public static byte[] Build_sortkey_val(Bry_bfr sortkey_val_bfr, byte version, Xoctg_collation_mgr collation_mgr, byte[] url_sortkey) {
		// find \n and ignore everything after it; needed else "< 'A\nA'" will pull up "A"; NOTE: can't find logic in MediaWiki CategoryViewer.php; DATE:2016-10-11
		// ALSO: needed for v2 else SQL will literally have WHERE cl_sortkey = 'A\nA';
		byte[] tmp_sortkey = url_sortkey;
		int nl_pos = Bry_find_.Find_fwd(tmp_sortkey, Byte_ascii.Nl);
		if (nl_pos != Bry_find_.Not_found)
			tmp_sortkey = Bry_.Mid(tmp_sortkey, 0, nl_pos);

		if (version == 4) {
			if (Bry_.Len_gt_0(url_sortkey)) {
				// make sortkey_val
				sortkey_val_bfr.Add_byte(Byte_ascii.Ltr_x).Add_byte_apos();
				gplx.core.encoders.Hex_utl_.Encode_bfr(sortkey_val_bfr, collation_mgr.Get_sortkey(tmp_sortkey));
				sortkey_val_bfr.Add_byte_apos();
			}
			else
				sortkey_val_bfr.Add_byte_apos().Add_byte_apos();
		}
		else
			sortkey_val_bfr.Add_byte_apos().Add(Db_sql_.Escape_arg(tmp_sortkey)).Add_byte_apos();
		return sortkey_val_bfr.To_bry_and_clear();
	}
}