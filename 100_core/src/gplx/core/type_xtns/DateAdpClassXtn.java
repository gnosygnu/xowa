/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.type_xtns;
import gplx.*;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateUtl;
public class DateAdpClassXtn extends ClassXtn_base implements ClassXtn {
	public String Key() {return Key_const;}                        public static final String Key_const = "datetime";
	public boolean Eq(Object lhs, Object rhs) {try {return ((GfoDate)lhs).Eq((GfoDate)rhs);} catch (Exception e) {return false;}}
	@Override public Class<?> UnderClass()                    {return GfoDate.class;}
	public Object DefaultValue()                                {return GfoDateUtl.MinValue;}
	@Override public Object ParseOrNull(String raw)                {return GfoDateUtl.ParseGplx(raw);}
	@Override public Object XtoDb(Object obj)                    {return ((GfoDate)obj).ToStrGplxLong();}
	@Override public String XtoUi(Object obj, String fmt)        {return ((GfoDate)obj).ToStrFmt(fmt);}
	public static final DateAdpClassXtn Instance =  new DateAdpClassXtn(); DateAdpClassXtn() {} // added to ClassXtnPool by default
}
