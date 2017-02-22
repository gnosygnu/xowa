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
package gplx;
public class GfoTreeBldr_fxt {
	public List_adp Atrs() {return atrs;}  List_adp atrs = List_adp_.New();
	public List_adp Subs() {return subs;}  List_adp subs = List_adp_.New();
	public GfoTreeBldr_fxt atr_(Object key, Object val) {
		atrs.Add(new Object[] {key, val});
		return this;
	}
	public GfoTreeBldr_fxt sub_(GfoTreeBldr_fxt... ary) {
		for (GfoTreeBldr_fxt sub : ary)
			subs.Add(sub);
		return this;
	}
	public static GfoTreeBldr_fxt new_() {return new GfoTreeBldr_fxt();} GfoTreeBldr_fxt() {}
}
