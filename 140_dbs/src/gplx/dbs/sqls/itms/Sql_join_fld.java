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
package gplx.dbs.sqls.itms; import gplx.*; import gplx.dbs.*; import gplx.dbs.sqls.*;
public class Sql_join_fld {
	public Sql_join_fld(String trg_fld, String src_tbl, String src_fld) {
		this.Trg_fld = trg_fld;
		this.Src_tbl = src_tbl;
		this.Src_fld = src_fld;
	}
	public final String Src_tbl;
	public final String Src_fld;
	public final String Trg_fld;

	public String To_fld_sql(boolean fld_is_src, String trg_tbl) {
		return fld_is_src ? Src_tbl + "." + Src_fld : trg_tbl + "." + Trg_fld;
	}

	public static final Sql_join_fld[] Ary__empty = new Sql_join_fld[0];
}
