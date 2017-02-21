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
package gplx.core.ios; import gplx.*; import gplx.core.*;
import gplx.core.type_xtns.*;
public class IoItmClassXtn extends ClassXtn_base implements ClassXtn {
	public String Key() {return Key_const;}						public static final String Key_const = "ioItemType";
	@Override public Class<?> UnderClass()					{return int.class;}
	public Object DefaultValue()								{return IoItmDir.Type_Dir;}
	public boolean Eq(Object lhs, Object rhs) {return ((IoItm_base)lhs).compareTo(rhs) == CompareAble_.Same;}
	@Override public Object ParseOrNull(String raw) {
		String rawLower = String_.Lower(raw);
		if		(String_.Eq(rawLower, "dir")) return IoItmDir.Type_Dir;
		else if (String_.Eq(rawLower, "fil")) return IoItmFil.Type_Fil;
		else	throw Err_.new_unhandled(raw);
	}
	@Override public Object XtoDb(Object obj)					{return Int_.cast(obj);}
	public static final IoItmClassXtn Instance = new IoItmClassXtn(); IoItmClassXtn() {}
}
