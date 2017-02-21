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
package gplx.xowa.addons.wikis.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*;
import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.addons.wikis.ctgs.dbs.*;
public class Xoax_ctg_addon implements Xoax_addon_itm {	// TODO_OLD:mem_mgr
	private final    Xow_wiki wiki;
	private final    Hash_adp_bry hash = Hash_adp_bry.cs();
	public Xoax_ctg_addon(Xow_wiki wiki) {this.wiki = wiki;}
	public Xoctg_ctg_itm Itms__get_or_null(byte[] key) {return (Xoctg_ctg_itm)hash.Get_by_bry(key);}
	public Xoctg_ctg_itm Itms__load(byte[] key) {
		Xowd_page_itm tmp_page = new Xowd_page_itm();
		wiki.Data__core_mgr().Tbl__page().Select_by_ttl(tmp_page, wiki.Ns_mgr().Ns_category(), key);
		gplx.xowa.wikis.data.tbls.Xowd_category_itm itm = Xodb_cat_db_.Get_cat_core_or_fail(wiki.Data__core_mgr()).Select(tmp_page.Id());
		return Itms__add(key, itm.Count_pages(), itm.Count_subcs(), itm.Count_files());
	}
	public Xoctg_ctg_itm Itms__add(byte[] key, int pages, int subcs, int files) {	// TEST:
		Xoctg_ctg_itm rv = new Xoctg_ctg_itm(key, pages, subcs, files);
		hash.Add(key, rv);
		return rv;
	}
/*
	public long Mem__size__max()	{return mem__size__max;}	private long mem__size__max;
	public long Mem__size__reduce()	{return mem__size__reduce;} private long mem__size__reduce;
	public void Mem__free__all()	{hash.Clear();}
	public void Mem__free__reduce()	{Mem_mgr_.Free__reduce(hash);}
	public void Mem__free__unused()	{Mem_mgr_.Free__unused(hash);}
	public long Mem__stat__size()	{return mem__stat__size;} private long mem__stat__size;
	public long Mem__stat__last()	{return mem__stat__last;} private long mem__stat__last;
	public int  Mem__stat__count()	{return mem__stat__count;} private int mem__stat__count;
*/
	public static Xoax_ctg_addon Get(Xow_wiki wiki) {
		Xoax_ctg_addon rv = (Xoax_ctg_addon)wiki.Addon_mgr().Itms__get_or_null(ADDON_KEY);
		if (rv == null) {
			rv = new Xoax_ctg_addon(wiki);
			wiki.Addon_mgr().Itms__add(rv);
		}
		return rv;
	}

	public String Addon__key() {return ADDON_KEY;} private static final String ADDON_KEY = "xowa.apps.category";
}
