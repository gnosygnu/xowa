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
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
public class BoolClassXtn extends ClassXtn_base implements ClassXtn {
	public static final String Key_const = "bo" + "ol";
	public String Key() {return Key_const;}
	@Override public Class<?> UnderClass()                    {return boolean.class;}
	public Object DefaultValue()                                {return false;}
	public boolean Eq(Object lhs, Object rhs) {try {return BoolUtl.Cast(lhs) == BoolUtl.Cast(rhs);} catch (Exception e) {return false;}}
	@Override public Object ParseOrNull(String raw)                {
		if    (    StringUtl.Eq(raw, "true")
			||    StringUtl.Eq(raw, "True")        // needed for Store_Wtr() {boolVal.toString();}
			||    StringUtl.Eq(raw, "1")        // needed for db; gplx field for boolean is int; need simple way to convert from dbInt to langBool
			)        
			return true;
		else if 
			(    StringUtl.Eq(raw, "false")
			||    StringUtl.Eq(raw, "False")
			||    StringUtl.Eq(raw, "0")
			) 
			return false;
		throw ErrUtl.NewParse(boolean.class, raw);
	}
	@Override public Object XtoDb(Object obj)                    {return obj;}
	public static final BoolClassXtn Instance =  new BoolClassXtn(); BoolClassXtn() {} // added to ClassXtnPool by default
}
