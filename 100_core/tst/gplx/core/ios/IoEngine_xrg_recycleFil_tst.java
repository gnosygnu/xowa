/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.ios;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.commons.GfoDateUtl;
import gplx.types.commons.GfoGuidUtl;
import gplx.libs.files.Io_url;
import gplx.libs.files.Io_url_;
import org.junit.*;
public class IoEngine_xrg_recycleFil_tst {
	@Before public void setup() {
		IoEngine_.Mem_init_();
	}
	@Test public void GenRecycleUrl() {
		tst_GenRecycleUrl(recycle_(), Io_url_.mem_fil_("mem/z_trash/20100102/gplx.images;115559123;;fil.txt"));
		tst_GenRecycleUrl(recycle_().Uuid_include_(), Io_url_.mem_fil_("mem/z_trash/20100102/gplx.images;115559123;467ffb41-cdfe-402f-b22b-be855425784b;fil.txt"));
	}
	IoEngine_xrg_recycleFil recycle_() {return IoEngine_xrg_recycleFil.gplx_(Io_url_.mem_fil_("mem/dir/fil.txt")).AppName_("gplx.images").Uuid_(GfoGuidUtl.Parse("467ffb41-cdfe-402f-b22b-be855425784b")).Time_(GfoDateUtl.ParseGplx("20100102_115559123"));}
	void tst_GenRecycleUrl(IoEngine_xrg_recycleFil xrg, Io_url expd) {
		GfoTstr.EqObj(expd, xrg.RecycleUrl());
	}
}
