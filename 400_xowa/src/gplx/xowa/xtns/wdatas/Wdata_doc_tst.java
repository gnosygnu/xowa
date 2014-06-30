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
package gplx.xowa.xtns.wdatas; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*; import gplx.json.*;
public class Wdata_doc_tst {
	@Test  public void Link_extract() {
		Tst_link_extract("{\"enwiki\":\"A\"}", "A");
		Tst_link_extract("{\"enwiki\":{\"name\":\"A\",\"badges\":[]}}", "A");
	}
	private void Tst_link_extract(String src, String expd) {
		Json_doc doc = Json_doc.new_(src);
		Json_itm_kv root = (Json_itm_kv)doc.Root().Subs_get_at(0);
		Tfds.Eq(expd, String_.new_ascii_(Wdata_doc_.Link_extract(root)));
	}
}
