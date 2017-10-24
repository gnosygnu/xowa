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
package gplx.xowa.langs.kwds; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
public class Xol_kwd_itm {// NOTE: keeping as separate class b/c may include fmt props later; EX: thumbnail=$1
	public Xol_kwd_itm(byte[] val) {this.val = val;}
	public byte[] Val() {return val;} private byte[] val;
	public void Val_(byte[] v) {val = v;}	// should only be called by lang
	public static final    Xol_kwd_itm[] Ary_empty = new Xol_kwd_itm[0];
}
