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
import gplx.core.tests.*;
public class Xop_tkn_chkr_base implements Tst_chkr {
	@gplx.Virtual public Class<?> TypeOf() {return Xop_tkn_itm.class;}
	@gplx.Virtual public byte Tkn_tid() {return Byte_.Max_value_127;}
	public Xop_tkn_chkr_base TypeId_dynamic(int v) {typeId = Xop_tkn_itm_.Tid__names[v]; return this;} private String typeId = null;
	public int Src_bgn() {return src_bgn;} private int src_bgn = -1;
	public int Src_end() {return src_end;} private int src_end = -1;
	public byte Ignore() {return ignore;} private Xop_tkn_chkr_base Ignore_(byte v) {ignore = v; return this;} private byte ignore = Bool_.__byte;
	public Xop_tkn_chkr_base Ignore_y_() {return Ignore_(Bool_.Y_byte);}
	public Xop_tkn_chkr_base Src_rng_(int bgn, int end) {src_bgn = bgn; src_end = end; return this;} 
	public String Raw() {return raw;} public Xop_tkn_chkr_base Raw_(String v) {raw = v; return this;} private String raw;
	public String Raw_src() {return raw_src;} public Xop_tkn_chkr_base Raw_src_(String v) {raw_src = v; return this;} private String raw_src;
	public Xop_tkn_chkr_base[] Subs() {return subs;} public Xop_tkn_chkr_base Subs_(Xop_tkn_chkr_base... v) {subs = v; return this;} private Xop_tkn_chkr_base[] subs = null;
	@gplx.Virtual public int Chk(Tst_mgr mgr, String path, Object actl_obj) {
		Xop_tkn_itm actl = (Xop_tkn_itm)actl_obj;
		int rv = 0;
		rv += Chk_basic(mgr, path, actl, rv);
		rv += Chk_hook(mgr, path, actl, rv);
		rv += Chk_subs(mgr, path, actl, rv);
		return rv;
	}
	@gplx.Virtual public int Chk_hook(Tst_mgr mgr, String path, Object actl_obj, int err) {return 0;}
	int Chk_basic(Tst_mgr mgr, String path, Xop_tkn_itm actl, int err) {
		if (typeId == null) typeId = Xop_tkn_itm_.Tid__names[this.Tkn_tid()];
		err += mgr.Tst_val(typeId == null, path, "typeId", typeId, Xop_tkn_itm_.Tid__names[actl.Tkn_tid()]);
		if (ignore != Bool_.__byte) err += mgr.Tst_val(ignore == Bool_.__byte, path, "ignore", ignore == Bool_.Y_byte, actl.Ignore());	// "ignore !=" to skip comparison unless explicitly toggled
		err += mgr.Tst_val(src_bgn == -1, path, "src_bgn",  src_bgn, actl.Src_bgn());
		err += mgr.Tst_val(src_end == -1, path, "src_end", src_end, actl.Src_end());
		if (raw != null) {
			String raw_actl = raw_src == null ? mgr.Vars_get_bry_as_str("raw_bry", actl.Src_bgn(), actl.Src_end()) : String_.Mid(raw_src, actl.Src_bgn(), actl.Src_end());
			err += mgr.Tst_val(raw == null, path, "raw", raw, raw_actl);
		}
		return err;
	}
	int Chk_subs(Tst_mgr mgr, String path, Xop_tkn_itm actl, int err) {
		if (subs != null) {
			int actl_subs_len = actl.Subs_len();
			Xop_tkn_itm[] actl_subs = new Xop_tkn_itm[actl_subs_len];  
			for (int i = 0; i < actl_subs_len; i++)
				actl_subs[i] = actl.Subs_get(i);
			return mgr.Tst_sub_ary(subs, actl_subs, path, err);
		}
		return err;
	}
	public static final Tst_chkr Null = Tst_mgr.Null_chkr;
	public static final Xop_tkn_chkr_base[] Ary_empty = new Xop_tkn_chkr_base[0];
}
