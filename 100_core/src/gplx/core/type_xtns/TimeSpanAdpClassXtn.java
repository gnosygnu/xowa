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
public class TimeSpanAdpClassXtn extends ClassXtn_base implements ClassXtn {
	public String Key() {return Key_const;}						public static final    String Key_const = "timeSpan";
	@Override public Class<?> UnderClass()					{return Time_span.class;}
	public Object DefaultValue()								{return Time_span_.Zero;}
	@Override public Object ParseOrNull(String raw)				{return Time_span_.parse(raw);}
	@Override public Object XtoDb(Object obj)					{return Time_span_.cast(obj).Total_secs();}
	@Override public String XtoUi(Object obj, String fmt)		{return Time_span_.cast(obj).To_str(fmt);}
	public boolean Eq(Object lhs, Object rhs) {try {return Time_span_.cast(lhs).Eq(rhs);} catch (Exception e) {Err_.Noop(e); return false;}}
	public static final    TimeSpanAdpClassXtn Instance =  new TimeSpanAdpClassXtn(); TimeSpanAdpClassXtn() {} // added to ClassXtnPool by default
}