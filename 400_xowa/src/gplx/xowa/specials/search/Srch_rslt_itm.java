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
package gplx.xowa.specials.search; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
public class Srch_rslt_itm {
	public final byte[]		key;
	public final byte[]		wiki_bry;
	public final Xoa_ttl		page_ttl;
	public final int			page_id;
	public final int			page_len;
	public Srch_rslt_itm(byte[] wiki_bry, Xoa_ttl page_ttl, int page_id, int page_len) {
		this.wiki_bry = wiki_bry;
		this.page_ttl = page_ttl;
		this.page_id = page_id;
		this.page_len = page_len;
		this.key = Bry_.Add(wiki_bry, Byte_ascii.Pipe_bry, page_ttl.Full_db());
	}
}
class Srch_rslt_itm_sorter implements gplx.core.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Srch_rslt_itm lhs = (Srch_rslt_itm)lhsObj;
		Srch_rslt_itm rhs = (Srch_rslt_itm)rhsObj;
		return -Int_.Compare(lhs.page_len, rhs.page_len);
	}
        public static final Srch_rslt_itm_sorter Page_len_dsc = new Srch_rslt_itm_sorter(); Srch_rslt_itm_sorter() {}
}
