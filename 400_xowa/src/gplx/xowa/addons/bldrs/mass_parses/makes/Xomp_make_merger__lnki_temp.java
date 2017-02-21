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
import gplx.xowa.addons.bldrs.files.dbs.*;
class Xomp_make_merger__lnki_temp extends Xomp_make_merger__base {
	private int lnki_id;
	private Xob_lnki_temp_tbl trg_tbl__lnki_temp;
	@Override protected Db_tbl	Init__trg_tbl(Xob_db_file trg_db) {
		this.trg_tbl__lnki_temp = new Xob_lnki_temp_tbl(trg_db.Conn());
		trg_db.Conn().Meta_tbl_remake(trg_tbl__lnki_temp);
		return trg_tbl__lnki_temp;
	}
	@Override protected String	Init__src_fld__page_id()			{return "lnki_page_id";}
	@Override protected void		Init__trg_bgn()						{trg_tbl__lnki_temp.Insert_stmt_make();}
	@Override protected Object Load__src_row(Db_rdr rdr) {
		Xob_lnki_temp_row rv = new Xob_lnki_temp_row();
		rv.Load(rdr, ++lnki_id);
		return rv;
	}
	@Override protected void Save__trg_row(Object row_obj) {
		Xob_lnki_temp_row row = (Xob_lnki_temp_row)row_obj;
		trg_tbl__lnki_temp.Insert_cmd_by_batch(row.Lnki_tier_id(), row.Lnki_page_id(), row.Lnki_ttl(), row.Lnki_commons_ttl()
			, row.Lnki_ext(), row.Lnki_type(), row.Lnki_src_tid(), row.Lnki_w(), row.Lnki_h(), row.Lnki_upright()
			, row.Lnki_time(), row.Lnki_page());
	}
	@Override protected int Compare__hook(Object lhsObj, Object rhsObj) {
		Xob_lnki_temp_row lhs = (Xob_lnki_temp_row)lhsObj;
		Xob_lnki_temp_row rhs = (Xob_lnki_temp_row)rhsObj;
		return Int_.Compare(lhs.Lnki_page_id(), rhs.Lnki_page_id());
	}
}
