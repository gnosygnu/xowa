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
package gplx.xowa.wikis.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.flds.*;
import gplx.xowa.wikis.nss.*;
public class Xoctg_view_itm implements gplx.CompareAble {
	public byte				Tid() {return tid;} private byte tid;				// Xoa_ctg_mgr.Tid*
	public int				Page_id() {return page_id;} private int page_id;
	public Xoa_ttl			Ttl() {return ttl;} private Xoa_ttl ttl;
	public byte[]			Sort_key() {return sort_key;} private byte[] sort_key;
	public int				Sort_idx() {return sort_idx;} private int sort_idx;
	public boolean				Missing() {return missing;} private boolean missing;				public void				Missing_y_() {missing = true;}

	public Xoctg_view_itm	Set__page(byte tid, int page_id) {this.tid = tid; this.page_id = page_id; return this;}
	public Xoctg_view_itm	Set__ttl__sortkey(Xoa_ttl ttl, byte[] sort_key) {
		this.ttl = ttl;
		Sort_key_(sort_key);
		return this;
	}
	public Xoctg_view_itm	Parse(Gfo_fld_rdr fld_rdr, int sort_idx) {	// NOTE: XO stores data as "page_id|ctg_added_on|sortkey"
		this.sort_idx = sort_idx;
		page_id	= fld_rdr.Read_int_base85_len5();
		fld_rdr.Read_int_base85_len5();	// skip ctg_added_on; not used
		Sort_key_(fld_rdr.Read_bry_escape());
		return this;
	}
	public void Clear() {
		sort_idx = page_id = 0;
		sort_key = null;
	}
	private void Sort_key_(byte[] v) {
		this.sort_key = v;
		if (sort_key.length == 0)		// v1 will not have sortkey data; PAGE:s.w:Category:Computer_science DATE:2015-11-22
			sort_key = ttl.Page_db();
	}
	public int compareTo(Object obj) {Xoctg_view_itm comp = (Xoctg_view_itm)obj; return Int_.Compare(page_id, comp.Page_id());}
	public static final Xoctg_view_itm[] Ary_empty = new Xoctg_view_itm[0];
}
