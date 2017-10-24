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
public class Xof_lnki_time {
	public static double	Db_save_double(double v) {return v;}
	public static double	Db_load_double(DataRdr rdr, String fld) {return rdr.ReadDouble(fld);}
	public static double	Db_load_double(Db_rdr rdr, String fld)	{return rdr.Read_double(fld);}
	public static int		Db_save_int(double v) {return (int)v;}
	public static double	Db_load_int(DataRdr rdr, String fld)	{return rdr.ReadInt(fld);}
	public static double	Db_load_int(Db_rdr rdr, String fld)		{return rdr.Read_int(fld);}
	public static int		X_int(double v) {return (int)v;}
	public static String	X_str(double v) {return Double_.To_str(v);}
	public static final double		Null = -1;
	public static boolean		Null_y(double v) {return v == Null;}
	public static boolean		Null_n(double v) {return v != Null;}
	public static final int		Null_as_int = -1;

	public static double	Convert_to_xowa_thumbtime	(int ext, double val)	{return Xof_ext_.Id_supports_time(ext)	? val		: Null;}
	public static int		Convert_to_xowa_page		(int ext, double val)	{return Xof_ext_.Id_supports_page(ext)		? (int)val	: Xof_lnki_page.Null;}
	public static double	Convert_to_fsdb_thumbtime	(int ext, double thumbtime, int page) {
		return	page != Xof_lnki_page.Null
			&&	Xof_ext_.Id_supports_page(ext)		// redefine thumbtime to page if pdf
			?	page
			:	thumbtime
			;
	}
}
