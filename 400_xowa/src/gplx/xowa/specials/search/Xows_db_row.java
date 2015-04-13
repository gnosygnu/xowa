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
public class Xows_db_row {
	public Xows_db_row(byte[] wiki_domain, int page_id, int page_ns, int page_len, byte[] page_ttl_w_ns, byte[] page_ttl_wo_ns) {
		this.wiki_domain = wiki_domain;
		this.page_id = page_id;
		this.page_ns = page_ns;
		this.page_len = page_len;
		this.page_ttl_w_ns = page_ttl_w_ns;
		this.page_ttl_wo_ns = page_ttl_wo_ns;
		this.key = Bry_.Add_w_dlm(Byte_ascii.Pipe, wiki_domain, page_ttl_w_ns);			
	}
	public byte[]		Key()				{return key;}				private final byte[] key;
	public byte[]		Wiki_domain()		{return wiki_domain;}		private final byte[] wiki_domain;
	public int			Page_id()			{return page_id;}			private final int page_id;
	public int			Page_ns()			{return page_ns;}			private final int page_ns;
	public int			Page_len()			{return page_len;}			private final int page_len;
	public byte[]		Page_ttl_w_ns()		{return page_ttl_w_ns;}		private final byte[] page_ttl_w_ns;
	public byte[]		Page_ttl_wo_ns()	{return page_ttl_wo_ns;}	private final byte[] page_ttl_wo_ns;
	public static Xows_db_row[] Ary(Xows_db_row... v) {return v;}
}
class Xows_db_row_sorter implements gplx.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Xows_db_row lhs = (Xows_db_row)lhsObj;
		Xows_db_row rhs = (Xows_db_row)rhsObj;
		return -Int_.Compare(lhs.Page_len(), rhs.Page_len());
	}
        public static final Xows_db_row_sorter Page_len_dsc = new Xows_db_row_sorter(); Xows_db_row_sorter() {}
}
