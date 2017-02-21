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
package gplx.xowa.parsers.apos; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xop_apos_itm {
	public int State() {return state;} public void State_clear() {state = Xop_apos_tkn_.State_nil;} private int state = Xop_apos_tkn_.State_nil;
	public int Typ() {return typ;} private int typ;
	public int Cmd() {return cmd;} private int cmd;
	public int Lit_apos() {return lit_apos;} private int lit_apos;
	public int Dual_cmd() {return dual_cmd;} private int dual_cmd;
	public void Init(int state, int typ, int cmd, int lit_apos, int dual_cmd) {
		this.state = state;
		this.typ = typ; this.cmd = cmd; this.lit_apos = lit_apos; this.dual_cmd = dual_cmd;
	}
	public static void Ident(Xop_apos_itm rv, Xop_ctx ctx, byte[] src, int apos_len, int cur_pos, int state) {
		switch (apos_len) {
			case Xop_apos_tkn_.Len_ital: case Xop_apos_tkn_.Len_bold: case Xop_apos_tkn_.Len_dual:
				Ident_props(rv, state, apos_len, 0); break;
			case Xop_apos_tkn_.Len_apos_bold:
				Ident_props(rv, state, Xop_apos_tkn_.Len_bold, 1); break;
			default:
				Ident_props(rv, state, Xop_apos_tkn_.Len_dual, apos_len - Xop_apos_tkn_.Len_dual);
				break;
		}
	}
	private static void Ident_props(Xop_apos_itm rv, int state, int apos_len, int lit_apos) {
		int typ = apos_len;
		int cmd = 0, dual_cmd = 0;
		switch (apos_len) {
			case Xop_apos_tkn_.Len_ital: {
				switch (state) {
					case Xop_apos_tkn_.State_i:		cmd = Xop_apos_tkn_.Cmd_i_end;			state = Xop_apos_tkn_.State_nil;	break;
					case Xop_apos_tkn_.State_bi:	cmd = Xop_apos_tkn_.Cmd_i_end;			state = Xop_apos_tkn_.State_b;		break;
					case Xop_apos_tkn_.State_ib:	cmd = Xop_apos_tkn_.Cmd_bi_end__b_bgn;	state = Xop_apos_tkn_.State_b;		break;
					case Xop_apos_tkn_.State_dual:	cmd = Xop_apos_tkn_.Cmd_i_end;			state = Xop_apos_tkn_.State_b;		dual_cmd = Xop_apos_tkn_.Cmd_bi_bgn; break;
					case Xop_apos_tkn_.State_b:		cmd = Xop_apos_tkn_.Cmd_i_bgn;			state = Xop_apos_tkn_.State_bi;		break;
					case Xop_apos_tkn_.State_nil:	cmd = Xop_apos_tkn_.Cmd_i_bgn;			state = Xop_apos_tkn_.State_i;		break;
					default:						throw Err_.new_unhandled(state);
				}
				break;
			}
			case Xop_apos_tkn_.Len_bold:	{
				switch (state) {
					case Xop_apos_tkn_.State_b:		cmd = Xop_apos_tkn_.Cmd_b_end;			state = Xop_apos_tkn_.State_nil;	break;
					case Xop_apos_tkn_.State_bi:	cmd = Xop_apos_tkn_.Cmd_ib_end__i_bgn;	state = Xop_apos_tkn_.State_i;		break;
					case Xop_apos_tkn_.State_ib:	cmd = Xop_apos_tkn_.Cmd_b_end;			state = Xop_apos_tkn_.State_i;		break;
					case Xop_apos_tkn_.State_dual:	cmd = Xop_apos_tkn_.Cmd_b_end;			state = Xop_apos_tkn_.State_i;		break; // NOTE: dual_cmd = Cmd_ib_bgn is implied
					case Xop_apos_tkn_.State_i:		cmd = Xop_apos_tkn_.Cmd_b_bgn;			state = Xop_apos_tkn_.State_ib;		break;
					case Xop_apos_tkn_.State_nil:	cmd = Xop_apos_tkn_.Cmd_b_bgn;			state = Xop_apos_tkn_.State_b;		break;
					default:						throw Err_.new_unhandled(state);
				}
				break;
			}
			case Xop_apos_tkn_.Len_dual:	{
				switch (state) {
					case Xop_apos_tkn_.State_b:		cmd = Xop_apos_tkn_.Cmd_b_end__i_bgn;	state = Xop_apos_tkn_.State_i;		break;
					case Xop_apos_tkn_.State_i:		cmd = Xop_apos_tkn_.Cmd_i_end__b_bgn;	state = Xop_apos_tkn_.State_b;		break;
					case Xop_apos_tkn_.State_bi:	cmd = Xop_apos_tkn_.Cmd_ib_end;			state = Xop_apos_tkn_.State_nil;	break;
					case Xop_apos_tkn_.State_ib:	cmd = Xop_apos_tkn_.Cmd_bi_end;			state = Xop_apos_tkn_.State_nil;	break;
					case Xop_apos_tkn_.State_dual:	cmd = Xop_apos_tkn_.Cmd_bi_end;			state = Xop_apos_tkn_.State_nil;	break; // NOTE: dual_cmd = Cmd_ib_bgn is implied
					case Xop_apos_tkn_.State_nil:	cmd = Xop_apos_tkn_.Cmd_ib_bgn;			state = Xop_apos_tkn_.State_dual;	break;
					default:						throw Err_.new_unhandled(state);
				}
				break;
			}
			default: throw Err_.new_unhandled_default(apos_len);
		}
		rv.Init(state, typ, cmd, lit_apos, dual_cmd);
	}
}
