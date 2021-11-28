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
	private final Xow_wiki wiki;
	private final Xoctg_catpage_mgr catpage_mgr;
	private final Xowd_page_tbl page_tbl;
	private final Db_attach_mgr attach_mgr;
	private final byte version;
	private final int link_dbs_len;
	private final Bry_bfr sortkey_val_bfr = Bry_bfr_.New();
	Xoctg_catlink_loader(Xow_wiki wiki, Xoctg_catpage_mgr catpage_mgr, Xowd_page_tbl page_tbl, byte version, int link_dbs_len, Db_attach_mgr attach_mgr) {
		this.wiki = wiki;
		this.catpage_mgr = catpage_mgr;
		this.page_tbl = page_tbl;
		this.version = version;
		this.link_dbs_len = link_dbs_len;
		this.attach_mgr = attach_mgr;
	}
	public void Run(Xoctg_catpage_ctg rv, int cat_id, byte grp_tid, boolean url_is_from, byte[] url_sortkey, int limit) {
		// get 200 catlinks per db; NOTE:CATEGORY_LINKS_LOADER
		List_adp catlink_list = List_adp_.New();
		for (int i = 0; i < link_dbs_len; i++) {
			String sql = Generate_sql(cat_id, grp_tid, url_is_from, url_sortkey, limit, i + 1);
			Load_catlinks(catlink_list, sql);
		}

		// no catlinks; exit
		if (catlink_list.Len() == 0) return;

		// sort and reduce list to 200 total
		catlink_list.Sort_by(new Xoctg_catlink_sorter(url_is_from));
		Ordered_hash catlink_hash = Ordered_hash_.New();
		int catlink_list_len = catlink_list.Len();
		int max = catlink_list_len < limit ? catlink_list_len : limit;
		for (int i = 0; i < max; i++) {
			Xoctg_catpage_itm itm = (Xoctg_catpage_itm)catlink_list.Get_at(i);
			catlink_hash.Add(itm.Page_id(), itm);
		}

		// load ns / ttl for each catlink
		Xoctg_catpage_grp grp = rv.Grp_by_tid(grp_tid);
		grp.Itms_(wiki, (Xoctg_catpage_itm[])catlink_hash.To_ary_and_clear(Xoctg_catpage_itm.class));

		// get zth_itm (for "Next 200" / "Previous 200")
		if (catlink_list_len > limit) {
			if (url_is_from) {	// from=some_key; 201st row is sort_key for "(Next 200)"
				Xoctg_catpage_itm zth_itm = (Xoctg_catpage_itm)catlink_list.Get_at(limit);
				if (version == 4) {
					Load_sortkey(grp, zth_itm);
				}
				grp.Next_sortkey_(zth_itm.Sortkey_handle());
			}
			else				// until=some_key; 201st row means that 200th row is not 1st row; show prev link
				grp.Prev_disable_(false);
		}
	}
	private String Generate_sql(int cat_id, byte grp_tid, boolean url_is_from, byte[] url_sortkey, int limit, int link_db_id) {
		// change sortkey vars per version; note that v3 differs from v2 and v4 b/c of cat_sort tbl
		String sortkey_col  = "cl_sortkey";
		String sortkey_join = "";
		if (version == 3) { // NOTE: version 3 takes sortkey from cat_sort
			sortkey_col = "cs.cs_key";
			sortkey_join = "\n        JOIN cat_sort cs ON cl.cl_sortkey_id = cs.cs_id";
		}

		// sortkey_val
		byte[] sortkey_val = Build_sortkey_val(sortkey_val_bfr, version, catpage_mgr.Collation_mgr(), url_sortkey);
		String sortkey_prefix_fld = version == 4 ? "cl_sortkey_prefix" : "''";

		// build sql; NOTE: building sql with args embedded b/c (a) UNION requires multiple Crt_arg for each ?; (EX: 4 unions, 3 ? require 12 .Crt_arg); (b) easier to debug
		Bry_bfr bfr = Bry_bfr_.New();
		bfr.Add_str_u8_fmt(String_.Concat_lines_nl
		( "SELECT  cl_to_id"
		, ",       cl_from"
		, ",       cl_type_id"
		, ",       {0} AS cl_sortkey"
		, ",       {1} AS cl_sortkey_prefix"
		, ",       p.page_namespace"
		, ",       p.page_title"
		, "FROM    <link_db_{3}>cat_link cl{2}"
		, "        LEFT JOIN <page_db>page p ON p.page_id = cl{2}.cl_from"
		), sortkey_col, sortkey_prefix_fld, sortkey_join, link_db_id);
		bfr.Add_str_u8_fmt(String_.Concat_lines_nl
		( "WHERE   cl_to_id = {0}"
		, "AND     cl_type_id = {1}"
		, "AND     {2} {3} {4}"
		), cat_id, grp_tid, sortkey_col, url_is_from ? ">=" : "<", sortkey_val);
		bfr.Add_str_u8_fmt(String_.Concat_lines_nl
		( "ORDER BY cl_to_id, cl_type_id, cl_sortkey {0}"
		, "LIMIT {1}"
		), url_is_from ? "ASC" : "DESC", limit + 1);
		return attach_mgr.Resolve_sql(bfr.To_str_and_clear());
	}
	private void Load_catlinks(List_adp catlink_list, String sql) {
		Db_rdr rdr = Db_rdr_.Empty;
		int count = 0;
		try {
			attach_mgr.Attach();
			rdr = attach_mgr.Conn_main().Stmt_sql(sql).Exec_select__rls_auto();
			while (rdr.Move_next()) {
				Xoctg_catpage_itm itm = Xoctg_catpage_itm.New_by_rdr(wiki, rdr, version);
				catlink_list.Add(itm);
				if (count >= 1000 && (count % 1000) == 0) Gfo_usr_dlg_.Instance.Prog_many("", "", "loading cat_links: count=~{0}", count);
				count++;
			}
		}
		finally {
			rdr.Rls();
			attach_mgr.Detach();
		}
	}
	private void Load_sortkey(Xoctg_catpage_grp grp, Xoctg_catpage_itm zth_itm) {
		// load page_ttl from db
		Xowd_page_itm tmp_pg = new Xowd_page_itm();
		page_tbl.Select_by_id(tmp_pg, zth_itm.Page_id());

		// set ttl; skip if page is missing (Talk: ns) else null-ref; PAGE:en.wCategory:Disambig-Class_Comics_articles_of_NA-importance DATE:2016-10-12
		if (tmp_pg.Exists()) {
			Xoa_ttl zth_ttl = wiki.Ttl_parse(tmp_pg.Ns_id(), tmp_pg.Ttl_page_db());
			zth_itm.Page_ttl_(zth_ttl);
		}

		// make sortkey
		byte[] prv_sortkey = grp.Itms__len() == 0 ? Bry_.Empty : grp.Itms__get_at(grp.Itms__len() - 1).Sortkey_handle();
		zth_itm.Sortkey_handle_make(Bry_bfr_.New(), wiki, prv_sortkey);
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
	public static Xoctg_catlink_loader New_v2(Xow_wiki wiki, Xoctg_catpage_mgr catpage_mgr, Xowd_page_tbl page_tbl, Xow_db_mgr db_mgr, int cat_link_db_idx) {
		Xow_db_file cat_link_db = db_mgr.Dbs__get_by_id_or_fail(cat_link_db_idx);
		Db_attach_mgr attach_mgr = new Db_attach_mgr(cat_link_db.Conn(), new Db_attach_itm("link_db_1", cat_link_db.Conn()));
		return new Xoctg_catlink_loader(wiki, catpage_mgr, page_tbl, Byte_.Cast(2), 1, attach_mgr);
	}
	public static Xoctg_catlink_loader New_v3_v4(Xow_wiki wiki, Xoctg_catpage_mgr catpage_mgr, Xowd_page_tbl page_tbl, Xow_db_mgr db_mgr, Db_conn cat_core_conn) {
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
		byte version = 4;
		int link_dbs_len = db_list.Len();
		if (cat_core_conn.Meta_tbl_exists("cat_sort")) {
			version = 3;
			db_1st = cat_core_conn;
		}

		// add page_db
		db_list.Add(new Db_attach_itm("page_db", page_tbl.Conn()));

		Db_attach_mgr attach_mgr = new Db_attach_mgr(db_1st, (Db_attach_itm[])db_list.To_ary_and_clear(Db_attach_itm.class));
		return new Xoctg_catlink_loader(wiki, catpage_mgr, page_tbl, version, link_dbs_len, attach_mgr);
	}
}
class Xoctg_catlink_sorter implements gplx.core.lists.ComparerAble {
	private final int sort_multiplier;
	public Xoctg_catlink_sorter(boolean sort_ascending) {
		this.sort_multiplier = sort_ascending ? 1 : -1;
	}
	public int compare(Object lhsObj, Object rhsObj) {
		Xoctg_catpage_itm lhs = (Xoctg_catpage_itm)lhsObj;
		Xoctg_catpage_itm rhs = (Xoctg_catpage_itm)rhsObj;
		return sort_multiplier * Bry_.Compare(lhs.Sortkey_binary(), rhs.Sortkey_binary());
	}
}
/*
== NOTE:CATEGORY_LINKS_LOADER ==

=== Background ===
Category links are used in 2 ways:
* On a page level: For a given page, find its categories
* On a category level: For a given category, find its pages

XOWA stores this bi-directional mapping in the "xtn.category.link" dbs inside the "cat_link" table 
* cl_from: page_id
* cl_to_id: category_id

There are two ways to store this mapping:
* On a page-centric level:
** For a given page, all its categories are saved in the same database
** However, for a given category, all its pages are then scattered across multiple databases
* On a category-centric level:
** For a given category, all its pages are saved in the same databases
** However, for a given page, all its categories are then scattered across multiple databases

By default, XOWA stores this mapping on page-centric level. 
* This means that all categories for a page will be stored continuously within one database
* This allows XOWA to quickly retrieve categories for a page by just running "SELECT * FROM cat_link WHERE cl_from = <page_id>;" from one database
* Unfortunately, this scatters pages for a category across multiple databases

=== APPROACH #0: Use one database ===
The best approach would be to avoid partitionining and store categories in one database.

However, this would mean one sqlite file. For 2019-05 enwiki that would be one 18 GB file. 

XOWA limits files to 2 GB for a few reasons:
* XOWA supports FAT32 filesystem which has a 4 GB limit and Android which has an unofficial 2 GB limit (NOTE: XOWA categories is not needed on Android)
* Distribution reasons: easier to distribute nine 2 GB files instead of one 18 GB file
* Difficulties working with large SQLite databases: INDEX/VACCUUM takes a very long time

=== APPROACH #1: Store both mappings===
The next best approach would be to store both page-centric and category-centric mappings

However, there are disadvantages
* Disk space: 
** For 2019-05 enwiki, there are roughly 150 million catlinks (12 million * 12) which uses 17.9 GB of space
** Storing the category-centric mapping could result in adding another 4.5 GB
*** (Back-of-the-envelope-calc: 4.5 GB = 150 million * (8 bytes for two ints + 8 bytes for indexing two ints + 14 bytes for estimating row overhead))
* Database schema: The database schema would have to be changed to save these new tables, as well as retrieve them
* Backward compatibility: Existing XOWA databases (from 2018-01 -> 2019-05) would still not work

=== APPROACH #2: UNION ===
Xoctg_catlink_loader used to run 1 SQL statement but UNION'd over multiple catlink dbs

This off-loaded all the work to sqlite. Specifically, sqlite got a list of 200 catlinks...
* FROM multiple catlink dbs
* WHERE the catlinks <> some_arbitrary_point
* ORDER'd BY sortkey
* LIMIT'd by 200

This seemed performant enough but fell apart as desb42 discovered in ISSUE#:238. 
* SQLITE has a 10 ATTACH TABLE limit (SEE https://www.sqlite.org/limits.html)
* XOWA catlink dbs now numbered 12 for enwiki (as early as 2018-11)

=== APPROACH #3: Merge-sort ===
The optimal way would be be to do a merge-sort. At a high-level:
* Open up 12 Db_rdr's (one for each catlink db)
* Loop over each of the 12 Db_rdr's to find the lowest sortkey
* Add to list and repeat until 200 found.

This has the advantage of being performant and retrieving only 200 total

This has one conceptual disadvantage: multiple disk seeks
* Each of the 200 records would result from a rdr.Move_next()
* Each of the rdr.Move_next() would be individual hard-disk seeks over any one of 12 files
* Since the 200 records could be distributed randomly over 12 files, this could mean 200 individual disk seeks

However:
* This performance hit may not actually be noticeable
* This performance hit will not be a factor on flash memory drives

=== APPROACH #4: Sequential load ===
The current approach is to sequentially loop through each database and SELECT 200 rows

This has these advantages:
* Conceptually, only 12 "disk seeks" will be done (reading the INDEX 12 times from 12 files)
* Also, it may parallel what SQLite is doing in the UNION approach (though SQLite may be closer to the merge-sort approach)
* Implementation is simple

This has the obvious disadvantage of selecting more rows than needed (potentially 2400 or 200 * 12)

Furthermore, the performance will decrease as the number of databases increase. For example, if there are 200 catlink databases
* 40,000 rows will be retrieved (200 * 200) when only 200 are needed
* 200 disks seeks will be done, which will be little different than the merge-sort approach
*/