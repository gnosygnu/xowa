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
package gplx.xowa.xtns.math; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
class Xomath_latex_itm {
	public Xomath_latex_itm(int uid, byte[] src, byte[] md5, Io_url png) {
		this.uid = uid;
		this.src = src;
		this.md5 = md5;
		this.png = png;
	}
	public int		Uid() {return uid;} private final    int uid;
	public byte[]	Src() {return src;} private final    byte[] src;
	public byte[]	Md5() {return md5;} private final    byte[] md5;
	public Io_url	Png() {return png;} private final    Io_url png;
}
