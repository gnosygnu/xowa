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
package gplx.xowa.xtns.gallery; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.primitives.*; import gplx.core.btries.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.lnkis.*; import gplx.xowa.parsers.lnkis.files.*; import gplx.xowa.parsers.tmpls.*;
import gplx.xowa.files.*;
public class Gallery_parser {
	private Xowe_wiki wiki; private Btrie_slim_mgr trie = Btrie_slim_mgr.ci_u8();
	private Gallery_itm cur_itm;
	private byte[] src; private int end_pos;
	private int cur_pos; private byte cur_byte;
	private byte cur_fld;
	private int itm_bgn;
	private Bry_bfr caption_bfr = Bry_bfr_.Reset(255); private int caption_bgn;
	private Xop_ctx ctx;
	private final    Btrie_rv trv = new Btrie_rv();
	public Gallery_parser Init_by_wiki(Xowe_wiki wiki) {
		this.wiki = wiki; Xol_lang_itm lang = wiki.Lang();
		this.ctx = wiki.Parser_mgr().Ctx();
		trie.Clear();
		Byte_obj_ref tmp_bref = Byte_obj_ref.zero_();
		Init_keyword(tmp_bref, lang, Xol_kwd_grp_.Id_img_alt, Fld_alt);		// NOTE: MW uses "gallery-@gplx.Internal protected-alt" which just maps to "img-alt"
		Init_keyword(tmp_bref, lang, Xol_kwd_grp_.Id_img_link, Fld_link);	// NOTE: MW uses "gallery-@gplx.Internal protected-link" which just maps to "img-link"
		Init_keyword(tmp_bref, lang, Xol_kwd_grp_.Id_img_page, Fld_page);
		return this;
	}
	public boolean Parse_in_progress() {return parse_in_progress;} public void Parse_in_progress_(boolean v) {parse_in_progress = v;} private boolean parse_in_progress;
	public void Parse_all(List_adp rv, Gallery_mgr_base mgr, Gallery_xnde xnde, byte[] src, int content_bgn, int content_end) {
		synchronized (trie) {parse_in_progress = true;}
		this.src = src;
		this.cur_pos = content_bgn; this.end_pos = content_end;
		cur_itm = new Gallery_itm();

		while (cur_pos < end_pos) {
			cur_itm.Reset();
			caption_bfr.Clear();
			byte cur_mode = Parse_itm();
			if (cur_itm.Ttl() != null) {
				if (caption_bfr.Len() > 0)
					cur_itm.Caption_bry_(Make_caption_bry(caption_bfr, wiki, ctx, caption_bfr.To_bry_and_clear()));
				Make_lnki_tkn(mgr, xnde, src);
				rv.Add(cur_itm);
				cur_itm = new Gallery_itm();
			}
			if (cur_mode == Mode_nl)
				++cur_pos;
		}
		synchronized (trie) {parse_in_progress = false;}
	}
	private void Make_lnki_tkn(Gallery_mgr_base mgr, Gallery_xnde xnde, byte[] src) {
		Xop_lnki_tkn lnki_tkn = ctx.Tkn_mkr().Lnki(cur_itm.Ttl_bgn(), cur_itm.Ttl_end()).Ttl_(cur_itm.Ttl());
		if (cur_itm.Link_bgn() != -1)
			lnki_tkn.Link_tkn_(new Arg_nde_tkn_mock("link", String_.new_u8(src, cur_itm.Link_bgn(), cur_itm.Link_end())));	// NOTE: hackish, but add the link as arg_nde, since gallery link is not parsed like a regular lnki
		cur_itm.Lnki_tkn_(lnki_tkn);
		if (cur_itm.Page_bgn() != -1) {
			int page_val = Bry_.To_int_or(src, cur_itm.Page_bgn(), cur_itm.Page_end(), -1);
			if (page_val == -1) Xoa_app_.Usr_dlg().Warn_many("", "", "page is not an int: wiki=~{0} ttl=~{1} page=~{2}", wiki.Domain_str(), ctx.Page().Ttl().Page_db(), String_.new_u8(src, cur_itm.Page_bgn(), cur_itm.Page_end()));
			lnki_tkn.Page_(page_val);
		}
		byte[] lnki_caption = cur_itm.Caption_bry();
		if (Bry_.Len_gt_0(lnki_caption)) {
			Xop_root_tkn caption_tkn = wiki.Parser_mgr().Main().Parse_text_to_wdom_old_ctx(ctx, lnki_caption, true);
			cur_itm.Caption_tkn_(caption_tkn);
		}
		ctx.Page().Lnki_list().Add(lnki_tkn);
		mgr.Get_thumb_size(lnki_tkn, cur_itm.Ext());			// NOTE: set thumb size, so that lnki.temp parse picks it up
		ctx.Lnki().File_logger().Log_file(ctx, lnki_tkn, Xop_file_logger_.Tid__gallery);	// NOTE: do not set file_wkr ref early (as member var); parse_all sets late
		lnki_tkn.W_(-1).H_(-1);									// NOTE: reset lnki back to defaults, else itm will show as large missing caption
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
		Object o = trie.Match_at_w_b0(trv, cur_byte, src, cur_pos, end_pos);
		if (o != null) {						// either "alt" or "link"
			int old_pos = cur_pos;
			cur_pos = trv.Pos();
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
				case Byte_ascii.Nl:		return Mode_nl;
				case Byte_ascii.Cr:
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
			int non_ws_pos = Bry_find_.Find_bwd_non_ws_or_not_found(src, cur_pos - 1, itm_bgn) + 1;	// SEE:non_ws_pos
			if (non_ws_pos != Bry_find_.Not_found + 1)
				fld_end = non_ws_pos;
		}
		switch (cur_fld) {
			case Fld_ttl:
				cur_itm.Ttl_end_(fld_end);
				byte[] ttl_bry = Bry_.Mid(src, cur_itm.Ttl_bgn(), fld_end);
				ttl_bry = gplx.langs.htmls.encoders.Gfo_url_encoder_.Http_url_ttl.Decode(ttl_bry);	// NOTE: must decode url-encoded entries; EX: "A%28b%29.png" -> "A(b).png"; DATE:2014-01-01
				if (gplx.core.envs.Env_.Mode_testing() && wiki == null) return; // TEST: else one test will throw benign null ref exception; DATE:2017-03-01
				Xoa_ttl ttl = Xoa_ttl.Parse(wiki, ttl_bry);
				if (	ttl == null				// invalid ttl; EX:	"<invalid>"
					||	ttl.Anch_bgn() == 1		// anchor-only ttl; EX: "#invalid"; DATE:2014-03-18
					)
					cur_itm.Reset();
				else {
					if (!ttl.Ns().Id_is_file_or_media())	// ttl does not have "File:"; MW allows non-ns names; EX: "A.png" instead of "File:A.png"; DATE:2013-11-18 
						ttl = Xoa_ttl.Parse(wiki, Xow_ns_.Tid__file, ttl_bry);
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
			default:			throw Err_.new_unhandled(fld_end);
		}
	}
	private byte Skip_ws() {
		while (cur_pos < end_pos) {
			cur_byte = src[cur_pos];
			switch (cur_byte) {
				case Byte_ascii.Cr:
				case Byte_ascii.Space:
				case Byte_ascii.Tab:			++cur_pos; continue; // ignore
				case Byte_ascii.Pipe:			return Mode_pipe;
				case Byte_ascii.Nl:		return Mode_nl;
				default:						return Mode_text;
			}
		}
		return Mode_eos;
	}
	private static final byte Fld_null = 0, Fld_ttl = 1, Fld_caption = 2, Fld_alt = 3, Fld_link = 4, Fld_page = 5;
	private static final byte Mode_eos = 1, Mode_nl = 2, Mode_pipe = 3, Mode_text = 4;
	private void Init_keyword(Byte_obj_ref tmp_bref, Xol_lang_itm lang, int kwd_id, byte trie_key) {
		Xol_kwd_grp grp = lang.Kwd_mgr().Get_at(kwd_id);
		if (grp == null) {Gfo_usr_dlg_.Instance.Warn_many("", "", "could not find gallery keyword: ~{0}", String_.new_u8(Xol_kwd_grp_.Bry_by_id(kwd_id))); return;}
		Xol_kwd_itm[] itms = grp.Itms();
		int len = itms.length;
		Byte_obj_val trie_ref = Byte_obj_val.new_(trie_key);
		for (int i = 0; i < len; i++) {
			Xol_kwd_itm itm = itms[i];
			byte[] itm_bry = Xol_kwd_parse_data.Strip(caption_bfr, itm.Val(), tmp_bref);	// strip off =$1 for "alt=$1"
			trie.Add_obj(itm_bry, trie_ref);
		}
	}
	// MW changed behavior from chaining multiple args to keeping last one; EX: "File:A.png|a|b" -> "b" x> "a|b" PAGE:fr.w:Belgique DATE:2016-10-17 REF: https://github.com/wikimedia/mediawiki/commit/63aeabeff1e098e872cc46f3698c61457e44ba15
	private static byte[] Make_caption_bry(Bry_bfr tmp_bfr, Xowe_wiki wiki, Xop_ctx ctx, byte[] src) {			
		// parse caption to tkns
		Xop_root_tkn root = wiki.Parser_mgr().Main().Parse_text_to_wdom(Xop_ctx.New__top(wiki), src, false);	// NOTE: ctx must be top, else <ref> will get double-counted

		// loop tkns
		int subs_len = root.Subs_len();
		for (int i = 0; i < subs_len; ++i) {
			Xop_tkn_itm sub = root.Subs_get(i);
			// pipe resets caption; EX: "a|b" -> "b"
			if (sub.Tkn_tid() == Xop_tkn_itm_.Tid_pipe)
				tmp_bfr.Clear();
			// else, add tkn to caption; note that loop does not recurse through tkn's subtkns; EX: "a|[[b|c]]" -> "text_tkn,lnki_tkn" -> "[[b|c]]"
			else
				tmp_bfr.Add_mid(root.Data_mid(), sub.Src_bgn(), sub.Src_end());
		}
		return tmp_bfr.To_bry_and_clear_and_trim();
	}
}
/*
SEE:non_ws_pos;
int non_ws_pos = Bry_find_.Find_bwd_non_ws(src, cur_pos - 1, itm_bgn) + 1;
. -1 to start before cur_pos (which is usually pipe);
. +1 to place after non_ws_char
EX: text="b c |"; cur_pos = 4;
. -1 to start at cur_pos = 3
. src[3] = ' '; ws; continue
. src[2] = c; return 2;
. + 1 to place after "c" -> 3
. fld_end = 3
*/
