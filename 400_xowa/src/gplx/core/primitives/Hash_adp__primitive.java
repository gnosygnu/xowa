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
package gplx.core.primitives; import gplx.*; import gplx.core.*;
public class Hash_adp__primitive {
	private final    Hash_adp hash = Hash_adp_.New();
	public byte Get_by_str_or_max(String key) {
		Byte_obj_val rv = (Byte_obj_val)hash.Get_by(key);
		return rv == null ? Byte_.Max_value_127 : rv.Val();
	}
	public Hash_adp__primitive Add_byte(String key, byte val) {
		hash.Add(key, Byte_obj_val.new_(val));
		return this;
	}
}
