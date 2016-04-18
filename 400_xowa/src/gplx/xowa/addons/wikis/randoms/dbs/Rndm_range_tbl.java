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
package gplx.xowa.addons.wikis.randoms.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.randoms.*;
import gplx.core.ios.*; import gplx.dbs.*; import gplx.dbs.utls.*;
public class Rndm_range_tbl implements Rls_able {
	private final    String tbl_name = "rndm_range"; private final    Dbmeta_fld_list flds = Dbmeta_fld_list.new_();
	private final    String fld_rng_uid, fld_rng_idx, fld_rng_page_id;
	private final    Db_conn conn; private Db_stmt stmt_select, stmt_insert;		
	public Rndm_range_tbl(Db_conn conn) {
		this.conn = conn;
		fld_rng_uid			= flds.Add_int("rng_uid");
		fld_rng_idx			= flds.Add_int("rng_idx");
		fld_rng_page_id 	= flds.Add_int("rng_page_id");
		conn.Rls_reg(this);
	}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds, Dbmeta_idx_itm.new_unique_by_name(tbl_name, "core", fld_rng_uid, fld_rng_idx)));}
	public void Insert_bgn(int uid) {
		conn.Stmt_delete(tbl_name, fld_rng_uid).Crt_int(fld_rng_uid, uid).Exec_delete();
		stmt_insert = conn.Stmt_insert(tbl_name, flds);
	}
	public void Insert_itm(int uid, int idx, int page_id) {
		stmt_insert.Clear().Val_int(fld_rng_uid, uid).Val_int(fld_rng_idx, idx).Val_int(fld_rng_page_id, page_id).Exec_insert();
	}
	public void Insert_end() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}
	public int Select_or_neg_1(int core_uid, int rng_idx) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_rng_uid, fld_rng_idx);
		Db_rdr rdr = stmt_select.Clear().Val_int(fld_rng_uid, core_uid).Val_int(fld_rng_idx, rng_idx).Exec_select__rls_manual();
		try {return rdr.Move_next() ? rdr.Read_int(fld_rng_page_id) : -1;}
		catch (Exception e) {Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to get rndm_idx; url=~{0} core_uid=~{1} rng_idx=~{2} err=~{3}", conn.Conn_info().Db_api(), core_uid, rng_idx, Err_.Message_gplx_log(e)); return -1;}
		finally {rdr.Rls();}
	}
	public void Rls() {
		stmt_select = Db_stmt_.Rls(stmt_select);
	}
}
