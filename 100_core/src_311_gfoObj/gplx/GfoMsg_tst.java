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
public class GfoMsg_tst {
	@Before public void setup() {
		GfsCore._.AddObj(new Mok(), "Mok");
	}
	@Test public void Write1() {
		GfoMsg m = GfoMsg_.root_leafArgs_(String_.Ary("a", "b"), KeyVal_.new_("int0", 1));
		tst_Msg(m, "a.b:int0='1';");
	}
	@Test public void Write() {
		Mok mok = new Mok();
		tst_Msg(GfoInvkXtoStr.WriteMsg(mok, Mok.Invk_Cmd0, true, 1, "a"), "Mok.Cmd0:bool0='y' int0='1' str0='a';");
		mok.Int0 = 2;
		mok.Bool0 = true;
		mok.Str0 = "b";
		tst_Msg(GfoInvkXtoStr.ReadMsg(mok, Mok.Invk_Cmd0), "Mok.Cmd0:bool0='y' int0='2' str0='b';");
	}
	void tst_Msg(GfoMsg m, String expd) {Tfds.Eq(expd, m.XtoStr());}
	class Mok implements GfoInvkAble {
		public boolean Bool0;
		public int Int0;
		public String Str0;
		public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
			if	(ctx.Match(k, Invk_Cmd0)) {
				Bool0 = m.ReadBoolOr("bool0", Bool0);
				Int0 = m.ReadIntOr("int0", Int0);
				Str0 = m.ReadStrOr("str0", Str0);
				if (ctx.Deny()) return this;
			}
			return this;
		}	public static final String Invk_Cmd0 = "Cmd0";
	}
}
