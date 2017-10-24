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
package gplx.xowa.addons.bldrs.exports.packs.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.packs.*;
class Pack_hash {
	private final    Ordered_hash hash = Ordered_hash_.New();
	public int			Len()				{return hash.Len();}
	public Pack_list	Get_at(int i)		{return (Pack_list)hash.Get_at(i);}
	public Pack_list	Get_by(int tid)		{return (Pack_list)hash.Get_by(tid);}
	public Pack_itm Add(Pack_zip_name_bldr bldr, int list_tid, Io_url file_url) {return Add(list_tid, bldr.Bld(file_url), file_url);}
	public Pack_itm Add(int list_tid, Io_url pack_url, Io_url... raw_urls) {
		Pack_list list = (Pack_list)hash.Get_by(list_tid);
		if (list == null) {
			list = new Pack_list(list_tid);
			hash.Add(list_tid, list);
		}
		Pack_itm itm = new Pack_itm(list_tid, pack_url, raw_urls);

		// check if file exists; needed for wikitext packs which add same urls as html packs; DATE:2017-04-09
		if (list.Has(pack_url)) 
			return itm;
		list.Add(itm);
		return itm;
	}
	public void Consolidate(int... tids) {	// merge n itms into 1 itm; needed for search-core + search-link -> search
		int tids_len = tids.length;
		for (int i = 0; i < tids_len; ++i) {
			Pack_list list = (Pack_list)hash.Get_by(tids[i]);
			if (list == null) continue;	// tid doesn't exist; EX: search in Tid_few
			int list_len = list.Len();
			Pack_itm itm_0 = (Pack_itm)list.Get_at(0);
			Io_url[] urls = new Io_url[list_len];
			for (int j = 0; j < list_len; ++j) {
				urls[j] = ((Pack_itm)list.Get_at(j)).Raw_urls()[0];
			}
			list.Clear();
			itm_0.Raw_urls_(urls);
			list.Add(itm_0);
		}
	}
}
