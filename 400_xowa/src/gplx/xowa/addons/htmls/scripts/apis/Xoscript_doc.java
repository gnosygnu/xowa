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
	public Xoscript_doc(Bry_bfr bfr, Xoscript_page page) {
		this.bfr = bfr;
		this.page = page;
		this.head = new Xoscript_doc_head(this);
		this.tail = new Xoscript_doc_tail(this);
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
	public boolean Dirty() {
		return html_dirty;
	}	private boolean html_dirty;
}
