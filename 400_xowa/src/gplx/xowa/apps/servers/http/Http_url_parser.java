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
	public byte[] Qarg() {return qarg;} public Http_url_parser Qarg_(String v) {this.qarg = Bry_.new_u8(v); return this;} private byte[] qarg;
	public byte Action() {return action;} public Http_url_parser Action_(byte v) {this.action = v; return this;} private byte action;
	public String Popup_mode() {return popup_mode;} public Http_url_parser Popup_mode_(String v) {this.popup_mode = v; return this;} private String popup_mode;
	public boolean Popup() {return popup;} public Http_url_parser Popup_(boolean v) {this.popup = v; return this;} private boolean popup;
	public String Popup_id() {return popup_id;} public Http_url_parser Popup_id_(String v) {this.popup_id = v; return this;} private String popup_id;
	public String Err_msg() {return err_msg;} public Http_url_parser Err_msg_(String v) {this.err_msg = v; return this;} private String err_msg;

	public String To_str() {
		Bry_bfr bfr = Bry_bfr_.New();
		bfr.Add_str_a7("wiki=").Add_safe(wiki).Add_byte_nl();
		bfr.Add_str_a7("page=").Add_safe(page).Add_byte_nl();
		bfr.Add_str_a7("qarg=").Add_safe(qarg).Add_byte_nl();
		bfr.Add_str_a7("action=").Add_byte_variable(action).Add_byte_nl();
		bfr.Add_str_a7("popup=").Add_yn(popup).Add_byte_nl();
		if (popup_id != null)
			bfr.Add_str_a7("popup_id=").Add_str_u8(popup_id).Add_byte_nl();
		if (popup_mode != null)
			bfr.Add_str_a7("popup_mode=").Add_str_u8(popup_mode).Add_byte_nl();
		bfr.Add_str_a7("err_msg=").Add_str_u8_null(err_msg).Add_byte_nl();
		return bfr.To_str_and_clear();
	}

	// Parse urls of form "/wiki_name/wiki/Page_name?action=val"
	public boolean Parse(byte[] url) {
		try {
			// validate
			if (url == null) return Fail(null, "invalid url; url is null");
			int url_len = url.length;
			if (url_len == 0) return Fail(null, "invalid url; url is empty");
			if (url[0] != Byte_ascii.Slash) return Fail(url, "invalid url; must start with '/'");

			// parse
			Gfo_url_parser url_parser = new Gfo_url_parser();
			Gfo_url url_obj = url_parser.Parse(url);
			byte[][] segs = url_obj.Segs();
			int segs_len = segs.length;

			// get wiki
			if (segs_len == 0) return Fail(url, "invalid url; no wiki");
			this.wiki = segs[0];

			// get page
			// only page; EX: wiki_name/wiki/Page
			if (segs_len == 3) {
				this.page = segs[2];
			}
			// page and subs; EX: wiki_name/wiki/Page/A/B/C
			else if (segs_len > 3) {
				Bry_bfr bfr = Bry_bfr_.New();
				for (int i = 2; i < segs_len; i++) {
					if (i != 2) bfr.Add_byte_slash();
					bfr.Add(segs[i]);
				}
				this.page = bfr.To_bry_and_clear();
			}

			// get qargs
			Gfo_qarg_itm[] qargs = url_obj.Qargs();
			int qargs_len = qargs.length;
			Bry_bfr qarg_bfr = Bry_bfr_.New();
			for (int i = 0; i < qargs_len; i++) {
				Gfo_qarg_itm qarg_itm = qargs[i];
				byte[] qarg_key = qarg_itm.Key_bry();
				byte[] qarg_val = qarg_itm.Val_bry();
				int qarg_tid = qarg_keys.Get_as_int_or(qarg_key, Byte_.Max_value_127);

				switch (qarg_tid) {
					case Tid__action:
						if      (Bry_.Eq(qarg_val, Xoa_url_.Qarg__action__read))
							this.action = Xopg_view_mode_.Tid__read;
						else if (Bry_.Eq(qarg_val, Xoa_url_.Qarg__action__edit))
							this.action = Xopg_view_mode_.Tid__edit;
						else if (Bry_.Eq(qarg_val, Xoa_url_.Qarg__action__html))
							this.action = Xopg_view_mode_.Tid__html;
						else if (Bry_.Eq(qarg_val, Qarg__action__popup))
							this.popup = true;
						break;
					case Tid__popup_id:
						this.popup_id = String_.new_u8(qarg_val);
						break;
					case Tid__popup_mode:
						this.popup_mode = String_.new_u8(qarg_val);
						break;
					default:
						qarg_bfr.Add_byte(qarg_bfr.Len_eq_0() ? Byte_ascii.Question : Byte_ascii.Amp);
						qarg_bfr.Add(qarg_key);
						qarg_bfr.Add_byte_eq();
						qarg_bfr.Add(qarg_val);
						break;
				}
			}
			if (qarg_bfr.Len_gt_0())
				qarg = qarg_bfr.To_bry_and_clear();
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
	private static final byte
	  Tid__action = 1
	, Tid__popup_id = 2
	, Tid__popup_mode = 3
	;
	private static final    byte[]
	  Qarg__action__popup = Bry_.new_a7("popup")
	, Qarg__popup_id      = Bry_.new_a7("popup_id")
	, Qarg__popup_mode    = Bry_.new_a7("popup_mode")
	;
	private static final    Hash_adp_bry qarg_keys = Hash_adp_bry.ci_a7()
		.Add_bry_int(Xoa_url_.Qarg__action, Tid__action)
		.Add_bry_int(Qarg__popup_id       , Tid__popup_id)
		.Add_bry_int(Qarg__popup_mode     , Tid__popup_mode)
	;
}
