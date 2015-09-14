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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.xtns.pfuncs.*;
public class Xot_defn_trace_dbg implements Xot_defn_trace {
	public void Trace_bgn(Xop_ctx ctx, byte[] src, byte[] name, Xot_invk caller, Xot_invk invk, Xot_defn defn) {
		if (count++ != 0) bfr.Add_byte_nl();	// do not add new line for 1st template
		indent += 2;
		// *invk
		bfr.Add_byte_repeat(Byte_ascii.Space, indent).Add(Ary_invk_lbl);
		bfr.Add_byte_repeat(Byte_ascii.Space, indent).Add(Xop_curly_bgn_lxr.Hook).Add(name);
		if (defn.Defn_tid() == Xot_defn_.Tid_func) {
			byte[] argx_ary = ((Pf_func_base)defn).Argx_dat();
			bfr.Add_byte(Byte_ascii.Colon).Add(argx_ary);
		}
		int args_len = invk.Args_len();
		for (int i = 0; i < args_len; i++) {
			bfr.Add_byte(Byte_ascii.Pipe);
			Arg_nde_tkn nde = invk.Args_get_by_idx(i);
			if (nde.KeyTkn_exists()) {
//					bfr.Add_mid(src, nde.KeyTkn().Dat_bgn(), nde.KeyTkn().Dat_end()).Add_byte(Byte_ascii.Eq);
				bfr.Add(nde.Key_tkn().Dat_ary()).Add_byte(Byte_ascii.Eq);
			}
//				Arg_itm_tkn val_tkn = nde.ValTkn();
//				if (val_tkn.Itm_static() == Bool_.Y_byte) {
//					bfr.Add_mid(src, val_tkn.Dat_bgn(), val_tkn.Dat_end());
//				}
//				else {
//					Xot_fmtr_prm raw_fmtr = Xot_fmtr_prm._;
//					nde.ValTkn().Tmpl_fmt(ctx, src, raw_fmtr);
//					raw_fmtr.Print(bfr);
//				}
			byte[] val_dat_ary = nde.Val_tkn().Dat_ary();
			if (val_dat_ary == Bry_.Empty) {
				Xot_fmtr_prm raw_fmtr = prm_fmtr;
				nde.Val_tkn().Tmpl_fmt(ctx, src, raw_fmtr);
				raw_fmtr.Print(bfr);
			}
			else
				bfr.Add(nde.Val_tkn().Dat_ary());
		}
		bfr.Add(Xop_curly_end_lxr.Hook);
		bfr.Add_byte_nl();
		
		// *name
		bfr	.Add_byte_repeat(Byte_ascii.Space, indent).Add(Ary_lnk_lbl)
			.Add(Xop_tkn_.Lnki_bgn)
			.Add(ctx.Wiki().Ns_mgr().Ns_template().Name_db_w_colon())
			.Add(defn.Name())
			.Add(Xop_tkn_.Lnki_end).Add_byte_nl();

		if (defn.Defn_tid() == Xot_defn_.Tid_tmpl) {
			// *args
			argKeys.Clear();
			int caller_args = invk.Args_len();
			int key_max = 0;
			bfr	.Add_byte_repeat(Byte_ascii.Space, indent).Add(Ary_args_lbl);
			for (int i = 0; i < caller_args; i++) {
				Arg_nde_tkn arg = invk.Args_get_by_idx(i);
				int digits = Int_.DigitCount(i + 1);
//					byte[] val_ary = Bry_.Mid(src, arg.ValTkn().Dat_bgn(), arg.ValTkn().Dat_end());
				bfr	.Add_byte_repeat(Byte_ascii.Space, indent + 2)
					.Add_byte_repeat(Byte_ascii.Space, 4 - digits)
					.Add_int_fixed(i + 1, digits)
					.Add_byte(Byte_ascii.Colon).Add_byte(Byte_ascii.Space)
					.Add(arg.Val_tkn().Dat_ary()).Add_byte_nl()
//						.Add(val_ary).Add_byte_nl()
					;
				if (arg.KeyTkn_exists()) {
//						byte[] key_ary = Bry_.Mid(src, arg.KeyTkn().Dat_bgn(), arg.KeyTkn().Dat_end());
					String key_str = String_.new_u8(arg.Key_tkn().Dat_ary());
					int key_str_len = String_.Len(key_str);
					if (key_str_len > key_max) key_max = key_str_len;
					argKeys.Add(key_str + "=" + String_.new_u8(arg.Val_tkn().Dat_ary()));
				}
			}
			argKeys.Sort();
			for (int i = 0; i < argKeys.Count(); i++) {
				String s = (String)argKeys.Get_at(i);
				String key = String_.GetStrBefore(s, "=");
				String val = String_.GetStrAfter(s, "=");
				bfr.Add_byte_repeat(Byte_ascii.Space, indent + 2).Add_str(key)
					.Add_byte_repeat(Byte_ascii.Space, key_max - String_.Len(key))
					.Add_byte(Byte_ascii.Colon).Add_byte(Byte_ascii.Space).Add_str(val).Add_byte_nl();
			}
		}

		if (defn.Defn_tid() == Xot_defn_.Tid_tmpl) {
			Xot_defn_tmpl defn_tmpl = ((Xot_defn_tmpl)defn);
			Xop_root_tkn root = defn_tmpl.Root();
//				Fmt(ctx, defn_tmpl.Src(), root, Ary_raw_lbl , null, false);
//				Fmt(ctx, defn_tmpl.Src(), root, Ary_fmt_lbl , invk, true);
			Fmt(ctx, defn_tmpl.Data_raw(), root, Ary_eval_lbl, invk, false);
		}
	}	private Bry_bfr bfr = Bry_bfr.new_(128); List_adp argKeys = List_adp_.new_(); Xot_fmtr_prm prm_fmtr = new Xot_fmtr_prm();
	private void Fmt(Xop_ctx ctx, byte[] src, Xop_tkn_itm root, byte[] lbl, Xot_invk caller, boolean newLineArgs) {
		bfr.Add_byte_repeat(Byte_ascii.Space, indent).Add(lbl);
		bfr.Add_byte_repeat(Byte_ascii.Space, indent);
		prm_fmtr.Caller_(caller).NewLineArgs_(newLineArgs);
		root.Tmpl_fmt(ctx, src, prm_fmtr);
		prm_fmtr.Print(bfr);
		bfr.Add_byte_nl();
	}
	public void Trace_end(int trg_bgn, Bry_bfr trg) {
		indent -= 2;
		bfr	.Add_byte_repeat(Byte_ascii.Space, indent).Add(Ary_result_lbl);
		if (trg_bgn < trg.Len())
			bfr	.Add_byte_repeat(Byte_ascii.Space, indent).Add_mid(trg.Bfr(), trg_bgn, trg.Len())
				.Add_byte_nl();
	}
	public void Print(byte[] src, Bry_bfr bb) {
		if (bfr.Len() == 0) return;
		if (bb.Len() != 0) bb.Add_byte_nl();	// only add newLine if something in bb; needed for tests
		bb	.Add(Ary_source_lbl)
			.Add(src).Add_byte_nl();
		bb.Add_bfr_and_preserve(bfr);
		bfr.Clear();
	}
	public void Clear() {bfr.Clear(); indent = 0; count = 0;}
	int indent = 0, count = 0;
	public static final Xot_defn_trace_dbg _ = new Xot_defn_trace_dbg(); Xot_defn_trace_dbg() {}
	private static final byte[] Ary_invk_lbl = Bry_.new_a7("*invk\n"), Ary_lnk_lbl = Bry_.new_a7("*lnk: "), Ary_args_lbl = Bry_.new_a7("*args\n")
		, Ary_result_lbl = Bry_.new_a7("*result\n")
		, Ary_eval_lbl = Bry_.new_a7("*eval\n")
		, Ary_source_lbl = Bry_.new_a7("*source\n");
}
