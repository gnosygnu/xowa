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
package gplx.langs.gfs; import gplx.*; import gplx.langs.*;
import gplx.core.btries.*;
public class Gfs_parser {
	private final    Btrie_fast_mgr trie = Gfs_parser_.trie_();
	private final    Gfs_parser_ctx ctx = new Gfs_parser_ctx();
	public Gfs_nde Parse(byte[] src) {
		ctx.Root().Subs_clear();
		int src_len = src.length; if (src_len == 0) return ctx.Root();
		ctx.Init(trie, src, src_len);
		int pos = 0;
		while (pos < src_len) {
			byte b = src[pos];
			Object o = trie.Match_at_w_b0(ctx.Trie_rv(), b, src, pos, src_len);
			if (o == null)
				ctx.Err_mgr().Fail_unknown_char(ctx, pos, b); 
			else {
				Gfs_lxr lxr = (Gfs_lxr)o;
				while (lxr != null) {
					int rslt = lxr.Process(ctx, pos, ctx.Trie_rv().Pos());
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
		Btrie_fast_mgr rv = Btrie_fast_mgr.ci_a7();	// NOTE:ci.ascii:gfs;letters/symbols only;
		Gfs_lxr_identifier word_lxr = Gfs_lxr_identifier.Instance;
		trie_add_rng(rv, word_lxr, Byte_ascii.Ltr_a, Byte_ascii.Ltr_z);
		trie_add_rng(rv, word_lxr, Byte_ascii.Ltr_A, Byte_ascii.Ltr_Z);
		trie_add_rng(rv, word_lxr, Byte_ascii.Num_0, Byte_ascii.Num_9);
		rv.Add(Byte_ascii.Underline, word_lxr);
		trie_add_many(rv, Gfs_lxr_whitespace.Instance, Byte_ascii.Space, Byte_ascii.Nl, Byte_ascii.Cr, Byte_ascii.Tab);
		trie_add_quote(rv, new byte[] {Byte_ascii.Apos});
		trie_add_quote(rv, new byte[] {Byte_ascii.Quote});
		trie_add_quote(rv, Bry_.new_a7("<:[\"\n"), Bry_.new_a7("\n\"]:>"));
		trie_add_quote(rv, Bry_.new_a7("<:['\n"), Bry_.new_a7("\n']:>"));
		trie_add_quote(rv, Bry_.new_a7("<:{'"), Bry_.new_a7("'}:>"));
		trie_add_comment(rv, new byte[] {Byte_ascii.Slash, Byte_ascii.Slash}, new byte[] {Byte_ascii.Nl});
		trie_add_comment(rv, new byte[] {Byte_ascii.Slash, Byte_ascii.Star}, new byte[] {Byte_ascii.Star, Byte_ascii.Slash});
		rv.Add(Byte_ascii.Semic, Gfs_lxr_semic.Instance);
		rv.Add(Byte_ascii.Paren_bgn, Gfs_lxr_paren_bgn.Instance);
		rv.Add(Byte_ascii.Paren_end, Gfs_lxr_paren_end.Instance);
		rv.Add(Byte_ascii.Curly_bgn, Gfs_lxr_curly_bgn.Instance);
		rv.Add(Byte_ascii.Curly_end, Gfs_lxr_curly_end.Instance);
		rv.Add(Byte_ascii.Dot, Gfs_lxr_dot.Instance);
		rv.Add(Byte_ascii.Comma, Gfs_lxr_comma.Instance);
		rv.Add(Byte_ascii.Eq, Gfs_lxr_equal.Instance);
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
