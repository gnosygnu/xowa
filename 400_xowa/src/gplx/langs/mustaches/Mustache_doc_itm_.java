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
package gplx.langs.mustaches; import gplx.*; import gplx.langs.*;
public class Mustache_doc_itm_ {
	public static final    byte[] Null_val = null;
	public static final    Mustache_doc_itm Null_itm = null;
	public static final    Mustache_doc_itm Itm__bool__y = new Mustache_doc_itm__bool(Bool_.Y), Itm__bool__n = new Mustache_doc_itm__bool(Bool_.N);
	public static final    Mustache_doc_itm[] Ary__empty = new Mustache_doc_itm[0], Ary__bool__y = new Mustache_doc_itm[] {Itm__bool__y}, Ary__bool__n = new Mustache_doc_itm[] {Itm__bool__n};
	public static Mustache_doc_itm[] Ary__bool(boolean v) {return v ? Ary__bool__y : Ary__bool__n;}
}
class Mustache_doc_itm__bool implements Mustache_doc_itm {
	public Mustache_doc_itm__bool(boolean val) {this.val = val;}
	public boolean Val() {return val;} private final    boolean val;
	public boolean Mustache__write(String key, Mustache_bfr bfr) {return false;}
	public Mustache_doc_itm[] Mustache__subs(String key) {return Mustache_doc_itm_.Ary__empty;}
}
