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
class Gfs_parser_ctx {
	public Btrie_fast_mgr Trie() {return trie;} Btrie_fast_mgr trie;
	public Btrie_rv Trie_rv() {return trie_rv;} private final    Btrie_rv trie_rv = new Btrie_rv();
	public Gfs_nde Root() {return root;} Gfs_nde root = new Gfs_nde();
	public byte[] Src() {return src;} private byte[] src;
	public int Src_len() {return src_len;} private int src_len;
	public int Prv_lxr() {return prv_lxr;} public Gfs_parser_ctx Prv_lxr_(int v) {prv_lxr = v; return this;} private int prv_lxr;
	public Gfs_nde Cur_nde() {return cur_nde;} Gfs_nde cur_nde;
	public int Nxt_pos() {return nxt_pos;} private int nxt_pos;
	public Gfs_lxr Nxt_lxr() {return nxt_lxr;} Gfs_lxr nxt_lxr;
	public Bry_bfr Tmp_bfr() {return tmp_bfr;} private Bry_bfr tmp_bfr = Bry_bfr_.New();
	public void Process_eos() {}
	public void Process_lxr(int nxt_pos, Gfs_lxr nxt_lxr)	{this.nxt_pos = nxt_pos; this.nxt_lxr = nxt_lxr;}
	public void Process_null(int cur_pos)					{this.nxt_pos = cur_pos; this.nxt_lxr = null;}
	public void Init(Btrie_fast_mgr trie, byte[] src, int src_len) {
		this.trie = trie; this.src = src; this.src_len = src_len;
		cur_nde = root;
		Stack_add();
	}
	public void Hold_word(int bgn, int end) {
		cur_idf_bgn = bgn;
		cur_idf_end = end;
	}	int cur_idf_bgn = -1, cur_idf_end = -1;
	private void Held_word_clear() {cur_idf_bgn = -1; cur_idf_end = -1;}
	public Gfs_nde Make_nde(int tkn_bgn, int tkn_end) {	// "abc."; "abc("; "abc;"; "abc{"
		Gfs_nde nde = new Gfs_nde().Name_rng_(cur_idf_bgn, cur_idf_end);
		this.Held_word_clear();
		cur_nde.Subs_add(nde);
		cur_nde = nde;
		return nde;
	}
	public void Make_atr_by_idf()								{Make_atr(cur_idf_bgn, cur_idf_end); Held_word_clear();}
	public void Make_atr_by_bry(int bgn, int end, byte[] bry)	{Make_atr(bgn, end).Name_(bry);}
	public Gfs_nde Make_atr(int bgn, int end) {
		Gfs_nde nde = new Gfs_nde().Name_rng_(bgn, end);
		cur_nde.Atrs_add(nde);
		return nde;
	}
	public void Cur_nde_from_stack() {cur_nde = (Gfs_nde)nodes.Get_at_last();}
	public void Stack_add() {nodes.Add(cur_nde);} List_adp nodes = List_adp_.New();
	public void Stack_pop(int pos) {
		if (nodes.Count() < 2) err_mgr.Fail_nde_stack_empty(this, pos);	// NOTE: need at least 2 items; 1 to pop and 1 to set as current
		List_adp_.Del_at_last(nodes);
		Cur_nde_from_stack();
	}
	public Gfs_err_mgr Err_mgr() {return err_mgr;} Gfs_err_mgr err_mgr = new Gfs_err_mgr();
}
class Gfs_err_mgr {
	public void Fail_eos(Gfs_parser_ctx ctx) {Fail(ctx, Fail_msg_eos, ctx.Src_len());}
	public void Fail_unknown_char(Gfs_parser_ctx ctx, int pos, byte c) {Fail(ctx, Fail_msg_unknown_char, pos, Keyval_.new_("char", Char_.To_str((char)c)));}
	public void Fail_nde_stack_empty(Gfs_parser_ctx ctx, int pos) {Fail(ctx, Fail_msg_nde_stack_empty, pos);}
	public void Fail_invalid_lxr(Gfs_parser_ctx ctx, int pos, int lxr_tid, byte c) {
		Fail(ctx, Fail_msg_invalid_lxr, pos, Keyval_.new_("char", Char_.To_str((char)c)), Keyval_.new_("cur_lxr", Gfs_lxr_.Tid__name(lxr_tid)), Keyval_.new_("prv_lxr", Gfs_lxr_.Tid__name(ctx.Prv_lxr())));
	}
	private void Fail(Gfs_parser_ctx ctx, String msg, int pos, Keyval... args) {
		byte[] src = ctx.Src(); int src_len = ctx.Src_len(); 
		Fail_args_standard(src, src_len, pos);
		int len = args.length;
		for (int i = 0; i < len; i++) {
			Keyval arg = args[i];
			tmp_fail_args.Add(arg.Key(), arg.Val_to_str_or_empty());
		}
		throw Err_.new_wo_type(Fail_msg(msg, tmp_fail_args));
	}
	private void Fail_args_standard(byte[] src, int src_len, int pos) {
		tmp_fail_args.Add("excerpt_bgn", Fail_excerpt_bgn(src, src_len, pos));		
		tmp_fail_args.Add("excerpt_end", Fail_excerpt_end(src, src_len, pos));		
		tmp_fail_args.Add("pos"	, pos);		
	}
	public static final    String Fail_msg_invalid_lxr = "invalid character", Fail_msg_unknown_char = "unknown char", Fail_msg_eos = "end of stream", Fail_msg_nde_stack_empty = "node stack empty";
	String Fail_msg(String type, Keyval_list fail_args) {
		tmp_fail_bfr.Add_str_u8(type).Add_byte(Byte_ascii.Colon);
		int len = fail_args.Count();
		for (int i = 0; i < len; i++) {
			tmp_fail_bfr.Add_byte(Byte_ascii.Space);
			Keyval kv = fail_args.Get_at(i);
			tmp_fail_bfr.Add_str_u8(kv.Key());
			tmp_fail_bfr.Add_byte(Byte_ascii.Eq).Add_byte(Byte_ascii.Apos);
			tmp_fail_bfr.Add_str_u8(kv.Val_to_str_or_empty()).Add_byte(Byte_ascii.Apos);
		}
		return tmp_fail_bfr.To_str_and_clear();
	}
	Bry_bfr tmp_fail_bfr = Bry_bfr_.Reset(255);
	Keyval_list tmp_fail_args = new Keyval_list();
	private static int excerpt_len = 50;
	String Fail_excerpt_bgn(byte[] src, int src_len, int pos) {
		int bgn = pos - excerpt_len; if (bgn < 0) bgn = 0;
		Fail_excerpt_rng(tmp_fail_bfr, src, bgn, pos);
		return tmp_fail_bfr.To_str_and_clear();
	}
	String Fail_excerpt_end(byte[] src, int src_len, int pos) {
		int end = pos + excerpt_len; if (end > src_len) end = src_len;
		Fail_excerpt_rng(tmp_fail_bfr, src, pos, end);
		return tmp_fail_bfr.To_str_and_clear();
	}
	private static void Fail_excerpt_rng(Bry_bfr bfr, byte[] src, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Tab: 			bfr.Add(Esc_tab); break;
				case Byte_ascii.Nl:		bfr.Add(Esc_nl); break;
				case Byte_ascii.Cr: bfr.Add(Esc_cr); break;
				default:						bfr.Add_byte(b); break;
			}
		}
	}
	private static final    byte[] Esc_nl = Bry_.new_a7("\\n"), Esc_cr = Bry_.new_a7("\\r"), Esc_tab = Bry_.new_a7("\\t");
}
