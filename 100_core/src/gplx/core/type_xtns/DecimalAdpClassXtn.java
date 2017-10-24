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
package gplx.core.type_xtns; import gplx.*; import gplx.core.*;
public class DecimalAdpClassXtn extends ClassXtn_base implements ClassXtn {
	public String Key() {return Key_const;}						public static final String Key_const = "decimal";	// current dsv files reference "decimal"
	@Override public Class<?> UnderClass()					{return Decimal_adp.class;}
	public Object DefaultValue()								{return 0;}
	public boolean Eq(Object lhs, Object rhs) {try {return Decimal_adp_.cast(lhs).Eq(Decimal_adp_.cast(rhs));} catch (Exception e) {Err_.Noop(e); return false;}}
	@Override public Object ParseOrNull(String raw)				{return Decimal_adp_.parse(raw);}
	@Override public String XtoUi(Object obj, String fmt)		{return Decimal_adp_.cast(obj).To_str();}
	public static final DecimalAdpClassXtn Instance =  new DecimalAdpClassXtn(); DecimalAdpClassXtn() {} // added to ClassXtnPool by default
}
