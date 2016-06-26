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
package gplx.xowa.htmls.core.wkrs.imgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.xowa.htmls.core.makes.tests.*;
public class Xoh_img_html__dump__tst {
	private final    Xoh_make_fxt fxt = new Xoh_make_fxt();
	@Before public void init() {fxt.Clear();}
	@Test   public void Basic() {
		fxt.Test__html
		( "[[File:A.png|220x110px|upright=.5|abc]]"
		, "<a href='/wiki/File:A.png' class='image' title='abc' xowa_title='A.png'><img data-xowa-title=\"A.png\" data-xoimg='0|220|110|0.5|-1|-1' src='' width='0' height='0' alt='abc'/></a>");
	}
	@Test   public void Empty_link() {
		fxt.Test__html("[[File:A.png|220x110px|link=|abc]]", "<img data-xowa-title=\"A.png\" data-xoimg='0|220|110|-1|-1|-1' src='' width='0' height='0' alt='abc'/>");
	}
	@Test   public void Lcase_img() {
		Make_ns_case_sensitive(fxt.Parser_fxt().Wiki(), gplx.xowa.wikis.nss.Xow_ns_.Tid__file);
		fxt.Test__html("[[File:a_b.png]]", "<a href=\"/wiki/File:a_b.png\" class=\"image\" xowa_title=\"a_b.png\"><img data-xowa-title=\"a_b.png\" data-xoimg=\"0|-1|-1|-1|-1|-1\" src=\"\" width=\"0\" height=\"0\" alt=\"\"/></a>");
	}
	@Test   public void Lcase_video() {
		Make_ns_case_sensitive(fxt.Parser_fxt().Wiki(), gplx.xowa.wikis.nss.Xow_ns_.Tid__file);
		fxt.Test__html("[[File:a b.ogv]]"
		, String_.Concat_lines_nl_skip_last
		( "    <div class=\"xowa_media_div\">"
		, "      <div><a href=\"/wiki/File:a_b.ogv\" class=\"image\" title=\"A_b.ogv\" xowa_title=\"a_b.ogv\"><img data-xowa-title=\"a_b.ogv\" data-xoimg=\"0|-1|-1|-1|-1|-1\" src=\"\" width=\"0\" height=\"0\" alt=\"\"/></a>"
		, "      </div>"
		, "<div><a href=\"\" xowa_title=\"a_b.ogv\" class=\"xowa_media_play\" style=\"width:218px;max-width:220px;\" alt=\"Play sound\"></a></div>"
		, "    </div>"
		)
		);
	}
	private static void Make_ns_case_sensitive(Xow_wiki wiki, int ns_id) {
		gplx.xowa.wikis.nss.Xow_ns_mgr ns_mgr = wiki.Ns_mgr();
		gplx.xowa.wikis.nss.Xow_ns ns = ns_mgr.Ids_get_or_null(ns_id);
		ns.Case_match_(gplx.xowa.wikis.nss.Xow_ns_case_.Tid__all);
		ns_mgr.Init();
	}
}
