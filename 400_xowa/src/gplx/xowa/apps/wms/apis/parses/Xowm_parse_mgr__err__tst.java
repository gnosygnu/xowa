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
public class Xowm_parse_mgr__err__tst {
	@Before public void init() {fxt.Clear();} private final    Xowm_parse_mgr_fxt fxt = new Xowm_parse_mgr_fxt();
	@Test   public void Url_does_not_start_with_upload_wikimedia_org() {
		fxt.Exec__parse(Gfh_utl.Replace_apos("<img src='//fail/wikipedia/commons/thumb/7/70/A.png/220px-A.png'>"))
			.Test__html(Gfh_utl.Replace_apos("<!--wm.parse:url does not start with //upload.wikimedia.org--><img src='//fail/wikipedia/commons/thumb/7/70/A.png/220px-A.png'>"));
	}
	@Test   public void Unknown_repo() {
		fxt.Exec__parse(Gfh_utl.Replace_apos("<img src='//upload.wikimedia.org/wiktionary/fr/thumb/7/70/A.png/220px-A.png'>"))
			.Test__html(Gfh_utl.Replace_apos("<!--wm.parse:unknown repo--><img src='//upload.wikimedia.org/wiktionary/fr/thumb/7/70/A.png/220px-A.png'>"));
	}
	@Test   public void Bad_md5() {
		fxt.Exec__parse(Gfh_utl.Replace_apos("<img src='//upload.wikimedia.org/wikipedia/commons/thumb/fail/A.png/220px-A.png'>"))
			.Test__html(Gfh_utl.Replace_apos("<!--wm.parse:invalid md5--><img src='//upload.wikimedia.org/wikipedia/commons/thumb/fail/A.png/220px-A.png'>"));
	}
	@Test   public void Missing_px() {
		fxt.Exec__parse(Gfh_utl.Replace_apos("<img src='//upload.wikimedia.org/wikipedia/commons/thumb/7/70/A.png/220-A.png'>"))
			.Test__html(Gfh_utl.Replace_apos("<!--wm.parse:missing px--><img src='//upload.wikimedia.org/wikipedia/commons/thumb/7/70/A.png/220-A.png'>"));
	}
	@Test   public void Bad_file_w() {
		fxt.Exec__parse(Gfh_utl.Replace_apos("<img src='//upload.wikimedia.org/wikipedia/commons/thumb/7/70/A.png/220_fail_px-A.png'>"))
			.Test__html(Gfh_utl.Replace_apos("<!--wm.parse:invalid file_w--><img src='//upload.wikimedia.org/wikipedia/commons/thumb/7/70/A.png/220_fail_px-A.png'>"));
	}
}
