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
package gplx.xowa.apps.fmtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import org.junit.*; import gplx.langs.gfs.*;
public class Xoa_fmtr_itm_tst {
	@Before public void init() {fxt.Clear();} private Xoa_fmtr_itm_fxt fxt = new Xoa_fmtr_itm_fxt();
	@Test   public void Basic() {
		fxt.Init_src("app.wikis;");
		fxt.Init_fmt("domain=~{<>domain;<>};");
		fxt.Test_run("domain=en.wikipedia.org;");
	}
}
class Xoa_fmtr_itm_fxt {
	private Xoae_app app; private Xoa_fmtr_itm itm;
	public void Clear() {
		app = Xoa_app_fxt.Make__app__edit();
		Xoa_app_fxt.Make__wiki__edit(app);	// create enwiki
		itm = new Xoa_fmtr_itm(app);
		GfsCore.Instance.MsgParser_(gplx.langs.gfs.Gfs_msg_bldr.Instance);
	}
	public Xoa_fmtr_itm_fxt Init_src(String v) {itm.Src_(v); return this;}
	public Xoa_fmtr_itm_fxt Init_fmt(String v) {itm.Fmt_(Bry_.new_a7(v)); return this;}
	public void Test_run(String expd) {
		String actl = itm.Run();
		Tfds.Eq(expd, actl);
	}
}
