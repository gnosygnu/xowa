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
package gplx.core.gfo_regys; import gplx.*; import gplx.core.*;
import org.junit.*;
public class GfoRegy_basic_tst {
	@Before public void setup() {
		regy = GfoRegy.new_();
	}	GfoRegy regy;
	@Test  public void RegObjByType() {
		regy.Parsers().Add("Io_url", Io_url_.Parser);
		Io_url expd = Io_url_.new_any_("C:\\fil.txt");
		regy.RegObjByType("test", expd.Xto_api(), "Io_url");
		Io_url actl = (Io_url)regy.FetchValOr("test", Io_url_.Empty);
		Tfds.Eq(expd.Xto_api(), actl.Xto_api());
	}
}
