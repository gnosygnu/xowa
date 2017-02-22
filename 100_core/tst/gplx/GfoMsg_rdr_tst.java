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
package gplx;
import org.junit.*;
public class GfoMsg_rdr_tst {
	@Before public void setup() {
		msg = msg_().Add("a", "1").Add("b", "2").Add("c", "3");
		ctx.Match("init", "init");
	}	GfoMsg msg; GfsCtx ctx = GfsCtx.new_();
	@Test  public void Key() {
		tst_Msg(msg, "a", "1");
		tst_Msg(msg, "b", "2");
		tst_Msg(msg, "c", "3");
		tst_Msg(msg, "d", null);
	}
	@Test  public void Pos() {
		msg = msg_().Add("", "1").Add("", "2").Add("", "3");
		tst_Msg(msg, "", "1");
		tst_Msg(msg, "", "2");
		tst_Msg(msg, "", "3");
		tst_Msg(msg, "", null);
	}
	@Test  public void OutOfOrder() {
		tst_Msg(msg, "c", "3");
		tst_Msg(msg, "b", "2");
		tst_Msg(msg, "a", "1");
	}
	@Test  public void Key3_Pos1_Pos2() {
		msg = msg_().Add("", "1").Add("", "2").Add("c", "3");
		tst_Msg(msg, "c", "3");
		tst_Msg(msg, "", "1");
		tst_Msg(msg, "", "2");
	}
	@Test  public void MultipleEmpty() {
		msg = msg_().Add("", "1").Add("", "2").Add("", "3");
		tst_Msg(msg, "", "1");
		tst_Msg(msg, "", "2");
		tst_Msg(msg, "", "3");
	}
	GfoMsg msg_() {return GfoMsg_.new_parse_("test");}
	void tst_Msg(GfoMsg m, String k, String expd) {Tfds.Eq(expd, m.ReadStrOr(k, null));}
}
