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
package gplx.xowa.wikis.xwikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.net.*; import gplx.core.brys.fmtrs.*;
import gplx.xowa.langs.*; import gplx.xowa.wikis.domains.*;
public class Xow_xwiki_itm implements gplx.CompareAble {
	private final    boolean show_in_sitelangs_base;
	public Xow_xwiki_itm(byte[] key_bry, byte[] url_fmt, int lang_id, int domain_tid, byte[] domain_bry, byte[] domain_name, byte[] abrv_wm) {
		this.key_bry = key_bry; this.key_str = String_.new_u8(key_bry); 
		this.url_fmt = url_fmt; this.lang_id = lang_id;
		this.url_fmtr = Bry_.Len_eq_0(url_fmt) ? null : Bry_fmtr.new_(url_fmt, "0");
		this.domain_tid = domain_tid; this.domain_bry = domain_bry; this.domain_name = domain_name; this.abrv_wm = abrv_wm;
		this.show_in_sitelangs_base = Calc_show_in_sitelangs(key_bry, url_fmt, lang_id, domain_tid, domain_bry);
	}
	public byte[]	Key_bry() {return key_bry;} private final    byte[] key_bry;				// EX: commons
	public String	Key_str() {return key_str;} private final    String key_str;
	public byte[]	Url_fmt() {return url_fmt;} private final    byte[] url_fmt;				// EX: //commons.wikimedia.org/wiki/Category:$1
	public Bry_fmtr	Url_fmtr(){return url_fmtr;} private final    Bry_fmtr url_fmtr;
	public int		Lang_id() {return lang_id;} private final    int lang_id;					// EX: Id__unknown
	public int		Domain_tid() {return domain_tid;} private final    int domain_tid;			// EX: Tid_int_commons
	public byte[]	Domain_bry() {return domain_bry;} private final    byte[] domain_bry;		// EX: commons.wikimedia.org
	public byte[]	Domain_name() {return domain_name;} private final    byte[] domain_name;	// EX: Wikimedia Commons
	public byte[]	Abrv_wm() {return abrv_wm;} private final    byte[] abrv_wm;				// EX: enwiki; needed for sitelinks
	public boolean		Offline() {return offline;} public Xow_xwiki_itm Offline_(boolean v) {offline = v; return this;} private boolean offline;
	public int compareTo(Object obj) {Xow_xwiki_itm comp = (Xow_xwiki_itm)obj; return Bry_.Compare(key_bry, comp.key_bry);}
	public boolean Show_in_sitelangs(byte[] cur_lang_key) {
		return	show_in_sitelangs_base
			&&	!Bry_.Eq(key_bry, cur_lang_key) 			// lang is different than current; EX: [[en:A]] in en.wikipedia.org shouldn't link back to self
			;
	}
	public static Xow_xwiki_itm new_(byte[] key_bry, byte[] url_fmt, int lang_id, int domain_tid, byte[] domain_bry, byte[] abrv_wm) {
		return new Xow_xwiki_itm(key_bry, url_fmt, lang_id, domain_tid, domain_bry, domain_bry, abrv_wm);
	}
	private static boolean Calc_show_in_sitelangs(byte[] key_bry, byte[] url_fmt, int lang_id, int domain_tid, byte[] domain_bry) {
		key_bry = Xow_domain_itm_.Alt_domain__get_subdomain_by_lang(key_bry);	// handle "nb" as alias for "no.wikipedia.org"; PAGE: nn.w:; DATE:2015-12-04
		int key_len = key_bry.length;
		boolean key_matches_domain_bgn = Bry_.Match(domain_bry, 0, key_len, key_bry) && key_len + 1 < domain_bry.length && domain_bry[key_len] == Byte_ascii.Dot;	// key + . matches start of domain; EX: "en" and "en.wikipedia.org"
		return	lang_id != Xol_lang_stub_.Id__unknown		// valid lang code
			&&	domain_tid != Xow_domain_tid_.Tid__commons	// commons should never be considered an xwiki_lang; EX:[[commons:A]] PAGE:species:Scarabaeidae; DATE:2014-09-10
			&&	Bry_.Len_gt_0(url_fmt)						// url_fmt exists
			&&	key_matches_domain_bgn
			;
	}
	
}
