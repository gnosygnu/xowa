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
package gplx.gfui; import gplx.*;
import org.junit.*;
public class IptArg_parser_tst {
	@Test  public void KeyBasic() {
		tst_parse_Key_("key.a", IptKey_.A);
		tst_parse_Key_("key.d0", IptKey_.D0);
		tst_parse_Key_("key.semicolon", IptKey_.Semicolon);
		tst_parse_Key_("key.equal", IptKey_.Equal);
		tst_parse_Key_("key.pageUp", IptKey_.PageUp);
		tst_parse_Key_("key.ctrl", IptKey_.Ctrl);
		tst_parse_Key_("key.none", IptKey_.None);
	}	void tst_parse_Key_(String raw, IptKey expd) {Tfds.Eq(expd.Val(), IptKey_.parse_(raw).Val());}
	@Test  public void KbdCmdModifiers() {
		tst_parse_Key_("key.ctrl+key.enter", IptKey_.Ctrl.Add(IptKey_.Enter));
		tst_parse_Key_("key.alt+key.escape", IptKey_.Alt.Add(IptKey_.Escape));
		tst_parse_Key_("key.shift+key.f1", IptKey_.Shift.Add(IptKey_.F1));
		tst_parse_Key_("key.shift+key.ctrl", IptKey_.Ctrl.Add(IptKey_.Shift));
		tst_parse_Key_("key.ctrl+key.alt+key.slash", IptKey_.Ctrl.Add(IptKey_.Alt).Add(IptKey_.Slash));
	}
	@Test  public void KeyWhitespace() {
		tst_parse_Key_("key.ctrl + key.alt + key.slash", IptKey_.Ctrl.Add(IptKey_.Alt).Add(IptKey_.Slash));
	}
	@Test  public void MouseBtn() {
		tst_parse_MouseBtn_("mouse.left", IptMouseBtn_.Left);
		tst_parse_MouseBtn_("mouse.right", IptMouseBtn_.Right);
		tst_parse_MouseBtn_("mouse.middle", IptMouseBtn_.Middle);
		tst_parse_MouseBtn_("mouse.x1", IptMouseBtn_.X1);
		tst_parse_MouseBtn_("mouse.x2", IptMouseBtn_.X2);
	}	void tst_parse_MouseBtn_(String raw, IptMouseBtn expd) {Tfds.Eq(expd, IptMouseBtn_.parse_(raw));}
	@Test  public void MouseWheel() {
		tst_parse_MouseWheel_("wheel.up", IptMouseWheel_.Up);
		tst_parse_MouseWheel_("wheel.down", IptMouseWheel_.Down);
	}	void tst_parse_MouseWheel_(String raw, IptMouseWheel expd) {Tfds.Eq(expd, IptMouseWheel_.parse_(raw));}
	@Test  public void Mod() {
		tst_parse_("mod.c", IptKey_.Ctrl);
		tst_parse_("mod.cs", IptKey_.add_(IptKey_.Ctrl, IptKey_.Shift));
		tst_parse_("mod.cas", IptKey_.add_(IptKey_.Ctrl, IptKey_.Alt, IptKey_.Shift));
		tst_parse_("mod.c+key.c", IptKey_.add_(IptKey_.Ctrl, IptKey_.C));
	}
	@Test  public void All() {
		tst_parse_("key.c", IptKey_.C);
		tst_parse_("mouse.left", IptMouseBtn_.Left);
		tst_parse_("wheel.up", IptMouseWheel_.Up);
		tst_parse_("mod.c", IptKey_.Ctrl);
	}	void tst_parse_(String raw, IptArg expd) {Tfds.Eq(expd, IptArg_.parse_(raw));}
}
