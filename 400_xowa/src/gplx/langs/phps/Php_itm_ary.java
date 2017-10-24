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
package gplx.langs.phps; import gplx.*; import gplx.langs.*;
public class Php_itm_ary implements Php_itm, Php_itm_sub {
	public Php_itm_ary() {}
	public byte Itm_tid() {return Php_itm_.Tid_ary;}
	public byte[] Val_obj_bry() {return null;}
	public int Subs_len() {return subs_len;} private int subs_len;
	public Php_itm_sub Subs_get(int i) {return ary[i];}
	public Php_itm_sub Subs_pop() {return ary[--subs_len];}
	public void Subs_add(Php_itm_sub v) {
		int new_len = subs_len + 1;
		if (new_len > subs_max) {	// ary too small >>> expand
			subs_max = new_len * 2;
			Php_itm_sub[] new_ary = new Php_itm_sub[subs_max];
			Array_.Copy_to(ary, 0, new_ary, 0, subs_len);
			ary = new_ary;
		}
		ary[subs_len] = v;
		subs_len = new_len;
	}	Php_itm_sub[] ary = Php_itm_sub_.Ary_empty; int subs_max;
}
