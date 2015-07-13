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
package gplx.xowa; import gplx.*;
import gplx.xowa.html.*; import gplx.xowa.html.hrefs.*;
public class Xop_redirect_mgr {		
	private final Xowe_wiki wiki; private final Url_encoder url_decoder; private Hash_adp_bry redirect_hash;
	public Xop_redirect_mgr(Xowe_wiki wiki) {this.wiki = wiki; this.url_decoder = Xoa_app_.Utl__encoder_mgr().Http_url_ttl();}	// NOTE: must be Url_ttl, not Url; PAGE:en.w:Template:Positionskarte+ -> Template:Location_map+, not Template:Location_map DATE:2014-08-21
	public void Clear() {redirect_hash = null;}	// TEST:
	public boolean Is_redirect(byte[] text, int text_len) {return this.Extract_redirect(text, text_len) != null;}
	public Xoa_ttl Extract_redirect_loop(byte[] src) {
		Xoa_ttl rv = null;
		for (int i = 0; i < Redirect_max_allowed; i++) {
			rv = Extract_redirect(src, src.length);
			if (rv != null) return rv;
		}
		return null;
	}
	public Xoa_ttl Extract_redirect(byte[] src) {return Extract_redirect(src, src.length);}
	public Xoa_ttl Extract_redirect(byte[] src, int src_len) {	// NOTE: this proc is called by every page. be careful of changes; DATE:2014-07-05
		if (src_len == 0) return Redirect_null_ttl;
		int bgn = Bry_finder.Find_fwd_while_not_ws(src, 0, src_len);
		if (bgn == src_len) return Redirect_null_ttl; // article is entirely whitespace
		int kwd_end = Xop_redirect_mgr_.Get_kwd_end_or_end(src, bgn, src_len);
		if (kwd_end == src_len) return Redirect_null_ttl;
		if (redirect_hash == null) redirect_hash = Xol_kwd_mgr.hash_(wiki.Lang().Kwd_mgr(), Xol_kwd_grp_.Id_redirect);
		Object redirect_itm = redirect_hash.Get_by_mid(src, bgn, kwd_end);
		if (redirect_itm == null)		return Redirect_null_ttl; // not a redirect kwd
		int ttl_bgn = Xop_redirect_mgr_.Get_ttl_bgn_or_neg1(src, kwd_end, src_len);
		if (ttl_bgn == Bry_.NotFound)	return Redirect_null_ttl;
		ttl_bgn += Xop_tkn_.Lnki_bgn.length;
		int ttl_end = Bry_finder.Find_fwd(src, Xop_tkn_.Lnki_end, ttl_bgn);
		if (ttl_end == Bry_.NotFound)	return Redirect_null_ttl;
		byte[] redirect_bry = Bry_.Mid(src, ttl_bgn, ttl_end);
		redirect_bry = url_decoder.Decode(redirect_bry);	// NOTE: url-decode links; PAGE: en.w:Watcher_(Buffy_the_Vampire_Slayer); DATE:2014-08-18
		return Xoa_ttl.parse_(wiki, redirect_bry);
	}
	public static final Xoa_ttl Extract_redirect_is_null = null;
	public static final int Redirect_max_allowed = 4;
	public static final Xoa_ttl	Redirect_null_ttl = null;
	public static final byte[]	Redirect_null_bry = Bry_.Empty;
	private static final byte[] Redirect_bry = Bry_.new_a7("#REDIRECT ");
	public static byte[] Make_redirect_text(byte[] redirect_to_ttl) {
		return Bry_.Add
			(	Redirect_bry				// "#REDIRECT "
			,	Xop_tkn_.Lnki_bgn			// "[["
			,	redirect_to_ttl				// "Page"
			,	Xop_tkn_.Lnki_end			// "]]"
			);
	}
	public static byte[] Bld_redirect_msg(Xoae_app app, Xowe_wiki wiki, Xoae_page page) {
		List_adp list = page.Redirected_ttls();
		int list_len = list.Count();
		if (list_len == 0) return Bry_.Empty;
		Bry_bfr redirect_bfr = app.Utl__bfr_mkr().Get_b512();
		for (int i = 0; i < list_len; i++) {
			if (i != 0) redirect_bfr.Add(Bry_redirect_dlm);
			byte[] redirect_ttl = (byte[])list.Get_at(i);
			redirect_bfr.Add(Xoh_consts.A_bgn)			// '<a href="'
				.Add(Xoh_href_parser.Href_wiki_bry)		// '/wiki/'
				.Add(redirect_ttl)						// 'PageA'
				.Add(Bry_redirect_arg)					// ?redirect=no
				.Add(Xoh_consts.A_bgn_lnki_0)			// '" title="'
				.Add(redirect_ttl)						// 'PageA'
				.Add(Xoh_consts.__end_quote)			// '">'
				.Add(redirect_ttl)						// 'PageA'
				.Add(Xoh_consts.A_end)					// </a>
				;
		}
		Xol_msg_itm msg_itm = wiki.Lang().Msg_mgr().Itm_by_id_or_null(Xol_msg_itm_.Id_redirectedfrom);
		Bry_bfr fmt_bfr = app.Utl__bfr_mkr().Get_b512();
		app.Tmp_fmtr().Fmt_(msg_itm.Val()).Bld_bfr_one(fmt_bfr, redirect_bfr);
		redirect_bfr.Clear().Mkr_rls(); fmt_bfr.Mkr_rls();
		return fmt_bfr.Xto_bry_and_clear();
	}	private static byte[] Bry_redirect_dlm = Bry_.new_a7(" <--- "), Bry_redirect_arg = Bry_.new_a7("?redirect=no");		
}
class Xop_redirect_mgr_ {
	public static int Get_kwd_end_or_end(byte[] src, int bgn, int end) {	// get end of kwd
		for (int i = bgn; i < end; ++i) {
			switch (src[i]) {
				case Byte_ascii.Nl: case Byte_ascii.Space: case Byte_ascii.Tab:
				case Byte_ascii.Brack_bgn: case Byte_ascii.Colon:
					return i;	// ASSUME: kwd does not have these chars
				default:
					break;
			}
		}
		return end;
	}
	public static int Get_ttl_bgn_or_neg1(byte[] src, int bgn, int end) {	// get bgn of ttl
		boolean colon_null = true;
		for (int i = bgn; i < end; ++i) {
			switch (src[i]) {
				case Byte_ascii.Nl: case Byte_ascii.Space: case Byte_ascii.Tab: break;	// skip all ws
				case Byte_ascii.Colon: // allow 1 colon
					if (colon_null)
						colon_null = false;
					else
						return Bry_.NotFound;
					break;
				default:
					break;
				case Byte_ascii.Brack_bgn:
					int nxt_pos = i + 1;
					if (nxt_pos >= end) return Bry_.NotFound;	// [ at eos
					return src[nxt_pos] == Byte_ascii.Brack_bgn ? i : Bry_.NotFound;
			}
		}
		return Bry_.NotFound;
	}
}
