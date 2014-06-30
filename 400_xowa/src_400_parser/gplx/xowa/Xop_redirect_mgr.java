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
import gplx.xowa.html.*;
public class Xop_redirect_mgr {
	public Xop_redirect_mgr(Xow_wiki wiki) {this.wiki = wiki; this.log_mgr = wiki.App().Msg_log();} private Xow_wiki wiki; Gfo_msg_log log_mgr; ByteTrieMgr_slim trie;
	public boolean Is_redirect(byte[] text, int text_len) {return this.Extract_redirect(text, text_len) != null;}
	public Xoa_ttl Extract_redirect_loop(byte[] src) {
		Xoa_ttl rv = null;
		for (int i = 0; i < Extract_redirect_loop_max; i++) {
			rv = Extract_redirect(src, src.length);
			if (rv != null) return rv;
		}
		return null;
	}
	public Xoa_ttl Extract_redirect(byte[] src) {return Extract_redirect(src, src.length);}
	public static final Xoa_ttl Extract_redirect_is_null = null;
	public static final int Extract_redirect_loop_max = 4;
	public Xoa_ttl Extract_redirect(byte[] src, int src_len) {
		if (src_len == 0) return Redirect_null_ttl;
		int bgn = Bry_finder.Find_fwd_while_not_ws(src, 0, src_len);
		if (bgn == src_len) return Redirect_null_ttl; // article is entirely whitespace
		if (trie == null) trie = Xol_kwd_mgr.trie_(wiki.Lang().Kwd_mgr(), Xol_kwd_grp_.Id_redirect);
		if (trie.MatchAtCur(src, bgn, src_len) == null) return Redirect_null_ttl; 
		int cur_pos = trie.Match_pos();
		if (cur_pos == src_len) {log_mgr.Add_itm_none(Xop_redirect_log.Lnki_not_found, src, 0, cur_pos); return Redirect_null_ttl;} // occurs when just #REDIRECT
		switch (src[cur_pos]) {	// REF.MW: Ttl.php|newFromRedirectInternal; see regx
			case Byte_ascii.NewLine:
			case Byte_ascii.Space:
			case Byte_ascii.Tab:
			case Byte_ascii.Brack_bgn:
			case Byte_ascii.Colon:
				break;
			default:
				log_mgr.Add_itm_none(Xop_redirect_log.False_match, src, 0, cur_pos);
				return Redirect_null_ttl;
		}
		int bgn_pos = Bry_finder.Find_fwd(src, Xop_tkn_.Lnki_bgn, cur_pos);
		if (bgn_pos == Bry_.NotFound) {log_mgr.Add_itm_none(Xop_redirect_log.Lnki_not_found, src, 0, cur_pos); return Redirect_null_ttl;}
		bgn_pos += Xop_tkn_.Lnki_bgn.length;
		int end_pos = Bry_finder.Find_fwd(src, Xop_tkn_.Lnki_end, bgn_pos);
		if (end_pos == Bry_.NotFound) {log_mgr.Add_itm_none(Xop_redirect_log.Lnki_not_found, src, 0, cur_pos); return Redirect_null_ttl;}
		byte[] redirect_ary = Bry_.Mid(src, bgn_pos, end_pos);
		return Xoa_ttl.parse_(wiki, redirect_ary);
	}
	public static final Xoa_ttl	Redirect_null_ttl = null;
	public static final byte[]	Redirect_null_bry = Bry_.Empty;
	public static final int Redirect_max_allowed = 4;
	private static final byte[] Redirect_bry = Bry_.new_ascii_("#REDIRECT ");
	public static byte[] Make_redirect_text(byte[] redirect_to_ttl) {
		return Bry_.Add
			(	Redirect_bry				// "#REDIRECT "
			,	Xop_tkn_.Lnki_bgn			// "[["
			,	redirect_to_ttl				// "Page"
			,	Xop_tkn_.Lnki_end			// "]]"
			);
	}
	public static byte[] Bld_redirect_msg(Xoa_app app, Xow_wiki wiki, Xoa_page page) {
		ListAdp list = page.Redirect_list();
		int list_len = list.Count();
		if (list_len == 0) return Bry_.Empty;
		Bry_bfr redirect_bfr = app.Utl_bry_bfr_mkr().Get_b512();
		for (int i = 0; i < list_len; i++) {
			if (i != 0) redirect_bfr.Add(Bry_redirect_dlm);
			byte[] redirect_ttl = (byte[])list.FetchAt(i);
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
		Bry_bfr fmt_bfr = app.Utl_bry_bfr_mkr().Get_b512();
		app.Tmp_fmtr().Fmt_(msg_itm.Val()).Bld_bfr_one(fmt_bfr, redirect_bfr);
		redirect_bfr.Clear().Mkr_rls(); fmt_bfr.Mkr_rls();
		return fmt_bfr.XtoAryAndClear();
	}	private static byte[] Bry_redirect_dlm = Bry_.new_ascii_(" <--- "), Bry_redirect_arg = Bry_.new_ascii_("?redirect=no");		
}
