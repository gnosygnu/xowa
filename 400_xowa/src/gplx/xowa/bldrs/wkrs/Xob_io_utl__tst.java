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
package gplx.xowa.bldrs.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import org.junit.*; import gplx.core.tests.*;
public class Xob_io_utl__tst {
	private final    Xob_io_utl__fxt fxt = new Xob_io_utl__fxt();
	@Test   public void Basic() {
		fxt.Test__match(String_.Ary("a.txt", "b.txt", "c.txt"), "b", String_.Ary(".txt"), "b.txt");
	}
	@Test   public void Include__ext() {// PURPOSE: handle calls like "a.sql", ".sql", ".gz"
		fxt.Test__match(String_.Ary("a.txt", "b.txt", "c.txt"), "b.txt", String_.Ary(".txt"), "b.txt");
	}
	@Test   public void Dupe__pick_last() {
		fxt.Test__match(String_.Ary("b0.txt", "b1.txt", "b2.txt"), "b", String_.Ary(".txt"), "b2.txt");
	}
	@Test   public void Ext() {
		fxt.Test__match(String_.Ary("b.txt", "b.png", "b.xml"), "b", String_.Ary(".xml", ".bz2"), "b.xml");
	}
	@Test   public void Ext__dupes() {
		fxt.Test__match(String_.Ary("b.txt", "b.png", "b.xml"), "b", String_.Ary(".txt", ".xml"), "b.xml");
	}
}
class Xob_io_utl__fxt {
	public void Test__match(String[] path_ary, String name_pattern, String[] ext_ary, String expd) {			
		Io_url actl = Xob_io_utl_.Find_nth_by_wildcard_or_null(Io_url_.Ary(path_ary), name_pattern, ext_ary);
		Gftest.Eq__str(expd, actl == null ? "<<NULL>>" : actl.Raw());
	}
}