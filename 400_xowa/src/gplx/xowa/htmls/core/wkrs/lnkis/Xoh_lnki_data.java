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
package gplx.xowa.htmls.core.wkrs.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*; import gplx.core.btries.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*; import gplx.xowa.htmls.hrefs.*;import gplx.xowa.htmls.core.wkrs.lnkis.anchs.*;
import gplx.xowa.wikis.ttls.*; import gplx.xowa.wikis.nss.*;
public class Xoh_lnki_data {
	private byte[] src;
	private int href_ns_id; private byte[] href_ns_name; private int href_ns_name_len;
	private byte[] capt_src; private int capt_bgn, capt_end;
	private final    Bry_rdr rdr = new Bry_rdr();
	public int			Src_bgn() {return src_bgn;} private int src_bgn;
	public int			Src_end() {return src_end;} private int src_end;
	public boolean			Capt_has_ns() {return capt_has_ns;} private boolean capt_has_ns;
	public byte			Text_tid() {return text_tid;} private byte text_tid;
	public byte[]		Text_0_src() {return text_0_src;} private byte[] text_0_src;
	public int			Text_0_bgn() {return text_0_bgn;} private int text_0_bgn;
	public int			Text_0_end() {return text_0_end;} private int text_0_end;
	public byte[]		Text_1_src() {return text_1_src;} private byte[] text_1_src;
	public int			Text_1_bgn() {return text_1_bgn;} private int text_1_bgn;
	public int			Text_1_end() {return text_1_end;} private int text_1_end;
	public byte[]		Href_src() {return href_src;} private byte[] href_src;
	public int			Href_bgn() {return href_bgn;} private int href_bgn;
	public int			Href_end() {return href_end;} private int href_end;
	public boolean			Title_missing_ns() {return title_missing_ns;} private boolean title_missing_ns;
	public int			Title_tid() {return title_tid;} private int title_tid;
	public int			Title_bgn() {return title_bgn;} private int title_bgn;
	public int			Title_end() {return title_end;} private int title_end;
	public byte			Cls_tid() {return cls_tid;} private byte cls_tid;
	public Xoh_anch_href_data Href_itm() {return href_itm;} private final    Xoh_anch_href_data href_itm = new Xoh_anch_href_data();
	public Xoh_anch_capt_itm Capt_itm() {return capt_itm;} private final    Xoh_anch_capt_itm capt_itm = new Xoh_anch_capt_itm();
	private void Init(byte[] src) {
		this.src = href_src = capt_src = src;
		capt_has_ns = title_missing_ns = false;
		href_ns_id = Xow_ns_.Tid__main; href_ns_name = null; href_ns_name_len = 0;
		href_bgn = href_end = capt_bgn = capt_end = title_bgn = title_end = -1;			
		title_tid = Title__href;
		this.cls_tid = Xoh_anch_cls_.Tid__none;
		href_itm.Clear();
	}
	public boolean Parse1(Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Gfh_tag_rdr tag_rdr, byte[] src, Gfh_tag anch_head) {
		Init(src);
		this.src_bgn = anch_head.Src_bgn();
		rdr.Init_by_wkr(tag_rdr.Err_wkr(), "lnki", src_bgn, src.length);
		Gfh_atr title_atr = anch_head.Atrs__get_by_or_empty(Gfh_atr_.Bry__title);
		Parse_href(hctx, anch_head);
		Parse_cls(anch_head);
		Parse_capt(tag_rdr, anch_head);
		Parse_title(title_atr);
		hdoc_wkr.On_lnki(this);
		return true;
	}
	private void Parse_href(Xoh_hdoc_ctx hctx, Gfh_tag anch_head) {
		href_itm.Parse(rdr.Err_wkr(), hctx, src, anch_head);
		this.href_bgn = href_itm.Ttl_bgn(); this.href_end = href_itm.Ttl_end();
		switch (href_itm.Tid()) {
			case Xoh_anch_href_data.Tid__wiki: case Xoh_anch_href_data.Tid__site:
				this.href_ns_id = href_itm.Ttl_ns_id();
				this.href_src = href_itm.Ttl_full_txt();
				this.href_bgn = 0;
				this.href_end = href_src.length;
				if (href_ns_id != Xow_ns_.Tid__main) {										// not main; try to remove template name;				
					int colon_pos = Bry_find_.Find_fwd(href_src, Byte_ascii.Colon, href_bgn, href_end);
					this.href_ns_name = Xoa_ttl.Replace_unders(Bry_.Mid(href_src, href_bgn, colon_pos + 1));		// EX: 11="Template talk:"
					this.href_ns_name_len = href_ns_name.length;
				}
				break;
		}
	}
	private void Parse_cls(Gfh_tag anch_head) {
		byte[] cls_bry = anch_head.Atrs__get_as_bry(Gfh_atr_.Bry__class); if (Bry_.Len_eq_0(cls_bry)) return;
		this.cls_tid = Xoh_anch_cls_.Trie.Match_byte_or(cls_bry, 0, cls_bry.length, Xoh_anch_cls_.Tid__unknown);
	}
	private void Parse_capt(Gfh_tag_rdr tag_rdr, Gfh_tag anch_head) {
		this.capt_bgn = anch_head.Src_end();										// capt starts after <a>
		Gfh_tag anch_tail = tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__a);				// </a>
		this.capt_end = anch_tail.Src_bgn();										// get capt between "<a>" and "</a>
		this.src_end = anch_tail.Src_end();

		// skip ns in href / capt
		if (href_ns_id != Xow_ns_.Tid__main) {										// not main; try to remove template name;				
			int capt_bgn_wo_ns = capt_bgn + href_ns_name_len;
			href_bgn += href_ns_name_len;											// skip ns_name for href; EX: "Help:A" -> "A"; "Help" will be saved as encoded number
			if (Bry_.Match(capt_src, capt_bgn, capt_bgn_wo_ns, href_ns_name)) { 	// capt matches ns_name; EX: <a href='/wiki/Help:A'>Help:A</a> -> "Help:A" matches "Help:"
				capt_bgn = capt_bgn_wo_ns;											// skip ns; "Help:"
				capt_has_ns = true;
			}
		}

		// get text splits
		this.text_tid	= href_itm.Tid() == Xoh_anch_href_data.Tid__anch 
						? Xoh_anch_capt_itm.Tid__diff
						: capt_itm.Parse(rdr, capt_has_ns, href_src, href_bgn, href_end, src, capt_bgn, capt_end);
		int split_pos = capt_itm.Split_pos();
		this.text_0_src = href_src; this.text_0_bgn = href_bgn; this.text_0_end = href_end;
		this.text_1_src = capt_src; this.text_1_bgn = capt_bgn; this.text_1_end = capt_end;
		switch (text_tid) {
			case Xoh_anch_capt_itm.Tid__same:
			// case Xoh_anch_capt_itm.Tid__href_pipe:
			case Xoh_anch_capt_itm.Tid__diff:		// nothing to do; href / capt already set above
				break;
			case Xoh_anch_capt_itm.Tid__more:
				this.text_1_bgn = split_pos;
				break;
			case Xoh_anch_capt_itm.Tid__less:
				this.text_0_end = split_pos;
				this.text_1_src = href_src;
				this.text_1_bgn = split_pos;
				this.text_1_end = href_end;
				break;
		}
	}
	private void Parse_title(Gfh_atr title_atr) {
		// Tfds.Dbg(Bry_.Mid(href_src, href_bgn, href_end), Bry_.Mid(src, capt_bgn, capt_end), Bry_.Mid(src, title_bgn, title_end));
		title_bgn = title_atr.Val_bgn(); title_end = title_atr.Val_end();
		if (href_ns_name != null) {	// ns_name exists
			int title_bgn_wo_ns = title_bgn + href_ns_name_len;
			if (Bry_.Match(src, title_bgn, title_bgn_wo_ns, href_ns_name))			// title matches href_ns;
				title_bgn = title_bgn_wo_ns;										// skip ns; "Help:"
			else
				title_missing_ns = true;
		}
		if (title_end == -1)
			title_tid = Title__missing;
		else {
			if		(Bry_.Match(src, title_bgn, title_end, href_src, href_bgn, href_end) && !title_missing_ns)	// NOTE: do not mark title=href if href omitted title; PAGE:en.b:Wikibooks:WikiProject; DATE:2016-01-20
				title_tid = Title__href;
			else if (Bry_.Match(src, title_bgn, title_end, src, capt_bgn, capt_end))
				title_tid = Title__capt;
			else {
				title_tid = Title__diff;
				if (href_ns_name != null) title_bgn = title_atr.Val_bgn();	// since title is different, add back ns_name; EX: "<a href='/wiki/Help:A_b#c' title='Help:A b'>a</a>"; title should be "Help:A b", not "A b"
			}
		}
	}
	public static final int // SERIALIAZED
	  Title__href			= 0
	, Title__capt			= 1
	, Title__diff			= 2
	, Title__missing		= 3
	;
}
