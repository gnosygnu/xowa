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
package gplx.xowa.xtns.cites; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Ref_itm_mgr {
	private final    Hash_adp_bry grps = Hash_adp_bry.cs();	// CASE_MATCH:changed from ci; DATE:2014-07-07
	private final    Ref_itm_grp grp_default = new Ref_itm_grp(Bry_.Empty);
	private int uid_last;		
	public boolean References__recursing() {return references__recursing;} public void References__recursing_(boolean v) {references__recursing = v;} private boolean references__recursing;
	public Ref_itm_lst Lst_get(byte[] grp_name, int lst_idx) {
		return Bry_.Len_eq_0(grp_name) ? grp_default.Lsts_get_at(lst_idx) : ((Ref_itm_grp)grps.Get_by(grp_name)).Lsts_get_at(lst_idx);	// NOTE: must be Bry_.Len_eq_0 else <references group=""/> not same as <references/>; DATE:2013-02-06
	}	
	public void Grps_add(byte[] grp_name, byte[] itm_name, byte[] follow, Ref_nde itm) {
		Ref_itm_grp grp = Grps_get(grp_name);
		grp.Lsts_add(itm_name, follow, itm);
		itm.Uid_(uid_last++);
	}
	public int Grps_seal(byte[] grp_name) {
		Ref_itm_grp grp = Grps_get(grp_name);
		return grp.Grp_seal();
	}
	public void Grps_clear() {
		grps.Clear();
		grp_default.Lsts_clear();
		uid_last = 0;
		references__recursing = false;
	}
	public Ref_itm_grp Grps_get(byte[] grp_name) {
		if (Bry_.Len_eq_0(grp_name)) return grp_default;
		Object o = grps.Get_by_bry(grp_name);
		if (o == null) {
			Ref_itm_grp grp = new Ref_itm_grp(grp_name);
			grps.Add(grp_name, grp);
			return grp;
		}
		else
			return (Ref_itm_grp)o;
	}
}
