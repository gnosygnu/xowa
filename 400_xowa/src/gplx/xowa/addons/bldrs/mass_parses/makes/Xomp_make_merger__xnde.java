/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.addons.bldrs.mass_parses.makes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.dbs.*; import gplx.xowa.bldrs.*;
import gplx.xowa.addons.bldrs.mass_parses.dbs.*;
import gplx.xowa.addons.bldrs.files.dbs.*;
import gplx.xowa.parsers.logs.*;
class Xomp_make_merger__xnde extends Xomp_make_merger__base {
	private Xop_log_basic_tbl trg_tbl__log_basic_temp;
	@Override protected Db_tbl	Init__trg_tbl(Xob_db_file trg_db) {
		this.trg_tbl__log_basic_temp = new Xop_log_basic_tbl(trg_db.Conn());
		return trg_tbl__log_basic_temp;
	}
	@Override protected String	Init__src_fld__page_id()			{return "page_id";}
	@Override protected void		Init__trg_bgn()						{}	// NOTE: trg_tbl has implicit insert_stmt creation
	@Override protected Object Load__src_row(Db_rdr rdr) {
		Xop_log_basic_row rv = new Xop_log_basic_row();
		rv.Load(rdr);
		return rv;
	}
	@Override protected void Save__trg_row(Object row_obj) {
		Xop_log_basic_row row = (Xop_log_basic_row)row_obj;
		trg_tbl__log_basic_temp.Insert(row.Log_tid, row.Log_msg, row.Log_time, row.Page_id, row.Page_ttl, row.Args_len, row.Args_str, row.Src_len, row.Src_str);
	}
	@Override protected int Compare__hook(Object lhsObj, Object rhsObj) {
		Xop_log_basic_row lhs = (Xop_log_basic_row)lhsObj;
		Xop_log_basic_row rhs = (Xop_log_basic_row)rhsObj;
		return Int_.Compare(lhs.Page_id, rhs.Page_id);
	}
}
class Xop_log_basic_row {
	public int Log_tid;
	public String Log_msg;
	public int Log_time;
	public int Page_id;
	public String Page_ttl;
	public int Args_len;
	public String Args_str;
	public int Src_len;
	public String Src_str;
	public void Load(Db_rdr rdr) {
		this.Log_tid = rdr.Read_int("log_tid");
		this.Log_msg = rdr.Read_str("log_msg");
		this.Log_time = rdr.Read_int("log_time");
		this.Page_id = rdr.Read_int("page_id");
		this.Page_ttl = rdr.Read_str("page_ttl");
		this.Args_len = rdr.Read_int("args_len");
		this.Args_str = rdr.Read_str("args_str");
		this.Src_len = rdr.Read_int("src_len");
		this.Src_str = rdr.Read_str("src_str");
	}
}
