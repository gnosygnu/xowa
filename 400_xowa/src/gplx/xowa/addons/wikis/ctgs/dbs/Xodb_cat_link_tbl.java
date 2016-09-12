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
package gplx.xowa.addons.wikis.ctgs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*;
import gplx.dbs.*;
import gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.*;
public class Xodb_cat_link_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_from, fld_to_id, fld_type_id, fld_sortkey_id, fld_timestamp_unix;
	private Db_stmt stmt_insert;
	public Xodb_cat_link_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name = "cat_link";
		this.fld_from			= flds.Add_int	("cl_from");
		this.fld_to_id			= flds.Add_int	("cl_to_id");
		this.fld_type_id		= flds.Add_byte	("cl_type_id");
		this.fld_sortkey_id		= flds.Add_int	("cl_sortkey_id");
		this.fld_timestamp_unix	= flds.Add_long	("cl_timestamp_unix");
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;} private final    Db_conn conn; 
	public String Tbl_name() {return tbl_name;} private final    String tbl_name; 
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Create_idx__from()	{conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, fld_from, fld_from));}
	public void Create_idx__to_id() {conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, fld_to_id, fld_to_id));}
	public void Insert_bgn() {conn.Txn_bgn("cl__insert"); stmt_insert = conn.Stmt_insert(tbl_name, flds);}
	public void Insert_end() {conn.Txn_end(); stmt_insert = Db_stmt_.Rls(stmt_insert);}
	public void Insert_cmd_by_batch(int from, int to_id, byte type_id, int sortkey_id, long timestamp_unix) {
		stmt_insert.Clear()
			.Val_int(fld_from					, from)
			.Val_int(fld_to_id					, to_id)
			.Val_byte(fld_type_id				, type_id)
			.Val_int(fld_sortkey_id				, sortkey_id)
			.Val_long(fld_timestamp_unix		, timestamp_unix)
			.Exec_insert();
	}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}
}
/*
NOTE_1: categorylinks row size: 34 + 20 + 12 + (cat_sortkey.length * 2)
row length (data)		: 34=8+4+4+14+4				ROWID, cl_from, cl_to_id, cl_timestamp, cl_type_id
cl_main length (idx)	: 20=8+4+4+4				ROWID, cl_from, cl_to_id, cl_type_id
cl_from length (idx)	: 12=8+4					ROWID, cl_from
variable_data length	: cat_sortkey.length * 2	sortkey is used for row and cl_main

Note the following
. ints are 4 bytes
. tinyint is assumed to be 4 bytes (should be 1, but sqlite only has one numeric datatype, so import all 4?)
. varchar(14) is assumed to be 14 bytes (should be 15? +1 for length of varchar?)
. calculations work out "too well". comparing 4 databases gets +/- .25 bytes per row. however
.. - bytes should not be possible
.. +.25 bytes is too low (18 MB out of 5.5 GB).*; there must be other bytes used for page breaks / fragmentation
*/
