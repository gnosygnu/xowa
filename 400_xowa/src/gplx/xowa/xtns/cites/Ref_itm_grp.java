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
public class Ref_itm_grp {
	private final    List_adp lsts = List_adp_.New();
	private int lst_idx;
	public Ref_itm_grp(byte[] grp_name) {this.grp_name = grp_name;}
	public byte[] Grp_name() {return grp_name;} private byte[] grp_name;
	public int Grp_seal() {
		if (lst_idx == lsts.Count())	// NOTE: lst_idx == lsts.Count() if there are no itms in lst; need to add placeholder list anyway; <references> nodes has a List_index, which will call lsts.Get_at(); see Empty_group_before_ref
			lsts.Add(new Ref_itm_lst(grp_name));
		return lst_idx++;
	}
	public int Lsts_len() {return lsts.Count();}
	public Ref_itm_lst Lsts_get_at(int i) {return i < lsts.Count() ? (Ref_itm_lst)lsts.Get_at(i) : null;}	// NOTE: null can be returned; see Infobox planet; w:Mars
	public void Lsts_add(byte[] itm_name, byte[] follow, Ref_nde itm) {
		Ref_itm_lst lst = null;
		int lsts_len = lsts.Count();
		if (lst_idx >= lsts_len) {
			lst = new Ref_itm_lst(grp_name);
			lsts.Add(lst);
		}
		else
			lst = (Ref_itm_lst)lsts.Get_at(lsts_len - 1);
		lst.Itms_add(itm_name, follow, itm);
	}
	public void Lsts_clear() {
		int lsts_len = lsts.Count();
		for (int i = 0; i < lsts_len; i++) {
			Ref_itm_lst lst = (Ref_itm_lst)lsts.Get_at(i);
			lst.Itms_clear();
		}
		lsts.Clear();
		lst_idx = 0;
	}
}	
