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
public class IptKey__tst {
	private final IptKey__fxt fxt = new IptKey__fxt();
	@Test  public void To_str() {
		fxt.Test_to_str(196608, "mod.cs");
	}
	@Test  public void To_str__numeric() {
		fxt.Test_to_str(16777296, "key.#16777296");
	}
	@Test   public void parse() {
		fxt.Test_parse("key.#10", 10);
	}
}
class IptKey__fxt {
	public void Test_to_str(int keycode, String expd) {
		Tfds.Eq(expd, IptKey_.To_str(keycode));
	}
	public void Test_parse(String raw, int keycode) {
		Tfds.Eq(keycode, IptKey_.parse(raw).Val());
	}
}
