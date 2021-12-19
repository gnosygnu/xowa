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
package gplx.xowa.xtns.kartographers;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.*;
import org.junit.*;
public class Maplink_xnde__tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Test public void Basic() {
		fxt.Test__parse__tmpl_to_html(StringUtl.ConcatLinesNlSkipLast
		( "<maplink zoom=9 latitude=37.8013 longitude=-122.3988>"
		, "{ 'type':'Feature'"
		, ", 'geometry': {}"
		, "</maplink>"
		), ""
		);
	}
}
