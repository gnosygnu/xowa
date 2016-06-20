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
package gplx.xowa.addons.bldrs.exports.packs.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.packs.*;
class Pack_hash {
	private final    Ordered_hash hash = Ordered_hash_.New();
	public int			Len()				{return hash.Len();}
	public Pack_list	Get_at(int i)		{return (Pack_list)hash.Get_at(i);}
	public Pack_list	Get_by(int tid)		{return (Pack_list)hash.Get_by(tid);}
	public void	Add(Pack_zip_name_bldr bldr, int list_tid, Io_url file_url) {
		Pack_list list = (Pack_list)hash.Get_by(list_tid);
		if (list == null) {
			list = new Pack_list(list_tid);
			hash.Add(list_tid, list);
		}
		Pack_itm itm = new Pack_itm(list_tid, bldr.Bld(file_url), file_url);
		list.Add(itm);
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
