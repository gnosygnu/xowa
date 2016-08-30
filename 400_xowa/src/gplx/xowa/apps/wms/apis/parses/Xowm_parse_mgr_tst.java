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
package gplx.xowa.apps.wms.apis.parses; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.wms.*; import gplx.xowa.apps.wms.apis.*;
import org.junit.*;
import gplx.langs.htmls.*;
public class Xowm_parse_mgr_tst {
	@Before public void init() {fxt.Clear();} private final    Xowm_parse_mgr_fxt fxt = new Xowm_parse_mgr_fxt();
	@Test   public void Remove_edit() {
		fxt.Exec__parse(Gfh_utl.Replace_apos_concat_lines
		( "<h2><span class='mw-headline' id='Section_1'>Section_1</span>"
		, "<span class='mw-editsection'>"
		, "<span class='mw-editsection-bracket'>[</span><a href='/w/index.php?title=Page_1&amp;action=edit&amp;section=1' title='Edit section: Section_1'>edit</a>"
		, "<span class='mw-editsection-bracket'>]</span>"
		, "</span>"
		, "</h2>"
		)).Test__html(Gfh_utl.Replace_apos_concat_lines
		( "<h2><span class='mw-headline' id='Section_1'>Section_1</span>"
		, ""
		, "</h2>"
		));
	}
	@Test   public void File() {
		fxt.Exec__parse(Gfh_utl.Replace_apos("<img src='//upload.wikimedia.org/wikipedia/commons/thumb/4/4a/Commons-logo.svg/12px-Commons-logo.svg.png'>"))
			.Test__html(Gfh_utl.Replace_apos("<img src='xowa:/file/commons.wikimedia.org/thumb/4/a/6/9/Commons-logo.svg/12px.png'>"))
			.Test__fsdb(fxt.Make__fsdb(Bool_.Y, Bool_.N, "Commons-logo.svg", 12, -1, -1));
		// https://upload.wikimedia.org/wikipedia/commons/thumb/f/fc/Papilio_dardanus_emerging.ogg/320px--Papilio_dardanus_emerging.ogg.jpg
		// https://upload.wikimedia.org/wikipedia/commons/thumb/3/30/Clip_from_My_Man_Godfrey.ogg/240px-seek%3D67-Clip_from_My_Man_Godfrey.ogg.jpg
		// https://upload.wikimedia.org/wikipedia/commons/thumb/7/7a/PL_Henryk_Sienkiewicz-Pisma_zapomniane_i_niewydane.djvu/page6-250px-PL_Henryk_Sienkiewicz-Pisma_zapomniane_i_niewydane.djvu.jpg
	}
//		@Test   public void Smoke() {
//			fxt.Exec__parse(Io_mgr.Instance.LoadFilStr("C:\\xowa\\dev\\wm.updater.src.html"));
//			Io_mgr.Instance.SaveFilBry("C:\\xowa\\dev\\wm.updater.trg.html", fxt.Hdoc().Converted());
//		}
}
