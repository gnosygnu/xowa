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
package gplx.xowa.wikis.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.caches.*;
import gplx.xowa.wikis.xwikis.sitelinks.*;
public class Xow_cache_mgr {
	private final    Xowe_wiki wiki;
	public Xow_cache_mgr(Xowe_wiki wiki) {
		this.wiki = wiki;
		this.page_cache = new Xow_page_cache(wiki);
		this.defn_cache = new Xow_defn_cache(wiki.Lang());
		this.ifexist_cache = new Xow_ifexist_cache(wiki, page_cache);
	}
	public Hash_adp				Tmpl_result_cache() {return tmpl_result_cache;} private final    Hash_adp tmpl_result_cache = Hash_adp_bry.cs();
	public Xow_defn_cache		Defn_cache()		{return defn_cache;}		private final    Xow_defn_cache defn_cache;
	public Hash_adp_bry			Lst_cache()			{return lst_cache;}			private final    Hash_adp_bry lst_cache = Hash_adp_bry.cs(); 
	public Hash_adp				Misc_cache()		{return misc_cache;}		private final    Hash_adp misc_cache = Hash_adp_.New();
	public Xow_page_cache		Page_cache()		{return page_cache;}		private Xow_page_cache page_cache;
	public Gfo_cache_mgr		Commons_cache()		{return commons_cache;}		private Gfo_cache_mgr commons_cache = new Gfo_cache_mgr().Max_size_(64 * Io_mgr.Len_mb).Reduce_by_(32 * Io_mgr.Len_mb);
	public Xow_ifexist_cache	Ifexist_cache()		{return ifexist_cache;}		private Xow_ifexist_cache ifexist_cache;

	public Xow_cache_mgr	Page_cache_(Xow_page_cache v) {this.page_cache = v; return this;}
	public Xow_cache_mgr	Commons_cache_(Gfo_cache_mgr v) {this.commons_cache = v; return this;}
	public Xow_cache_mgr	Ifexist_cache_(Xow_ifexist_cache v) {this.ifexist_cache = v; return this;}
	public void Load_wkr_(Xow_page_cache_wkr v) {
		page_cache.Load_wkr_(v);
		ifexist_cache.Load_wkr_(v);
	}
	public Keyval[] Scrib_lang_names() {
		if (scrib_lang_names == null) {
			List_adp list = List_adp_.New();
			Xoa_sitelink_itm_mgr itm_mgr = wiki.App().Xwiki_mgr__sitelink_mgr().Itm_mgr();
			int len = itm_mgr.Len();
			for (int i = 0; i < len; ++i) {
				Xoa_sitelink_itm itm = itm_mgr.Get_at(i);
				Keyval kv = Keyval_.new_(String_.new_u8(itm.Key()), String_.new_u8(itm.Name()));
				list.Add(kv);
			}
			scrib_lang_names = (Keyval[])list.To_ary(Keyval.class);
		}
		return scrib_lang_names;
	}
	public void Free_mem__page()	{this.Free_mem(Free_mem__page_tid);}
	public void Free_mem__wbase()	{this.Free_mem(Free_mem__wbase_tid);}
	public void Free_mem__all()		{this.Free_mem(Free_mem__all_tid);}
	private void Free_mem(int level) {
		switch (level) {
			case Free_mem__page_tid:
				page_cache.Free_mem(false);
				break;
			case Free_mem__wbase_tid:
				page_cache.Free_mem(false);
				wiki.Appe().Wiki_mgr().Wdata_mgr().Clear();
				break;
			case Free_mem__all_tid:
				page_cache.Free_mem(true);
				wiki.Appe().Wiki_mgr().Wdata_mgr().Clear();
				commons_cache.Clear();
				ifexist_cache.Clear();
				break;
		}
		wiki.Ctg__catpage_mgr().Free_mem_all();
		tmpl_result_cache.Clear();
		defn_cache.Free_mem_all();
		lst_cache.Clear();
		misc_cache.Clear();
		// scrib_lang_names = null;
	}
	private static final int Free_mem__page_tid = 0, Free_mem__wbase_tid = 1, Free_mem__all_tid = 2;
	private static Keyval[] scrib_lang_names;
}
