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
package gplx.xowa.wikis.xwikis.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.xwikis.*;
import gplx.core.net.*;
import gplx.langs.dsvs.*;
import gplx.xowa.langs.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.xwikis.bldrs.*;
public class Xow_xwiki_itm_parser extends Dsv_wkr_base {
	private Xow_domain_itm owner_domain_itm;
	private int cur_tid = -1; private byte[] cur_fld1, cur_fld2, cur_fld3;
	private final    Gfo_url_parser url_parser = new Gfo_url_parser();
	public Ordered_hash Xwiki_list() {return xwiki_list;} private final    Ordered_hash xwiki_list = Ordered_hash_.New();
	@Override public Dsv_fld_parser[] Fld_parsers() {return new Dsv_fld_parser[] {Dsv_fld_parser_.Bry_parser, Dsv_fld_parser_.Bry_parser, Dsv_fld_parser_.Bry_parser, Dsv_fld_parser_.Bry_parser};}
	@Override public boolean Write_bry(Dsv_tbl_parser parser, int fld_idx, byte[] src, int bgn, int end) {
		switch (fld_idx) {
			case 0: cur_tid		= Bry_.To_int_or(src, bgn, end, -1); return true;
			case 1: cur_fld1	= Bry_.Mid(src, bgn, end); return true;
			case 2: cur_fld2	= Bry_.Mid(src, bgn, end); return true;
			case 3: cur_fld3	= Bry_.Mid(src, bgn, end); return true;
			default: return false;
		}
	}
	public Xow_xwiki_itm_parser Init_by_wiki(Xow_domain_itm owner_domain_itm) {this.owner_domain_itm = owner_domain_itm; return this;}
	@Override public void Load_by_bry_bgn() {xwiki_list.Clear();}
	@Override public void Commit_itm(Dsv_tbl_parser parser, int pos) {
		byte[][] key_ary = Bry_split_.Split(cur_fld1, Byte_ascii.Semic);	// allow multiple key defs; EX: "w;wikipedia"
		boolean xwiki_is_mw = true;
		byte[] domain_name = cur_fld3;	// NOTE: by happenstance, domain_name is always cur_fld3
		byte[] url_fmt = null, domain_bry = null;
		switch (cur_tid) {
			case Tid__manual:			// EX: "0|domz|http://www.dmoz.org/~{0}|DMOZ"
				xwiki_is_mw = false;
				url_fmt = cur_fld2;
				domain_bry = Xow_xwiki_mgr.Get_domain_from_url(url_parser, url_fmt);
				break;
			case Tid__mw_domain:		// EX: "1|w|en.wikipedia.org"
				domain_bry = cur_fld2;
				break;
			case Tid__wm_peer:			// EX: "2|wikt|wikipedia"
				domain_bry = Bry_.Add(owner_domain_itm.Lang_actl_key(), Byte_ascii.Dot_bry, cur_fld2, gplx.xowa.apps.urls.Xow_url_parser.Bry_dot_org);
				break;
			case Tid__wm_lang:			// EX: "3|en;english|en|English"
				domain_bry = Bry_.Add(cur_fld2, Byte_ascii.Dot_bry, owner_domain_itm.Domain_type().Key_bry(), gplx.xowa.apps.urls.Xow_url_parser.Bry_dot_org);
				break;
			default:		throw Err_.new_unhandled(cur_tid);
		}			
		byte[] abrv_wm = null;
		int lang_id = Xol_lang_stub_.Id__unknown, domain_tid = Xow_domain_tid_.Tid__other;
		if (xwiki_is_mw) {
			url_fmt = Xow_xwiki_mgr.Bld_url_fmt(domain_bry);
			Xow_domain_itm domain_itm = Xow_domain_itm_.parse(domain_bry);
			if (Bry_.Len_eq_0(domain_name)) {	// no name; build default
				Xol_lang_stub stub_itm = Xol_lang_stub_.Get_by_key_or_null(domain_itm.Lang_actl_itm().Key());
				byte[] lang_name = stub_itm == null ? Bry_.Empty : stub_itm.Canonical_name();
				domain_name = Bry_.Add_w_dlm(Byte_ascii.Space, lang_name, domain_itm.Domain_type().Display_bry());
			}
			abrv_wm = domain_itm.Abrv_wm();
			lang_id = domain_itm.Lang_actl_uid();
			domain_tid = domain_itm.Domain_type_id();
		}			
		Create_xwikis(key_ary, url_fmt, lang_id, domain_tid, domain_bry, domain_name, abrv_wm);
		cur_tid = -1;
		cur_fld1 = cur_fld2 = cur_fld3 = null;
	}
	private void Create_xwikis(byte[][] key_ary, byte[] url_fmt, int lang_id, int domain_tid, byte[] domain_bry, byte[] domain_name, byte[] abrv_wm) {
		for (byte[] key : key_ary) {
			Xow_xwiki_itm itm = Xow_xwiki_itm_bldr.Instance.Bld_xo(owner_domain_itm, key, url_fmt, domain_name);
			xwiki_list.Add(key, itm);
		}
	}
	public static final int 
	  Tid__manual		= 0
	, Tid__mw_domain	= 1
	, Tid__wm_peer		= 2
	, Tid__wm_lang		= 3
	;
}
