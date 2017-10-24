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
package gplx.dbs.bulks; import gplx.*; import gplx.dbs.*;
import gplx.dbs.metas.*;
public class Db_bulk_exec_ {
	public static void Insert(Db_bulk_prog prog_wkr, String msg, Dbmeta_fld_itm[] flds, Db_rdr src, Db_stmt trg, Db_conn trg_conn) {
		// init
		int flds_len = flds.length;
		String[] fld_names = Db_bulk_exec_utl_.To_fld_names(flds, flds_len);
		int[] fld_types = Db_bulk_exec_utl_.To_fld_types(flds, flds_len);

		// loop all rows
		Gfo_log_.Instance.Prog(msg);
		trg_conn.Txn_bgn("bulk_insert");
		try {
			while (src.Move_next()) {
				// fill insert
				trg.Clear();
				int row_size = 0;
				for (int i = 0; i < flds_len; ++i) {
					String fld_name = fld_names[i];
					switch (fld_types[i]) {
						case Dbmeta_fld_tid.Tid__bool	: trg.Val_bool_as_byte	(fld_name, src.Read_bool_by_byte	(fld_name)); row_size += 1; break;
						case Dbmeta_fld_tid.Tid__byte	: trg.Val_byte			(fld_name, src.Read_byte			(fld_name)); row_size += 1; break;
						case Dbmeta_fld_tid.Tid__int	: trg.Val_int			(fld_name, src.Read_int				(fld_name)); row_size += 4; break;
						case Dbmeta_fld_tid.Tid__long	: trg.Val_long			(fld_name, src.Read_long			(fld_name)); row_size += 8; break;
						case Dbmeta_fld_tid.Tid__float	: trg.Val_float			(fld_name, src.Read_float			(fld_name)); row_size += 4; break;
						case Dbmeta_fld_tid.Tid__double	: trg.Val_double		(fld_name, src.Read_double			(fld_name)); row_size += 8; break;
						case Dbmeta_fld_tid.Tid__str	: String src_str = src.Read_str(fld_name); trg.Val_str(fld_name, src_str); row_size += src_str == null ? 0 : String_.Len(src_str); break;
						case Dbmeta_fld_tid.Tid__bry	: byte[] src_bry = src.Read_bry(fld_name); trg.Val_bry(fld_name, src_bry); row_size += src_bry == null ? 0 : src_bry.length; break;
						default							: throw Err_.new_unhandled_default(fld_types[i]);
					}					
				}

				// exec insert
				try {trg.Exec_insert();}
				catch (Exception e) {throw Db_bulk_exec_utl_.New_err(e, src, flds_len, fld_names, fld_types);}

				// commit and notify if applicable
				if (prog_wkr.Prog__insert_and_stop_if_suspended(row_size)) break;
			}
		}
		catch (Exception e) {throw Err_.new_wo_type("dbs.bulk:insert failed", "err", e);}
		finally {
			trg_conn.Txn_end();
		}
	}
	public static final String Invk__bulk_insert_err = "bulk.insert.err", Invk__bulk_insert_prog = "bulk.insert.prog";
}
class Db_bulk_exec_utl_ {
	public static String[] To_fld_names(Dbmeta_fld_itm[] flds, int flds_len) {
		String[] rv = new String[flds_len];
		for (int i = 0; i < flds_len; ++i)
			rv[i] = flds[i].Name();
		return rv;
	}
	public static int[] To_fld_types(Dbmeta_fld_itm[] flds, int flds_len) {
		int[] rv = new int[flds_len];
		for (int i = 0; i < flds_len; ++i)
			rv[i] = flds[i].Type().Tid_ansi();
		return rv;
	}
	public static Err New_err(Exception e, Db_rdr rdr, int flds_len, String[] fld_names, int[] fld_types) {
		Object[] args = new Object[(flds_len * 2) + 2];
		for (int i = 0; i < flds_len; i += 2) {
			args[i    ] = fld_names[i];
			args[i + 1] = rdr.Read_at(i);
		}
		args[flds_len - 2] = "err";
		args[flds_len - 1] = Err_.Message_gplx_log(e);
		return Err_.new_wo_type("dbs.bulk:insert row failed", args);
	}
}
