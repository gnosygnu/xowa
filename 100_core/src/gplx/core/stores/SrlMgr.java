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
package gplx.core.stores; import gplx.*; import gplx.core.*;
public interface SrlMgr {
	boolean		Type_rdr();
	Object		StoreRoot(SrlObj root, String key);

	boolean		SrlBoolOr(String key, boolean v);
	byte		SrlByteOr(String key, byte v);
	int			SrlIntOr(String key, int v);
	long		SrlLongOr(String key, long v);
	String		SrlStrOr(String key, String v);
	double		SrlDoubleOr(String key, double v);
	Decimal_adp	SrlDecimalOr(String key, Decimal_adp v);
	DateAdp		SrlDateOr(String key, DateAdp v);
	Object		SrlObjOr(String key, Object v);
	void		SrlList(String key, List_adp list, SrlObj proto, String itmKey);
	void		TypeKey_(String v);	
	SrlMgr		SrlMgr_new(Object o);
}
