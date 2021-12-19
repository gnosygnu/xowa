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
import gplx.types.commons.GfoTimeSpan;
import gplx.types.commons.GfoTimeSpanUtl;
public class GfoTimeSpanClassXtn extends ClassXtn_base implements ClassXtn {
	public String Key() {return Key_const;}                        public static final String Key_const = "timeSpan";
	@Override public Class<?> UnderClass()                    {return GfoTimeSpan.class;}
	public Object DefaultValue()                                {return GfoTimeSpanUtl.Zero;}
	@Override public Object ParseOrNull(String raw)                {return GfoTimeSpanUtl.Parse(raw);}
	@Override public Object XtoDb(Object obj)                    {return ((GfoTimeSpan)obj).TotalSecs();}
	@Override public String XtoUi(Object obj, String fmt)        {return ((GfoTimeSpan)obj).ToStr(fmt);}
	public boolean Eq(Object lhs, Object rhs) {try {return ((GfoTimeSpan)lhs).Eq(rhs);} catch (Exception e) {return false;}}
	public static final GfoTimeSpanClassXtn Instance =  new GfoTimeSpanClassXtn(); GfoTimeSpanClassXtn() {} // added to ClassXtnPool by default
}
