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
package gplx.langs.phps; import gplx.*; import gplx.langs.*;
import gplx.core.log_msgs.*;
/*
NOTE: naive implementation of PHP parser; intended only for parsing Messages**.php files in MediaWiki. Specifically, it assumes the following:
- all lines are assignment lines: EX: $a = b;
- only the assignment operator is allowed (=); EX: $a = 5 + 7; fails b/c of + operator;
- no functions are supported: EX: strlen('a') fails
*/
public class Php_evaluator implements Php_tkn_wkr {
	byte mode = Mode_key_bgn, next_tid = 0, next_mode = 0;
	Php_line_assign cur_line; Php_itm_ary cur_ary; Php_key cur_kv_key;
	List_adp frame_stack = List_adp_.New();
	public Php_evaluator(Gfo_msg_log msg_log) {this.msg_log = msg_log;} Gfo_msg_log msg_log;
	public void Init(Php_ctx ctx) {src = ctx.Src(); frame_stack.Clear();} private byte[] src;
	public List_adp List() {return lines;} List_adp lines = List_adp_.New();
	public Gfo_msg_log Msg_log() {return msg_log;}
	public void Clear() {
		lines.Clear(); msg_log.Clear();
		cur_line = null;
		cur_ary = null;
		cur_kv_key = null;
		mode = Mode_key_bgn;
		next_tid = next_mode = 0;
	}
	public void Process(Php_tkn tkn) {
		byte tkn_tid = tkn.Tkn_tid();
		switch (tkn_tid) {
			case Php_tkn_.Tid_declaration: case Php_tkn_.Tid_comment: case Php_tkn_.Tid_ws:	// always discard, regardless of mode
				return;
		}
		switch (mode) {
			case Mode_expect: // handles sequences like "array(" which hook in to "array" but need to skip "("
				if (tkn_tid == next_tid)
					mode = next_mode;
				else {
					Msg_many(src, tkn.Src_bgn(), tkn.Src_end(), Expecting_itm_failed, Php_tkn_.Xto_str(next_tid), Php_tkn_.Xto_str(tkn_tid));
					Fail();
				}
				break;
			case Mode_suspend:
				if (tkn_tid == Php_tkn_.Tid_semic) mode = Mode_key_bgn;
				break;
			case Mode_key_bgn:
				if (tkn_tid == Php_tkn_.Tid_var) {
					cur_ary = null;
					cur_line = new Php_line_assign();
					lines.Add(cur_line);
					
					Php_tkn_var var_tkn = (Php_tkn_var)tkn;
					cur_line.Key_(new Php_itm_var(var_tkn.Var_name(src)));
					
					mode = Mode_key_end;
				}
				else {
					Msg_many(src, tkn.Src_bgn(), tkn.Src_end(), Expecting_itm_failed, Php_tkn_.Xto_str(Php_tkn_.Tid_var), Php_tkn_.Xto_str(tkn_tid));
					Fail();
				}
				break;
			case Mode_key_end:
				switch (tkn_tid) {
					case Php_tkn_.Tid_eq:			mode = Mode_val; break;
					case Php_tkn_.Tid_brack_bgn:	mode = Mode_brack_itm; break;
					case Php_tkn_.Tid_brack_end:	Expect(Php_tkn_.Tid_eq, Mode_val); break;
					default: {
						Msg_many(src, tkn.Src_bgn(), tkn.Src_end(), Expecting_itm_failed, Php_tkn_.Xto_str(Php_tkn_.Tid_var), Php_tkn_.Xto_str(tkn_tid));
						Fail();
						break;
					}
				}
				break;
			case Mode_brack_itm:
				switch (tkn_tid) {
					case Php_tkn_.Tid_quote:
						Php_tkn_quote tkn_quote = (Php_tkn_quote)tkn; 
						Php_itm_quote key_sub = new Php_itm_quote(tkn_quote.Quote_text(src));
						cur_line.Key_subs_(new Php_key[] {key_sub});
						mode = Mode_key_end;
						break;
					default: {
						Msg_many(src, tkn.Src_bgn(), tkn.Src_end(), Expecting_itm_failed, Php_tkn_.Xto_str(Php_tkn_.Tid_var), Php_tkn_.Xto_str(tkn_tid));
						Fail();
						break;
					}
				}
				break;
			case Mode_val:
				Php_itm line_val = null;
				switch (tkn_tid) {
					case Php_tkn_.Tid_null:		Expect(Php_tkn_.Tid_semic, Mode_key_bgn); line_val = Php_itm_null.Instance; break;
					case Php_tkn_.Tid_false:	Expect(Php_tkn_.Tid_semic, Mode_key_bgn); line_val = Php_itm_bool_false.Instance; break;
					case Php_tkn_.Tid_true:		Expect(Php_tkn_.Tid_semic, Mode_key_bgn); line_val = Php_itm_bool_true.Instance; break;
					case Php_tkn_.Tid_quote:
						Expect(Php_tkn_.Tid_semic, Mode_key_bgn);
						Php_tkn_quote tkn_quote = (Php_tkn_quote)tkn; 
						line_val = new Php_itm_quote(tkn_quote.Quote_text(src));
						break;
					case Php_tkn_.Tid_ary:
					case Php_tkn_.Tid_brack_bgn:
						Php_itm_ary ary = new Php_itm_ary();
						if (cur_ary == null)
							line_val = ary;
						else {
							cur_ary.Subs_add(ary);
							frame_stack.Add(new Php_scanner_frame(cur_ary));
							cur_kv_key = null;
						}
						this.cur_ary = ary;
						if (tkn_tid == Php_tkn_.Tid_ary)
							Expect(Php_tkn_.Tid_paren_bgn, Mode_ary_subs);
						else
							mode = Mode_ary_subs;
						break;
					case Php_tkn_.Tid_txt:
					case Php_tkn_.Tid_var:
						break;
					case Php_tkn_.Tid_eq:
					case Php_tkn_.Tid_eq_kv:
					case Php_tkn_.Tid_semic:
					case Php_tkn_.Tid_comma:
					case Php_tkn_.Tid_paren_bgn:
					case Php_tkn_.Tid_paren_end:
					case Php_tkn_.Tid_brack_end:
					case Php_tkn_.Tid_num:
						break;
				}
				cur_line.Val_(line_val);
				break;
			case Mode_ary_subs:
				switch (tkn_tid) {
					case Php_tkn_.Tid_null:		Ary_add_itm(Php_itm_null.Instance); break;
					case Php_tkn_.Tid_false:	Ary_add_itm(Php_itm_bool_false.Instance); break;
					case Php_tkn_.Tid_true:		Ary_add_itm(Php_itm_bool_true.Instance); break;
					case Php_tkn_.Tid_quote:
						Php_tkn_quote tkn_quote = (Php_tkn_quote)tkn;
						Ary_add_itm(new Php_itm_quote(tkn_quote.Quote_text(src)));
						break;
					case Php_tkn_.Tid_num:
						Php_tkn_num tkn_num = (Php_tkn_num)tkn;
						Ary_add_itm(new Php_itm_int(tkn_num.Num_val_int(src)));
						break;
					case Php_tkn_.Tid_var:
						Php_tkn_var tkn_var = (Php_tkn_var)tkn; 
						Ary_add_itm(new Php_itm_var(Bry_.Mid(src, tkn_var.Src_bgn(), tkn_var.Src_end())));
						break;
					case Php_tkn_.Tid_txt:
						Php_tkn_txt tkn_txt = (Php_tkn_txt)tkn; 
						Ary_add_itm(new Php_itm_var(Bry_.Mid(src, tkn_txt.Src_bgn(), tkn_txt.Src_end())));
						break;
					case Php_tkn_.Tid_ary:
					case Php_tkn_.Tid_brack_bgn:
						Php_itm_ary ary = new Php_itm_ary();
						if (cur_ary == null)
							line_val = ary;
						else {
							frame_stack.Add(new Php_scanner_frame(cur_ary));
							if (cur_kv_key == null)
								cur_ary.Subs_add(ary);
							else {
								Php_itm_kv ary_itm = new Php_itm_kv().Key_(cur_kv_key).Val_(ary);
								cur_ary.Subs_add(ary_itm);
								cur_kv_key = null;
							}
						}
						this.cur_ary = ary;
						if (tkn_tid == Php_tkn_.Tid_ary)
							Expect(Php_tkn_.Tid_paren_bgn, Mode_ary_subs);
						else
							mode = Mode_ary_subs;
						break;
					case Php_tkn_.Tid_paren_end:
					case Php_tkn_.Tid_brack_end:
						mode = Mode_ary_term;						
						if (frame_stack.Count() == 0)
							cur_ary = null;
						else {
							Php_scanner_frame frame = (Php_scanner_frame)List_adp_.Pop(frame_stack);
							cur_ary = frame.Ary();
							frame.Rls();
						}
						break;
					case Php_tkn_.Tid_semic:	// NOTE: will occur in following construct array(array());
						mode = Mode_key_bgn;
						break;
					case Php_tkn_.Tid_eq:
					case Php_tkn_.Tid_eq_kv:
					case Php_tkn_.Tid_comma:
					case Php_tkn_.Tid_paren_bgn:
						break;
				}				
				break;
			case Mode_ary_dlm:
				switch (tkn_tid) {
					case Php_tkn_.Tid_comma:
						mode = Mode_ary_subs;
						break;
					case Php_tkn_.Tid_paren_end:
					case Php_tkn_.Tid_brack_end:
						mode = Mode_ary_term;						
						if (frame_stack.Count() == 0)
							cur_ary = null;
						else {
							Php_scanner_frame frame = (Php_scanner_frame)List_adp_.Pop(frame_stack);
							cur_ary = frame.Ary();
							frame.Rls();
						}
						break;
					case Php_tkn_.Tid_eq_kv:
						Php_itm_sub tmp_key = cur_ary.Subs_pop(); 
						cur_kv_key = (Php_key)tmp_key;
						mode = Mode_ary_subs;
						break;
				}
				break;
			case Mode_ary_term:
				switch (tkn_tid) {
					case Php_tkn_.Tid_comma:
					case Php_tkn_.Tid_paren_end:	// NOTE: paren_end occurs in multiple nests; EX: array(array())
					case Php_tkn_.Tid_brack_end:
						mode = Mode_ary_subs;
						break;
					case Php_tkn_.Tid_semic:
						mode = Mode_key_bgn;
						break;
				}
				break;
		}
	}
	private void Fail() {mode = Mode_suspend;}
	private void Ary_add_itm(Php_itm val) {
		mode = Mode_ary_dlm;
		if (cur_kv_key == null)
			cur_ary.Subs_add((Php_itm_sub)val);
		else {
			Php_itm_kv ary_itm = new Php_itm_kv().Key_(cur_kv_key).Val_(val);
			cur_ary.Subs_add(ary_itm);
			cur_kv_key = null;
		}		
	}
	private void Expect(byte next_tid, byte next_mode) {
		mode = Mode_expect;
		this.next_tid = next_tid;
		this.next_mode = next_mode;
	}
	public void Msg_many(byte[] src, int bgn, int end, Gfo_msg_itm itm, Object... args) {
		msg_log.Add_itm_many(itm, src, bgn, end, args);
	}
	public static final    Gfo_msg_itm Expecting_itm_failed = Gfo_msg_itm_.new_warn_(Php_parser.Log_nde, "expecting_itm_failed", "expecting_itm ~{0} but got ~{1} instead");
	private static final byte Mode_key_bgn = 1, Mode_key_end = 2, Mode_expect = 3, Mode_suspend = 4, Mode_val = 5, Mode_ary_subs = 6, Mode_ary_dlm = 7, Mode_ary_term = 8, Mode_brack_itm = 9;		
}
class Php_scanner_frame {
	public Php_scanner_frame(Php_itm_ary ary) {this.ary = ary;}
	public Php_itm_ary Ary() {return ary;} Php_itm_ary ary;
	public void Rls() {ary = null;}
}
class Php_parser_interrupt {
	public static final    Php_parser_interrupt Char = new Php_parser_interrupt(); 
}

