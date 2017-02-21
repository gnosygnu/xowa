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
public class StringClassXtn extends ClassXtn_base implements ClassXtn {
	public static final String Key_const = "string";
	public String Key() {return Key_const;}
	@Override public Class<?> UnderClass()					{return String.class;}
	public Object DefaultValue()								{return "";}
	@Override public Object ParseOrNull(String raw)				{return raw;}
	@Override public String XtoUi(Object obj, String fmt)		{return String_.as_(obj);}
	public boolean Eq(Object lhs, Object rhs) {try {return String_.Eq(String_.cast(lhs), String_.cast(rhs));} catch (Exception e) {Err_.Noop(e); return false;}}
	public static final StringClassXtn Instance =  new StringClassXtn(); StringClassXtn() {} // added to ClassXtnPool by default
}