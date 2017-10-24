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
package gplx.xowa; import gplx.*;
import gplx.core.net.*; import gplx.core.net.qargs.*; import gplx.xowa.apps.urls.*;
import gplx.xowa.htmls.hrefs.*;
public class Xoa_url {
	public int				Tid() {return tid;} public void Tid_(int v) {this.tid = v;} private int tid;
	public byte[]			Raw() {return raw;} private byte[] raw = Bry_.Empty;
	public byte[]			Orig() {return orig;} private byte[] orig;
	public byte[]			Wiki_bry() {return wiki_bry;} public Xoa_url Wiki_bry_(byte[] v) {wiki_bry = v; return this;} private byte[] wiki_bry;
	public byte[]			Page_bry() {return page_bry;} public Xoa_url Page_bry_(byte[] v) {page_bry = v; return this;} private byte[] page_bry;
	public byte[]			Anch_bry() {return anch_bry;} public Xoa_url Anch_bry_(byte[] v) {anch_bry = v; return this;} private byte[] anch_bry;
	public String			Anch_str() {return anch_bry == null ? null : String_.new_u8(anch_bry);}
	public byte[][]			Segs_ary() {return segs_ary;} private byte[][] segs_ary;
	public Gfo_qarg_itm[]	Qargs_ary() {return qargs_ary;} public Xoa_url Qargs_ary_(Gfo_qarg_itm[] v) {qargs_ary = v; return this;} private Gfo_qarg_itm[] qargs_ary = Gfo_qarg_itm.Ary_empty;
	public Gfo_qarg_mgr_old	Qargs_mgr() {if (qargs_mgr == null) qargs_mgr = new Gfo_qarg_mgr_old().Load(qargs_ary); return qargs_mgr;} private Gfo_qarg_mgr_old qargs_mgr;
	public byte				Protocol_tid() {return protocol_tid;} private byte protocol_tid;
	public byte[]			Protocol_bry() {return protocol_bry;} private byte[] protocol_bry;
	public boolean				Protocol_is_relative() {return protocol_is_relative;} private boolean protocol_is_relative;
	public byte[]			Vnt_bry() {return vnt_bry;} private byte[] vnt_bry;
	public boolean				Wiki_is_missing() {return wiki_is_missing;} private boolean wiki_is_missing;
	public boolean				Wiki_is_same() {return wiki_is_same;} private boolean wiki_is_same;
	public boolean				Page_is_main() {return page_is_main;} private boolean page_is_main;
	public Xoa_url Ctor(int tid, byte[] orig, byte[] raw, byte protocol_tid, byte[] protocol_bry, boolean protocol_is_relative
		, byte[] wiki, byte[] page, Gfo_qarg_itm[] qargs,  byte[] anch
		, byte[][] segs_ary, byte[] vnt_bry, boolean wiki_is_missing, boolean wiki_is_same, boolean page_is_main) {
		this.tid = tid; this.orig = orig; this.raw = raw;
		this.protocol_tid = protocol_tid; this.protocol_bry = protocol_bry; this.protocol_is_relative = protocol_is_relative;
		this.wiki_bry = wiki; this.page_bry = page; this.qargs_ary = qargs; this.anch_bry = anch;
		this.segs_ary = segs_ary; this.vnt_bry = vnt_bry;
		this.wiki_is_missing = wiki_is_missing; this.wiki_is_same = wiki_is_same; this.page_is_main = page_is_main;
		return this;
	}
	public byte[] Page_for_lnki() {
		int raw_len = raw.length;
		int page_bgn = Page_bgn(raw_len);
		if (page_bgn == Bry_find_.Not_found)	// no /wiki/ found; return page
			return page_bry == null ? Bry_.Empty : page_bry;	// guard against null ref
		else
			return Bry_.Mid(raw, page_bgn, raw_len);// else take everything after "/wiki/";
	}
	private int Page_bgn(int raw_len) {
		int wiki_pos = Bry_find_.Find_fwd(raw, Xoh_href_.Bry__wiki, 0, raw_len);	 // look for /wiki/
		return wiki_pos == Bry_find_.Not_found ? Bry_find_.Not_found : wiki_pos + Xoh_href_.Bry__wiki.length;
	}
	public boolean Eq_page(Xoa_url comp) {return Bry_.Eq(wiki_bry, comp.wiki_bry) && Bry_.Eq(page_bry, comp.page_bry) && this.Qargs_mgr().Match(Xoa_url_.Qarg__redirect, Xoa_url_.Qarg__redirect__no) == comp.Qargs_mgr().Match(Xoa_url_.Qarg__redirect, Xoa_url_.Qarg__redirect__no);}
	public String To_str()					{return String_.new_u8(To_bry(Bool_.Y, Bool_.Y));}
	public byte[] To_bry_page_w_anch()		{
		byte[] page = page_bry, anch = anch_bry;
		byte[] anch_spr = anch == null ? null : Byte_ascii.Hash_bry;
		return Bry_.Add(page, anch_spr, anch);
	}
	public byte[] To_bry_full_wo_qargs()	{return To_bry(Bool_.Y, Bool_.N);}
	public byte[] To_bry(boolean full, boolean show_qargs) {							// currently used for status bar; not embedded in any html
		switch (tid) {
			case Xoa_url_.Tid_unknown:											// unknown; should not occur?
				return Bry_.Len_eq_0(raw) ? Bry_.Add(wiki_bry, Xoh_href_.Bry__wiki, page_bry) : raw;	// raw is empty when using new_();
			case Xoa_url_.Tid_inet:												// protocol; embed all; EX: "http://a.org/A"; "file:///C/dir/file.txt"
			case Xoa_url_.Tid_file:												// file; EX: "file:///C:/A/B.jpg"
				return raw;
			case Xoa_url_.Tid_xcmd:												// xcmd; embed page only; EX: "xowa.usr.bookmarks.add"
				return page_bry;
			default:
				throw Err_.new_unhandled(tid);
			case Xoa_url_.Tid_anch:
			case Xoa_url_.Tid_page:
				break;
		}
		byte[] wiki = wiki_bry, page = page_bry, anch = anch_bry;
		byte[] wiki_spr = vnt_bry == null ? Xoh_href_.Bry__wiki : Bry_.Add(Byte_ascii.Slash_bry, vnt_bry, Byte_ascii.Slash_bry);
		byte[] anch_spr = anch == null ? null : Byte_ascii.Hash_bry;
		if (!full) {
			boolean tid_is_anch = tid == Xoa_url_.Tid_anch;
			if (	wiki_is_same										// same wiki; don't show wiki; EX: "/wiki/A" -> "A" x> "en.wikipedia.org/wiki/A"
				||	tid_is_anch) {										// anch never shows wiki;	EX: #A
				wiki = wiki_spr = null;									// don't show wiki;
			}
			if (tid_is_anch)
				page = null;
		}

		Bry_bfr bfr = Bry_bfr_.New();
		bfr.Add_safe(wiki).Add_safe(wiki_spr);							// add wiki_key;	EX: "en.wikipedia.org", "/wiki/"
		bfr.Add_safe(page);												// add page;		EX: "A"
		if (show_qargs || qargs_ary.length > 0) {
			Gfo_qarg_mgr_old.Concat_bfr(bfr, gplx.langs.htmls.encoders.Gfo_url_encoder_.Href, qargs_ary);
		}
		if (anch != null)												// NOTE: anch must go last (after qargs); DATE:2016-10-08
			bfr.Add(anch_spr).Add(anch);								// add anch			EX: "#", "B"
		return bfr.To_bry_and_clear();
	}
	public static final    Xoa_url Null = null;
	public static Xoa_url blank() {return new Xoa_url();}
	public static Xoa_url New(Xow_wiki wiki, Xoa_ttl ttl) {return New(wiki.Domain_bry(), ttl.Full_txt_w_ttl_case());}
	public static Xoa_url New(byte[] wiki, byte[] page) {
		Xoa_url rv = new Xoa_url();
		rv.Wiki_bry_(wiki);
		rv.Page_bry_(page);
		rv.tid = Xoa_url_.Tid_page;
		return rv;
	}	Xoa_url() {}
}
