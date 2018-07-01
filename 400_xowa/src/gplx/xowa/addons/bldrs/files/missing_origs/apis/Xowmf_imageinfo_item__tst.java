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
package gplx.xowa.addons.bldrs.files.missing_origs.apis; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*; import gplx.xowa.addons.bldrs.files.missing_origs.*;
import org.junit.*; import gplx.core.tests.*;
public class Xowmf_imageinfo_item__tst {
	private final    Xowmf_imageinfo_item__fxt fxt = new Xowmf_imageinfo_item__fxt();

	@Test   public void Normalize_ttl() {
		fxt.Test__Normalize_ttl("File:A b.png", "A_b.png");
	}
	@Test   public void Normalize_minor_mime() {
		fxt.Test__Normalize_minor_mime("image/svg+xml", "svg+xml");
	}
	@Test   public void Normalize_timestamp() {
		fxt.Test__Normalize_timestamp("2017-03-06T08:09:10Z", "20170306080910");
	}
}
class Xowmf_imageinfo_item__fxt {
	public void Test__Normalize_ttl(String src, String expd) {
		Gftest.Eq__str(expd, Xowmf_imageinfo_item.Normalize_ttl(Bry_.new_u8(src)));
	}
	public void Test__Normalize_timestamp(String src, String expd) {
		Gftest.Eq__str(expd, Xowmf_imageinfo_item.Normalize_timestamp(Bry_.new_u8(src)));
	}
	public void Test__Normalize_minor_mime(String src, String expd) {
		Gftest.Eq__str(expd, Xowmf_imageinfo_item.Normalize_minor_mime(Bry_.new_u8(src)));
	}
}
