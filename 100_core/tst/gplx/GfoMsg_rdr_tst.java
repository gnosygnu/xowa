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
package gplx;
import org.junit.*;
public class GfoMsg_rdr_tst {
	@Before public void setup() {
		msg = msg_().Add("a", "1").Add("b", "2").Add("c", "3");
		ctx.Match("init", "init");
	}	GfoMsg msg; GfsCtx ctx = GfsCtx.new_();
	@Test public void Key() {
		tst_Msg(msg, "a", "1");
		tst_Msg(msg, "b", "2");
		tst_Msg(msg, "c", "3");
		tst_Msg(msg, "d", null);
	}
	@Test public void Pos() {
		msg = msg_().Add("", "1").Add("", "2").Add("", "3");
		tst_Msg(msg, "", "1");
		tst_Msg(msg, "", "2");
		tst_Msg(msg, "", "3");
		tst_Msg(msg, "", null);
	}
	@Test public void OutOfOrder() {
		tst_Msg(msg, "c", "3");
		tst_Msg(msg, "b", "2");
		tst_Msg(msg, "a", "1");
	}
	@Test public void Key3_Pos1_Pos2() {
		msg = msg_().Add("", "1").Add("", "2").Add("c", "3");
		tst_Msg(msg, "c", "3");
		tst_Msg(msg, "", "1");
		tst_Msg(msg, "", "2");
	}
	@Test public void MultipleEmpty() {
		msg = msg_().Add("", "1").Add("", "2").Add("", "3");
		tst_Msg(msg, "", "1");
		tst_Msg(msg, "", "2");
		tst_Msg(msg, "", "3");
	}
	GfoMsg msg_() {return GfoMsg_.new_parse_("test");}
	void tst_Msg(GfoMsg m, String k, String expd) {Tfds.Eq(expd, m.ReadStrOr(k, null));}
}
