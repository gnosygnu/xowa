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
package gplx.xowa.addons.bldrs.mass_parses.makes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.dbs.*; import gplx.xowa.bldrs.*;
import gplx.xowa.addons.bldrs.mass_parses.dbs.*;
// merges xomp.wkr_dbs into xowa.file.make.sqlite3
// NOTE: can't do INSERT b/c (a) autonum id is same in diff worker dbs; (b): want autonum to match ns_id, page_id order
abstract class Xomp_make_merger__base implements Xomp_make_merger, gplx.core.lists.ComparerAble {
	private Xob_db_file trg_db;
	protected Db_tbl trg_tbl;
	private List_adp rows = List_adp_.New();
	private String src_tbl__name, src_fld__page_id;
	public void	Merger__init(Xowe_wiki wiki, Xomp_wkr_db[] src_dbs) {
		// get trg.db
		this.trg_db = Xob_db_file.New__file_make(wiki.Fsys_mgr().Root_dir());

		// make trg.tbl
		this.trg_tbl = Init__trg_tbl(trg_db);
		trg_db.Conn().Meta_tbl_remake(trg_tbl);

		// make idxs on src.tbls
		this.src_tbl__name = this.trg_tbl.Tbl_name();
		this.src_fld__page_id = this.Init__src_fld__page_id();
		int len = src_dbs.length;
		for (int i = 0; i < len; ++i) {
			src_dbs[i].Conn().Meta_idx_assert(src_tbl__name, src_fld__page_id, src_fld__page_id);
		}

		// do any other init, such as init'ing insert stmt
		this.Init__trg_bgn();
		this.trg_db.Conn().Txn_bgn("merger__" + src_tbl__name);
	}
	protected abstract Db_tbl	Init__trg_tbl(Xob_db_file trg_db);
	protected abstract String	Init__src_fld__page_id();
	@gplx.Virtual protected void		Init__trg_bgn() {}

	public int Merger__load(Xomp_mgr_db src_mgr_db, Xomp_wkr_db src_wkr_db, int uid_bgn, int uid_end) {
		// build sql
		Db_attach_mgr attach_mgr = new Db_attach_mgr(src_mgr_db.Conn());
		attach_mgr.Conn_links_(new Db_attach_itm("src_wkr_db", src_wkr_db.Conn()));
		String sql = Db_sql_.Make_by_fmt(String_.Ary
		( "SELECT  src_mgr.xomp_uid"
		, ",       src_wkr.*"
		, "FROM    <src_wkr_db>{0} src_wkr"
		, "        JOIN xomp_page src_mgr ON src_wkr.{1} = src_mgr.page_id"
		, "WHERE   src_mgr.xomp_uid > {2} AND src_mgr.xomp_uid <= {3}"	// NOTE: mgr.xomp_uid will sort pages by ns_id, page_id
		)
		, src_tbl__name, src_fld__page_id
		, uid_bgn, uid_end
		);	
		sql = attach_mgr.Resolve_sql(sql);

		// load rows
		attach_mgr.Attach();
		Db_rdr rdr = src_mgr_db.Conn().Stmt_sql(sql).Exec_select__rls_auto();
		int rv = -1;
		try {
			while (rdr.Move_next()) {
				rv = rdr.Read_int("xomp_uid");
				Object row = Load__src_row(rdr);
				rows.Add(row);
			}
		}	finally {rdr.Rls();}
		attach_mgr.Detach();
		return rv;
	}
	protected abstract Object Load__src_row(Db_rdr rdr);

	public void	Merger__save() {
		rows.Sort_by(this);
		int len = rows.Len();
		for (int i = 0; i < len; ++i) {
			Save__trg_row(rows.Get_at(i));
		}
		rows.Clear();
	}
	protected abstract void Save__trg_row(Object row_obj);
	public void	Merger__term() {
		this.trg_db.Conn().Txn_end();
	}
	public int compare(Object lhsObj, Object rhsObj) {
		return Compare__hook(lhsObj, rhsObj);
	}
	protected abstract int Compare__hook(Object lhsObj, Object rhsObj);
}
