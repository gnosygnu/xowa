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
package gplx.xowa.parsers; import gplx.*; import gplx.xowa.*;
import gplx.xowa.parsers.tmpls.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.htmls.*;
public abstract class Xop_tkn_itm_base implements Xop_tkn_itm {
	public abstract byte Tkn_tid();
	public Xop_tkn_grp Tkn_grp() {return grp == null ? this : grp;} private Xop_tkn_grp grp;	// NOTE: not sure about this; need to handle null refs when tkns are manipulated but not yet added to a group
	public Xop_tkn_itm Tkn_ini_pos(boolean immutable, int bgn, int end) {this.immutable = immutable; this.src_bgn = bgn; this.src_end = end; return this;}
	public Xop_tkn_itm Tkn_grp_(Xop_tkn_grp grp, int sub_idx) {this.grp = grp; this.tkn_sub_idx = sub_idx; return this;}
	@gplx.Virtual public Xop_tkn_itm Tkn_clone(Xop_ctx ctx, int bgn, int end) {throw Err_.new_wo_type("tkn_clone not implemented", "name", Xop_tkn_itm_.Tid__names[this.Tkn_tid()]);}
	public boolean Tkn_immutable() {return immutable;} private boolean immutable;
	public int Tkn_sub_idx() {return tkn_sub_idx;} private int tkn_sub_idx = -1;
	public int Src_bgn() {return src_bgn;} private int src_bgn = -1;
	public int Src_end() {return src_end;} private int src_end = -1;
	public void Src_end_(int v) {src_end = v;}
	public int Src_bgn_grp(Xop_tkn_grp grp, int sub_idx) {return immutable ? grp.Subs_src_bgn(sub_idx) : src_bgn;}
	public int Src_end_grp(Xop_tkn_grp grp, int sub_idx) {return immutable ? grp.Subs_src_end(sub_idx) : src_end;}
	public int Subs_src_bgn(int sub_idx) {if (subs_len == 0) throw Err_.new_wo_type("no subs available", "idx", sub_idx); return subs_pos_ary[ sub_idx * 2];}
	public int Subs_src_end(int sub_idx) {if (subs_len == 0) throw Err_.new_wo_type("no subs available", "idx", sub_idx); return subs_pos_ary[(sub_idx * 2) + 1];}
	public void Subs_src_pos_(int sub_idx, int bgn, int end) {
		int pos_idx = sub_idx * 2;
		int subs_pos_ary_len = subs_pos_ary.length;
		if (pos_idx + 1 > subs_pos_ary_len)  {
			int[] new_subs_pos_ary = new int[(pos_idx + 1) * 2];
			Array_.Copy_to(subs_pos_ary, 0, new_subs_pos_ary, 0, subs_pos_ary.length);
			subs_pos_ary = new_subs_pos_ary;
		}
		subs_pos_ary[pos_idx] = bgn;
		subs_pos_ary[pos_idx + 1] = end;
	}
	public boolean Ignore() {return ignore;} private boolean ignore;
	public Xop_tkn_itm Ignore_y_() {
		ignore = true;
		return this;
	}
	public int Subs_len() {return subs_len;} private int subs_len;
	public Xop_tkn_itm[] Subs() {return subs;}
	public Xop_tkn_itm Subs_get(int i) {return subs[i];}		
	public Xop_tkn_itm Subs_get_or_null(int i) {return i < subs_len ? subs[i] : null;}		
	public void Subs_add(Xop_tkn_itm sub) {
		int new_len = subs_len + 1;
		if (new_len > subs_max) {	// ary too small >>> expand
			subs_max = new_len * 2;
			Xop_tkn_itm[] new_subs = new Xop_tkn_itm[subs_max];
			Array_.Copy_to(subs, 0, new_subs, 0, subs_len);
			subs = new_subs;
		}
		subs[subs_len] = sub;
		sub.Tkn_grp_(this, subs_len);
		subs_len = new_len;
	}	private Xop_tkn_itm[] subs = Xop_tkn_itm_.Ary_empty; int subs_max; int[] subs_pos_ary = Int_.Ary_empty;
	public void Subs_add_grp(Xop_tkn_itm sub, Xop_tkn_grp old_grp, int old_sub_idx) {
		this.Subs_add(sub);	
		if (sub.Tkn_immutable())
			this.Subs_src_pos_(subs_len - 1, sub.Src_bgn_grp(old_grp, old_sub_idx), sub.Src_end_grp(old_grp, old_sub_idx));
	}
	public void Subs_del_after(int tkn_sub_idx) {
		if (tkn_sub_idx >= subs_len) return;	// ignore delete after len; PRUNE: breaks 3 tests;
		for (int i = tkn_sub_idx; i < subs_len; i++)
			subs[i] = null;
		subs_len = tkn_sub_idx;
	}
	public void Subs_del_between(Xop_ctx ctx, int idx_bgn, int idx_end) {
		if (idx_bgn >= subs_len || idx_bgn >= idx_end) return;	// ignore invalid bounds; PRUNE: breaks 2 tests 
		int idx_dif = idx_end - idx_bgn;
		for (int trg_idx = idx_bgn; trg_idx < subs_len; trg_idx++) {
			int src_idx = trg_idx + idx_dif;
			if (src_idx < subs_len) {	// trg exists >>> move tkn from src to trg
				Xop_tkn_itm src_tkn = subs[src_idx];
				subs[trg_idx] = src_tkn;
				src_tkn.Tkn_grp_(this, trg_idx);
				subs[src_idx] = null;
			}
			else
				subs[trg_idx] = null;
		}
		subs_len -= idx_dif;
	}
	public void Subs_clear() {
		subs_len = subs_max = 0;
		subs = Xop_tkn_itm_.Ary_empty;
		subs_pos_ary = Int_.Ary_empty;
	}
	public void Subs_move(Xop_tkn_itm tkn) {
		int nxt_idx = tkn_sub_idx + 1, len = tkn.Subs_len();
		for (int i = nxt_idx; i < len; i++) {
			Xop_tkn_itm sub = tkn.Subs_get(i);
			Subs_add_grp(sub, tkn, i);
		}
		tkn.Subs_del_after(nxt_idx);
	}
	public void Subs_move(Xop_tkn_itm owner, int sub_idx, int subs_len) {
		for (int i = sub_idx; i < subs_len; i++) {
			Xop_tkn_itm sub = owner.Subs_get(i);
			this.Subs_add(sub);
		}
		owner.Subs_del_after(sub_idx);
	}
	public Xop_tkn_itm Immutable_clone(Xop_ctx ctx, Xop_tkn_itm tkn, int sub_idx) {
		int pos_idx = sub_idx * 2;
		Xop_tkn_itm rv = tkn.Tkn_clone(ctx, subs_pos_ary[pos_idx], subs_pos_ary[pos_idx + 1]);
		subs[sub_idx] = rv;
		rv.Tkn_grp_(this, sub_idx);
		return rv;
	}
	public void Src_end_grp_(Xop_ctx ctx, Xop_tkn_grp grp, int sub_idx, int src_end) {
		Xop_tkn_itm tkn = this;
		if (immutable) tkn = grp.Immutable_clone(ctx, this, sub_idx);
		tkn.Src_end_(src_end);
		subs_pos_ary[(sub_idx * 2) + 1] = src_end;
	}
	public void Ignore_y_grp_(Xop_ctx ctx, Xop_tkn_grp grp, int sub_idx) {
		Xop_tkn_itm tkn = this;
		if (immutable) tkn = grp.Immutable_clone(ctx, this, sub_idx);
		tkn.Ignore_y_();
	}
	public void Subs_grp_(Xop_ctx ctx, Xop_tkn_itm tkn, Xop_tkn_grp grp, int sub_idx) {
//			if (tkn.Tkn_immutable()) tkn = Subs_immutable_clone(ctx, tkn);
//			tkn.Tkn_grp_(grp, sub_idx);
	}
	@gplx.Virtual public void Reset() {
		src_bgn = src_end = tkn_sub_idx = -1; ignore = false;  tmpl_static = false;
		if (subs.length > Tkn_subs_max) {
			subs = new Xop_tkn_itm[Tkn_subs_max];
			subs_max = Tkn_subs_max;
			subs_pos_ary = new int[(Tkn_subs_max + 1) * 2];
		}
		else {
			for (int i = 0; i < subs_len; i++)
				subs[i] = null;
		}
		subs_len = 0;
	}
	@gplx.Virtual public void Html__write(Bry_bfr bfr, Xoh_html_wtr wtr, Xowe_wiki wiki, Xoae_page page, Xop_ctx ctx, Xoh_wtr_ctx hctx, Xoh_html_wtr_cfg cfg, Xop_tkn_grp grp, int sub_idx, byte[] src) {throw Err_.new_unimplemented();}
	public void Clear() {
		src_bgn = src_end = tkn_sub_idx = -1; ignore = false;  tmpl_static = false;
		Subs_clear();
	}
	@gplx.Virtual public void Tmpl_fmt(Xop_ctx ctx, byte[] src, Xot_fmtr fmtr) {fmtr.Reg_ary(ctx, src, tmpl_static, src_bgn, src_end, subs_len, subs);}
	@gplx.Virtual public void Tmpl_compile(Xop_ctx ctx, byte[] src, Xot_compile_data prep_data) {
		if (!ignore) tmpl_static = true;
		for (int i = 0; i < subs_len; i++)
			subs[i].Tmpl_compile(ctx, src, prep_data);
	}	boolean tmpl_static = false;
	@gplx.Virtual public boolean Tmpl_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Bry_bfr bfr) {
		if (tmpl_static) bfr.Add_mid(src, src_bgn, src_end);
		for (int i = 0; i < subs_len; i++)
			subs[i].Tmpl_evaluate(ctx, src, caller, bfr);
		return true;
	}
	static final String GRP_KEY = "xowa.tkn_base";
	public static final int Tkn_subs_max = 16;
}
