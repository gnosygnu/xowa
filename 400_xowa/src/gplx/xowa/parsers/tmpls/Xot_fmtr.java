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
	}	static final byte[] Bry_bgn = new byte[] {Byte_ascii.Curly_bgn, Byte_ascii.Curly_bgn, Byte_ascii.Curly_bgn}, Bry_end = new byte[] {Byte_ascii.Curly_end, Byte_ascii.Curly_end, Byte_ascii.Curly_end};
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
	Bry_bfr trg = Bry_bfr.new_(); int depth = 0;
	public static final Xot_fmtr_prm _ = new Xot_fmtr_prm();
}
