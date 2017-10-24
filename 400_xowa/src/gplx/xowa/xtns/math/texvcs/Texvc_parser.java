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
package gplx.xowa.xtns.math.texvcs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.math.*;
import gplx.core.btries.*;
import gplx.xowa.xtns.math.texvcs.lxrs.*; import gplx.xowa.xtns.math.texvcs.tkns.*;
public class Texvc_parser {
	private final    Btrie_rv trv = new Btrie_rv();
	public void Parse(Texvc_ctx ctx, Texvc_root root, byte[] src) {
		int src_len = src.length;
		ctx.Clear();
		root.Init_as_root(ctx.Tkn_mkr(), src, 0, src.length);
		Parse(root, ctx, ctx.Lxr_trie(), src, src_len, 0, src_len);
	}
	private int Parse(Texvc_root root, Texvc_ctx ctx, Btrie_fast_mgr lxr_trie, byte[] src, int src_len, int bgn_pos, int end_pos) {
		int pos = bgn_pos;
		int txt_bgn = pos, txt_uid = -1;
		byte b = src[pos];
		while (true) {
			Object o = lxr_trie.Match_at_w_b0(trv, b, src, pos, src_len);
			if (o == null)				// no lxr found; char is txt; increment pos
				pos++;
			else {						// lxr found
				Texvc_lxr lxr = (Texvc_lxr)o;
				if (txt_bgn != pos)	txt_uid = Txt_calc(ctx, root, src, src_len, pos, txt_bgn, txt_uid);// chars exist between pos and txt_bgn; make txt_tkn;						
				pos = lxr.Make_tkn(ctx, root, src, src_len, pos, trv.Pos());
				if (pos > 0) {txt_bgn = pos; txt_uid = -1;}	// reset txt_tkn
			}
			if (pos == end_pos) break;
			b = src[pos];
		}
		if (txt_bgn != pos) txt_uid = Txt_calc(ctx, root, src, src_len, src_len, txt_bgn, txt_uid);
		return pos;
	}
	private static int Txt_calc(Texvc_ctx ctx, Texvc_root root, byte[] src, int src_len, int bgn_pos, int txt_bgn, int txt_uid) {
		if (txt_uid == -1)						// no existing txt_tkn; create new one
			txt_uid = root.Regy__add(Texvc_tkn_.Tid__text, Texvc_tkn_.Tid__text, txt_bgn, bgn_pos, null);
		else									// existing txt_tkn; happens for false matches; EX: abc[[\nef[[a]]; see NOTE_1
			root.Regy__update_end(txt_uid, bgn_pos);
		return txt_uid;
	}
}
