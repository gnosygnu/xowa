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
	public Xow_popup_itm(int id, byte[] page_href, int init_words_needed) {
		this.popup_id = "popup_" + Int_.XtoStr(id);
		this.words_needed = init_words_needed;
		this.page_href = page_href;
	}
	public boolean Canceled() {return canceled;} private boolean canceled = false;
	public void Cancel() {canceled = true;}
	public void Cancel_reset() {canceled = false;}
	public byte Mode() {return mode;} private byte mode = Mode_init;
	public Xow_popup_itm Mode_more_(int more_words) {
		mode = Mode_more;
		words_needed = popup_html_word_count + more_words;
		return this;
	}
	public Xow_popup_itm Mode_all_() {
		mode = Mode_all;
		words_needed = Int_.MaxValue;
		return this;
	}
	public String Popup_id() {return popup_id;} private String popup_id;
	public byte[] Popup_html() {return popup_html;} public Xow_popup_itm Popup_html_(byte[] v) {popup_html = v; return this;} private byte[] popup_html;
	public byte[] Page_href() {return page_href;} private byte[] page_href;
	public int Popup_html_word_count() {return popup_html_word_count;} public void Popup_html_word_count_(int v) {popup_html_word_count = v;} private int popup_html_word_count;
	public int Words_needed() {return words_needed;} private int words_needed;
	public static final byte Mode_init = 0, Mode_more = 1, Mode_all = 2;
}
