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
package gplx.xowa.addons.builds.volumes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.builds.*;
import org.junit.*; import gplx.core.tests.*;
public class Volume_prep_rdr_tst {
	private final    Volume_prep_rdr_fxt fxt = new Volume_prep_rdr_fxt();
	@Test 	public void Parse() {
		fxt.Test__parse(String_.Concat_lines_nl_skip_last("A", "", "B")
			, fxt.Make__itm("A")
			, fxt.Make__itm("B")
			);
	}
}
class Volume_prep_rdr_fxt {
	private final    Volume_prep_rdr rdr = new Volume_prep_rdr();
	public Volume_prep_rdr_fxt Test__parse(String raw, Volume_prep_itm... expd) {
		Gftest.Eq__ary(expd, rdr.Parse(Bry_.new_u8(raw)));
		return this;
	}
	public Volume_prep_itm Make__itm(String page_ttl) {
		Volume_prep_itm rv = new Volume_prep_itm();
		rv.Page_ttl = Bry_.new_u8(page_ttl);
		return rv;
	}
}
