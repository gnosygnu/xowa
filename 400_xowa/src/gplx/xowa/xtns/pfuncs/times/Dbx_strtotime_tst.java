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
package gplx.xowa.xtns.pfuncs.times; import org.junit.After;
import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.core.tests.*;
import gplx.core.btries.*;

import org.junit.Before;
import org.junit.Test;

public class Dbx_strtotime_tst {
	private final Dbx_strtotime_fxt fxt = new Dbx_strtotime_fxt();
	@Before	public void setup()	  {
		Datetime_now.Manual_(DateAdp_.new_(2012, 1, 2, 3, 4, 5, 6));
	}
	@After public void teardown() {
		Datetime_now.Manual_n_();
	}
	@Test public void Basic() {
//		"{{#time:r|n.d.}}", "Sat, 08 Feb 2020 22:57:12 +0000");
//		fxt.Test__date("n.d.", "Sat, 08 Feb 2020 22:57:12 +0000");
//		fxt.Test__date("30.6.2011-06-30", "Sat, 08 Feb 2020 22:57:12 +0000");		
//		fxt.Test__date("7 May 2013", "2013-05-07 00:00:00");
	}

}
class Dbx_strtotime_fxt {
	public Dbx_strtotime_fxt() {
		Btrie_slim_mgr trie = Pxd_parser_.Trie();
        Dbx_scan_support.Init(trie);		
	}
	public void Test__date(String raw, String expd) {
		DateAdp date = Dbx_scan_support.Parse(Bry_.new_u8(raw));
		Gftest.Eq__str(expd, date.XtoStr_fmt_iso_8561());
	}	
}
