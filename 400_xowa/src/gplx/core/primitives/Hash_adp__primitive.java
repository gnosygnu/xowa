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
package gplx.core.primitives;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.lists.Hash_adp_;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.wrappers.ByteVal;
public class Hash_adp__primitive {
	private final Hash_adp hash = Hash_adp_.New();
	public byte Get_by_str_or_max(String key) {
		ByteVal rv = (ByteVal)hash.GetByOrNull(key);
		return rv == null ? ByteUtl.MaxValue127 : rv.Val();
	}
	public Hash_adp__primitive Add_byte(String key, byte val) {
		hash.Add(key, ByteVal.New(val));
		return this;
	}
}
