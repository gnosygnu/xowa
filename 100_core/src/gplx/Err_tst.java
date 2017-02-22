/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
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
