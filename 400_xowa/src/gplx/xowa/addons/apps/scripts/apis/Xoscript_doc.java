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
package gplx.xowa.addons.apps.scripts.apis; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.scripts.*;
public class Xoscript_doc {
	private final    Bry_bfr bfr;
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	public Xoscript_doc(Bry_bfr bfr, Xoscript_page page_var) {
		this.bfr = bfr;
		this.page_var = page_var;
		this.head_var = new Xoscript_doc_head(this);
		this.tail_var = new Xoscript_doc_tail(this);

		head_var.reg_marker("<!--XOWA.SCRIPT.HEAD.TOP-->", "top");
		head_var.reg_marker("<!--XOWA.SCRIPT.HEAD.BOT-->", "bot", Xoscript_doc_sect_base.Pos__default);
		tail_var.reg_marker("<!--XOWA.SCRIPT.TAIL.TOP-->", "top", Xoscript_doc_sect_base.Pos__default);
	}
	public Xoscript_page page() {return page_var;} private final    Xoscript_page page_var;
	public Xoscript_doc_head head() {return head_var;} private final    Xoscript_doc_head head_var;
	public Xoscript_doc_tail tail() {return tail_var;} private final    Xoscript_doc_tail tail_var;
	public String html() {
		if (html_var == null) {
			html_var = bfr.To_str();
		}
		return html_var;
	}	private String html_var;
	public void html(String s) {
		html_var = s;
		html_dirty = true;
	}
	public void html_by_marker(byte[] marker, byte[] marker_html) {
		byte[] html_bry = Bry_.new_u8(this.html());

		// find marker, and splice it in
		int marker_pos = Bry_find_.Find_fwd(html_bry, marker);
		if (marker_pos != Bry_find_.Not_found) {
			tmp_bfr.Add_mid(html_bry, 0, marker_pos);
			tmp_bfr.Add(marker_html);
			tmp_bfr.Add_mid(html_bry, marker_pos, html_bry.length);
			html_bry = tmp_bfr.To_bry_and_clear();
		}
		this.html(String_.new_u8(html_bry));
	}
	public boolean dirty() {
		return html_dirty;
	}	private boolean html_dirty;
}
