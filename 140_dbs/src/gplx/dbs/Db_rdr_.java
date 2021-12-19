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
package gplx.dbs;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.utls.FloatUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateUtl;
import gplx.libs.files.Io_url;
public class Db_rdr_ {
	public static final Db_rdr Empty = new Db_rdr__empty();
}
class Db_rdr__empty implements Db_rdr {
	public boolean			Move_next()						{return false;}
	public byte[]		Read_bry(String k)				{return BryUtl.Empty;}
	public byte[]		Read_bry_by_str(String k)		{return BryUtl.Empty;}
	public void			Save_bry_in_parts(Io_url url, String tbl, String fld, String crt_key, Object crt_val) {}
	public byte			Read_byte(String k)				{return ByteUtl.MaxValue127;}
	public String 		Read_str(String k)				{return StringUtl.Empty;}
	public GfoDate Read_date_by_str(String k)		{return GfoDateUtl.MinValue;}
	public int 			Read_int(String k)				{return IntUtl.MinValue;}
	public long 		Read_long(String k)				{return LongUtl.MinValue;}
	public float		Read_float(String k)			{return FloatUtl.NaN;}
	public double		Read_double(String k)			{return DoubleUtl.NaN;}
	public boolean			Read_bool_by_byte(String k)		{return false;}
	public int			Fld_len()						{return 0;}
	public Object 		Read_obj(String k)				{return null;}
	public Object 		Read_at(int i)					{return null;}
	public void			Rls() {}
}
