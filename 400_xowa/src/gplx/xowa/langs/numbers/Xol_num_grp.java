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
package gplx.xowa.langs.numbers; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
public class Xol_num_grp {
	public Xol_num_grp(byte[] dlm, int digits, boolean repeat) {this.dlm = dlm; this.digits = digits; this.repeat = repeat;}
	public byte[] Dlm() {return dlm;} private byte[] dlm;
	public int Digits() {return digits;} private int digits;
	public boolean Repeat() {return repeat;} private boolean repeat;
	public static final Xol_num_grp[] Ary_empty = new Xol_num_grp[0];
	public static final Xol_num_grp Default = new Xol_num_grp(new byte[] {Byte_ascii.Comma}, 3, true);
}
