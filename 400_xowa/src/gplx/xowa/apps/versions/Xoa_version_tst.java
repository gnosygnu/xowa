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
package gplx.xowa.apps.versions; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import org.junit.*;
public class Xoa_version_tst {
	@Before public void init() {fxt.Clear();} private Xoa_version_fxt fxt = new Xoa_version_fxt();
	@Test  public void Compare() {
		fxt.Test_compare("1.8.1.1", "1.8.2.1"	, CompareAble_.Less);	// rev:less
		fxt.Test_compare("1.8.2.1", "1.8.1.1"	, CompareAble_.More);	// rev:more
		fxt.Test_compare("1.8.1.1", "1.8.1.1"	, CompareAble_.Same);	// rev:same
		fxt.Test_compare("1.7.9.1", "1.8.1.1"	, CompareAble_.Less);	// min:less
		fxt.Test_compare("", "1.8.1.1"			, CompareAble_.Less);	// empty:less
		fxt.Test_compare("1.8.1.1", ""			, CompareAble_.More);	// empty:more
		fxt.Test_compare("", ""					, CompareAble_.Same);	// empty:more
	}
}
class Xoa_version_fxt {
	public void Clear() {}
	public void Test_compare(String lhs, String rhs, int expd) {
		Tfds.Eq(expd, Xoa_version_.Compare(lhs, rhs), lhs + "|" + rhs);
	}
}
