/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.addons.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*;
import gplx.xowa.wikis.data.tbls.*;
public class Xoax_ctg_addon implements Xoax_addon_itm {	// TODO:mem_mgr
	private final Xow_wiki wiki;
	private final Hash_adp_bry hash = Hash_adp_bry.cs();
	public Xoax_ctg_addon(Xow_wiki wiki) {this.wiki = wiki;}
	public byte[] Addon__key() {return Key_const;} public static final byte[] Key_const = Bry_.new_a7("xowa.category");
	public Xoctg_ctg_itm Itms__get_or_null(byte[] key) {return (Xoctg_ctg_itm)hash.Get_by_bry(key);}
	public Xoctg_ctg_itm Itms__load(byte[] key) {
		Xowd_page_itm tmp_page = new Xowd_page_itm();
		wiki.Data__core_mgr().Tbl__page().Select_by_ttl(tmp_page, wiki.Ns_mgr().Ns_category(), key);
		gplx.xowa.wikis.data.tbls.Xowd_category_itm itm = wiki.Data__core_mgr().Db__cat_core().Tbl__cat_core().Select(tmp_page.Id());
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
		Xoax_ctg_addon rv = (Xoax_ctg_addon)wiki.Addon_mgr().Itms__get_or_null(Key_const);
		if (rv == null) {
			rv = new Xoax_ctg_addon(wiki);
			wiki.Addon_mgr().Itms__add(rv);
		}
		return rv;
	}
}
