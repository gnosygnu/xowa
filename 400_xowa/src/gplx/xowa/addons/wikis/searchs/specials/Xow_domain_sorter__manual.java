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
package gplx.xowa.addons.wikis.searchs.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
import gplx.core.primitives.*; import gplx.xowa.langs.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.domains.crts.*;
public class Xow_domain_sorter__manual implements gplx.core.lists.ComparerAble {
	private final    Xow_domain_itm cur_domain;
	private final    Xow_domain_crt_itm[] ary; private final    int ary_len;
	public Xow_domain_sorter__manual(Xow_domain_itm cur_domain, Xow_domain_crt_itm[] ary) {
		this.cur_domain = cur_domain; this.ary = ary; this.ary_len = ary.length;
	}
	public int compare(Object lhsObj, Object rhsObj) {
		Xow_domain_itm lhs = (Xow_domain_itm)lhsObj;
		Xow_domain_itm rhs = (Xow_domain_itm)rhsObj;
		int lhs_sort = Get_sort_idx_or_neg1(lhs);
		int rhs_sort = Get_sort_idx_or_neg1(rhs);
		if		(lhs_sort == -1 && rhs_sort != -1)	return rhs_sort;
		else if	(lhs_sort != -1 && rhs_sort == -1)	return lhs_sort;
		else if (lhs_sort != -1 && rhs_sort != -1)	return Int_.Compare(lhs_sort, rhs_sort);
		else										return Bry_.Compare(lhs.Domain_bry(), rhs.Domain_bry());
	}
	private int Get_sort_idx_or_neg1(Xow_domain_itm domain) {
		int sort_idx = domain.Sort_idx(); if (sort_idx != -1) return sort_idx;
		sort_idx = Int_.Max_value;
		for (int i = 0; i < ary_len; ++i) {
			Xow_domain_crt_itm crt = ary[i];
			if (crt.Matches(cur_domain, domain)) {sort_idx = i; break;}
		}
		domain.Sort_idx_(sort_idx);
		return sort_idx;
	}
	public static void Sort(Xow_domain_sorter__manual sorter, Xow_domain_itm[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i)
			ary[i].Sort_idx_(-1);
		Array_.Sort(ary, sorter);
	}
}
class Xow_domain_sorter__manual_tid implements gplx.core.lists.ComparerAble {
	private final    Hash_adp sort_hash = Hash_adp_.New(); private final    Int_obj_ref sort_key = Int_obj_ref.New_neg1();
	public Xow_domain_sorter__manual_tid(int[] id_ary) {
		int len = id_ary.length;
		for (int i = 0; i < len; ++i) {
			int id_itm = id_ary[i];
			sort_hash.Add_if_dupe_use_nth(Int_obj_ref.New(id_itm), Int_obj_ref.New(i));
		}
	}
	public int compare(Object lhsObj, Object rhsObj) {
		Xow_domain_itm lhs = (Xow_domain_itm)lhsObj;
		Xow_domain_itm rhs = (Xow_domain_itm)rhsObj;
		int lhs_sort = Get_sort_idx_or_neg1(lhs.Domain_type_id());
		int rhs_sort = Get_sort_idx_or_neg1(rhs.Domain_type_id());
		if		(lhs_sort == -1 && rhs_sort != -1)	return rhs_sort;
		else if	(lhs_sort != -1 && rhs_sort == -1)	return lhs_sort;
		else if (lhs_sort != -1 && rhs_sort != -1)	return Int_.Compare(lhs_sort, rhs_sort);
		else										return Bry_.Compare(Xow_domain_tid_.Get_type_as_bry(lhs.Domain_type_id()), Xow_domain_tid_.Get_type_as_bry(rhs.Domain_type_id()));
	}
	private int Get_sort_idx_or_neg1(int tid) {
		Object o = sort_hash.Get_by(sort_key.Val_(tid));
		return o == null ? -1 : ((Int_obj_ref)o).Val();
	}
	public static Xow_domain_sorter__manual_tid new_(byte[]... id_brys) {
		int len = id_brys.length;
		int[] id_ints = new int[len];
		for (int i = 0; i < len; ++i) {
			byte[] id_bry = id_brys[i];
			int id_int = Xow_domain_tid_.Get_type_as_tid(id_bry);
			id_ints[i] = id_int;
		}
		return new Xow_domain_sorter__manual_tid(id_ints);
	}
}
class Xow_domain_sorter__manual_lang implements gplx.core.lists.ComparerAble {
	private final    Hash_adp sort_hash = Hash_adp_.New(); private final    Int_obj_ref sort_key = Int_obj_ref.New_neg1();
	public Xow_domain_sorter__manual_lang(int[] id_ary) {
		int len = id_ary.length;
		for (int i = 0; i < len; ++i) {
			int id_int = id_ary[i];
			sort_hash.Add_if_dupe_use_nth(Int_obj_ref.New(id_int), Int_obj_ref.New(i));
		}
	}
	public int compare(Object lhsObj, Object rhsObj) {
		Xow_domain_itm lhs = (Xow_domain_itm)lhsObj;
		Xow_domain_itm rhs = (Xow_domain_itm)rhsObj;
		int lhs_sort = Get_sort_idx_or_neg1(lhs.Lang_actl_uid());
		int rhs_sort = Get_sort_idx_or_neg1(rhs.Lang_actl_uid());
		if		(lhs_sort == -1 && rhs_sort != -1)	return rhs_sort;
		else if	(lhs_sort != -1 && rhs_sort == -1)	return lhs_sort;
		else if (lhs_sort != -1 && rhs_sort != -1)	return Int_.Compare(lhs_sort, rhs_sort);
		else										return Bry_.Compare(lhs.Lang_actl_key(), rhs.Lang_actl_key());
	}
	private int Get_sort_idx_or_neg1(int tid) {
		Object o = sort_hash.Get_by(sort_key.Val_(tid));
		return o == null ? -1 : ((Int_obj_ref)o).Val();
	}
	public static Xow_domain_sorter__manual_lang new_(byte[]... id_brys) {
		int len = id_brys.length;
		int[] id_ints = new int[len];
		for (int i = 0; i < len; ++i) {
			byte[] id_bry = id_brys[i];
			int id_int = Xol_lang_stub_.Get_by_key_or_intl(id_bry).Id();
			id_ints[i] = id_int;
		}
		return new Xow_domain_sorter__manual_lang(id_ints);
	}
}
