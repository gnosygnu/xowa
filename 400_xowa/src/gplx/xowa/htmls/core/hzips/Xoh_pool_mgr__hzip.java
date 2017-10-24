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
package gplx.xowa.htmls.core.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.core.btries.*; import gplx.core.threads.poolables.*;
import gplx.xowa.htmls.core.wkrs.escapes.*;
import gplx.xowa.htmls.core.wkrs.hdrs.*; import gplx.xowa.htmls.core.wkrs.lnkes.*; import gplx.xowa.htmls.core.wkrs.lnkis.*; import gplx.xowa.htmls.core.wkrs.xndes.*;
import gplx.xowa.htmls.core.wkrs.imgs.*; import gplx.xowa.htmls.core.wkrs.thms.*; import gplx.xowa.htmls.core.wkrs.glys.*; import gplx.xowa.htmls.core.wkrs.tocs.*;
import gplx.xowa.htmls.core.wkrs.addons.timelines.*; import gplx.xowa.htmls.core.wkrs.addons.gallerys.*; import gplx.xowa.htmls.core.wkrs.addons.medias.*;
public class Xoh_pool_mgr__hzip {
	private final    Btrie_slim_mgr trie = Btrie_slim_mgr.cs();
	private Gfo_poolable_mgr mkr__escape, mkr__xnde, mkr__lnke, mkr__lnki, mkr__hdr, mkr__img, mkr__thm, mkr__gly, mkr__img_bare, mkr__gallery, mkr__timeline, mkr__toc, mkr__media;
	public Xoh_escape_hzip		Mw__escape()		{return (Xoh_escape_hzip)		mkr__escape.Get_fast();}
	public Xoh_xnde_hzip		Mw__xnde()			{return (Xoh_xnde_hzip)			mkr__xnde.Get_fast();}
	public Xoh_hdr_hzip			Mw__hdr()			{return (Xoh_hdr_hzip)			mkr__hdr.Get_fast();}
	public Xoh_lnke_hzip		Mw__lnke()			{return (Xoh_lnke_hzip)			mkr__lnke.Get_fast();}
	public Xoh_lnki_hzip		Mw__lnki()			{return (Xoh_lnki_hzip)			mkr__lnki.Get_fast();}
	public Xoh_img_hzip			Mw__img()			{return (Xoh_img_hzip)			mkr__img.Get_fast();}
	public Xoh_img_bare_hzip	Mw__img_bare()		{return (Xoh_img_bare_hzip)		mkr__img_bare.Get_fast();}
	public Xoh_thm_hzip			Mw__thm()			{return (Xoh_thm_hzip)			mkr__thm.Get_fast();}
	public Xoh_gly_hzip			Mw__gly()			{return (Xoh_gly_hzip)			mkr__gly.Get_fast();}
	public Xoh_toc_hzip			Mw__toc()			{return (Xoh_toc_hzip)			mkr__toc.Get_fast();}
	public Xoh_gallery_hzip		Mw__gallery()		{return (Xoh_gallery_hzip)		mkr__gallery.Get_fast();}
	public Xoh_timeline_hzip	Mw__timeline()		{return (Xoh_timeline_hzip)		mkr__timeline.Get_fast();}
	public Xoh_media_hzip		Mw__media()			{return (Xoh_media_hzip)		mkr__media.Get_fast();}
	public void Init() {
		this.Reg_all(false, Hook__core, Hook__html, Hook__mw);
	}
	public Xoh_hzip_wkr Get(byte b, byte[] src, int src_bgn, int src_end) {
		Object mgr_obj = trie.Match_bgn_w_byte(b, src, src_bgn, src_end); if (mgr_obj == null) return null;
		Gfo_poolable_mgr mgr = (Gfo_poolable_mgr)mgr_obj;
		return (Xoh_hzip_wkr)mgr.Get_fast();
	}
	private void Reg_all(boolean mode_is_b256, int hook__core, int hook__html, int hook__mw) {	// SERIALIZED
		mkr__escape		= Reg(New_hook_len2(mode_is_b256, hook__core,  0)	, new Xoh_escape_hzip());
		mkr__xnde		= Reg(New_hook_len1(mode_is_b256, hook__html)		, new Xoh_xnde_hzip());
		mkr__hdr		= Reg(New_hook_len2(mode_is_b256, hook__mw	,  1)	, new Xoh_hdr_hzip());
		mkr__lnke		= Reg(New_hook_len2(mode_is_b256, hook__mw	,  2)	, new Xoh_lnke_hzip());
		mkr__lnki		= Reg(New_hook_len2(mode_is_b256, hook__mw	,  3)	, new Xoh_lnki_hzip());
		mkr__img		= Reg(New_hook_len2(mode_is_b256, hook__mw	,  4)	, new Xoh_img_hzip());
		mkr__thm		= Reg(New_hook_len2(mode_is_b256, hook__mw	,  5)	, new Xoh_thm_hzip());
		mkr__gly		= Reg(New_hook_len2(mode_is_b256, hook__mw	,  6)	, new Xoh_gly_hzip());
		mkr__img_bare	= Reg(New_hook_len2(mode_is_b256, hook__mw	,  7)	, new Xoh_img_bare_hzip());
		mkr__toc		= Reg(New_hook_len2(mode_is_b256, hook__mw	,  8)	, new Xoh_toc_hzip());
		mkr__media		= Reg(New_hook_len2(mode_is_b256, hook__mw  ,  9)   , new Xoh_media_hzip());
		mkr__gallery	= Reg(Xoh_gallery_data.Hook_bry						, new Xoh_gallery_hzip());
		mkr__timeline	= Reg(Xoh_timeline_data.Hook_bry					, new Xoh_timeline_hzip());
	}
	private Gfo_poolable_mgr Reg(byte[] hook, Gfo_poolable_itm proto) {
		Gfo_poolable_mgr rv = Gfo_poolable_mgr_.New(1, 32, proto, Object_.Ary(hook));
		trie.Add_obj(hook, rv);
		return rv;
	}
	private static byte[] New_hook_len2(boolean mode_is_b256, int b0, int b1)	{return Bry_.New_by_ints(b0, mode_is_b256 ? b1 : b1 + Byte_ascii.Bang);}
	private static byte[] New_hook_len1(boolean mode_is_b256, int b0)			{return Bry_.New_by_ints(b0);}
	public static final byte
	  Hook__core	= 1
	, Hook__html	= 2
	, Hook__mw		= 27
	;
	public static final    byte[] Hooks_ary = new byte[] {Hook__core, Hook__html, Hook__mw};
}
