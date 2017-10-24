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
package gplx.xowa.wikis.domains; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.xowa.langs.*;
public class Xow_domain_itm {
	Xow_domain_itm(byte[] domain_bry, Xow_domain_tid domain_type, Xol_lang_stub lang_actl_itm, byte[] lang_orig_key) {
		this.domain_bry = domain_bry; this.domain_type = domain_type; this.lang_actl_itm = lang_actl_itm; this.lang_orig_key = lang_orig_key;
		this.domain_str = String_.new_u8(domain_bry);
		this.abrv_wm = Xow_abrv_wm_.To_abrv(this);
		this.abrv_xo = Xow_abrv_xo_.To_bry(domain_bry, lang_orig_key, domain_type);
		this.abrv_xo_str = String_.new_u8(abrv_xo);
	}
	public byte[]			Domain_bry() {return domain_bry;} private final    byte[] domain_bry;
	public String			Domain_str() {return domain_str;} private final    String domain_str;
	public Xow_domain_tid	Domain_type() {return domain_type;} private final    Xow_domain_tid domain_type;
	public int				Domain_type_id() {return domain_type.Tid();}
	public byte[]			Abrv_wm() {return abrv_wm;} private final    byte[] abrv_wm;							// EX: enwiki
	public byte[]			Abrv_xo() {return abrv_xo;} private final    byte[] abrv_xo;							// EX: en.w
	public String			Abrv_xo_str() {return abrv_xo_str;} private final    String abrv_xo_str;				// EX: en.w
	public Xol_lang_stub	Lang_actl_itm() {return lang_actl_itm;} private final    Xol_lang_stub lang_actl_itm;	// EX: zh
	public int				Lang_actl_uid() {return lang_actl_itm.Id();}
	public byte[]			Lang_actl_key() {return lang_actl_itm.Key();}
	public byte[]			Lang_orig_key() {return lang_orig_key;} private final    byte[] lang_orig_key;			// EX: lzh
	public int				Sort_idx() {return sort_idx;} public void Sort_idx_(int v) {sort_idx = v;} private int sort_idx = -1;	// used for Search
	public static Xow_domain_itm new_(byte[] domain_bry, int domain_tid, byte[] lang_key) {
		Xol_lang_stub lang_actl_itm = Xol_lang_stub_.Get_by_key_or_intl(lang_key);
		return new Xow_domain_itm(domain_bry, Xow_domain_tid_.Get_type_as_itm(domain_tid), lang_actl_itm, lang_actl_itm.Key());
	}
	public static Xow_domain_itm new_(byte[] domain_bry, int domain_tid, Xol_lang_stub lang_actl, byte[] lang_orig) {
		return new Xow_domain_itm(domain_bry, Xow_domain_tid_.Get_type_as_itm(domain_tid), lang_actl, lang_orig);
	}
	public static Xow_domain_itm new_(byte[] domain_bry, int domain_tid, byte[] lang_actl_key, byte[] lang_orig_key) {
		return new Xow_domain_itm(domain_bry, Xow_domain_tid_.Get_type_as_itm(domain_tid), Xol_lang_stub_.Get_by_key_or_intl(lang_actl_key), lang_orig_key);
	}
}
