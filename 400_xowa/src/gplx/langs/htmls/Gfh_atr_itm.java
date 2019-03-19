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
package gplx.langs.htmls; import gplx.*; import gplx.langs.*;
public class Gfh_atr_itm {
	public Gfh_atr_itm(byte[] key, byte[] val) {
		this.key = key;
		this.val = val;
	}
	public byte[] Key() {return key;} private final    byte[] key;
	public byte[] Val() {return val;} private final    byte[] val;
	public static Gfh_atr_itm New(byte[] key, String val) {return new Gfh_atr_itm(key, Bry_.new_u8(val));}
	public static Gfh_atr_itm New(byte[] key, byte[] val) {return new Gfh_atr_itm(key, val);}
}
