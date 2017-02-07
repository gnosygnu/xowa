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