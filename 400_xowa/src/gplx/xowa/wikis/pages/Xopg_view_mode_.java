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
package gplx.xowa.wikis.pages;
import gplx.types.basics.utls.BryUtl;
import gplx.types.errs.ErrUtl;
public class Xopg_view_mode_ {
	public static final byte
	  Tid__read = 0
	, Tid__edit = 1
	, Tid__html = 2;
	public static final byte[]
	  Bry__read = BryUtl.NewA7("read")
	, Bry__edit = BryUtl.NewA7("edit")
	, Bry__html = BryUtl.NewA7("html");
	public static byte[] To_bry(byte tid) {
		switch (tid) {
			case Tid__read: return Bry__read;
			case Tid__edit: return Bry__edit;
			case Tid__html: return Bry__html;
			default: throw ErrUtl.NewUnhandled(tid);
		}
	}
}
