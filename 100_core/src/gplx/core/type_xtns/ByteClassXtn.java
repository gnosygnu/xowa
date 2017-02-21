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
public class ByteClassXtn extends ClassXtn_base implements ClassXtn {
	public static final String Key_const = "byte";
	public String Key() {return Key_const;}
	@Override public Class<?> UnderClass()					{return byte.class;}
	public Object DefaultValue()								{return 0;}
	public boolean Eq(Object lhs, Object rhs) {try {return Byte_.cast(lhs) == Byte_.cast(rhs);} catch (Exception e) {Err_.Noop(e); return false;}}
	@Override public Object ParseOrNull(String raw)				{return raw == null ? (Object)null : Byte_.parse(raw);}
	@Override public Object XtoDb(Object obj)					{return Byte_.cast(obj);}
	public static final ByteClassXtn Instance =  new ByteClassXtn(); ByteClassXtn() {} // added to ClassXtnPool by default
}