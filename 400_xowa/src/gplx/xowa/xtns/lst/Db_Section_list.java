/* search for '</?section' then find fwd '>'
any </section ignore

within bgn to end need to dig out 'begin' or 'end' or the language sensitivities
also the key value

store
 name of section, start or end
  if start position after close >
  if end position before <
*/
package gplx.xowa.xtns.lst;
import gplx.Bry_;
import gplx.List_adp;
import gplx.List_adp_;

import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.parsers.Xop_root_tkn;
import gplx.xowa.parsers.Xop_parser;
import gplx.xowa.parsers.Xop_parser_;
import gplx.xowa.parsers.Xop_parser_tid_;
import gplx.xowa.parsers.Xop_tkn_mkr;
import gplx.xowa.parsers.tmpls.Xot_invk_temp;
import gplx.xowa.parsers.lnkis.files.Xop_file_logger_;
import gplx.xowa.parsers.tmpls.Xot_defn_tmpl;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;
import gplx.Bool_;
import gplx.Hash_adp_bry;

public class Db_Section_list {
	private List_adp sects;
	private List_adp heads;
	private byte[] src;
	private Xop_ctx ctx;
	private Xop_ctx sub_ctx;
	private Xowe_wiki wiki;
	private Xoa_ttl ttl;
	private byte[] ttl_bry;
	private static final byte Include_between = 0, Include_to_eos = 1, Include_to_bos = 2;
	public Db_Section_list(byte[] src, int langid, Xop_ctx ctx, Xop_ctx sub_ctx, Xoa_ttl ttl, byte[] ttl_bry) {
		byte b;
		this.src = src;
		this.ctx = ctx;
		this.wiki = ctx.Wiki();
		this.sub_ctx = sub_ctx;
		this.ttl = ttl;
		this.ttl_bry = ttl_bry;
		int src_len = src.length;
		int pos = 0;
		int bgn, end, atr;
		sects = List_adp_.New();
		begin_end keyword;
		switch (langid) {
			case 1: // german!!!
				keyword = new DE_begin_end();
				break;
			default:
				keyword = new EN_begin_end();
				break;
		}
		while (pos < src_len) {
			b = src[pos++];
			if (b == '<') {
				if (pos + 10 < src_len && (src[pos] | 32) == 's' && (src[pos+1] | 32) == 'e' && (src[pos+2] | 32) == 'c' && (src[pos+3] | 32) == 't' && (src[pos+4] | 32) == 'i' && (src[pos+5] | 32) == 'o' && (src[pos+6] | 32) == 'n' && src[pos+7] == ' ') {
					bgn = pos - 1;
					pos += 8;
					atr = pos;
					while (pos < src_len) {
						b = src[pos++];
						if (b == '>')
							break;
					}
					if (pos == src_len) // no end found
						break;
					end = pos;
					// now find a keyword
					begin_end_result bg = keyword.Find(src, atr, end);
					if (bg != null) {
						sects.Add(new Section(src, bg.start, bg.type, bgn, end));
					}
				}
			}
			else if (b == '\n') { // check for headers
				if (pos + 10 < src_len && src[pos] == '=') {
					int count = 1;
					pos++;
					while (pos < src_len) {
						b = src[pos++];
						if (b != '=')
							break;
						count++;
					}
					// now find the next <nl>
					if (b != '\n') {
						int npos = pos;
						while (npos < src_len) {
							b = src[npos++];
							if (b == '\n')
								break;
						}
						if (b == '\n') {
							// now count any '=' backwards
							int ncount = 0;
							while (npos > pos) {
								b = src[--npos];
								if (b == '=')
									ncount++;
								else
									break;
							}
							if (ncount == count) { // we have a header
								heads.Add(new Header(src, pos, npos, count));
								pos = npos + count;
							}
						}
					}
				}
			}
		}
	}
	public byte[] Include(byte[] from, byte[] to) {
		if		(to == Lst_pfunc_itm.Null_arg) {		// no end arg; EX: {{#lst:page|bgn}}; NOTE: different than {{#lst:page|bgn|}}
			if	(from == Lst_pfunc_itm.Null_arg) {		// no bgn arg; EX: {{#lst:page}}
				return Compile3(src);
			}
			else							// bgn exists; set end to bgn; EX: {{#lst:page|bgn}} is same as {{#lst:page|bgn|bgn}}; NOTE: {{#lst:page|bgn|}} means write from bgn to eos
				to = from;
		}
		Bry_bfr bfr = Bry_bfr_.New();
		byte include_mode = Include_between;
		if		(Bry_.Len_eq_0(to))
			include_mode = Include_to_eos;
		else if (Bry_.Len_eq_0(from))
			include_mode = Include_to_bos;
		int bgn_pos = 0; boolean bgn_found = false; int src_page_bry_len = src.length;
		int sections_len = sects.Len();
		for (int i = 0; i < sections_len; i++) {
			Section sect = (Section)sects.Get_at(i);
			byte section_tid = (byte)sect.type;
			if (section_tid == begin_end_result.BEGIN && Matchkey(sect, from)) {
				int sect_bgn_rhs = sect.end;
				if (include_mode == Include_to_eos) {					// write from cur to eos; EX: {{#lst:page|bgn|}}
					bfr.Add_mid(src, sect_bgn_rhs, src_page_bry_len);
					bgn_found = false;
					break;
				}
				else {													// bgn and end
					if (!bgn_found) {									// NOTE: !bgn_found to prevent "resetting" of dupe; EX: <s begin=key0/>a<s begin=key0/>b; should start from a not b
						bgn_pos = sect_bgn_rhs;
						bgn_found = true;
					}
				}
			}
			else if (section_tid == begin_end_result.END && Matchkey(sect, to)) {
				int sect_end_lhs = sect.bgn;
				if (include_mode == Include_to_bos) {					// write from bos to cur; EX: {{#lst:page||end}}
					bfr.Add_mid(src, 0, sect_end_lhs);
					bgn_found = false;
					break;
				}
				else {
					if (bgn_found) {									// NOTE: bgn_found to prevent writing from bos; EX: a<s end=key0/>b should not write anything 
						bfr.Add_mid(src, bgn_pos, sect_end_lhs);
						bgn_found = false;
					}
				}
			}
		}
		if (bgn_found)	// bgn_found, but no end; write to end of page; EX: "a <section begin=key/> b" -> " b"
			bfr.Add_mid(src, bgn_pos, src_page_bry_len);

		return Compile3(bfr.To_bry());
	}
	public byte[] Exclude(byte[] sect_exclude, byte[] sect_replace) {
		if		(Bry_.Len_eq_0(sect_exclude)) {	// no exclude arg; EX: {{#lstx:page}} or {{#lstx:page}}
			return Compile3(src);							// write all and exit
		}
		int sections_len = sects.Len();
		int bgn_pos = 0;
		Bry_bfr bfr = Bry_bfr_.New();
		for (int i = 0; i < sections_len; i++) {
			Section sect = (Section)sects.Get_at(i);
			byte section_tid = (byte)sect.type;
			if (section_tid == begin_end_result.BEGIN && Matchkey(sect, sect_exclude)) {
				bfr.Add_mid(src, bgn_pos, sect.bgn);									// write everything from bgn_pos up to exclude
			}
			else if (section_tid == begin_end_result.END && Matchkey(sect, sect_exclude)) {	// exclude end found
				if (sect_replace != null)
					bfr.Add(sect_replace);					// write replacement
				bgn_pos = sect.end;	// reset bgn_pos
			}
		}
		bfr.Add_mid(src, bgn_pos, src.length);
		return Compile3(bfr.To_bry());
	}
	public byte[] Header(byte[] lhs_hdr, byte[] rhs_hdr) {
		return Bry_.Empty;
	}
	private boolean Matchkey(Section sect, byte[] find) {
            if (find == Lst_pfunc_itm.Null_arg) return false;
		int pos = sect.keybgn;
		int keylen = sect.keyend - pos;
		int find_end = find.length;
		if (find_end != keylen) return false;
		for (int i = 0; i < find_end; i++) {
			if (src[pos + i] != find[i]) return false;
		}
		return true;
	}
	// need ctx hence wiki and page
	private byte[] Compile(byte[] page_bry) {
            Xop_root_tkn xtn_root = null;
		// set recursing flag
		Xoae_page page = ctx.Page();
		Bry_bfr full_bfr = wiki.Utl__bfr_mkr().Get_m001();
		try {
			wiki.Parser_mgr().Lst__recursing_(true);
			Hash_adp_bry lst_page_regy = ctx.Lst_page_regy(); if (lst_page_regy == null) lst_page_regy = Hash_adp_bry.cs();	// SEE:NOTE:page_regy; DATE:2014-01-01
			page.Html_data().Indicators().Enabled_(Bool_.N);				// disable <indicator> b/c <page> should not add to current page; PAGE:en.s:The_Parochial_System_(Wilberforce,_1838); DATE:2015-04-29
			xtn_root = Bld_root_nde(full_bfr, lst_page_regy, page_bry);	// NOTE: this effectively reparses page twice; needed b/c of "if {| : ; # *, auto add new_line" which can build different tokens
		} finally {
			wiki.Parser_mgr().Lst__recursing_(false);
			full_bfr.Mkr_rls();
		}
		page.Html_data().Indicators().Enabled_(Bool_.Y);
		if (xtn_root == null) return null;
		//html_wtr.Write_tkn_to_html(bfr, ctx, hctx, xtn_root.Root_src(), xnde, Xoh_html_wtr.Sub_idx_null, xtn_root);
                return null;
	}
	private byte[] Compile2(byte[] msg_val) {
		Xowe_wiki wikie = (Xowe_wiki)wiki;
		Xop_ctx sub_ctx = Xop_ctx.New__sub__reuse_page(wikie.Parser_mgr().Ctx());
                sub_ctx.Parse_tid_(Xop_parser_tid_.Tid__wtxt);
		Xop_tkn_mkr tkn_mkr = sub_ctx.Tkn_mkr();
		Xop_root_tkn sub_root = tkn_mkr.Root(msg_val);
		return wikie.Parser_mgr().Main().Expand_tmpl(sub_root, sub_ctx, tkn_mkr, msg_val);
	}
	private byte[] Compile3(byte[] sub_src) {
		// parse page; note adding to stack to prevent circular recursions
		if (!wiki.Parser_mgr().Tmpl_stack_add(ttl.Full_db())) return null;
		Xot_defn_tmpl tmpl = wiki.Parser_mgr().Main().Parse_text_to_defn_obj(sub_ctx, sub_ctx.Tkn_mkr(), ttl.Ns(), ttl_bry, sub_src);	// NOTE: parse as tmpl to ignore <noinclude>
		wiki.Parser_mgr().Tmpl_stack_del();	// take template off stack; evaluate will never recurse, but will fail if ttl is still on stack; DATE:2014-03-10

		// eval tmpl
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_m001();
		try {
			tmpl.Tmpl_evaluate(sub_ctx, Xot_invk_temp.New_root(ttl.Page_txt()), tmp_bfr);
			sub_src = tmp_bfr.To_bry_and_clear();
		} finally {
			tmp_bfr.Mkr_rls();
		}
		return sub_src;
	}
	private Xop_root_tkn Bld_root_nde(Bry_bfr page_bfr, Hash_adp_bry lst_page_regy, byte[] wikitext) {
		Xop_ctx tmp_ctx = Xop_ctx.New__sub__reuse_lst(wiki, ctx, lst_page_regy);
		tmp_ctx.Page().Ttl_(ctx.Page().Ttl());					// NOTE: must set tmp_ctx.Ttl to ctx.Ttl; EX: Flatland and First World; DATE:2013-04-29
		tmp_ctx.Lnki().File_logger_(Xop_file_logger_.Noop);	// NOTE: set file_wkr to null, else items will be double-counted
		tmp_ctx.Parse_tid_(Xop_parser_tid_.Tid__defn);
		Xop_parser tmp_parser = Xop_parser.new_(wiki, wiki.Parser_mgr().Main().Tmpl_lxr_mgr(), wiki.Parser_mgr().Main().Wtxt_lxr_mgr());
		Xop_root_tkn rv = tmp_ctx.Tkn_mkr().Root(wikitext);
		tmp_parser.Parse_text_to_wdom(rv, tmp_ctx, tmp_ctx.Tkn_mkr(), wikitext, Xop_parser_.Doc_bgn_bos);
		return rv;
	}
}

class Section {
	public int keybgn;
	public int keyend;
	public int type;
	public int bgn;
	public int end;
	Section(byte[] src, int keybgn, int type, int bgn, int end) {
		this.type = type;
		this.bgn = bgn;
		this.end = end;
		byte b = src[keybgn];
		if (b == '\'' || b == '"')
			keybgn++;
		keyend = end - 2;
		while (keyend > bgn) {
			b = src[keyend - 1];
			if (b != ' ' && b != '\t' && b != '\n')
				break;
			keyend--;
		}
		if (b == '\'' || b == '"')
			keyend--;
		this.keybgn = keybgn;
	}
}
class Header {
	public int bgn;
	public int end;
	public int level;
	Header(byte[] src, int bgn, int end, int level) {
		this.level = level;
		byte b;
		while (bgn < end) {
			b = src[bgn];
			if (b == ' ' || b == '\t')
				bgn++;
			else
				break;
		}
		this.bgn = bgn;
		while (end > bgn) {
			b = src[end - 1];
			if (b == ' ' || b == '\t')
				bgn--;
			else
				break;
		}
		this.end = end;
	}
}
interface begin_end {
    begin_end_result Find(byte[] src, int bgn, int end);
}

class EN_begin_end implements begin_end {
	public begin_end_result Find(byte[] src, int bgn, int end) {
		while (bgn < end) {
			byte b = src[bgn++];
			switch (b) {
				case 'b':
				case 'B':
					if ((src[bgn] | 32) == 'e' && (src[bgn+1] | 32) == 'g' && (src[bgn+2] | 32) == 'i' && (src[bgn+3] | 32) == 'n' && src[bgn+4] == '=') {
						bgn += 5;
						b = src[bgn];
						return new begin_end_result(begin_end_result.BEGIN, bgn);
					}
					break;
				case 'e':
				case 'E':
					if ((src[bgn] | 32) == 'n' && (src[bgn+1] | 32) == 'd' && src[bgn+2] == '=') {
						bgn += 3;
						b = src[bgn];
						return new begin_end_result(begin_end_result.END, bgn);
					}
					break;
			}
		}
		return null;
	}
}
class DE_begin_end implements begin_end {
	public begin_end_result Find(byte[] src, int bgn, int end) {
		while (bgn < end) {
			byte b = src[bgn++];
			switch (b) {
				case 'b':
				case 'B':
					if ((src[bgn] | 32) == 'e' && (src[bgn+1] | 32) == 'g' && (src[bgn+2] | 32) == 'i' && (src[bgn+3] | 32) == 'n' && src[bgn+4] == '=') {
						bgn += 5;
						b = src[bgn];
						return new begin_end_result(begin_end_result.BEGIN, bgn);
					}
					break;
				// End
				// Ende
				case 'e':
				case 'E':
					if  ((src[bgn] | 32) == 'n' && (src[bgn+1] | 32) == 'd') {
						if (src[bgn+2] == '=') {
							bgn += 3;
						}
						else if ((src[bgn+2] | 32) == 'e' && src[bgn+3] == '=') {
							bgn += 4;
						}
						else
							break;
						b = src[bgn];
						return new begin_end_result(begin_end_result.END, bgn);
					}
					break;
				// Anfang
				case 'a':
				case 'A':
					if ((src[bgn] | 32) == 'n' && (src[bgn+1] | 32) == 'f' && (src[bgn+2] | 32) == 'a' && (src[bgn+3] | 32) == 'n' && (src[bgn+4] | 32) == 'g' && src[bgn+5] == '=') {
						bgn += 6;
						b = src[bgn];
						return new begin_end_result(begin_end_result.END, bgn);
					}
					break;
			}
		}
		return null;
	}
}
class begin_end_result {
	public static int BEGIN = 1;
	public static int END = 2;
	public int type;
	public int start;
	begin_end_result(int type, int start) {
		this.type = type;
		this.start = start;
	}
}