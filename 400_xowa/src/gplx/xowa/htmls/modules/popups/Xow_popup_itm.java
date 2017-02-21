/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.htmls.modules.popups; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.modules.*;
public class Xow_popup_itm implements Cancelable {
	public Xow_popup_itm(int id, byte[] page_href, byte[] tooltip, int init_words_needed) {
		this.popup_id = gplx.xowa.apps.Xoa_thread_.Key_page_popup + Int_.To_str(id);
		this.words_needed = init_words_needed;
		this.page_href = page_href;
		this.tooltip = tooltip;
	}
	public boolean Canceled() {return canceled;} private boolean canceled = false;
	public void Cancel() {canceled = true;}
	public byte Mode() {return mode;} private byte mode = Mode_tid_init;
	public Xow_popup_itm Mode_more_(int more_words) {
		mode = Mode_tid_more;
		words_needed = words_found + more_words;
		return this;
	}
	public boolean Mode_all() {return mode == Mode_tid_all;}
	public Xow_popup_itm Mode_all_() {
		mode = Mode_tid_all;
		words_needed = Int_.Max_value;
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
