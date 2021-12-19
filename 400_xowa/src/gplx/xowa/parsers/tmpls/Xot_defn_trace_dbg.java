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
package gplx.xowa.parsers.tmpls;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.parsers.*;
import gplx.xowa.xtns.pfuncs.*;
public class Xot_defn_trace_dbg implements Xot_defn_trace {
	public void Trace_bgn(Xop_ctx ctx, byte[] src, byte[] name, Xot_invk caller, Xot_invk invk, Xot_defn defn) {
		if (count++ != 0) bfr.AddByteNl();	// do not add new line for 1st template
		indent += 2;
		// *invk
		bfr.AddByteRepeat(AsciiByte.Space, indent).Add(Ary_invk_lbl);
		bfr.AddByteRepeat(AsciiByte.Space, indent).Add(Xop_curly_bgn_lxr.Hook).Add(name);
		if (defn.Defn_tid() == Xot_defn_.Tid_func) {
			byte[] argx_ary = ((Pf_func_base)defn).Argx_dat();
			bfr.AddByte(AsciiByte.Colon).Add(argx_ary);
		}
		int args_len = invk.Args_len();
		for (int i = 0; i < args_len; i++) {
			bfr.AddByte(AsciiByte.Pipe);
			Arg_nde_tkn nde = invk.Args_get_by_idx(i);
			if (nde.KeyTkn_exists()) {
//					bfr.Add_mid(src, nde.KeyTkn().Dat_bgn(), nde.KeyTkn().Dat_end()).Add_byte(Byte_ascii.Eq);
				bfr.Add(nde.Key_tkn().Dat_ary()).AddByte(AsciiByte.Eq);
			}
//				Arg_itm_tkn val_tkn = nde.ValTkn();
//				if (val_tkn.Itm_static() == BoolUtl.Y_byte) {
//					bfr.Add_mid(src, val_tkn.Dat_bgn(), val_tkn.Dat_end());
//				}
//				else {
//					Xot_fmtr_prm raw_fmtr = Xot_fmtr_prm.Instance;
//					nde.ValTkn().Tmpl_fmt(ctx, src, raw_fmtr);
//					raw_fmtr.Print(bfr);
//				}
			byte[] val_dat_ary = nde.Val_tkn().Dat_ary();
			if (val_dat_ary == BryUtl.Empty) {
				Xot_fmtr_prm raw_fmtr = prm_fmtr;
				nde.Val_tkn().Tmpl_fmt(ctx, src, raw_fmtr);
				raw_fmtr.Print(bfr);
			}
			else
				bfr.Add(nde.Val_tkn().Dat_ary());
		}
		bfr.Add(Xop_curly_end_lxr.Hook);
		bfr.AddByteNl();
		
		// *name
		bfr	.AddByteRepeat(AsciiByte.Space, indent).Add(Ary_lnk_lbl)
			.Add(Xop_tkn_.Lnki_bgn)
			.Add(ctx.Wiki().Ns_mgr().Ns_template().Name_db_w_colon())
			.Add(defn.Name())
			.Add(Xop_tkn_.Lnki_end).AddByteNl();

		if (defn.Defn_tid() == Xot_defn_.Tid_tmpl) {
			// *args
			argKeys.Clear();
			int caller_args = invk.Args_len();
			int key_max = 0;
			bfr	.AddByteRepeat(AsciiByte.Space, indent).Add(Ary_args_lbl);
			for (int i = 0; i < caller_args; i++) {
				Arg_nde_tkn arg = invk.Args_get_by_idx(i);
				int digits = IntUtl.CountDigits(i + 1);
//					byte[] val_ary = Bry_.Mid(src, arg.ValTkn().Dat_bgn(), arg.ValTkn().Dat_end());
				bfr	.AddByteRepeat(AsciiByte.Space, indent + 2)
					.AddByteRepeat(AsciiByte.Space, 4 - digits)
					.AddIntFixed(i + 1, digits)
					.AddByte(AsciiByte.Colon).AddByte(AsciiByte.Space)
					.Add(arg.Val_tkn().Dat_ary()).AddByteNl()
//						.Add(val_ary).Add_byte_nl()
					;
				if (arg.KeyTkn_exists()) {
//						byte[] key_ary = Bry_.Mid(src, arg.KeyTkn().Dat_bgn(), arg.KeyTkn().Dat_end());
					String key_str = StringUtl.NewU8(arg.Key_tkn().Dat_ary());
					int key_str_len = StringUtl.Len(key_str);
					if (key_str_len > key_max) key_max = key_str_len;
					argKeys.Add(key_str + "=" + StringUtl.NewU8(arg.Val_tkn().Dat_ary()));
				}
			}
			argKeys.Sort();
			for (int i = 0; i < argKeys.Len(); i++) {
				String s = (String)argKeys.GetAt(i);
				String key = StringUtl.GetStrBefore(s, "=");
				String val = StringUtl.GetStrAfter(s, "=");
				bfr.AddByteRepeat(AsciiByte.Space, indent + 2).AddStrU8(key)
					.AddByteRepeat(AsciiByte.Space, key_max - StringUtl.Len(key))
					.AddByte(AsciiByte.Colon).AddByte(AsciiByte.Space).AddStrU8(val).AddByteNl();
			}
		}

		if (defn.Defn_tid() == Xot_defn_.Tid_tmpl) {
			Xot_defn_tmpl defn_tmpl = ((Xot_defn_tmpl)defn);
			Xop_root_tkn root = defn_tmpl.Root();
//				Fmt(ctx, defn_tmpl.Src(), root, Ary_raw_lbl , null, false);
//				Fmt(ctx, defn_tmpl.Src(), root, Ary_fmt_lbl , invk, true);
			Fmt(ctx, defn_tmpl.Data_raw(), root, Ary_eval_lbl, invk, false);
		}
	}	private BryWtr bfr = BryWtr.NewWithSize(128); List_adp argKeys = List_adp_.New(); Xot_fmtr_prm prm_fmtr = new Xot_fmtr_prm();
	private void Fmt(Xop_ctx ctx, byte[] src, Xop_tkn_itm root, byte[] lbl, Xot_invk caller, boolean newLineArgs) {
		bfr.AddByteRepeat(AsciiByte.Space, indent).Add(lbl);
		bfr.AddByteRepeat(AsciiByte.Space, indent);
		prm_fmtr.Caller_(caller).NewLineArgs_(newLineArgs);
		root.Tmpl_fmt(ctx, src, prm_fmtr);
		prm_fmtr.Print(bfr);
		bfr.AddByteNl();
	}
	public void Trace_end(int trg_bgn, BryWtr trg) {
		indent -= 2;
		bfr	.AddByteRepeat(AsciiByte.Space, indent).Add(Ary_result_lbl);
		if (trg_bgn < trg.Len())
			bfr	.AddByteRepeat(AsciiByte.Space, indent).AddMid(trg.Bry(), trg_bgn, trg.Len())
				.AddByteNl();
	}
	public void Print(byte[] src, BryWtr bb) {
		if (bfr.Len() == 0) return;
		if (bb.Len() != 0) bb.AddByteNl();	// only add newLine if something in bb; needed for tests
		bb	.Add(Ary_source_lbl)
			.Add(src).AddByteNl();
		bb.AddBfrAndPreserve(bfr);
		bfr.Clear();
	}
	public void Clear() {bfr.Clear(); indent = 0; count = 0;}
	int indent = 0, count = 0;
	public static final Xot_defn_trace_dbg Instance = new Xot_defn_trace_dbg(); Xot_defn_trace_dbg() {}
	private static final byte[] Ary_invk_lbl = BryUtl.NewA7("*invk\n"), Ary_lnk_lbl = BryUtl.NewA7("*lnk: "), Ary_args_lbl = BryUtl.NewA7("*args\n")
		, Ary_result_lbl = BryUtl.NewA7("*result\n")
		, Ary_eval_lbl = BryUtl.NewA7("*eval\n")
		, Ary_source_lbl = BryUtl.NewA7("*source\n");
}
