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
package gplx.xowa.langs.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.core.btries.*;
public class Xop_vnt_lxr_ {
	public static void set_(Xowe_wiki wiki) {
		Btrie_fast_mgr wiki_trie = wiki.Parser().Wtxt_trie();
		Object exists = wiki_trie.Match_bgn(Xop_vnt_lxr_.Hook_bgn, 0, Xop_vnt_lxr_.Hook_bgn.length);
		if (exists == null) {
			Xop_vnt_lxr_eqgt._.Init_by_wiki(wiki, wiki_trie);
			Xop_vnt_lxr_bgn._.Init_by_wiki(wiki, wiki_trie);
			new Xop_vnt_lxr_end().Init_by_wiki(wiki, wiki_trie);
			// Btrie_fast_mgr tmpl_trie = wiki.Parser().Tmpl_trie();	// do not add to tmpl trie
			// Xop_vnt_lxr_bgn._.Init_by_wiki(wiki, tmpl_trie);
		}
	}
	public static final byte[] Hook_bgn = new byte[] {Byte_ascii.Dash, Byte_ascii.Curly_bgn}, Hook_end = new byte[] {Byte_ascii.Curly_end, Byte_ascii.Dash};
}
class Xop_vnt_lxr_eqgt implements Xop_lxr {
	public byte Lxr_tid() {return Xop_lxr_.Tid_vnt_eqgt;}
	public void Init_by_wiki(Xowe_wiki wiki, Btrie_fast_mgr core_trie) {core_trie.Add(Hook, this);}
	public void Init_by_lang(Xol_lang lang, Btrie_fast_mgr core_trie) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		ctx.Subs_add_and_stack(root, tkn_mkr.Vnt_eqgt(bgn_pos, cur_pos));
		return cur_pos;
	}
	public static final byte[] Hook = new byte[] {Byte_ascii.Eq, Byte_ascii.Gt};
	public static final Xop_vnt_lxr_eqgt _ = new Xop_vnt_lxr_eqgt(); Xop_vnt_lxr_eqgt() {}
}
class Xop_vnt_lxr_bgn implements Xop_lxr {
	public byte Lxr_tid() {return Xop_lxr_.Tid_vnt_bgn;}
	public void Init_by_wiki(Xowe_wiki wiki, Btrie_fast_mgr core_trie) {core_trie.Add(Xop_vnt_lxr_.Hook_bgn, this);}
	public void Init_by_lang(Xol_lang lang, Btrie_fast_mgr core_trie) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		ctx.Subs_add_and_stack(root, tkn_mkr.Vnt(bgn_pos, cur_pos));
		return cur_pos;
	}
	public static final Xop_vnt_lxr_bgn _ = new Xop_vnt_lxr_bgn(); Xop_vnt_lxr_bgn() {}
}
class Xop_vnt_lxr_end implements Xop_lxr {
	private Xop_vnt_flag_parser flag_parser;
	private Xop_vnt_rules_parser rule_parser;
	public byte Lxr_tid() {return Xop_lxr_.Tid_vnt_end;}
	public void Init_by_wiki(Xowe_wiki wiki, Btrie_fast_mgr core_trie) {
		core_trie.Add(Xop_vnt_lxr_.Hook_end, this);
		Xol_vnt_mgr vnt_mgr = wiki.Lang().Vnt_mgr();
		flag_parser = new Xop_vnt_flag_parser(vnt_mgr);
		rule_parser = new Xop_vnt_rules_parser(vnt_mgr);
	}
	public void Init_by_lang(Xol_lang lang, Btrie_fast_mgr core_trie) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		int stack_pos = ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_vnt);
		if (stack_pos == Xop_ctx.Stack_not_found) return ctx.Lxr_make_txt_(cur_pos);	// "}-" found but no "-{" in stack;
		Xop_vnt_tkn vnt_tkn = (Xop_vnt_tkn)ctx.Stack_pop_til(root, src, stack_pos, false, bgn_pos, cur_pos, Xop_tkn_itm_.Tid_vnt);
		Xowe_wiki wiki = ctx.Wiki();
		try {
			vnt_tkn.Src_end_(cur_pos);
			vnt_tkn.Subs_move(root);
			Xop_vnt_flag[] vnt_flag_ary = Xop_vnt_flag_.Ary_empty;
			int rule_subs_bgn = 0;
			int pipe_tkn_count = vnt_tkn.Vnt_pipe_tkn_count();
			if (pipe_tkn_count > 0) {
				flag_parser.Parse(wiki, vnt_tkn, pipe_tkn_count, src);
				vnt_flag_ary = flag_parser.Rslt_flags();
				rule_subs_bgn = flag_parser.Rslt_tkn_pos();
				vnt_tkn.Vnt_pipe_idx_last_(flag_parser.Rslt_pipe_last());
			}
			vnt_tkn.Vnt_flags_(vnt_flag_ary);
			Xop_vnt_rule[] rules = rule_parser.Parse(ctx, vnt_tkn, src, rule_subs_bgn);
			vnt_tkn.Vnt_rules_(rules);
			vnt_tkn.Vnt_cmd_calc(wiki, ctx.Cur_page(), ctx, src);
		}
		catch (Exception e) {
			ctx.App().Usr_dlg().Warn_many("", "", "vnt.parse failed: page=~{0} src=~{1} err=~{2}", ctx.Cur_page().Ttl().Raw(), String_.new_u8(src, bgn_pos, cur_pos), Err_.Message_gplx_full(e));
			if (vnt_tkn != null)
				root.Subs_add(tkn_mkr.Bry_mid(src, vnt_tkn.Src_bgn(), cur_pos));
		}
		return cur_pos;
	}
}
