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
public class Err_tst {
	private final Err_fxt fxt = new Err_fxt();
	@Test  public void Trace_to_str__gplx() {
		fxt.Test_Trace_to_str(Bool_.Y, Bool_.N, 0, String_.Concat_lines_nl_skip_last
		( "gplx.Err_.new_wo_type(Err_.java:1)"			// ignore this line
		, "gplx.String_.Len(String_.java:2)"
		), String_.Concat_lines_nl_skip_last
		( ""
		, "  gplx.String_.Len(String_.java:2)"
		));
	}
	@Test  public void Trace_to_str__gplx_ignore() {
		fxt.Test_Trace_to_str(Bool_.Y, Bool_.N, 1, String_.Concat_lines_nl_skip_last
		( "gplx.Err_.new_wo_type(Err_.java:1)"				// ignore this line
		, "gplx.String_.Fail(String_.java:2)"		// ignore this line also
		, "gplx.String_.Len(String_.java:3)"
		), String_.Concat_lines_nl_skip_last
		( ""
		, "  gplx.String_.Len(String_.java:3)"
		));
	}
}
class Err_fxt {
	public void Test_Trace_to_str(boolean is_gplx, boolean called_by_log, int ignore_lines, String trace, String expd) {
		String actl = Err.Trace_to_str(is_gplx, called_by_log, ignore_lines, trace);
		Tfds.Eq_str_lines(expd, actl);
	}
}
