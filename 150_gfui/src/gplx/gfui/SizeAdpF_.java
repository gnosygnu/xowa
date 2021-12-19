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
package gplx.gfui;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.FloatUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
public class SizeAdpF_ {
	public static final SizeAdpF Null = new_(IntUtl.MinValue, IntUtl.MinValue);
	public static final SizeAdpF Zero = new_(0, 0);
	public static final SizeAdpF Parser = new SizeAdpF(0, 0);
	public static SizeAdpF as_(Object obj) {return obj instanceof SizeAdpF ? (SizeAdpF)obj : null;}
	public static SizeAdpF new_(float width, float height) {return new SizeAdpF(width, height);}
	public static SizeAdpF coerce_(Object obj) {SizeAdpF rv = as_(obj); return rv == null ? parse((String)obj) : rv;}
	public static SizeAdpF parse(String s) {
		try {
			String[] ary = StringUtl.Split(s, ","); if (ary.length != 2) throw ErrUtl.NewArgs("SizeAdf should only have 2 numbers separated by 1 comma");
			float val1 = FloatUtl.Parse(ary[0]);
			float val2 = FloatUtl.Parse(ary[1]);
			return new_(val1, val2);
		}	catch (Exception e) {throw ErrUtl.NewParse(e, SizeAdpF.class, s);}
	}
}
