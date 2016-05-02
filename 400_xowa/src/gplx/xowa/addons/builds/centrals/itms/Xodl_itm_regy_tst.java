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
package gplx.xowa.addons.builds.centrals.itms; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.builds.*; import gplx.xowa.addons.builds.centrals.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.langs.jsons.*;
public class Xodl_itm_regy_tst {
	private final    Xodl_itm_regy_fxt fxt = new Xodl_itm_regy_fxt();
	@Test 	public void Basic() {
		fxt.Test__srl
		( Json_doc.Make_str_ary_by_apos
		( "["
		, "  { 'id':1"
		, "  , 'wiki':'simple.wikipedia.org'"
		, "  , 'date':'2016-04-07'"
		, "  , 'type':'html'"
		, "  , 'name':'html: complete'"
		, "  , 'size':1234"
		, "  , 'url':'http://archive.org/Xowa_simplewiki_latest/Xowa_simplewiki_2016-04-07_html.zip'"
		, "  }"
		, ","
		, "  { 'id':2"
		, "  , 'wiki':'simple.wikipedia.org'"
		, "  , 'date':'2016-04-07'"
		, "  , 'type':'file'"
		, "  , 'name':'file: complete'"
		, "  , 'size':4321"
		, "  , 'url':'http://archive.org/Xowa_simplewiki_latest/Xowa_simplewiki_2016-04-07_file.zip'"
		, "  }"
		, "]"
		)
		, fxt.Make__regy
		(  fxt.Make__pack(1, "simple.wikipedia.org", "2016-04-07", "html", -1, -1, "html: complete", 1234, "http://archive.org/Xowa_simplewiki_latest/Xowa_simplewiki_2016-04-07_html.zip")
		,  fxt.Make__pack(2, "simple.wikipedia.org", "2016-04-07", "file", -1, -1, "file: complete", 4321, "http://archive.org/Xowa_simplewiki_latest/Xowa_simplewiki_2016-04-07_file.zip")
		)
		);
	}
}
class Xodl_itm_regy_fxt {
	private final    Json_wtr wtr = new Json_wtr();
	public Xodl_itm_regy Make__regy(Xodl_itm_pack... pack_ary) {return new Xodl_itm_regy(pack_ary);}
	public Xodl_itm_pack Make__pack(int id, String wiki, String date, String type, int ns, int part, String name, long size, String url) {return new Xodl_itm_pack(id, wiki, date, type, ns, part, name, size, url);}
	public Xodl_itm_regy_fxt Test__srl(String[] json, Xodl_itm_regy regy) {
		regy.To_json(wtr);
		byte[][] json_bry = wtr.Bfr().To_bry_ary_and_clear();
		Gftest.Eq__ary(json, json_bry, "to_json");

		regy = Xodl_itm_regy.Load_by_json(Bry_.Add(json_bry));
		regy.To_json(wtr);
		json_bry = wtr.Bfr().To_bry_ary_and_clear();
		Gftest.Eq__ary(json, json_bry, "by_json");
		return this;
	}
}
