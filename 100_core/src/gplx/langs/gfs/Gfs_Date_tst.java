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
package gplx.langs.gfs; import gplx.*; import gplx.langs.*;
import org.junit.*;
public class Gfs_Date_tst {
	@Before public void setup() {
		fx = new GfsCoreFxt();
		fx.AddObj(DateAdp_.Gfs, "Date_");
		Datetime_now.Manual_y_();
	}	GfsCoreFxt fx;
	@Test  public void Now() {
		fx.tst_MsgStr(fx.msg_(String_.Ary("Date_", "Now")), DateAdp_.parse_gplx("2001-01-01 00:00:00.000"));
	}
	@Test  public void Add_day() {
		fx.tst_MsgStr(fx.msg_(String_.Ary("Date_", "Now", "Add_day"), Keyval_.new_("days", 1)), DateAdp_.parse_gplx("2001-01-02 00:00:00.000"));
	}
}
class GfsCoreFxt {
	public GfsCore Core() {return core;} GfsCore core = GfsCore.new_();
	public GfoMsg msg_(String[] ary, Keyval... kvAry) {return GfoMsg_.root_leafArgs_(ary, kvAry);}
	public void AddObj(Gfo_invk invk, String s) {core.AddObj(invk, s);}
	public void tst_MsgStr(GfoMsg msg, Object expd) {
		GfsCtx ctx = GfsCtx.new_();
		Object actl = core.ExecOne(ctx, msg);
		Tfds.Eq(Object_.Xto_str_strict_or_null_mark(expd), Object_.Xto_str_strict_or_null_mark(actl));
	}
}
