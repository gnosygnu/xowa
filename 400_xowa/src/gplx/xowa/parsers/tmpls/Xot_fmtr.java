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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public interface Xot_fmtr {
	void Reg_ary(Xop_ctx ctx, byte[] src, boolean tmpl_static, int src_bgn, int src_end, int subs_len, Xop_tkn_itm[] subs);
	void Reg_prm(Xop_ctx ctx, byte[] src, Xot_prm_tkn self, int prm_idx, byte[] prm_key, Xop_tkn_itm dflt_tkn);
	void Reg_tmpl(Xop_ctx ctx, byte[] src, Xop_tkn_itm name_tkn, int args_len, Arg_nde_tkn[] args);
	void Reg_arg(Xop_ctx ctx, byte[] src, int arg_idx, Arg_nde_tkn self_tkn);
}
class Xot_fmtr_prm implements Xot_fmtr {
	public Xot_fmtr_prm Caller_(Xot_invk v)		{this.caller = v; return this;} private Xot_invk caller;
	public Xot_fmtr_prm NewLineArgs_(boolean v)	{this.newLineArgs = v; return this;} private boolean newLineArgs = false;
	public void Reg_ary(Xop_ctx ctx, byte[] src, boolean tmpl_static, int src_bgn, int src_end, int subs_len, Xop_tkn_itm[] subs) {
		if (tmpl_static && src_bgn != -1) trg.Add_mid(src, src_bgn, src_end);	// HACK: fails for {{IPA-de|l|lang|De-Ludwig_van_Beethoven.ogg}}
		for (int i = 0; i < subs_len; i++)
			subs[i].Tmpl_fmt(ctx, src, this);
	}
	public void Reg_prm(Xop_ctx ctx, byte[] src, Xot_prm_tkn self, int prm_idx, byte[] prm_key, Xop_tkn_itm dflt_tkn) {
		if (caller == null) {	// raw mode
			trg.Add(Bry_bgn);
			if (prm_idx == -1)	{if (prm_key != null) trg.Add(prm_key);}
			else				trg.Add_int_variable(prm_idx);
			if (dflt_tkn != null) {
				trg.Add_byte(Byte_ascii.Pipe);
				dflt_tkn.Tmpl_fmt(ctx, src, this);
			}
			trg.Add(Bry_end);
		}
		else					// invk mode
			self.Tmpl_evaluate(ctx, src, caller, trg);
	}	private static final    byte[] Bry_bgn = new byte[] {Byte_ascii.Curly_bgn, Byte_ascii.Curly_bgn, Byte_ascii.Curly_bgn}, Bry_end = new byte[] {Byte_ascii.Curly_end, Byte_ascii.Curly_end, Byte_ascii.Curly_end};
	public void Reg_tmpl(Xop_ctx ctx, byte[] src, Xop_tkn_itm name_tkn, int args_len, Arg_nde_tkn[] args) {
		trg.Add(Xop_curly_bgn_lxr.Hook);
		++depth;
		name_tkn.Tmpl_fmt(ctx, src, this);
		for (int i = 0; i < args_len; i++) {
			if (depth == 1 && newLineArgs) trg.Add_byte_nl();
			trg.Add_byte(Byte_ascii.Pipe);
			args[i].Tmpl_fmt(ctx, src, this);
		}
		--depth;
		trg.Add(Xop_curly_end_lxr.Hook);
	}
	public void Write(byte b) {trg.Add_byte(b);}
	public void Reg_arg(Xop_ctx ctx, byte[] src, int arg_idx, Arg_nde_tkn self_tkn) {
		self_tkn.Key_tkn().Tmpl_fmt(ctx, src, this);
		if (self_tkn.KeyTkn_exists()) {
			if (arg_idx == 0) {
				if (self_tkn.Eq_tkn().Tkn_tid() == Xop_tkn_itm_.Tid_colon)
					trg.Add_byte(Byte_ascii.Colon);
			}
			else
				trg.Add_byte(Byte_ascii.Eq);
		}
		self_tkn.Val_tkn().Tmpl_fmt(ctx, src, this);
	}
	public void Print(Bry_bfr bb) {bb.Add_bfr_and_preserve(trg); trg.Clear(); depth = 0;}
	Bry_bfr trg = Bry_bfr_.New(); int depth = 0;
	public static final    Xot_fmtr_prm Instance = new Xot_fmtr_prm();
}
