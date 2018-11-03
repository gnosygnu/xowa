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
package gplx.xowa.apps.servers.http; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.servers.*;
import gplx.langs.htmls.encoders.*;
import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.wikis.pages.*;
class Http_url_parser {
	private final    Gfo_url_encoder url_encoder;
	public Http_url_parser(Gfo_url_encoder url_encoder) {
		this.url_encoder = url_encoder;
	}
	public byte[] Wiki() {return wiki;} public Http_url_parser Wiki_(String v) {this.wiki = Bry_.new_u8(v); return this;} private byte[] wiki;
	public byte[] Page() {return page;} public Http_url_parser Page_(String v) {this.page = Bry_.new_u8(v); return this;} private byte[] page;
	public byte Action() {return action;} public Http_url_parser Action_(byte v) {this.action = v; return this;} private byte action;
	public String Err_msg() {return err_msg;} public Http_url_parser Err_msg_(String v) {this.err_msg = v; return this;} private String err_msg;

	public String To_str() {
		Bry_bfr bfr = Bry_bfr_.New();
		bfr.Add_str_a7("wiki=").Add_safe(wiki).Add_byte_nl();
		bfr.Add_str_a7("page=").Add_safe(page).Add_byte_nl();
		bfr.Add_str_a7("action=").Add_byte(action).Add_byte_nl();
		bfr.Add_str_a7("err_msg=").Add_str_u8_null(err_msg).Add_byte_nl();
		return bfr.To_str_and_clear();
	}

	// Parse urls of form "/wiki_name/wiki/Page_name?action=val"
	public boolean Parse(byte[] url) {
		try {
			// initial validations
			if (url == null) return Fail(url, "invalid url; url is null");
			int url_len = url.length;
			if (url_len == 0) return Fail(url, "invalid url; url is empty");
			if (url[0] != Byte_ascii.Slash) return Fail(url, "invalid url; must start with '/'");

			// get wiki
			int wiki_bgn = 1; // skip initial "/"
			int wiki_end = Bry_find_.Find_fwd_or(url, Byte_ascii.Slash, wiki_bgn, url_len, url_len);
			this.wiki = Bry_.Mid(url, wiki_bgn, wiki_end);
			if (wiki_end == url_len) {// no slash found; url is wiki-only; EX: "/en.wikipedia.org"
				return true;
			}

			// get page after "/wiki/"
			byte[] wiki_separator = Xoh_href_.Bry__wiki;
			int page_bgn = wiki_end + wiki_separator.length;
			if (!Bry_.Eq(url, wiki_end, wiki_end + wiki_separator.length, Xoh_href_.Bry__wiki)) 
				return Fail(url, "invalid url; must have '/wiki/' after wiki_domain");
			int page_end = url_len;

			// search for action arg
			this.action = Xopg_page_.Tid_read;
			int action_key_bgn = Bry_find_.Find_bwd(url, Qarg__action__frag, url_len);
			if (action_key_bgn != Bry_find_.Not_found) {
				int action_val_bgn = action_key_bgn + Qarg__action__frag.length;
				int action_val_end = url_len;
				boolean trim_page = true;
				if      (Bry_.Eq(url, action_val_bgn, action_val_end, Xoa_url_.Qarg__action__read))
					this.action = Xopg_page_.Tid_read;
				else if (Bry_.Eq(url, action_val_bgn, action_val_end, Xoa_url_.Qarg__action__edit))
					this.action = Xopg_page_.Tid_edit;
				else if (Bry_.Eq(url, action_val_bgn, action_val_end, Xoa_url_.Qarg__action__html))
					this.action = Xopg_page_.Tid_html;
				else
					trim_page = false;
				if (trim_page)
					page_end = action_key_bgn;
			}

			this.page = url_encoder.Decode(Bry_.Mid(url, page_bgn, page_end));
			return true;
		}
		catch (Exception e) {
			this.err_msg = Err_.Message_gplx_log(e);
			return false;
		}
	}
	private boolean Fail(byte[] url, String err_msg) {
		this.wiki = null;
		this.page = null;
		this.err_msg = err_msg + "; url=" + String_.new_u8(url);
		return false;
	}
	private static final    byte[]
	  Qarg__action__frag = Bry_.Add(Byte_ascii.Question_bry, Xoa_url_.Qarg__action, Byte_ascii.Eq_bry) // "?action="
	;
}
