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
package gplx.xowa.apps; import gplx.*; import gplx.xowa.*;
import org.junit.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.apps.gfs.*;
public class Xoa_app_eval_tst {
	Xoa_app_eval_fxt fxt = new Xoa_app_eval_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Basic() {
		fxt.Eval_test("[~{<>app.sys_cfg.version;<>}]", "[" + Xoa_app_.Version + "]");
	}
}
class Xoa_app_eval_fxt {
	public void Clear() {
		if (app == null) {
			app = Xoa_app_fxt.Make__app__edit();
			fmtr = Bry_fmtr.new_();
			eval = new Xoa_app_eval();
			fmtr.Eval_mgr_(eval);
			Xoa_gfs_mgr.Msg_parser_init();
		}
	}	private Xoae_app app; Bry_fmtr fmtr; Xoa_app_eval eval;
	public void Eval_test(String raw, String expd) {
		Tfds.Eq(fmtr.Fmt_(raw).Bld_str_many(), expd);
	}
}
