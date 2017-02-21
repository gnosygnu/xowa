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
package gplx.xowa.addons.bldrs.files.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import gplx.dbs.*; import gplx.xowa.files.*;
public class Xob_lnki_temp_tbl implements Db_tbl {
	private static final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private static final    String tbl_name = "lnki_temp";
	public static final    String 
	  Fld_lnki_id				= flds.Add_int_pkey_autonum("lnki_id");	// NOTE: insertion order index; public b/c not used and want to bypass warning
	private static final    String 
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
	public Db_conn Conn()		{return conn;} private final    Db_conn conn;
	public String Tbl_name()	{return tbl_name;}
	public void Create_tbl()	{conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Insert_bgn()	{conn.Txn_bgn("bldr__lnki_temp"); stmt_insert = conn.Stmt_insert(tbl_name, flds);}
	public void Insert_stmt_make() {stmt_insert = conn.Stmt_insert(tbl_name, flds);}
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
	public void Rls() {}
}
