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
package gplx.dbs; import gplx.*;
import gplx.dbs.metas.*; import gplx.dbs.sqls.*; import gplx.dbs.sqls.wtrs.*;
public class Dbmeta_tbl_itm {
	public String Name() {return name;} private String name;
	public Dbmeta_idx_mgr Idxs() {return idxs;} private final Dbmeta_idx_mgr idxs = new Dbmeta_idx_mgr();
	public Dbmeta_fld_mgr Flds() {return flds;} private final Dbmeta_fld_mgr flds = new Dbmeta_fld_mgr();
	public String To_sql_create(Sql_qry_wtr sql_wtr) {return sql_wtr.Schema_wtr().Bld_create_tbl(this);}

	public static Dbmeta_tbl_itm New(String name, Dbmeta_fld_list flds, Dbmeta_idx_itm... idxs)	{return New(name, flds.To_fld_ary(), idxs);}
	public static Dbmeta_tbl_itm New(String name, Dbmeta_fld_itm... flds)							{return New(name, flds, Dbmeta_idx_itm.Ary_empty);}
	public static Dbmeta_tbl_itm New(String name, Dbmeta_fld_itm[] flds, Dbmeta_idx_itm... idxs) {
		Dbmeta_tbl_itm rv = new Dbmeta_tbl_itm();
		rv.name = name;
		if (flds != null) {
			int flds_len = flds.length;
			for (int i = 0; i < flds_len; ++i)
				rv.flds.Add(flds[i]);
		}
		if (idxs != null) {
			int idxs_len = idxs.length;
			for (int i = 0; i < idxs_len; ++i)
				rv.idxs.Add(idxs[i]);
		}
		return rv;
	}
}
