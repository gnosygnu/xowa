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
package gplx.php; import gplx.*;
public class Php_parser {
	Php_lxr[] lxrs; int lxrs_len;
	int txt_bgn; Php_tkn_txt txt_tkn;
	private ByteTrieMgr_slim trie = ByteTrieMgr_slim.ci_ascii_();	// NOTE:ci:PHP tkns are ASCII
	byte[] src; int src_len; Php_tkn_wkr tkn_wkr; Php_tkn_factory tkn_factory = new Php_tkn_factory(); Php_ctx ctx = new Php_ctx();
	Php_parser_interrupt[] parser_interrupts = new Php_parser_interrupt[256]; 
	public Php_parser() {
		ListAdp list = ListAdp_.new_();
		Init_lxr(list, new Php_lxr_declaration());
		Init_lxr(list, new Php_lxr_ws(Php_tkn_ws.Tid_space));
		Init_lxr(list, new Php_lxr_ws(Php_tkn_ws.Tid_nl));
		Init_lxr(list, new Php_lxr_ws(Php_tkn_ws.Tid_tab));
		Init_lxr(list, new Php_lxr_ws(Php_tkn_ws.Tid_cr));
		Init_lxr(list, new Php_lxr_comment(Php_tkn_comment.Tid_mult));
		Init_lxr(list, new Php_lxr_comment(Php_tkn_comment.Tid_slash));
		Init_lxr(list, new Php_lxr_comment(Php_tkn_comment.Tid_hash));
		Init_lxr(list, new Php_lxr_var());
		Init_lxr(list, new Php_lxr_sym(";", Php_tkn_.Tid_semic));
		Init_lxr(list, new Php_lxr_sym("=", Php_tkn_.Tid_eq));
		Init_lxr(list, new Php_lxr_sym("=>", Php_tkn_.Tid_eq_kv));
		Init_lxr(list, new Php_lxr_sym(",", Php_tkn_.Tid_comma));
		Init_lxr(list, new Php_lxr_sym("(", Php_tkn_.Tid_paren_bgn));
		Init_lxr(list, new Php_lxr_sym(")", Php_tkn_.Tid_paren_end));
		Init_lxr(list, new Php_lxr_sym("[", Php_tkn_.Tid_brack_bgn));
		Init_lxr(list, new Php_lxr_sym("]", Php_tkn_.Tid_brack_end));
		Init_lxr(list, new Php_lxr_keyword("null", Php_tkn_.Tid_null));
		Init_lxr(list, new Php_lxr_keyword("false", Php_tkn_.Tid_false));
		Init_lxr(list, new Php_lxr_keyword("true", Php_tkn_.Tid_true));
		Init_lxr(list, new Php_lxr_keyword("array", Php_tkn_.Tid_ary));
		Init_lxr(list, new Php_lxr_num());
		Init_lxr(list, new Php_lxr_quote(Byte_ascii.Apos));
		Init_lxr(list, new Php_lxr_quote(Byte_ascii.Quote));
		lxrs = (Php_lxr[])list.XtoAry(Php_lxr.class);
		lxrs_len = list.Count();
	}
	private void Init_lxr(ListAdp list, Php_lxr lxr) {
		lxr.Lxr_ini(trie, parser_interrupts);
		list.Add(lxr);
	}
	public void Parse_tkns(String src, Php_tkn_wkr tkn_wkr) {Parse_tkns(Bry_.new_utf8_(src), tkn_wkr);}
	public void Parse_tkns(byte[] src, Php_tkn_wkr tkn_wkr) {
		this.src = src; this.src_len = src.length; this.tkn_wkr = tkn_wkr;
		ctx.Src_(src);
		tkn_wkr.Init(ctx);
		if (src_len == 0) return;
		
		for (int i = 0; i < lxrs_len; i++)
			lxrs[i].Lxr_bgn(src, src_len, tkn_wkr, tkn_factory);
		
		int pos = 0;
		byte b = src[pos];
		txt_tkn = null; txt_bgn = 0;
		boolean loop_raw = true, loop_txt = true;
		while (loop_raw) {
			Object o = trie.Match(b, src, pos, src_len);
			if (o == null) {		// char does not hook into a lxr
				loop_txt = true;
				while (loop_txt) {	// keep looping until end of String or parser_interrupt 
					++pos;
					if (pos == src_len) {loop_raw = false; break;}
					b = src[pos];
					if (parser_interrupts[b & 0xFF] == Php_parser_interrupt.Char) {
						Make_txt(txt_bgn, pos);
						break;
					}
				}
				if (!loop_raw) break;
				continue;	// continue b/c b is set to interrupt char, and should be matched against trie
			}
			else {	// char hooks into lxr
				if (txt_bgn != pos)	// txt_bgn is set; make text tkn
					Make_txt(txt_bgn, pos);
				Php_lxr lxr = (Php_lxr)o;
				int match_pos = trie.Match_pos();
				int make_pos = lxr.Lxr_make(ctx, pos, match_pos);
				if (make_pos == Php_parser.NotFound) {
					Make_txt(txt_bgn, pos);
					++pos;
				}
				else {
					txt_tkn = null;
					txt_bgn = pos = make_pos;
				}
			}
			if (pos == src_len) break;
			b = src[pos];
		}
		if (txt_bgn != pos)
			Make_txt(txt_bgn, pos);
	}
	int Make_txt(int bgn, int end) {
		if (txt_tkn == null) {
			txt_tkn = tkn_factory.Txt(bgn, end);
			tkn_wkr.Process(txt_tkn);
		}
		else
			txt_tkn.Src_end_(end);
		return end;
	}
	public static final int NotFound = -1;
	public static final Gfo_msg_grp Log_nde = Gfo_msg_grp_.new_(Gfo_msg_grp_.Root_gplx, "php_parser");
}
