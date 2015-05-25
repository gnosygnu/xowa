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
package gplx.gfs; import gplx.*;
import gplx.core.btries.*;
public class Gfs_parser {
	Btrie_fast_mgr trie = Gfs_parser_.trie_();
	Gfs_parser_ctx ctx = new Gfs_parser_ctx();
	public Gfs_nde Parse(byte[] src) {
		ctx.Root().Subs_clear();
		int src_len = src.length; if (src_len == 0) return ctx.Root();
		ctx.Init(trie, src, src_len);
		int pos = 0;
		while (pos < src_len) {
			byte b = src[pos];
			Object o = trie.Match_bgn_w_byte(b, src, pos, src_len);
			if (o == null)
				ctx.Err_mgr().Fail_unknown_char(ctx, pos, b); 
			else {
				Gfs_lxr lxr = (Gfs_lxr)o;
				while (lxr != null) {
					int rslt = lxr.Process(ctx, pos, trie.Match_pos());
					switch (lxr.Lxr_tid()) {
						case Gfs_lxr_.Tid_whitespace: break; 
						case Gfs_lxr_.Tid_comment: break; 
						default: ctx.Prv_lxr_(lxr.Lxr_tid()); break;
					}
					switch (rslt) {
						case Gfs_lxr_.Rv_lxr:
							pos = ctx.Nxt_pos();
							lxr = ctx.Nxt_lxr();
							break;
						case Gfs_lxr_.Rv_eos:
							pos = src_len;
							lxr = null;
							break;
						default:
							pos = rslt;
							lxr = null;
							break;
					}
				}
			}
		}
		switch (ctx.Prv_lxr()) {
			case Gfs_lxr_.Tid_curly_end:
			case Gfs_lxr_.Tid_semic:		break;
			default:						ctx.Err_mgr().Fail_eos(ctx);  break;
		}
		return ctx.Root();
	}
}
class Gfs_parser_ {
	public static Btrie_fast_mgr trie_() {
		Btrie_fast_mgr rv = Btrie_fast_mgr.ci_ascii_();	// NOTE:ci.ascii:gfs;letters/symbols only;
		Gfs_lxr_identifier word_lxr = Gfs_lxr_identifier._;
		trie_add_rng(rv, word_lxr, Byte_ascii.Ltr_a, Byte_ascii.Ltr_z);
		trie_add_rng(rv, word_lxr, Byte_ascii.Ltr_A, Byte_ascii.Ltr_Z);
		trie_add_rng(rv, word_lxr, Byte_ascii.Num_0, Byte_ascii.Num_9);
		rv.Add(Byte_ascii.Underline, word_lxr);
		trie_add_many(rv, Gfs_lxr_whitespace._, Byte_ascii.Space, Byte_ascii.NewLine, Byte_ascii.CarriageReturn, Byte_ascii.Tab);
		trie_add_quote(rv, new byte[] {Byte_ascii.Apos});
		trie_add_quote(rv, new byte[] {Byte_ascii.Quote});
		trie_add_quote(rv, Bry_.new_a7("<:[\"\n"), Bry_.new_a7("\n\"]:>"));
		trie_add_quote(rv, Bry_.new_a7("<:['\n"), Bry_.new_a7("\n']:>"));
		trie_add_comment(rv, new byte[] {Byte_ascii.Slash, Byte_ascii.Slash}, new byte[] {Byte_ascii.NewLine});
		trie_add_comment(rv, new byte[] {Byte_ascii.Slash, Byte_ascii.Asterisk}, new byte[] {Byte_ascii.Asterisk, Byte_ascii.Slash});
		rv.Add(Byte_ascii.Semic, Gfs_lxr_semic._);
		rv.Add(Byte_ascii.Paren_bgn, Gfs_lxr_paren_bgn._);
		rv.Add(Byte_ascii.Paren_end, Gfs_lxr_paren_end._);
		rv.Add(Byte_ascii.Curly_bgn, Gfs_lxr_curly_bgn._);
		rv.Add(Byte_ascii.Curly_end, Gfs_lxr_curly_end._);
		rv.Add(Byte_ascii.Dot, Gfs_lxr_dot._);
		rv.Add(Byte_ascii.Comma, Gfs_lxr_comma._);
		rv.Add(Byte_ascii.Eq, Gfs_lxr_equal._);
		return rv;
	}
	private static void trie_add_rng(Btrie_fast_mgr trie, Gfs_lxr lxr, byte bgn, byte end) {
		for (byte b = bgn; b <= end; b++)
			trie.Add(b, lxr);
	}
	private static void trie_add_many(Btrie_fast_mgr trie, Gfs_lxr lxr, byte... ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++)
			trie.Add(ary[i], lxr);
	}
	private static void trie_add_quote(Btrie_fast_mgr trie, byte[] bgn)				{trie_add_quote(trie, bgn, bgn);}
	private static void trie_add_quote(Btrie_fast_mgr trie, byte[] bgn, byte[] end)	{trie.Add(bgn, new Gfs_lxr_quote(bgn, end));}
	private static void trie_add_comment(Btrie_fast_mgr trie, byte[] bgn, byte[] end) {trie.Add(bgn, new Gfs_lxr_comment_flat(bgn, end));}
}
