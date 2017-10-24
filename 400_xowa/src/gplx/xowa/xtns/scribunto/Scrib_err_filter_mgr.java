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
package gplx.xowa.xtns.scribunto; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Scrib_err_filter_mgr implements Gfo_invk {
	private final    Ordered_hash hash_by_mod = Ordered_hash_.New();
	public void Clear() {hash_by_mod.Clear();}
	public boolean Count_eq_0() {return hash_by_mod.Count() == 0;}
	public boolean Match(String mod, String fnc, String err) {
		List_adp itms = Get_itms_or_null(mod, fnc); if (itms == null) return false;
		int itms_len = itms.Count();
		boolean match = false;
		for (int i = 0; i < itms_len; ++i) {
			Scrib_err_filter_itm itm = (Scrib_err_filter_itm)itms.Get_at(i);
			if (String_.Has(err, itm.Err())) {
				match = true;
				itm.Count_actl_add_1();
				break;
			}
		}
		return match;
	}
	public void Add(int count_expd, String mod, String fnc, String err, String comment) {
		List_adp itms = Get_itms_or_null(mod, fnc);
		if (itms == null) itms = New_itms(mod, fnc);
		itms.Add(new Scrib_err_filter_itm(count_expd, mod, fnc, err, comment));
	}
	public String Print() {
		Bry_bfr bfr = Bry_bfr_.New_w_size(8);
		int i_len = hash_by_mod.Count();
		for (int i = 0; i < i_len; ++i) {
			Ordered_hash fncs = (Ordered_hash)hash_by_mod.Get_at(i);
			int j_len = fncs.Count();
			for (int j = 0; j < j_len; ++j) {
				List_adp errs = (List_adp)fncs.Get_at(j);
				int k_len = errs.Count();
				for (int k = 0; k < k_len; ++k) {
					Scrib_err_filter_itm err = (Scrib_err_filter_itm)errs.Get_at(k);
					bfr.Add_int_variable(err.Count_actl()).Add_byte_pipe().Add_int_variable(err.Count_expd())
						.Add_byte_pipe().Add_str_u8(err.Mod()).Add_byte_pipe().Add_str_u8(err.Fnc()).Add_byte_pipe().Add_str_u8(err.Err())
						.Add_byte_pipe().Add_str_u8(err.Comment())
						.Add_byte_nl();
				}
			}
		}
		return bfr.To_str_and_clear();
	}
	private List_adp Get_itms_or_null(String mod, String fnc) {
		Ordered_hash hash_by_fnc = (Ordered_hash)hash_by_mod.Get_by(mod); if (hash_by_fnc == null) return null;
		return (List_adp)hash_by_fnc.Get_by(fnc);
	}
	private List_adp New_itms(String mod, String fnc) {
		Ordered_hash hash_by_fnc = (Ordered_hash)hash_by_mod.Get_by(mod);
		if (hash_by_fnc == null) {
			hash_by_fnc = Ordered_hash_.New();
			hash_by_mod.Add(mod, hash_by_fnc);
		}
		List_adp list_of_err = (List_adp)hash_by_fnc.Get_by(fnc);
		if (list_of_err == null) {
			list_of_err = List_adp_.New();
			hash_by_fnc.Add(fnc, list_of_err);
		}
		return list_of_err;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_add))	Add(m.ReadInt("count_expd"), m.ReadStr("mod"), m.ReadStr("fnc"), m.ReadStr("err"), m.ReadStr("comment"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_add = "add";
}
class Scrib_err_filter_itm {
	public Scrib_err_filter_itm(int count_expd, String mod, String fnc, String err, String comment) {this.count_expd = count_expd; this.mod = mod; this.err = err; this.fnc = fnc; this.comment = comment;}
	public String Mod() {return mod;} private final    String mod;
	public String Fnc() {return fnc;} private final    String fnc;
	public String Err() {return err;} private final    String err;
	public String Comment() {return comment;} private final    String comment;
	public int Count_expd() {return count_expd;} private final    int count_expd;
	public int Count_actl() {return count_actl;} private int count_actl;
	public void Count_actl_add_1() {++count_actl;}
}
