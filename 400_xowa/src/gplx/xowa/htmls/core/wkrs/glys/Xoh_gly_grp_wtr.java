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
package gplx.xowa.htmls.core.wkrs.glys;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.custom.brys.wtrs.args.BryBfrArgClearable;
import gplx.types.custom.brys.fmts.fmtrs.*;
import gplx.langs.htmls.*;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.htmls.core.wkrs.bfr_args.*;
class Xoh_gly_grp_wtr implements BryBfrArg {
	private final BryBfrArgClearable[] arg_ary;
	private final Bfr_arg__hatr_id ul_id = Bfr_arg__hatr_id.New_id("xogly_ul_");
	private final Bfr_arg__hatr__style ul_style = new Bfr_arg__hatr__style(Gfh_atr_.Bry__style);
	private final Bfr_arg__hatr__xogly ul_xogly = new Bfr_arg__hatr__xogly();
	private final Bfr_arg__elem__capt li_capt = new Bfr_arg__elem__capt();
	private byte[] ul_cls, xtra_cls, xtra_atr_bry, ul_nl;
	private final Xoh_gly_itm_list_wtr itm_list_wtr = new Xoh_gly_itm_list_wtr();
	public Xoh_gly_grp_wtr() {
		arg_ary = new BryBfrArgClearable[] {ul_id, ul_xogly, li_capt};
	}
	public void Init(boolean mode_is_diff, int id, int xnde_w, int xnde_h, int xnde_per_row, byte[] cls, int ul_style_max_w, int ul_style_w
		, byte[] xtra_cls, byte[] xtra_style_bry, byte[] xtra_atr_bry, byte[] capt, Xoh_gly_itm_wtr[] ary) {
		this.Clear();
		if (!mode_is_diff)
			ul_id.Set(id); 
		ul_xogly.Set_args(xnde_w, xnde_h, xnde_per_row);
		this.ul_cls = cls;
		this.xtra_cls = xtra_cls == null ? BryUtl.Empty : BryUtl.Add(AsciiByte.SpaceBry, xtra_cls);
		this.xtra_atr_bry = xtra_atr_bry;
		this.ul_nl = ary.length == 0 ? BryUtl.Empty : AsciiByte.NlBry;	// TIDY: <ul></ul> should be on same line if 0 items
		li_capt.Capt_(capt);
		itm_list_wtr.Init(ary);
		ul_style.Set_args(ul_style_max_w, ul_style_w, xtra_style_bry);			
	}
	public void Clear() {
		for (BryBfrArgClearable arg : arg_ary)
			arg.BfrArgClear();
		ul_id.BfrArgClear();
		ul_style.Clear();
		ul_cls = null;
	}
	public void Bfr_arg__clear() {this.Clear();}
	public boolean Bfr_arg__missing() {return false;}
	public void AddToBfr(BryWtr bfr) {
		fmtr.BldToBfrMany(bfr, ul_id, ul_xogly, ul_cls, xtra_cls, ul_style, xtra_atr_bry, li_capt, itm_list_wtr, ul_nl);
	}
	private static final BryFmtr fmtr = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( "<ul~{id}~{xogly} class=\"gallery mw-gallery-~{cls}~{xtra_cls}\"~{style}~{xtra_atr}>~{capt}~{itms}~{ul_nl}</ul>"
	), "id", "xogly", "cls", "xtra_cls", "style", "xtra_atr", "capt", "itms", "ul_nl");
}
class Xoh_gly_itm_list_wtr implements BryBfrArg {
	private Xoh_gly_itm_wtr[] ary; private int ary_len;
	public void Init(Xoh_gly_itm_wtr[] ary) {
		this.ary = ary; this.ary_len = ary.length;
	}
	public void Bfr_arg__clear() {
		for (int i = 0; i < ary_len; ++i)
			ary[i].Clear();
		ary = null;
	}
	public boolean Bfr_arg__missing() {return ary == null;}
	public void AddToBfr(BryWtr bfr) {
		for (int i = 0; i < ary_len; ++i) {
			Xoh_gly_itm_wtr itm = ary[i];
			itm.AddToBfr(bfr);
		}
	}
}
