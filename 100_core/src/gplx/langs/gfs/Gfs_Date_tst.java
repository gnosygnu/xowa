/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.langs.gfs; import gplx.*; import gplx.langs.*;
import org.junit.*;
public class Gfs_Date_tst {
	@Before public void setup() {
		fx = new GfsCoreFxt();
		fx.AddObj(DateAdp_.Gfs, "Date_");
		Tfds.Now_enabled_y_();
	}	GfsCoreFxt fx;
	@Test  public void Now() {
		fx.tst_MsgStr(fx.msg_(String_.Ary("Date_", "Now")), DateAdp_.parse_gplx("2001-01-01 00:00:00.000"));
	}
	@Test  public void Add_day() {
		fx.tst_MsgStr(fx.msg_(String_.Ary("Date_", "Now", "Add_day"), KeyVal_.new_("days", 1)), DateAdp_.parse_gplx("2001-01-02 00:00:00.000"));
	}
}
class GfsCoreFxt {
	public GfsCore Core() {return core;} GfsCore core = GfsCore.new_();
	public GfoMsg msg_(String[] ary, KeyVal... kvAry) {return GfoMsg_.root_leafArgs_(ary, kvAry);}
	public void AddObj(GfoInvkAble invk, String s) {core.AddObj(invk, s);}
	public void tst_MsgStr(GfoMsg msg, Object expd) {
		GfsCtx ctx = GfsCtx.new_();
		Object actl = core.ExecOne(ctx, msg);
		Tfds.Eq(Object_.Xto_str_strict_or_null_mark(expd), Object_.Xto_str_strict_or_null_mark(actl));
	}
}
