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
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.commons.lists.CompareAbleUtl;
import gplx.types.basics.utls.ClassUtl;
import gplx.types.errs.ErrUtl;
public abstract class ClassXtn_base {
	public abstract Class<?> UnderClass();
	public abstract Object ParseOrNull(String raw);
	public Object XtoDb(Object obj)                        {return obj;}
	public String XtoUi(Object obj, String fmt)            {return ObjectUtl.ToStrOrNullMark(obj);}
	public boolean MatchesClass(Object obj) {if (obj == null) throw ErrUtl.NewNull();
		return ClassUtl.EqByObj(UnderClass(), obj);
	}
	public int compareTo(Object lhs, Object rhs) {return CompareAbleUtl.Compare_obj(lhs, rhs);}
}
