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
package gplx.gfui; import gplx.*;
public class PointAdp_ {
	public static final    PointAdp Null = new PointAdp(Int_.Min_value, Int_.Min_value);
	public static final    PointAdp Zero = new PointAdp(0, 0);
	public static PointAdp as_(Object obj) {return obj instanceof PointAdp ? (PointAdp)obj : null;}
	public static PointAdp cast(Object obj) {try {return (PointAdp)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, PointAdp.class, obj);}}
	public static PointAdp new_(int x, int y) {return new PointAdp(x, y);}
	public static PointAdp coerce_(Object o) {PointAdp rv = PointAdp_.as_(o); return (rv == null) ? parse((String)o) : rv;}
	public static PointAdp parse(String raw) {
		try {
			String[] ary = String_.Split(raw, ",");
			return new PointAdp(Int_.parse(ary[0]), Int_.parse(ary[1]));
		}	catch (Exception exc) {throw Err_.new_parse_exc(exc, PointAdp.class, raw);}
	}
}
