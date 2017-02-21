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
package gplx.xowa.specials.xowa.diags; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*; import gplx.xowa.specials.xowa.*;
import gplx.core.net.*; import gplx.core.net.qargs.*; import gplx.core.envs.*;
import gplx.fsdb.*; import gplx.fsdb.meta.*;
import gplx.xowa.files.origs.*;
import gplx.xowa.apps.urls.*;
class Xows_cmd__file_check {
	private Io_url tmp_dir;
	public void Exec(Bry_bfr bfr, Xoa_app app, Xoa_url url, Gfo_qarg_mgr_old arg_hash) {
		byte[] wiki_bry = arg_hash.Get_val_bry_or(Arg_wiki, null);	if (wiki_bry == null) {Xoa_app_.Usr_dlg().Warn_many("", "", "special.cmd; no wiki: url=~{0}", url.Raw()); return;}
		byte[] file_bry = arg_hash.Get_val_bry_or(Arg_file, null);	if (file_bry == null) {Xoa_app_.Usr_dlg().Warn_many("", "", "special.cmd; no file: url=~{0}", url.Raw()); return;}
		Xow_wiki wiki = app.Wiki_mgri().Get_by_or_make_init_y(wiki_bry);
		tmp_dir = wiki.Fsys_mgr().Tmp_dir();
		boolean schema_1 = wiki.File__fsdb_core().File__schema_is_1();
		Fsdb_db_file atr_main = null;
		try {Write_kv(bfr, "machine.op_sys", Op_sys.Cur().Os_name());} catch (Exception e) {bfr.Add_str_u8(Err_.Message_gplx_full(e));}
		try {Write_kv(bfr, "app.version", Xoa_app_.Version);} catch (Exception e) {bfr.Add_str_u8(Err_.Message_gplx_full(e));}
		try {Write_kv(bfr, "cfg_file_retrieve", ((Xowe_wiki)wiki).File_mgr().Cfg_download().Enabled());} catch (Exception e) {bfr.Add_str_u8(Err_.Message_gplx_full(e));}
		try {Write_kv(bfr, "fsdb.schema_is_1", schema_1);} catch (Exception e) {bfr.Add_str_u8(Err_.Message_gplx_full(e));}
		try {Write_kv(bfr, "fsdb.mnt_file", wiki.File__fsdb_core().File__mnt_file().Url());} catch (Exception e) {bfr.Add_str_u8(Err_.Message_gplx_full(e));}
		try {Write_kv(bfr, "fsdb.abc_file", wiki.File__fsdb_core().File__abc_file__at(Fsm_mnt_mgr.Mnt_idx_main).Url());} catch (Exception e) {bfr.Add_str_u8(Err_.Message_gplx_full(e));}
		try {
			atr_main = wiki.File__fsdb_core().File__atr_file__at(Fsm_mnt_mgr.Mnt_idx_main);
			Write_kv(bfr, "fsdb.atr_file", atr_main.Url());
		}	catch (Exception e) {bfr.Add_str_u8(Err_.Message_gplx_full(e));}
		try {Write_kv(bfr, "fsdb.orig", wiki.File__fsdb_core().File__orig_tbl_ary()[0].Conn().Conn_info().Raw());} catch (Exception e) {bfr.Add_str_u8(Err_.Message_gplx_full(e));}
		Fsdb_sql_mkr sql_mkr = schema_1 ? Fsdb_sql_mkr__v1.Instance : Fsdb_sql_mkr__v2.Instance;
		String sql = "";
		try {
			Xof_orig_tbl orig_tbl = wiki.File__fsdb_core().File__orig_tbl_ary()[0];
			sql = sql_mkr.Orig_by_ttl(file_bry);
			Write_sect(bfr, "fsdb.orig.select", sql);
			Db_rdr_utl.Load_and_write(orig_tbl.Conn(), sql, bfr);
		}	catch (Exception e) {bfr.Add_str_u8(Err_.Message_gplx_full(e));}
		Object[][] rows = null;
		try {
			sql = sql_mkr.Fil_by_ttl(file_bry);
			Write_sect(bfr, "fsdb.fil.select", sql);
			rows = Db_rdr_utl.Load(atr_main.Conn(), sql);
			Db_rdr_utl.Write_to_bfr(bfr, rows);
			Write_thms(bfr, file_bry, sql_mkr, wiki.File__mnt_mgr().Mnts__get_main(), atr_main, rows);
		}	catch (Exception e) {bfr.Add_str_u8(Err_.Message_gplx_full(e));}
	}
	private void Write_thms(Bry_bfr bfr, byte[] file_bry, Fsdb_sql_mkr sql_mkr, Fsm_mnt_itm mnt_itm, Fsdb_db_file atr_main, Object[][] rows) {
		int rows_len = rows.length;
		for (int i = 0; i < rows_len; ++i) {
			Object[] row = rows[i];
			int file_id = Int_.cast(row[0]);
			String sql = sql_mkr.Thm_by_id(file_id);
			Write_sect(bfr, "fsdb.thm.select", sql);
			Object[][] thm_rows = Db_rdr_utl.Load(atr_main.Conn(), sql);
			Db_rdr_utl.Write_to_bfr(bfr, thm_rows);	
			Write_bins(bfr, file_bry, sql_mkr, mnt_itm, thm_rows, 0, 6);
			Object bin_db_id_obj = row[4];
			if (bin_db_id_obj != null) {
				Write_bins(bfr, file_bry, sql_mkr, mnt_itm, rows, 0, 4);
			}
		}
	}
	private void Write_bins(Bry_bfr bfr, byte[] file_bry, Fsdb_sql_mkr sql_mkr, Fsm_mnt_itm mnt_itm, Object[][] rows, int owner_id_ordinal, int bin_db_id_ordinal) {
		int rows_len = rows.length;
		for (int i = 0; i < rows_len; ++i) {
			Object[] row = rows[i];
			int bin_db_id = Int_.cast(row[bin_db_id_ordinal]); if (bin_db_id == -1) continue;
			Fsm_bin_fil bin_db = mnt_itm.Bin_mgr().Dbs__get_at(bin_db_id);
			int owner_id = Int_.cast(row[owner_id_ordinal]);
			String sql = sql_mkr.Bin_by_id(owner_id);
			Write_sect(bfr, "fsdb.bin.select", sql);
			Object[][] bin_rows = Db_rdr_utl.Load(bin_db.Conn(), sql);
			Db_rdr_utl.Write_to_bfr(bfr, bin_rows, 4);	
			Export_bins(bfr, file_bry, bin_rows, 0, 4);
		}
	}
	private void Export_bins(Bry_bfr bfr, byte[] file_bry, Object[][] rows, int owner_id_ordinal, int bin_data_ordinal) {
		int rows_len = rows.length;
		Bry_bfr tmp_bfr = Bry_bfr_.New();
		for (int i = 0; i < rows_len; ++i) {
			Object[] row = rows[i];
			int owner_id = Int_.cast(row[owner_id_ordinal]);
			byte[] bin_data = (byte[])row[bin_data_ordinal];
			file_bry = gplx.xowa.files.repos.Xof_itm_ttl_.Remove_invalid(tmp_bfr, file_bry);
			Io_url bin_url = tmp_dir.GenSubFil(String_.new_u8(file_bry));
			bin_url = tmp_dir.GenSubFil(bin_url.NameOnly() + "_" + Int_.To_str(owner_id) + bin_url.Ext());
			Write_kv(bfr, "fsdb.bin.export", bin_url.Raw());
			Write_kv(bfr, "fsdb.bin.len", bin_data.length);
			Io_mgr.Instance.SaveFilBry(bin_url, bin_data);
		}
	}
	private static void Write_kv(Bry_bfr bfr, String key, Object val) {
		bfr.Add_str_u8(key);
		bfr.Add_str_a7(" = ");
		bfr.Add_obj(val);
		bfr.Add_byte_nl();
	}
	private static void Write_sect(Bry_bfr bfr, String key, Object val) {
		bfr.Add_byte_nl();
		bfr.Add_str_u8("------------------------------------------------------").Add_byte_nl();
		bfr.Add_str_u8(key).Add_str_a7(": ").Add_obj(val).Add_byte_nl();
		bfr.Add_str_u8("------------------------------------------------------").Add_byte_nl();
	}
	private static final    byte[] Arg_wiki = Bry_.new_a7("wiki"), Arg_file = Bry_.new_a7("file");
        public static final    Xows_cmd__file_check Instance = new Xows_cmd__file_check(); Xows_cmd__file_check() {}
}
interface Fsdb_sql_mkr {
	String Orig_by_ttl(byte[] ttl);
	String Fil_by_ttl(byte[] ttl);
	String Thm_by_id(int id);
	String Bin_by_id(int id);
}
abstract class Fsdb_sql_mkr__base {
	public String Fil_by_ttl(byte[] ttl) {return String_.Format("SELECT * FROM fsdb_fil WHERE fil_name = '{0}';", ttl);}
	public String Bin_by_id(int id) {return String_.Format("SELECT * FROM fsdb_bin WHERE bin_owner_id = {0};", id);}
}
class Fsdb_sql_mkr__v1 extends Fsdb_sql_mkr__base implements Fsdb_sql_mkr {
	public String Orig_by_ttl(byte[] ttl) {return String_.Format("SELECT * FROM wiki_orig WHERE orig_ttl = '{0}';", ttl);}
	public String Thm_by_id(int id) {return String_.Format("SELECT * FROM fsdb_xtn_thm WHERE thm_owner_id = {0};", id);}
        public static final    Fsdb_sql_mkr Instance = new Fsdb_sql_mkr__v1(); Fsdb_sql_mkr__v1() {}
}
class Fsdb_sql_mkr__v2 extends Fsdb_sql_mkr__base implements Fsdb_sql_mkr {
	public String Orig_by_ttl(byte[] ttl) {return String_.Format("SELECT * FROM orig_reg WHERE orig_ttl = '{0}';", ttl);}
	public String Thm_by_id(int id) {return String_.Format("SELECT * FROM fsdb_thm WHERE thm_owner_id = {0};", id);}
        public static final    Fsdb_sql_mkr Instance = new Fsdb_sql_mkr__v2(); Fsdb_sql_mkr__v2() {}
}
