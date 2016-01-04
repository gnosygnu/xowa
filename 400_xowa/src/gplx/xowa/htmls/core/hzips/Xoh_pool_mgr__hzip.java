/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.htmls.core.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.core.btries.*; import gplx.core.threads.poolables.*;
import gplx.xowa.htmls.core.wkrs.escapes.*;
import gplx.xowa.htmls.core.wkrs.hdrs.*; import gplx.xowa.htmls.core.wkrs.lnkes.*; import gplx.xowa.htmls.core.wkrs.lnkis.*; import gplx.xowa.htmls.core.wkrs.xndes.*;
import gplx.xowa.htmls.core.wkrs.imgs.*; import gplx.xowa.htmls.core.wkrs.thms.*; import gplx.xowa.htmls.core.wkrs.glys.*;
public class Xoh_pool_mgr__hzip {
	private final Btrie_slim_mgr trie = Btrie_slim_mgr.cs();
	private Gfo_poolable_mgr mkr__escape, mkr__xnde, mkr__lnke, mkr__lnki, mkr__hdr, mkr__img, mkr__thm, mkr__gly, mkr__img_bare;
	public Xoh_escape_hzip		Mw__escape()		{return (Xoh_escape_hzip)		mkr__escape.Get_fast();}
	public Xoh_xnde_hzip		Mw__xnde()			{return (Xoh_xnde_hzip)			mkr__xnde.Get_fast();}
	public Xoh_hdr_hzip			Mw__hdr()			{return (Xoh_hdr_hzip)			mkr__hdr.Get_fast();}
	public Xoh_lnke_hzip		Mw__lnke()			{return (Xoh_lnke_hzip)			mkr__lnke.Get_fast();}
	public Xoh_lnki_hzip		Mw__lnki()			{return (Xoh_lnki_hzip)			mkr__lnki.Get_fast();}
	public Xoh_img_hzip			Mw__img()			{return (Xoh_img_hzip)			mkr__img.Get_fast();}
	public Xoh_img_bare_hzip	Mw__img_bare()		{return (Xoh_img_bare_hzip)		mkr__img_bare.Get_fast();}
	public Xoh_thm_hzip			Mw__thm()			{return (Xoh_thm_hzip)			mkr__thm.Get_fast();}
	public Xoh_gly_hzip			Mw__gly()			{return (Xoh_gly_hzip)			mkr__gly.Get_fast();}
	public void Init() {
		this.Reg_all(false, Hook__core, Hook__html, Hook__mw);
	}
	public Xoh_hzip_wkr Get(byte b, byte[] src, int src_bgn, int src_end) {
		Object mgr_obj = trie.Match_bgn_w_byte(b, src, src_bgn, src_end); if (mgr_obj == null) return null;
		Gfo_poolable_mgr mgr = (Gfo_poolable_mgr)mgr_obj;
		return (Xoh_hzip_wkr)mgr.Get_fast();
	}
	private void Reg_all(boolean mode_is_b256, int hook__core, int hook__html, int hook__mw) {
		mkr__escape		= Reg(New_hook_len2(mode_is_b256, hook__core,  0)	, new Xoh_escape_hzip());
		mkr__xnde		= Reg(New_hook_len1(mode_is_b256, hook__html)		, new Xoh_xnde_hzip());
		mkr__hdr		= Reg(New_hook_len2(mode_is_b256, hook__mw	,  1)	, new Xoh_hdr_hzip());
		mkr__lnke		= Reg(New_hook_len2(mode_is_b256, hook__mw	,  2)	, new Xoh_lnke_hzip());
		mkr__lnki		= Reg(New_hook_len2(mode_is_b256, hook__mw	,  3)	, new Xoh_lnki_hzip());
		mkr__img		= Reg(New_hook_len2(mode_is_b256, hook__mw	,  4)	, new Xoh_img_hzip());
		mkr__thm		= Reg(New_hook_len2(mode_is_b256, hook__mw	,  5)	, new Xoh_thm_hzip());
		mkr__gly		= Reg(New_hook_len2(mode_is_b256, hook__mw	,  6)	, new Xoh_gly_hzip());
		mkr__img_bare	= Reg(New_hook_len2(mode_is_b256, hook__mw	,  7)	, new Xoh_img_bare_hzip());
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
	public static final byte[] Hooks_ary = new byte[] {Hook__core, Hook__html, Hook__mw};
}
