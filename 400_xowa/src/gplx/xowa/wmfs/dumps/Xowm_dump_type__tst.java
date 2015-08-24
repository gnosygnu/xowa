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
package gplx.xowa.wmfs.dumps; import gplx.*; import gplx.xowa.*; import gplx.xowa.wmfs.*;
import org.junit.*; import gplx.core.primitives.*; import gplx.xowa.wikis.*;
public class Xowm_dump_type__tst {
	private final Xowm_dump_type__fxt fxt = new Xowm_dump_type__fxt();
	@Test  public void Parse() {
		fxt.Test_parse("pages-articles.xml"				, Xowm_dump_type_.Int__pages_articles);
		fxt.Test_parse("pages-meta-current.xml"			, Xowm_dump_type_.Int__pages_meta_current);
	}
}
class Xowm_dump_type__fxt {
	public void Test_parse(String raw_str, int expd) {Tfds.Eq_int(expd, Xowm_dump_type_.parse_by_file(Bry_.new_u8(raw_str)), "dump_type");}
}
