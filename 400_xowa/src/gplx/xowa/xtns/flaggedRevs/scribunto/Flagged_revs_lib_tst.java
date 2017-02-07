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
package gplx.xowa.xtns.flaggedRevs.scribunto; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.flaggedRevs.*;
import org.junit.*;
import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.libs.*; import gplx.xowa.xtns.scribunto.engines.mocks.*;
public class Flagged_revs_lib_tst {
	private final Mock_scrib_fxt fxt = new Mock_scrib_fxt(); private Flagged_revs_lib lib;
	@Before public void init() {
		fxt.Clear();
		lib = new Flagged_revs_lib();
		lib.Init();
		lib.Core_(fxt.Core());
	}
	@Test   public void GetStabilitySettings() {
		fxt.Test__proc__objs__nest(lib, Flagged_revs_lib.Invk_getStabilitySettings, Object_.Ary("Page1"), String_.Concat_lines_nl_skip_last
		( "1="
		, "  over"+"ride=0"
		, "  autoreview="
		, "  expiry=infinity"
		));
	}
}	
