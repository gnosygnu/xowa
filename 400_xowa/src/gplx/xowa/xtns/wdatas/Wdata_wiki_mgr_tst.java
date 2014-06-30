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
import org.junit.*; import gplx.xowa.xtns.wdatas.imports.*;
public class Wdata_wiki_mgr_tst {
	@Test  public void Basic() {
		Wdata_wiki_mgr_fxt fxt = new Wdata_wiki_mgr_fxt().Init();
		fxt.Init_links_add("enwiki", "q1", "q1_en");
		fxt.Test_link("en", "q1", "q1_en");
		fxt.Test_link("en", "q2", null);
	}
	@Test  public void Case_sensitive() {	// PURPOSE: wikidata lkp should be case_sensitive; a vs A DATE:2013-09-03
		Wdata_wiki_mgr_fxt fxt = new Wdata_wiki_mgr_fxt().Init();
		fxt.Init_links_add("enwiki", "q1", "q1_lc");
		fxt.Test_link("en", "q1", "q1_lc");
		fxt.Test_link("en", "Q1", null);	// Q1 should not match q1
	}
	@Test   public void Write_json_as_html() {
		Wdata_wiki_mgr_fxt fxt = new Wdata_wiki_mgr_fxt().Init();
		fxt.Test_write_json_as_html("{'a':'b','c':['d','e'],'f':{'g':'<h>'}}", String_.Concat_lines_nl_skip_last
		(	"<span id=\"xowa-wikidata-json\">"
		,	"{ \"a\":\"b\""
		,	", \"c\":"
		,	"  [ \"d\""
		,	"  , \"e\""
		,	"  ]"
		,	", \"f\":"
		,	"  { \"g\":\"&lt;h&gt;\""
		,	"  }"
		,	"}"
		,	"</span>"
		));
	}
	@Test  public void Get_low_qid() {
		Wdata_wiki_mgr_fxt fxt = new Wdata_wiki_mgr_fxt();
		fxt.Test_get_low_qid("q1"	, "q1");	// return self
		fxt.Test_get_low_qid("q2|q1", "q1");	// return lowest
	}
}
