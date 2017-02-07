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
package gplx.xowa.bldrs.setups.addons; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.setups.*;
import org.junit.*;
import gplx.core.ios.*;
public class Xoi_firefox_installer_tst {
	private Xoi_firefox_pref_fxt fxt = new Xoi_firefox_pref_fxt();
	@Test  public void Pref_update() {
		fxt.Test_pref_update(String_.Concat_lines_nl
		(	"pref(\"key_0\", \"val_0\"); // comment_0"
		,	"pref(\"key_1\", \"val_1\"); // comment_1"
		,	"pref(\"key_2\", \"val_2\"); // comment_2"
		)
		,	"key_1", "val_1_updated"
		,	String_.Concat_lines_nl
		(	"pref(\"key_0\", \"val_0\"); // comment_0"
		,	"pref(\"key_1\", \"val_1_updated\");"
		,	"pref(\"key_2\", \"val_2\"); // comment_2"
		)
		);
	}
}
class Xoi_firefox_pref_fxt {
	public void Test_pref_update(String src, String key, String val, String expd) {
		String actl = Xoi_firefox_installer.Pref_update(src, key, val);
		Tfds.Eq_str_lines(expd, actl);
	}
}
