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
package gplx.xowa.xtns.scribunto; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Scrib_lua_proc {
	public Scrib_lua_proc(String key, int id) {this.key = key; this.id = id;}
	public String Key() {return key;} private String key;
	public int Id() {return id;} private int id;
	@Override public String toString() {return key + ":" + id;}
	public static Scrib_lua_proc cast_or_null_(Object o) {	// NOTE: maxStringLength and maxPatternLength return d:INF; ignore these
		return Type_adp_.ClassOf_obj(o) == Scrib_lua_proc.class ? (Scrib_lua_proc)o : null;
	}
}
