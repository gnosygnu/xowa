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
package gplx.core.net.qargs; import gplx.*; import gplx.core.*; import gplx.core.net.*;
public class Gfo_qarg_itm {
	public Gfo_qarg_itm(byte[] key_bry, byte[] val_bry) {
		this.key_bry = key_bry;
		this.val_bry = val_bry;
	}
	public byte[]			Key_bry() {return key_bry;} private final    byte[] key_bry;
	public byte[]			Val_bry() {return val_bry;} private byte[] val_bry;
	public void				Val_bry_(byte[] v) {val_bry = v;}

	public static final    Gfo_qarg_itm[] Ary_empty = new Gfo_qarg_itm[0];
}
