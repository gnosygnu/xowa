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
package gplx.xowa.htmls.core.wkrs.imgs.atrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.imgs.*;
import org.junit.*;
public class Xoh_img_cls__tst {
	private Xoh_img_cls__fxt fxt = new Xoh_img_cls__fxt();
	@Test  public void To_html() {
		fxt.Test__to_html(Xoh_img_cls_.Tid__none		, null	, "");
		fxt.Test__to_html(Xoh_img_cls_.Tid__none		, "a"	, " class=\"a\"");
		fxt.Test__to_html(Xoh_img_cls_.Tid__thumbimage	, null	, " class=\"thumbimage\"");
		fxt.Test__to_html(Xoh_img_cls_.Tid__thumbborder	, null	, " class=\"thumbborder\"");
		fxt.Test__to_html(Xoh_img_cls_.Tid__thumbborder	, "a"	, " class=\"thumbborder a\"");
	}
}
class Xoh_img_cls__fxt {
	public void Test__to_html(byte tid, String other, String expd) {
		Tfds.Eq(expd, String_.new_u8(Xoh_img_cls_.To_html(tid, Bry_.new_u8_safe(other))));
	}
}
