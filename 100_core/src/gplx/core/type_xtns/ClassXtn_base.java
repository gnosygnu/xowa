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
package gplx.core.type_xtns; import gplx.*;
import gplx.objects.lists.CompareAbleUtl;
public abstract class ClassXtn_base {
	public abstract Class<?> UnderClass();
	public abstract Object ParseOrNull(String raw);
	public Object XtoDb(Object obj)						{return obj;}
	public String XtoUi(Object obj, String fmt)			{return Object_.Xto_str_strict_or_null_mark(obj);}
	public boolean MatchesClass(Object obj) {if (obj == null) throw Err_.new_null();
		return Type_.Eq_by_obj(obj, UnderClass());
	}
	public int compareTo(Object lhs, Object rhs) {return CompareAbleUtl.Compare_obj(lhs, rhs);}
}
