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
package gplx.xowa.addons.htmls.scripts.apis; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.htmls.*; import gplx.xowa.addons.htmls.scripts.*;
public class Xoscript_doc {
	private final    Bry_bfr bfr;
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	public Xoscript_doc(Bry_bfr bfr, Xoscript_page page) {
		this.bfr = bfr;
		this.page = page;
		this.head = new Xoscript_doc_head(this);
		this.tail = new Xoscript_doc_tail(this);

		head.Reg_marker("<!--XOWA.SCRIPT.HEAD.TOP-->", "top", Xoscript_doc_sect_base.Pos__default);
		head.Reg_marker("<!--XOWA.SCRIPT.HEAD.BOT-->", "bot");
		tail.Reg_marker("<!--XOWA.SCRIPT.TAIL.TOP-->", "top", Xoscript_doc_sect_base.Pos__default);
	}
	public Xoscript_page Page() {return page;} private final    Xoscript_page page;
	public Xoscript_doc_head Head() {return head;} private final    Xoscript_doc_head head;
	public Xoscript_doc_tail Tail() {return tail;} private final    Xoscript_doc_tail tail;
	public String Html() {
		if (html == null) {
			html = bfr.To_str();
		}
		return html;
	}	private String html;
	public void Html_(String s) {
		html = s;
		html_dirty = true;
	}
	public void Html_by_marker_(byte[] marker, byte[] marker_html) {
		byte[] html_bry = Bry_.new_u8(this.Html());

		// find marker, and splice it in
		int marker_pos = Bry_find_.Find_fwd(html_bry, marker);
		if (marker_pos != Bry_find_.Not_found) {
			tmp_bfr.Add_mid(html_bry, 0, marker_pos);
			tmp_bfr.Add(marker_html);
			tmp_bfr.Add_mid(html_bry, marker_pos, html_bry.length);
			html_bry = tmp_bfr.To_bry_and_clear();
		}
		Html_(String_.new_u8(html_bry));
	}
	public boolean Dirty() {
		return html_dirty;
	}	private boolean html_dirty;
}
