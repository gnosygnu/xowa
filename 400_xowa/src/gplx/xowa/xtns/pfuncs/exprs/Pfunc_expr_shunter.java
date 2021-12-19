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
package gplx.xowa.xtns.pfuncs.exprs;
import gplx.core.btries.Btrie_fast_mgr;
import gplx.core.btries.Btrie_rv;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.custom.brys.fmts.fmtrs.BryFmtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.CharUtl;
import gplx.types.commons.GfoDecimal;
import gplx.xowa.langs.msgs.Xol_msg_itm_;
import gplx.xowa.parsers.Xop_ctx;
public class Pfunc_expr_shunter {
	private final Btrie_fast_mgr trie = expression_(); private final Btrie_rv trv = new Btrie_rv();
	private final Val_stack val_stack = new Val_stack();
	private final Func_tkn_stack prc_stack = new Func_tkn_stack();
	private final BryFmtr tmp_fmtr = BryFmtr.NewTmp();
	public BryWtr Err() {return err_bfr;} private final BryWtr err_bfr = BryWtr.New();
	public GfoDecimal Err_set(Xop_ctx ctx, int msgId) {return Err_set(ctx, msgId, BryUtl.Empty);}
	public GfoDecimal Err_set(Xop_ctx ctx, int msg_id, byte[] arg) {
		byte[] msg_val = ctx.Wiki().Msg_mgr().Val_by_id(msg_id);
		err_bfr.Clear().Add(Err_bgn_ary);
		tmp_fmtr.FmtSet(msg_val).BldToBfrObj(err_bfr, arg);
		err_bfr.Add(Err_end_ary);
		return Null_rslt;
	}
	public void Rslt_set(byte[] bry) {
		err_bfr.Add(bry);
	}
	public GfoDecimal Evaluate(Xop_ctx ctx, byte[] src) {	// REF.MW: Expr.php
		int src_len = src.length; if (src_len == 0) return Null_rslt;
		int cur_pos = 0; byte cur_byt = src[0];
		boolean mode_expr = true; Func_tkn prv_prc = null;
		val_stack.Clear(); prc_stack.Clear();
		while (true) {
			// can't think of a way for this to happen; note that operators will automatically push values/operators off stack that are lower; can't get up to 100 
			// if (val_stack.Len() > 100 || prc_stack.Len() > 100) return Err_set(ctx, Xol_msg_itm_.Id_pfunc_expr_err__stack_exhausted);
			Object o = trie.Match_at_w_b0(trv, cur_byt, src, cur_pos, src_len);
			int bgn_pos = cur_pos;
			if (o == null) {	// letter or unknown symbol
				while (cur_pos < src_len) {
					byte b = src[cur_pos++];
					if (AsciiByte.IsLtr(b))
						continue;
					else
						break;
				}
				return Err_set(ctx, Xol_msg_itm_.Id_pfunc_expr_unrecognised_word, BryLni.Mid(src, bgn_pos, cur_pos));
			}
			else {
				Expr_tkn t = (Expr_tkn)o;
				cur_pos = trv.Pos();
				switch (t.Tid()) {
					case Expr_tkn_.Tid_space: break;
					case Expr_tkn_.Tid_number:
						if (!mode_expr) return Err_set(ctx, Xol_msg_itm_.Id_pfunc_expr_unexpected_number);
						int numBgn = cur_pos - 1;
						boolean loop = true;
						while (loop) {
							if (cur_pos == src_len) break;
							switch (src[cur_pos]) {
								case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
								case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
								case AsciiByte.Dot:
									++cur_pos;
									break;
								default: loop = false; break;
							}
						}
						GfoDecimal num = Null_rslt;
						try {num = BryUtl.ToDecimal(src, numBgn, cur_pos);}
						catch (Exception exc) {
							// NOTE: PATCH.PHP: 65.5.5 can evaluate to 65.5; EX "{{Geological eras|-600|height=2|border=none}}" eventually does "|10-to={{#ifexpr:{{{1|-4567}}}<-65.5|-65.5|{{{1}}}}}.5" which is 65.5.5

							int dot_count = 0;
							for (int i = numBgn; i < cur_pos; i++) {
								if (src[i] == AsciiByte.Dot) {
									switch (dot_count) {
										case 0: dot_count = 1; break;
										case 1: 
											try {
												num = BryUtl.ToDecimal(src, numBgn, i);
											}
											catch (Exception exc_inner) {}
											break;
									}
								}
							}
							if (num == null) return Null_rslt;
						}
						num = num.RoundToDefaultPrecision(); // PURPOSE: number should be set to precision of 14; PAGE:de.wikipedia.org/wiki/Nationalpark_Mu_Ko_Ang_Thong ISSUE#:683 DATE:2020-03-24
						val_stack.Push(num);
						mode_expr = false;
						break;
					case Expr_tkn_.Tid_paren_lhs:
						if (!mode_expr) return Err_set(ctx, Xol_msg_itm_.Id_pfunc_expr_unexpected_operator, BryUtl.NewA7("("));
						prc_stack.Push((Func_tkn)t);
						break;
					case Expr_tkn_.Tid_operator:
						Func_tkn cur_prc = (Func_tkn)t;
						if (AsciiByte.IsLtr(cur_byt)) {
							int nxt_pos = BryFind.FindFwdWhileLetter(src, cur_pos, src_len);
							if (nxt_pos > cur_pos)
								return Err_set(ctx, Xol_msg_itm_.Id_pfunc_expr_unrecognised_word, BryLni.Mid(src, bgn_pos, nxt_pos));
						}
						if (cur_prc.Func_is_const()) {		// func is "pi" or "e"; DATE:2014-03-01
							if (mode_expr) {				// number expected; just call Calc (which will place value on stack)
								cur_prc.Calc(ctx, this, val_stack);
								mode_expr = false;          // 2020-11-25|ISSUE#:819|Set mode_expr to false which forces next term to be operator not number. EX: `pi0` -> fails
								break;
							}
							else							// operator expected; fail b/c pi / e is not an operator;
								return Err_set(ctx, Xol_msg_itm_.Id_pfunc_expr_unexpected_number);
						}
						if (mode_expr) {	// NOTE: all the GetAlts have higher precedence; "break;" need to skip evaluation below else will fail for --1
							Func_tkn alt_prc = cur_prc.GetAlt();
							prc_stack.Push(alt_prc);
							if (alt_prc == Func_tkn_e_const.Instance) {
								mode_expr = false;          // 2020-11-25|ISSUE#:819|Set mode_expr to false which forces next term to be operator not number. EX: `e0` -> fails
							}
							break;
						}
						prv_prc = prc_stack.Get_at_last();
						while (prv_prc != null && (cur_prc.Precedence() <= prv_prc.Precedence())) {
							if (!prv_prc.Calc(ctx, this, val_stack)) return Null_rslt;
							prc_stack.Pop();
							prv_prc = prc_stack.Get_at_last();
						}
						prc_stack.Push(cur_prc);
						mode_expr = true;
						break;
					case Expr_tkn_.Tid_paren_rhs: {
						prv_prc = prc_stack.Get_at_last();
						while (prv_prc != null && prv_prc.Tid() != Expr_tkn_.Tid_paren_lhs) {
							if (!prv_prc.Calc(ctx, this, val_stack)) return Null_rslt;
							prc_stack.Pop();
							prv_prc = prc_stack.Get_at_last();
						}
						if (prv_prc == Paren_bgn_tkn.Instance)
							prc_stack.Pop();
						else
							return Err_set(ctx, Xol_msg_itm_.Id_pfunc_expr_unexpected_closing_bracket);
						mode_expr = false;
						break;
					}
				}
			}
			if (cur_pos == src_len) break;
			cur_byt = src[cur_pos];
		}
		while (prc_stack.Len() > 0) {
			Func_tkn cur_prc = prc_stack.Pop();
			if (cur_prc.Tid() == Expr_tkn_.Tid_paren_lhs) return Err_set(ctx, Xol_msg_itm_.Id_pfunc_expr_unclosed_bracket);
			if (!cur_prc.Calc(ctx, this, val_stack)) return Null_rslt;
		}
		return val_stack.Len() == 0 ? Null_rslt : val_stack.Pop();	// HACK: for [[List of Premiers of South Australia by time in office]] and {{#expr:\n{{age in days
	}


	public static final GfoDecimal Null_rslt = null;
	private static Btrie_fast_mgr expression_() {	// changed to instance; DATE:2016-07-20
		Btrie_fast_mgr rv = Btrie_fast_mgr.ci_a7();	// NOTE:ci.ascii:MW_const.en; math and expressions
		Trie_add(rv, new Ws_tkn(AsciiByte.Space));
		Trie_add(rv, new Ws_tkn(AsciiByte.Tab));
		Trie_add(rv, new Ws_tkn(AsciiByte.Nl));
		Trie_add(rv, Paren_bgn_tkn.Instance);
		Trie_add(rv, Paren_end_tkn.Instance);
		Trie_add(rv, new Func_tkn_plus("+"));
		Trie_add(rv, new Func_tkn_minus("-"));
		Trie_add(rv, new Func_tkn_minus(CharUtl.ToStr((char)8722)));
		Trie_add(rv, new Func_tkn_times("*"));
		Trie_add(rv, new Func_tkn_divide("/"));
		Trie_add(rv, new Func_tkn_divide("div"));
		Trie_add(rv, new Func_tkn_pow("^"));
		Trie_add(rv, new Func_tkn_mod("mod"));
		Trie_add(rv, new Func_tkn_eq("="));
		Trie_add(rv, new Func_tkn_neq("<>"));
		Trie_add(rv, new Func_tkn_neq("!="));
		Trie_add(rv, new Func_tkn_gt(">"));
		Trie_add(rv, new Func_tkn_lt("<"));
		Trie_add(rv, new Func_tkn_gte(">="));
		Trie_add(rv, new Func_tkn_lte("<="));
		Trie_add(rv, new Func_tkn_and("and"));
		Trie_add(rv, new Func_tkn_or("or"));
		Trie_add(rv, new Func_tkn_not("not"));
		Trie_add(rv, new Func_tkn_e_op("e"));
		Trie_add(rv, new Func_tkn_pi("pi"));
		Trie_add(rv, new Func_tkn_ceil("ceil"));
		Trie_add(rv, new Func_tkn_trunc("trunc"));
		Trie_add(rv, new Func_tkn_floor("floor"));
		Trie_add(rv, new Func_tkn_abs("abs"));
		Trie_add(rv, new Func_tkn_exp("exp"));
		Trie_add(rv, new Func_tkn_ln("ln"));
		Trie_add(rv, new Func_tkn_sin("sin"));
		Trie_add(rv, new Func_tkn_cos("cos"));
		Trie_add(rv, new Func_tkn_tan("tan"));
		Trie_add(rv, new Func_tkn_asin("asin"));
		Trie_add(rv, new Func_tkn_acos("acos"));
		Trie_add(rv, new Func_tkn_atan("atan"));
		Trie_add(rv, new Func_tkn_round("round"));
		Trie_add(rv, new Func_tkn_fmod("fmod"));
		Trie_add(rv, new Func_tkn_sqrt("sqrt"));
		Trie_add(rv, new Num_tkn(0));
		Trie_add(rv, new Num_tkn(1));
		Trie_add(rv, new Num_tkn(2));
		Trie_add(rv, new Num_tkn(3));
		Trie_add(rv, new Num_tkn(4));
		Trie_add(rv, new Num_tkn(5));
		Trie_add(rv, new Num_tkn(6));
		Trie_add(rv, new Num_tkn(7));
		Trie_add(rv, new Num_tkn(8));
		Trie_add(rv, new Num_tkn(9));
		Trie_add(rv, new Dot_tkn());
		Trie_add(rv, new Func_tkn_gt("&gt;"));
		Trie_add(rv, new Func_tkn_lt("&lt;"));
		Trie_add(rv, new Func_tkn_minus("&minus;"));
		return rv;
	}
	private static void Trie_add(Btrie_fast_mgr trie, Expr_tkn tkn) {trie.Add(tkn.Val_ary(), tkn);}
	private static final byte[] Err_bgn_ary = BryUtl.NewA7("<strong class=\"error\">"), Err_end_ary = BryUtl.NewA7("</strong>");
}
