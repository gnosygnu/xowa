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
package gplx.xowa.bldrs.cmds.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.dbs.*; import gplx.xowa.files.*;
class Xob_lnki_temp_tbl {
	private static final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private static final String Tbl_name = "lnki_temp";
	public static final String 
	  Fld_lnki_id				= flds.Add_int_pkey_autonum("lnki_id");	// NOTE: insertion order index; public b/c not used and want to bypass warning
	private static final String 
	  Fld_lnki_tier_id			= flds.Add_int("lnki_tier_id")
	, Fld_lnki_page_id			= flds.Add_int("lnki_page_id")
	, Fld_lnki_ttl				= flds.Add_str("lnki_ttl", 255)
	, Fld_lnki_commons_ttl		= flds.Add_str_null("lnki_commons_ttl", 255)
	, Fld_lnki_ext				= flds.Add_int("lnki_ext")
	, Fld_lnki_type				= flds.Add_int("lnki_type")
	, Fld_lnki_src_tid			= flds.Add_int("lnki_src_tid")
	, Fld_lnki_w				= flds.Add_int("lnki_w")
	, Fld_lnki_h				= flds.Add_int("lnki_h")
	, Fld_lnki_upright			= flds.Add_double("lnki_upright")
	, Fld_lnki_time				= flds.Add_double("lnki_time")	// NOTE: thumbtime is float; using double b/c upright is double and would like to keep datatypes same; https://bugzilla.wikimedia.org/show_bug.cgi?id=39014
	, Fld_lnki_page				= flds.Add_int("lnki_page")
	;
	private Db_stmt stmt_insert;
	public Xob_lnki_temp_tbl(Db_conn conn) {this.conn = conn;}
	public Db_conn Conn()		{return conn;} private final Db_conn conn;
	public void Create_tbl()	{conn.Ddl_create_tbl(Db_meta_tbl.new_(Tbl_name, flds));}
	public void Insert_bgn()	{conn.Txn_bgn("bldr__lnki_temp"); stmt_insert = conn.Stmt_insert(Tbl_name, flds);}
	public void Insert_commit()	{conn.Txn_sav();}
	public void Insert_end()	{conn.Txn_end(); stmt_insert = Db_stmt_.Rls(stmt_insert);}
	public void Insert_cmd_by_batch(int tier_id, int page_id, byte[] ttl, byte[] ttl_commons, byte ext_id, byte img_type, byte lnki_src_tid, int w, int h, double upright, double time, int page) {
		stmt_insert.Clear()
		.Val_int		(Fld_lnki_tier_id		, tier_id)
		.Val_int		(Fld_lnki_page_id		, page_id)
		.Val_bry_as_str	(Fld_lnki_ttl			, ttl)
		.Val_bry_as_str	(Fld_lnki_commons_ttl	, ttl_commons)
		.Val_byte		(Fld_lnki_ext			, ext_id)
		.Val_byte		(Fld_lnki_type			, img_type)
		.Val_int		(Fld_lnki_src_tid		, lnki_src_tid)
		.Val_int		(Fld_lnki_w				, w)
		.Val_int		(Fld_lnki_h				, h)
		.Val_double		(Fld_lnki_upright		, upright)
		.Val_double		(Fld_lnki_time			, Xof_lnki_time.Db_save_double(time))
		.Val_int		(Fld_lnki_page			, page)
		.Exec_insert();
	}
}
