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
package gplx.xowa.mws; import gplx.*; import gplx.xowa.*;
import org.junit.*; import gplx.core.tests.*;
public class Xomw_MagicWordArray__tst {
	private final    Xomw_MagicWordArray__fxt fxt = new Xomw_MagicWordArray__fxt();
	@Test  public void Nil() {
		fxt.Init__word(Bool_.Y, "nil", "nil");
		fxt.Init__ary("nil");
		fxt.Test__matchVariableStartToEnd("nil", "nil", "nila");
	}
	@Test  public void Bgn() {
		fxt.Init__word(Bool_.Y, "bgn", "bgn$1");
		fxt.Init__ary("bgn");
		fxt.Test__matchVariableStartToEnd("bgn", "bgn", "bgna");
	}
	@Test  public void End() {
		fxt.Init__word(Bool_.Y, "end", "$1end");
		fxt.Init__ary("end");
		fxt.Test__matchVariableStartToEnd("end", "end", "aend");
	}
	@Test  public void Smoke() {
		fxt.Init__word(Bool_.Y, "img_upright", "upright", "upright=$1", "upright $1");
		fxt.Init__word(Bool_.Y, "img_width", "$1px");
		fxt.Init__ary("img_upright", "img_width");

		fxt.Test__matchVariableStartToEnd("img_upright", "upright", "upright=123", "upright 123");
		fxt.Test__matchVariableStartToEnd("img_width", "123px");
	}
}
class Xomw_MagicWordArray__fxt {
	private final    Xomw_MagicWordMgr magic_word_mgr = new Xomw_MagicWordMgr();
	private Xomw_MagicWordArray magic_word_ary;
	public void Init__word(boolean cs, String word, String... synonyms) {
		magic_word_mgr.Add(Bry_.new_u8(word), cs, Bry_.Ary(synonyms));
	}
	public void Init__ary(String... words) {
		magic_word_ary = new Xomw_MagicWordArray(magic_word_mgr, Bry_.Ary(words));
	}
	public void Test__matchVariableStartToEnd(String expd_str, String... vals) {
		byte[] expd = Bry_.new_u8(expd_str);
		for (String val : vals) {
			byte[] actl = magic_word_ary.matchVariableStartToEnd(Bry_.new_u8(val));
			Gftest.Eq__bry(expd, actl, val);
		}
	}
}
