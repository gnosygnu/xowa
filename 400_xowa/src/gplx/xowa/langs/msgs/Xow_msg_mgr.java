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
package gplx.xowa.langs.msgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.htmls.sidebar.*;
public class Xow_msg_mgr implements Gfo_invk {
	private final    Xowe_wiki wiki; private Xol_lang_itm lang; private final    Xol_msg_mgr msg_mgr;
	private final    Bry_fmtr tmp_fmtr = Bry_fmtr.New__tmp();
	public Xow_msg_mgr(Xowe_wiki wiki, Xol_lang_itm lang) {
		this.wiki = wiki;
		this.lang = lang;
		this.msg_mgr = new Xol_msg_mgr(wiki, false);
	}
	public void Clear() {msg_mgr.Clear();}
	public void Lang_(Xol_lang_itm v) {
		this.lang = v;
		this.Clear();
	}
	public byte[] Val_by_id_args(int id, Object... args) {return Val_by_id_priv(id, args);}
	public byte[] Val_by_id(int id) {return Val_by_id_priv(id, null);}
	private byte[] Val_by_id_priv(int id, Object[] args) {
		Xol_msg_itm itm = msg_mgr.Itm_by_id_or_null(id);
		if (itm == null)
			itm = lang.Msg_mgr().Itm_by_id_or_null(id);
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b512();
		byte[] rv = Val_by_itm(tmp_bfr, itm, args);
		tmp_bfr.Mkr_rls();
		return rv;
	}
	public Xol_msg_itm Get_or_make(byte[] key) {return msg_mgr.Itm_by_key_or_new(key);}
	public Xol_msg_itm Get_or_null(byte[] key) {return msg_mgr.Itm_by_key_or_null(key);}
	public Xol_msg_itm Find_or_null(byte[] key) {
		Xol_msg_itm itm = msg_mgr.Itm_by_key_or_null(key);
		if (itm == null) {
			Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b512();
			itm = Xol_msg_mgr_.Get_msg_itm(tmp_bfr, wiki, lang, key);
			if (itm.Defined_in_none()) itm = null;
			tmp_bfr.Mkr_rls();
		}
		return itm;
	}
	public byte[] Val_by_key_args(byte[] key, Object... args) {return Val_by_key(key, args);}
	public byte[] Val_by_key_obj(String key) {return Val_by_key(Bry_.new_u8(key), null);}
	public byte[] Val_by_key_obj(byte[] key) {return Val_by_key(key, null);}
	private byte[] Val_by_key(byte[] key, Object[] args) {
		Xol_msg_itm itm = msg_mgr.Itm_by_key_or_null(key);
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b512();
		if (itm == null)
			itm = Xol_msg_mgr_.Get_msg_itm(tmp_bfr, wiki, lang, key);
		if (itm.Defined_in_none()) {
			tmp_bfr.Mkr_rls();
			return Bry_.Empty;
		}
		byte[] rv = Val_by_itm(tmp_bfr, itm, args);
		tmp_bfr.Mkr_rls();
		return rv;
	}
	public byte[] Val_by_itm(Bry_bfr tmp_bfr, Xol_msg_itm itm, Object[] args) {
		byte[] rv = itm.Val();
		if (args != null) rv = itm.Fmt_tmp(tmp_bfr, tmp_fmtr, rv, args);
		if (itm.Has_tmpl_txt()) rv = wiki.Parser_mgr().Main().Expand_tmpl(rv);
		return rv;
	}
	public byte[] Val_html_accesskey_and_title(byte[] id) {
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		byte[] rv = Val_html_accesskey_and_title(id, bfr, null);
		bfr.Mkr_rls();
		return rv;
	}
	public byte[] Val_html_accesskey_and_title(byte[] id, Bry_bfr bfr, Xowh_sidebar_itm itm) {
		byte[] tooltip_key = Bry_.Add(CONST_prefix_tooltip, id);
		byte[] tooltip_val = Val_by_key_obj(tooltip_key);
		boolean tooltip_found = Bry_.Len_gt_0(tooltip_val);
		byte[] accesskey_key = Bry_.Empty, accesskey_val = Bry_.Empty;
		boolean accesskey_found = false;
		if (tooltip_found) {
			accesskey_key = Bry_.Add(CONST_prefix_accesskey, id);
			accesskey_val = Val_by_key_obj(accesskey_key);
			accesskey_found = Bry_.Len_gt_0(accesskey_val);
		}
		if (accesskey_found)
			bfr.Add(CONST_atr_accesskey).Add(accesskey_val).Add_byte(Byte_ascii.Quote);
		bfr.Add(CONST_atr_title).Add(tooltip_found ? tooltip_val : Bry_.Empty);	// NOTE: if tooltip not found, make blank; don't bother showing tooltip_key
		if (accesskey_found)
			bfr.Add_byte(Byte_ascii.Space).Add_byte(Byte_ascii.Brack_bgn).Add(accesskey_val).Add_byte(Byte_ascii.Brack_end);
		bfr.Add_byte(Byte_ascii.Quote);
		byte[] rv = bfr.To_bry_and_clear();
		if (itm == null)
			return rv;
		else {
			itm.Accesskey_(accesskey_val).Title_(tooltip_val).Atr_accesskey_and_title_(rv);
			return null;
		}
	}	static final    byte[] CONST_prefix_tooltip = Bry_.new_a7("tooltip-"), CONST_prefix_accesskey = Bry_.new_a7("accesskey-"), CONST_atr_title = Bry_.new_a7(" title=\""), CONST_atr_accesskey = Bry_.new_a7(" accesskey=\"");
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))							return this.Val_by_key_obj(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_get_html_accesskey_and_title))	return this.Val_html_accesskey_and_title(m.ReadBry("v"));
		else	return Gfo_invk_.Rv_unhandled;
	}	private static final String Invk_get = "get", Invk_get_html_accesskey_and_title = "get_html_accesskey_and_title";
}
