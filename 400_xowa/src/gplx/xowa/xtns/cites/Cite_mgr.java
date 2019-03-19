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
package gplx.xowa.xtns.cites; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*;
import gplx.langs.htmls.*;
class Cite_mgr { // REF.MW:/extensions/Cite/includes/Cite.php
	private final    Xowe_wiki wiki;
	private final    Hash_adp_bry messages_by_group = Hash_adp_bry.cs();
	private Cite_xtn_data xtn_data;
	private Cite_link_label_mgr link_label_mgr;
	public Cite_mgr(Xowe_wiki wiki) {
		this.wiki = wiki;
	}
	public byte[] getLinkLabel(int offset, byte[] group) {
		// get xtn_data; NOTE: should go in Init_by_wiki, but Init_by_wiki doesn't get called by tests
		if (xtn_data == null) {
			this.xtn_data = Cite_xtn_data.Get_or_make(wiki.Parser_mgr().Data_store());
			this.link_label_mgr = xtn_data.Link_labels();
		}

		// get message; use cache to avoid multiple concantenations; EX: "cite_link_label_group-" + "lower-roman";
		byte[] message = (byte[])messages_by_group.Get_by_bry(group);
		if (message == null) {
			message = Bry_.Add(Msg__cite_link_label_group, group);
			messages_by_group.Add(group, message);
		}

		// get linkLabels or gen
		Cite_link_label_grp linkLabels = link_label_mgr.Get_or_null(group);
		if (linkLabels == null) {
			linkLabels = this.genLinkLabels(group, message);
		}

		// if linkLabels group missing, just concat; EX: "custom-group 1"
		if (linkLabels.Len() == 0) {
			return Bry_.Add((group.length == 0 ? Bry_.Empty : Bry_.Add(group, Byte_ascii.Space)), wiki.Lang().Num_mgr().Format_num(offset));  
		}

		// linkLabels group exists; pull corresponding offset; EX: "5" in lower-roman -> "v"
		byte[] rv = linkLabels.Get_or_null(offset - 1);
		return rv == null
			? this.plainError(Msg__cite_error_no_link_label_group, group, message)
			: rv;
	}
	private Cite_link_label_grp genLinkLabels(byte[] group, byte[] message) {
		Xol_msg_itm msg = wiki.Msg_mgr().Find_or_null(message);
		byte[][] text = msg == null
			? Bry_.Ary_empty
			: Bry_split_.Split_ws(msg.Val());

		Cite_link_label_grp grp = new Cite_link_label_grp(group, text);
		link_label_mgr.Add(group, grp);
		return grp;
	}
	private byte[] plainError(byte[] key, Object... ary) {
		Bry_bfr tmp_bfr = Bry_bfr_.New();
		Bry_fmtr tmp_fmtr = Bry_fmtr.New__tmp();

		// build specific error msg
		Xol_msg_itm msg_itm = wiki.Msg_mgr().Find_or_null(key);
		byte[] msg = (msg_itm == null) ? key : msg_itm.Fmt(tmp_bfr, tmp_fmtr, ary);

		// wrap specific error message with "Cite error: "
		msg_itm = wiki.Msg_mgr().Find_or_null(Msg__cite_error);
		if (msg_itm != null)
			msg = msg_itm.Fmt(tmp_bfr, tmp_fmtr, msg);

		// generate tag
		Xol_lang_itm lang = wiki.Lang();
		byte[] ret = Gfh_html_.rawElement
			( tmp_bfr, Gfh_tag_.Id__span
			, msg
			, Gfh_atr_itm.New(Gfh_atr_.Bry__class, "error mw-ext-cite-error")
			, Gfh_atr_itm.New(Gfh_atr_.Bry__lang , lang.Key_bry())
			, Gfh_atr_itm.New(Gfh_atr_.Bry__dir  , lang.Dir_ltr_bry())
			);
		return ret;
	}
	public static final    byte[]
	  Msg__cite_link_label_group = Bry_.new_a7("cite_link_label_group-")
	, Msg__cite_error = Bry_.new_a7("cite_error")
	, Msg__cite_error_no_link_label_group = Bry_.new_a7("cite_error_no_link_label_group")
	;
}
