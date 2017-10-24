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
public class Php_itm_int implements Php_itm, Php_itm_sub, Php_key {
	public Php_itm_int(int v) {this.val_obj_int = v;}
	public byte Itm_tid() {return Php_itm_.Tid_int;}
	public byte[] Val_obj_bry() {return Bry_.new_by_int(val_obj_int);}
	public int Val_obj_int() {return val_obj_int;} private int val_obj_int;
}
