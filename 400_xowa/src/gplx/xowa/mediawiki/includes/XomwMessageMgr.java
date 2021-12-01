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
package gplx.xowa.mediawiki.includes; import gplx.*;
import gplx.xowa.mediawiki.languages.*;
public class XomwMessageMgr {
	private final Hash_adp hash = Hash_adp_.New();
	public void Old_add(String key, String val, XomwLanguage language) {
		hash.Add(key, new XomwMessageOld(Bry_.new_u8(val), language));
	}
	public XomwMessageOld Old_get_by_str(String key) {return (XomwMessageOld)hash.GetByOrNull(key);}
}
