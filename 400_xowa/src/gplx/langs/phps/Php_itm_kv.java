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
package gplx.langs.phps;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
public class Php_itm_kv implements Php_itm, Php_itm_sub {
	public byte Itm_tid() {return Php_itm_.Tid_kv;}
	public byte[] Val_obj_bry() {return null;}
	public Php_key Key() {return key;} public Php_itm_kv Key_(Php_key v) {this.key = v; return this;} private Php_key key;
	public Php_itm Val() {return val;} public Php_itm_kv Val_(Php_itm v) {this.val = v; return this;} private Php_itm val;

	private List_adp comments;
	public int Comments__len() {return comments == null ? 0 : comments.Len();} 
	public Php_tkn_comment Comments__get_at__or_null(int i) {return comments == null ? null : (Php_tkn_comment)comments.GetAt(0);}
	public void Comments__add(Php_tkn comment) {
		if (comments == null) {
			comments = List_adp_.New();
		}
		comments.Add(comment);
	}
}
