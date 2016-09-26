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
package gplx.xowa.addons.wikis.ctgs.htmls.pageboxs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*;
import gplx.core.lists.hashs.*;
class Xoctg_pagebox_hash {
	private final    Ordered_hash hash_by_ttl = Ordered_hash_.New_bry();
	private final    Hash_adp__int hash_by_id = new Hash_adp__int();
	public int Len() {return hash_by_ttl.Len();}
	public Xoctg_pagebox_itm Get_at(int i) {return (Xoctg_pagebox_itm)hash_by_ttl.Get_at(i);}
	public Xoctg_pagebox_itm Get_by_ttl(byte[] full_db) {return (Xoctg_pagebox_itm)hash_by_ttl.Get_by(full_db);}
	public Xoctg_pagebox_itm Get_by_id(int page_id) {return (Xoctg_pagebox_itm)hash_by_id.Get_by_or_fail(page_id);}
	public Xoctg_pagebox_itm[] To_ary_and_clear() {
		hash_by_id.Clear();
		return (Xoctg_pagebox_itm[])hash_by_ttl.To_ary_and_clear(Xoctg_pagebox_itm.class);
	}
	public Xoctg_pagebox_itm Add_by_ttl(Xoa_ttl ttl) {
		Xoctg_pagebox_itm rv = Xoctg_pagebox_itm.New_by_ttl(ttl);
		hash_by_ttl.Add(ttl.Full_db(), rv);
		return rv;
	}
	public void Sort_and_fill_ids() {
		// sort
		hash_by_ttl.Sort_by(Xoctg_pagebox_hash_sorter.Sorter);

		// fill ids_hash
		hash_by_id.Clear();
		int len = hash_by_ttl.Len();
		for (int i = 0; i < len; ++i) {
			Xoctg_pagebox_itm itm = (Xoctg_pagebox_itm)hash_by_ttl.Get_at(i);
			hash_by_id.Add(itm.Id(), itm);
		}
	}
}
class Xoctg_pagebox_hash_sorter implements gplx.core.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Xoctg_pagebox_itm lhs = (Xoctg_pagebox_itm)lhsObj;
		Xoctg_pagebox_itm rhs = (Xoctg_pagebox_itm)rhsObj;
		return -Int_.Compare(lhs.Count__all(), rhs.Count__all());
	}
	public static final    Xoctg_pagebox_hash_sorter Sorter = new Xoctg_pagebox_hash_sorter(); Xoctg_pagebox_hash_sorter() {}
}
