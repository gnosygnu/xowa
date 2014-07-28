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
package gplx.xowa.html.modules.popups; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.modules.*;
public class Xow_popup_itm implements Cancelable {
	public Xow_popup_itm(int id, byte[] page_href, byte[] tooltip, int init_words_needed) {
		this.popup_id = "popup_" + Int_.XtoStr(id);
		this.words_needed = init_words_needed;
		this.page_href = page_href;
		this.tooltip = tooltip;
	}
	public boolean Canceled() {return canceled;} private boolean canceled = false;
	public void Cancel() {canceled = true;}
	public void Cancel_reset() {canceled = false;}
	public byte Mode() {return mode;} private byte mode = Mode_tid_init;
	public Xow_popup_itm Mode_more_(int more_words) {
		mode = Mode_tid_more;
		words_needed = words_found + more_words;
		return this;
	}
	public boolean Mode_all() {return mode == Mode_tid_all;}
	public Xow_popup_itm Mode_all_() {
		mode = Mode_tid_all;
		words_needed = Int_.MaxValue;
		return this;
	}
	public String Popup_id() {return popup_id;} private String popup_id;
	public byte[] Popup_html() {return popup_html;} public void Popup_html_(byte[] v) {popup_html = v;} private byte[] popup_html;
	public byte[] Tooltip() {return tooltip;} private byte[] tooltip;
	public byte[] Wiki_domain() {return wiki_domain;} private byte[] wiki_domain;
	public byte[] Page_href() {return page_href;} private byte[] page_href;
	public Xoa_ttl Page_ttl() {return page_ttl;} private Xoa_ttl page_ttl;
	public void Init(byte[] wiki_domain, Xoa_ttl page_ttl) {this.wiki_domain = wiki_domain; this.page_ttl = page_ttl;}
	public int Words_needed() {return words_needed;} private int words_needed;
	public int Words_found() {return words_found;} public void Words_found_(int v) {words_found = v;} private int words_found;
	public static final byte Mode_tid_init = 0, Mode_tid_more = 1, Mode_tid_all = 2;
}
