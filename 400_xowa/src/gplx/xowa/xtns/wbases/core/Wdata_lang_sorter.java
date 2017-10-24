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
package gplx.xowa.xtns.wbases.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.xowa.apps.apis.xowa.xtns.*;
public class Wdata_lang_sorter implements Gfo_evt_itm, gplx.core.lists.ComparerAble {
	private Hash_adp_bry hash = Hash_adp_bry.cs();
	public Wdata_lang_sorter() {evt_mgr = new Gfo_evt_mgr(this);}
	public Gfo_evt_mgr Evt_mgr() {return evt_mgr;} private Gfo_evt_mgr evt_mgr;
	public byte[][] Langs() {return langs;} private byte[][] langs;
	public void Langs_(byte[][] langs) {
		this.langs = langs;
		hash.Clear();
		int len = langs.length;
		for (int i = 0; i < len; ++i) {
			byte[] lang = langs[i];
			Wdata_lang_sorter_itm itm = new Wdata_lang_sorter_itm(i, lang);
			hash.Add_if_dupe_use_1st(lang, itm);
		}
	}
	public int compare(Object lhsObj, Object rhsObj) {
		Wdata_lang_sortable lhs = (Wdata_lang_sortable)lhsObj;
		Wdata_lang_sortable rhs = (Wdata_lang_sortable)rhsObj;
		int lhs_lang_sort = lhs.Lang_sort(), rhs_lang_sort = rhs.Lang_sort();
		if (lhs_lang_sort != Sort_none || rhs_lang_sort != Sort_none)	// one of the items has a lang order
			return Int_.Compare(lhs_lang_sort, rhs_lang_sort);			// sort by defined lang order
		else
			return Bry_.Compare(lhs.Lang_code(), rhs.Lang_code());		// sort by alphabetical
	}
	public void Init_by_wdoc(Wdata_doc wdoc) {
		if ((Bry_.Ary_eq(wdoc.Sort_langs(), langs))) return;
		wdoc.Sort_langs_(langs);
		Sort_wdoc_list(Bool_.Y, wdoc.Slink_list());
		Sort_wdoc_list(Bool_.N, wdoc.Label_list());
		Sort_wdoc_list(Bool_.N, wdoc.Descr_list());
		Sort_wdoc_list(Bool_.N, wdoc.Alias_list());
	}
	private void Sort_wdoc_list(boolean is_slink, Ordered_hash list) {
		int len = list.Count();
		for (int i = 0; i < len; ++i) {
			Wdata_lang_sortable itm = (Wdata_lang_sortable)list.Get_at(i);
			if (is_slink) {
				Wdata_sitelink_itm slink = (Wdata_sitelink_itm)itm;
				byte[] lang_val = slink.Domain_info().Lang_orig_key();	// use orig, not cur; EX: simplewiki has orig of "simple" but lang of "en"
				if (Bry_.Len_eq_0(lang_val)) lang_val = slink.Site();	// unknown lang; EX: "xyzwiki" -> ""; make site = lang, else "" lang will sort towards top of list; PAGE:wd.q:20 DATE:2014-10-03
				slink.Lang_(lang_val);
			}
			itm.Lang_sort_(Sort_calc(itm));
		}
	}
	private int Sort_calc(Wdata_lang_sortable data_itm) {
		Wdata_lang_sorter_itm sort_itm = (Wdata_lang_sorter_itm)hash.Get_by(data_itm.Lang_code());
		int new_sort = sort_itm == null ? Sort_none : sort_itm.Sort();
		data_itm.Lang_sort_(new_sort);
		return new_sort;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Xoapi_wikibase.Evt_sort_langs_changed))	Langs_((byte[][])m.ReadObj("v", null));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final int Sort_null = Int_.Min_value;
	private static final int Sort_none = Int_.Max_value;
}
class Wdata_lang_sorter_itm {
	public Wdata_lang_sorter_itm(int sort, byte[] lang) {this.sort = sort; this.lang = lang;}
	public int Sort() {return sort;} private final     int sort;
	public byte[] Lang() {return lang;} private final    byte[] lang;
}
