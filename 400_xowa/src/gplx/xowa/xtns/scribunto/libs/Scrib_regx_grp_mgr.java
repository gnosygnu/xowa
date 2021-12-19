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
package gplx.xowa.xtns.scribunto.libs;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.lists.Hash_adp_;
import gplx.types.basics.utls.IntUtl;
import gplx.types.commons.KeyVal;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.langs.regxs.Regx_group;
import gplx.langs.regxs.Regx_match;
import gplx.types.basics.utls.BoolUtl;
class Scrib_regx_grp_mgr {
	private final List_adp capt_list = List_adp_.New();
	private final List_adp full_list = List_adp_.New();
	private final List_adp open_list = List_adp_.New();
	private final Hash_adp idx_list = Hash_adp_.New();
	private int fake_count;
	public void Clear() {
		open_list.Clear();
		capt_list.Clear();
		full_list.Clear();
		idx_list.Clear();
		fake_count = 0;
	}
	public int Full__len() {return full_list.Len();}
	public int Open__len() {return open_list.Len();}
	public int Open__get_at(int idx) {return IntUtl.Cast(open_list.GetAt(idx));}
	public void Open__pop() {List_adp_.DelAtLast(open_list);}
	public boolean Open__has(int v) {
		int len = open_list.Len();
		for (int i = 0; i < len; i++) {
			Object o = open_list.GetAt(i);
			if (IntUtl.Cast(o) == v) return true;
		}
		return false;
	}

	public int Capt__len() {return capt_list.Len();}
	public KeyVal[] Capt__to_ary() {return capt_list.Len() == 0 ? null : (KeyVal[])capt_list.ToAry(KeyVal.class);}
	public void Capt__add__real(int grp_idx, boolean is_empty_capture) {
		capt_list.Add(KeyVal.NewInt(grp_idx, is_empty_capture));
		open_list.Add(grp_idx);
		full_list.Add(new Scrib_regx_grp_itm(BoolUtl.N, is_empty_capture, full_list.Len()));
		idx_list.Add(grp_idx, full_list.Len());
	}
	public void Capt__add__fake(int count) {
		for (int i = 0; i < count; i++)
			full_list.Add(new Scrib_regx_grp_itm(BoolUtl.Y, BoolUtl.N, full_list.Len()));
		fake_count += count;
	}
	public void Idx__add(BryWtr bfr, int regx_idx) {
		int actl_idx = IntUtl.Cast(idx_list.GetByOrNull(regx_idx));
		bfr.AddIntVariable(actl_idx);
	}
	public Regx_match[] Adjust_balanced_many(Regx_match[] matches) {
		if (fake_count == 0) return matches;

		int matches_len = matches.length;
		Regx_match[] rv = new Regx_match[matches_len];
		for (int i = 0; i < matches_len; i++) {
			rv[i] = Adjust_balanced_one(matches[i]);
		}
		return rv;
	}
	public Regx_match Adjust_balanced_one(Regx_match match) {
		if (full_list.Len() == 0) return match; // no capture groups, so don't bother adjusting for balanced; DATE:2019-04-16

		Regx_group[] old_groups = match.Groups();
		Regx_group[] new_groups = new Regx_group[full_list.Len() - fake_count];
		int group_idx = 0;
		for (int j = 0; j < old_groups.length; j++) {
			Scrib_regx_grp_itm itm = (Scrib_regx_grp_itm)full_list.GetAt(j);
			if (itm.Is_fake()) continue;
			new_groups[group_idx++] = old_groups[j];
		}
		return new Regx_match(match.Rslt(), match.Find_bgn(), match.Find_end(), new_groups);
	}
}
class Scrib_regx_grp_itm {
	public Scrib_regx_grp_itm(boolean is_fake, boolean is_empty_capture, int idx) {
		this.is_fake = is_fake;
		this.is_empty_capture = is_empty_capture;
		this.idx = idx;
	}
	public boolean Is_fake() {return is_fake;} private final boolean is_fake;
	public boolean Is_empty_capture() {return is_empty_capture;} private final boolean is_empty_capture;
	public int Idx() {return idx;} private final int idx;
}
