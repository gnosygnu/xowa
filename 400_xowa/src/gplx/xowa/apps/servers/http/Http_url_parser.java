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
import gplx.core.net.*; import gplx.core.net.qargs.*;
import gplx.langs.htmls.encoders.*;
import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.wikis.pages.*;
class Http_url_parser {
	public byte[] Wiki() {return wiki;} public Http_url_parser Wiki_(String v) {this.wiki = Bry_.new_u8(v); return this;} private byte[] wiki;
	public byte[] Page() {return page;} public Http_url_parser Page_(String v) {this.page = Bry_.new_u8(v); return this;} private byte[] page;
	public byte Action() {return action;} public Http_url_parser Action_(byte v) {this.action = v; return this;} private byte action;
	public String Popup_mode() {return popup_mode;} public Http_url_parser Popup_mode_(String v) {this.popup_mode = v; return this;} private String popup_mode;
	public boolean Popup() {return popup;} public Http_url_parser Popup_(boolean v) {this.popup = v; return this;} private boolean popup;
	public String Popup_id() {return popup_id;} public Http_url_parser Popup_id_(String v) {this.popup_id = v; return this;} private String popup_id;
	public String Err_msg() {return err_msg;} public Http_url_parser Err_msg_(String v) {this.err_msg = v; return this;} private String err_msg;

	public String To_str() {
		Bry_bfr bfr = Bry_bfr_.New();
		bfr.Add_str_a7("wiki=").Add_safe(wiki).Add_byte_nl();
		bfr.Add_str_a7("page=").Add_safe(page).Add_byte_nl();
		bfr.Add_str_a7("action=").Add_byte_variable(action).Add_byte_nl();
		bfr.Add_str_a7("popup=").Add_yn(popup).Add_byte_nl();
		bfr.Add_str_a7("err_msg=").Add_str_u8_null(err_msg).Add_byte_nl();
		return bfr.To_str_and_clear();
	}

	// Parse urls of form "/wiki_name/wiki/Page_name?action=val"
	public boolean Parse(byte[] url) {
		try {
			// initial validations
			if (url == null) return Fail(null, "invalid url; url is null");
			int url_len = url.length;
			if (url_len == 0) return Fail(null, "invalid url; url is empty");
			if (url[0] != Byte_ascii.Slash) return Fail(url, "invalid url; must start with '/'");

			Gfo_url_parser url_parser = new Gfo_url_parser();
			Gfo_url url_obj = url_parser.Parse(url);
			this.wiki = url_obj.Segs()[0];

			int segs_len = url_obj.Segs().length;
			if (segs_len > 1) {
				byte[] x =  url_obj.Segs()[2];
				if (segs_len > 2) {
					Bry_bfr bfr = Bry_bfr_.New();
					for (int i = 2; i < segs_len; i++) {
						if (i != 2) bfr.Add_byte_slash();
						bfr.Add(url_obj.Segs()[i]);
					}
					x = bfr.To_bry_and_clear();
				}
				this.page = x;
			}

			Gfo_qarg_mgr qarg_mgr = new Gfo_qarg_mgr().Init(url_obj.Qargs());
			byte[] action_val = qarg_mgr.Read_bry_or("action", Bry_.Empty);
			if      (Bry_.Eq(action_val, Xoa_url_.Qarg__action__read))
				this.action = Xopg_view_mode_.Tid__read;
			else if (Bry_.Eq(action_val, Xoa_url_.Qarg__action__edit))
				this.action = Xopg_view_mode_.Tid__edit;
			else if (Bry_.Eq(action_val, Xoa_url_.Qarg__action__html))
				this.action = Xopg_view_mode_.Tid__html;
			else if (Bry_.Eq(action_val, Qarg__action__popup)) {
				this.popup = true;
				this.popup_id = qarg_mgr.Read_str_or_null(Bry_.new_a7("popup_id"));
				this.popup_mode = qarg_mgr.Read_str_or_null(Bry_.new_a7("popup_mode"));
			}
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
		this.err_msg = err_msg;
		if (url != null)
			this.err_msg += "; url=" + String_.new_u8(url);
		return false;
	}
	private static final    byte[]
	  Qarg__action__popup = Bry_.new_a7("popup")
	;
}
