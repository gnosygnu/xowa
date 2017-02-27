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
package gplx.xowa.mediawiki.includes.xohtml; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
public class Xomw_atr_itm {
	public Xomw_atr_itm(int key_int, byte[] key, byte[] val) {
		this.key_int = key_int;
		this.key_bry = key;
		this.val = val;
	}
	public int Key_int() {return key_int;} private int key_int;
	public byte[] Key_bry() {return key_bry;} private byte[] key_bry;
	public byte[] Val() {return val;} private byte[] val;
	public void Val_(byte[] v) {this.val = v;}
}
