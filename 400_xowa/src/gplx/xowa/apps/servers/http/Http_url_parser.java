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
package gplx.xowa.apps.servers.http;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.*;
import gplx.core.net.*; import gplx.core.net.qargs.*;
import gplx.xowa.wikis.pages.*;
class Http_url_parser {
	public byte[] Wiki() {return wiki;} public Http_url_parser Wiki_(String v) {this.wiki = BryUtl.NewU8(v); return this;} private byte[] wiki;
	public byte[] Page() {return page;} public Http_url_parser Page_(String v) {this.page = BryUtl.NewU8(v); return this;} private byte[] page;
	public byte[] Qarg() {return qarg;} public Http_url_parser Qarg_(String v) {this.qarg = BryUtl.NewU8(v); return this;} private byte[] qarg;
	public byte Action() {return action;} public Http_url_parser Action_(byte v) {this.action = v; return this;} private byte action;
	public String Popup_mode() {return popup_mode;} public Http_url_parser Popup_mode_(String v) {this.popup_mode = v; return this;} private String popup_mode;
	public boolean Popup() {return popup;} public Http_url_parser Popup_(boolean v) {this.popup = v; return this;} private boolean popup;
	public String Popup_id() {return popup_id;} public Http_url_parser Popup_id_(String v) {this.popup_id = v; return this;} private String popup_id;
	public String Err_msg() {return err_msg;} public Http_url_parser Err_msg_(String v) {this.err_msg = v; return this;} private String err_msg;

	public String To_str() {
		BryWtr bfr = BryWtr.New();
		bfr.AddStrA7("wiki=").AddSafe(wiki).AddByteNl();
		bfr.AddStrA7("page=").AddSafe(page).AddByteNl();
		bfr.AddStrA7("qarg=").AddSafe(qarg).AddByteNl();
		bfr.AddStrA7("action=").AddByteVariable(action).AddByteNl();
		bfr.AddStrA7("popup=").AddYn(popup).AddByteNl();
		if (popup_id != null)
			bfr.AddStrA7("popup_id=").AddStrU8(popup_id).AddByteNl();
		if (popup_mode != null)
			bfr.AddStrA7("popup_mode=").AddStrU8(popup_mode).AddByteNl();
		bfr.AddStrA7("err_msg=").AddStrU8Null(err_msg).AddByteNl();
		return bfr.ToStrAndClear();
	}

	// Parse urls of form "/wiki_name/wiki/Page_name?action=val"
	public boolean Parse(byte[] url) {
		try {
			// validate
			if (url == null) return Fail(null, "invalid url; url is null");
			int url_len = url.length;
			if (url_len == 0) return Fail(null, "invalid url; url is empty");
			if (url[0] != AsciiByte.Slash) return Fail(url, "invalid url; must start with '/'");

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
				BryWtr bfr = BryWtr.New();
				for (int i = 2; i < segs_len; i++) {
					if (i != 2) bfr.AddByteSlash();
					bfr.Add(segs[i]);
				}
				this.page = bfr.ToBryAndClear();
			}

			// get qargs
			Gfo_qarg_itm[] qargs = url_obj.Qargs();
			int qargs_len = qargs.length;
			BryWtr qarg_bfr = BryWtr.New();
			for (int i = 0; i < qargs_len; i++) {
				Gfo_qarg_itm qarg_itm = qargs[i];
				byte[] qarg_key = qarg_itm.Key_bry();
				byte[] qarg_val = qarg_itm.Val_bry();
				int qarg_tid = qarg_keys.Get_as_int_or(qarg_key, ByteUtl.MaxValue127);

				switch (qarg_tid) {
					case Tid__action:
						if      (BryLni.Eq(qarg_val, Xoa_url_.Qarg__action__read))
							this.action = Xopg_view_mode_.Tid__read;
						else if (BryLni.Eq(qarg_val, Xoa_url_.Qarg__action__edit))
							this.action = Xopg_view_mode_.Tid__edit;
						else if (BryLni.Eq(qarg_val, Xoa_url_.Qarg__action__html))
							this.action = Xopg_view_mode_.Tid__html;
						else if (BryLni.Eq(qarg_val, Qarg__action__popup))
							this.popup = true;
						break;
					case Tid__popup_id:
						this.popup_id = StringUtl.NewU8(qarg_val);
						break;
					case Tid__popup_mode:
						this.popup_mode = StringUtl.NewU8(qarg_val);
						break;
					default:
						qarg_bfr.AddByte(qarg_bfr.HasNone() ? AsciiByte.Question : AsciiByte.Amp);
						qarg_bfr.Add(qarg_key);
						qarg_bfr.AddByteEq();
						qarg_bfr.Add(qarg_val);
						break;
				}
			}
			if (qarg_bfr.HasSome())
				qarg = qarg_bfr.ToBryAndClear();
			return true;
		}
		catch (Exception e) {
			this.err_msg = ErrUtl.ToStrLog(e);
			return false;
		}
	}
	private boolean Fail(byte[] url, String err_msg) {
		this.wiki = null;
		this.page = null;
		this.err_msg = err_msg;
		if (url != null)
			this.err_msg += "; url=" + StringUtl.NewU8(url);
		return false;
	}
	private static final byte
	  Tid__action = 1
	, Tid__popup_id = 2
	, Tid__popup_mode = 3
	;
	private static final byte[]
	  Qarg__action__popup = BryUtl.NewA7("popup")
	, Qarg__popup_id      = BryUtl.NewA7("popup_id")
	, Qarg__popup_mode    = BryUtl.NewA7("popup_mode")
	;
	private static final Hash_adp_bry qarg_keys = Hash_adp_bry.ci_a7()
		.Add_bry_int(Xoa_url_.Qarg__action, Tid__action)
		.Add_bry_int(Qarg__popup_id       , Tid__popup_id)
		.Add_bry_int(Qarg__popup_mode     , Tid__popup_mode)
	;
}
