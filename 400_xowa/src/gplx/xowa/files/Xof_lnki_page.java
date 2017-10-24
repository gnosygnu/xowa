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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
import gplx.core.stores.*;
import gplx.dbs.*;
public class Xof_lnki_page {
	public static final int		Null = -1;
	public static boolean		Null_y(int v) {return v == Null;}
	public static boolean		Null_n(int v) {return v != Null;}
	public static int		Db_load_int(DataRdr rdr, String fld)	{return rdr.ReadInt(fld);}
	public static int		Db_load_int(Db_rdr rdr, String fld)		{return rdr.Read_int(fld);}
	public static int		Db_save_int(int v) {return v;}
}
