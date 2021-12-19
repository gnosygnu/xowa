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
import gplx.types.errs.ErrUtl;
public class ObjectClassXtn extends ClassXtn_base implements ClassXtn {
	public String Key() {return Key_const;}                        public static final String Key_const = "Object";
	@Override public Class<?> UnderClass()                    {return Object.class;}
	public Object DefaultValue()                                {return null;}
	@Override public Object ParseOrNull(String raw)                {throw ErrUtl.NewUnimplemented();}
	@Override public Object XtoDb(Object obj)                    {throw ErrUtl.NewUnimplemented();}
	public boolean Eq(Object lhs, Object rhs) {return lhs == rhs;}
	public static final ObjectClassXtn Instance =  new ObjectClassXtn(); ObjectClassXtn() {} // added to ClassXtnPool by default
}
