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
package gplx.xowa.xtns.gallery; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.primitives.*; import gplx.core.btries.*;
import gplx.xowa.parsers.lnkis.redlinks.*;
import gplx.xowa.files.*;
public class Gallery_itm_parser {		
	private Xow_wiki wiki; private Btrie_slim_mgr trie = Btrie_slim_mgr.ci_utf_8_();
	private Gallery_itm cur_itm;
	private byte[] src; private int end_pos;
	private int cur_pos; private byte cur_byte;
	private byte cur_fld;
	private int itm_bgn;
	private Bry_bfr caption_bfr = Bry_bfr.reset_(255); private int caption_bgn;
	private Xop_ctx ctx;
	public Gallery_itm_parser Init_by_wiki(Xow_wiki wiki) {
		this.wiki = wiki; Xol_lang lang = wiki.Lang();
		this.ctx = wiki.Ctx();
		trie.Clear();
		Byte_obj_ref tmp_bref = Byte_obj_ref.zero_();
		Init_keyword(tmp_bref, lang, Xol_kwd_grp_.Id_img_alt, Fld_alt);		// NOTE: MW uses "gallery-@gplx.Internal protected-alt" which just maps to "img-alt"
		Init_keyword(tmp_bref, lang, Xol_kwd_grp_.Id_img_link, Fld_link);	// NOTE: MW uses "gallery-@gplx.Internal protected-link" which just maps to "img-link"
		Init_keyword(tmp_bref, lang, Xol_kwd_grp_.Id_img_page, Fld_page);
		return this;
	}
	public void Parse_all(ListAdp rv, Gallery_mgr_base mgr, Gallery_xnde xnde, byte[] src, int content_bgn, int content_end) {
		this.src = src;
		this.cur_pos = content_bgn; this.end_pos = content_end;
		cur_itm = new Gallery_itm();
		while (cur_pos < end_pos) {
			cur_itm.Reset();
			caption_bfr.Clear();
			byte cur_mode = Parse_itm();
			if (cur_itm.Ttl() != null) {
				if (caption_bfr.Len() > 0)
					cur_itm.Caption_bry_(caption_bfr.Xto_bry_and_clear_and_trim());
				Make_lnki_tkn(mgr, xnde, src);
				rv.Add(cur_itm);
				cur_itm = new Gallery_itm();
			}
			if (cur_mode == Mode_nl)
				++cur_pos;
		}
	}
	private void Make_lnki_tkn(Gallery_mgr_base mgr, Gallery_xnde xnde, byte[] src) {
		Xop_lnki_tkn lnki_tkn = ctx.Tkn_mkr().Lnki(cur_itm.Ttl_bgn(), cur_itm.Ttl_end()).Ttl_(cur_itm.Ttl());
		if (cur_itm.Link_bgn() != -1)
			lnki_tkn.Link_tkn_(new Arg_nde_tkn_mock("link", String_.new_utf8_(src, cur_itm.Link_bgn(), cur_itm.Link_end())));	// NOTE: hackish, but add the link as arg_nde, since gallery link is not parsed like a regular lnki
		cur_itm.Lnki_tkn_(lnki_tkn);
		byte[] lnki_caption = cur_itm.Caption_bry();
		if (Bry_.Len_gt_0(lnki_caption)) {
			Xop_root_tkn caption_tkn = wiki.Parser().Parse_text_to_wdom_old_ctx(ctx, lnki_caption, true);
			cur_itm.Caption_tkn_(caption_tkn);
		}
		Xop_lnki_logger file_wkr = ctx.Lnki().File_wkr();	// NOTE: do not set file_wkr ref early (as member var); parse_all sets late
		ctx.Cur_page().Lnki_list().Add(lnki_tkn);
		mgr.Get_thumb_size(lnki_tkn, cur_itm.Ext());		// NOTE: set thumb size, so that lnki.temp parse picks it up
		if (file_wkr != null) file_wkr.Wkr_exec(ctx, src, lnki_tkn, gplx.xowa.bldrs.files.Xob_lnki_src_tid.Tid_gallery);
		lnki_tkn.Lnki_w_(-1).Lnki_h_(-1);					// NOTE: reset lnki back to defaults, else itm will show as large missing caption
	}
	private byte Parse_itm() {
		int fld_count = 0;
		itm_bgn = cur_pos;
		while (cur_pos < end_pos) {
			byte mode = Fld_bgn(fld_count);
			if (mode == Mode_nl || mode == Mode_eos) return mode;
			mode = Skip_to_fld_end();
			Fld_end();
			if (mode != Mode_pipe) return mode;
			++cur_pos;	// position after pipe
			++fld_count;
		}
		return Mode_eos;
	}
	private byte Fld_bgn(int fld_count) {
		cur_fld = Fld_null;
		int bgn_pos = cur_pos;
		byte mode = Skip_ws();
		switch (mode) {
			case Mode_nl:
			case Mode_eos:
				return mode;
		}
		Object o = trie.Match_bgn_w_byte(cur_byte, src, cur_pos, end_pos);
		if (o != null) {						// either "alt" or "link"
			int old_pos = cur_pos;
			cur_pos = trie.Match_pos();
			Skip_ws();
			if (cur_byte == Byte_ascii.Eq) {	// "="
				++cur_pos;						// position after eq
				Skip_ws();
				cur_fld = ((Byte_obj_val)o).Val();
				switch (cur_fld) {
					case Fld_alt:	cur_itm.Alt_bgn_(cur_pos);  return Mode_text;
					case Fld_link:	cur_itm.Link_bgn_(cur_pos); return Mode_text;
					case Fld_page:
						if (cur_itm.Ext() != null && cur_itm.Ext().Id_supports_page()) {	// NOTE: ext can be null with long multi-line captions; EX:<ref\n|page=1 />; DATE:2014-03-21
							cur_itm.Page_bgn_(cur_pos);
							return Mode_text;
						}
						else {	// not a pdf / djvu; treat "page=" as caption
							cur_fld = Fld_caption;
							cur_pos = old_pos;
							break;	// NOTE: do not return Mode_text, so it reaches caption code below
						}
				}					
			}
			else
				cur_pos = old_pos;
		}
		if (fld_count == 0) {
			cur_fld = Fld_ttl;
			cur_itm.Ttl_bgn_(cur_pos);
		}
		else {
			cur_fld = Fld_caption;
			caption_bgn = caption_bfr.Len() == 0 ? cur_pos : bgn_pos;	// if 1st caption, trim bgn; otherwise, enclose rest; EX: "File:A.png| a| b" -> "a| b"
		}
		return Mode_text;
	}
	private byte Skip_to_fld_end() {
		while (cur_pos < end_pos) {
			cur_byte = src[cur_pos];
			switch (cur_byte) {
				case Byte_ascii.Pipe:			return Mode_pipe;
				case Byte_ascii.NewLine:		return Mode_nl;
				case Byte_ascii.CarriageReturn:
				case Byte_ascii.Space:
				case Byte_ascii.Tab:
					++cur_pos;
					continue;
				default:
					++cur_pos;
					continue;
			}			
		}
		return Mode_eos;
	}
	private void Fld_end() {
		int fld_end = cur_pos;
		if (cur_fld != Fld_caption) {
			int non_ws_pos = Bry_finder.Find_bwd_non_ws_or_not_found(src, cur_pos - 1, itm_bgn) + 1;	// SEE:non_ws_pos
			if (non_ws_pos != Bry_.NotFound + 1)
				fld_end = non_ws_pos;
		}
		switch (cur_fld) {
			case Fld_ttl:
				cur_itm.Ttl_end_(fld_end);
				byte[] ttl_bry = Bry_.Mid(src, cur_itm.Ttl_bgn(), fld_end);
				ttl_bry = ctx.App().Encoder_mgr().Url_ttl().Decode(ttl_bry);	// NOTE: must decode url-encoded entries; EX: "A%28b%29.png" -> "A(b).png"; DATE:2014-01-01
				Xoa_ttl ttl = Xoa_ttl.parse_(wiki, ttl_bry);
				if (	ttl == null				// invalid ttl; EX:	"<invalid>"
					||	ttl.Anch_bgn() == 1		// anchor-only ttl; EX: "#invalid"; DATE:2014-03-18
					)
					cur_itm.Reset();
				else {
					if (!ttl.Ns().Id_file_or_media())	// ttl does not have "File:"; MW allows non-ns names; EX: "A.png" instead of "File:A.png"; DATE:2013-11-18 
						ttl = Xoa_ttl.parse_(wiki, Xow_ns_.Id_file, ttl_bry);
					cur_itm.Ttl_(ttl);
					cur_itm.Ext_(Xof_ext_.new_by_ttl_(ttl_bry));
				}
				break;
			case Fld_caption:
				if (caption_bfr.Len() != 0) caption_bfr.Add_byte_pipe();	// prepend | to all other captions; EX: File:A.png|a|b -> "a|b" (pipe not added to 1st, but added to 2nd)
				caption_bfr.Add_mid(src, caption_bgn, fld_end);
				break;
			case Fld_alt: 
				if (fld_end < cur_itm.Alt_bgn()) 
					cur_itm.Alt_bgn_(-1);
				else
					cur_itm.Alt_end_(fld_end); 
				break;
			case Fld_link:
				if (fld_end < cur_itm.Link_bgn()) 
					cur_itm.Link_bgn_(-1);
				else
					cur_itm.Link_end_(fld_end);
				break;
			case Fld_page:
				if (fld_end < cur_itm.Page_bgn()) 
					cur_itm.Page_bgn_(-1);
				else
					cur_itm.Page_end_(fld_end);
				break;
			default:			throw Err_.unhandled(fld_end);
		}
	}
	private byte Skip_ws() {
		while (cur_pos < end_pos) {
			cur_byte = src[cur_pos];
			switch (cur_byte) {
				case Byte_ascii.CarriageReturn:
				case Byte_ascii.Space:
				case Byte_ascii.Tab:			++cur_pos; continue; // ignore
				case Byte_ascii.Pipe:			return Mode_pipe;
				case Byte_ascii.NewLine:		return Mode_nl;
				default:						return Mode_text;
			}
		}
		return Mode_eos;
	}
	private static final byte Fld_null = 0, Fld_ttl = 1, Fld_caption = 2, Fld_alt = 3, Fld_link = 4, Fld_page = 5;
	private static final byte Mode_eos = 1, Mode_nl = 2, Mode_pipe = 3, Mode_text = 4;
	private void Init_keyword(Byte_obj_ref tmp_bref, Xol_lang lang, int kwd_id, byte trie_key) {
		Xol_kwd_grp grp = lang.Kwd_mgr().Get_at(kwd_id);
		if (grp == null) {lang.App().Usr_dlg().Warn_many("", "", "could not find gallery keyword: ~{0}", String_.new_utf8_(Xol_kwd_grp_.Bry_by_id(kwd_id))); return;}
		Xol_kwd_itm[] itms = grp.Itms();
		int len = itms.length;
		Byte_obj_val trie_ref = Byte_obj_val.new_(trie_key);
		for (int i = 0; i < len; i++) {
			Xol_kwd_itm itm = itms[i];
			byte[] itm_bry = Xol_kwd_parse_data.Strip(caption_bfr, itm.Val(), tmp_bref);	// strip off =$1 for "alt=$1"
			trie.Add_obj(itm_bry, trie_ref);
		}
	}
}
/*
SEE:non_ws_pos;
int non_ws_pos = Bry_finder.Find_bwd_non_ws(src, cur_pos - 1, itm_bgn) + 1;
. -1 to start before cur_pos (which is usually pipe);
. +1 to place after non_ws_char
EX: text="b c |"; cur_pos = 4;
. -1 to start at cur_pos = 3
. src[3] = ' '; ws; continue
. src[2] = c; return 2;
. + 1 to place after "c" -> 3
. fld_end = 3
*/
