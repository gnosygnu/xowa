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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*;
public class Xoctg_catpage_itm implements gplx.CompareAble {
	public Xoctg_catpage_itm(int page_id, Xoa_ttl page_ttl, byte[] sort_key) {
		this.page_id = page_id;
		this.page_ttl = page_ttl;
		this.sort_key = sort_key.length == 0 ? page_ttl.Page_db() : sort_key; // v1 will not have sortkey data; PAGE:s.w:Category:Computer_science DATE:2015-11-22
	}
	public int					Page_id() {return page_id;} private int page_id;
	public Xoa_ttl				Page_ttl() {return page_ttl;} private Xoa_ttl page_ttl;
	public byte[]				Sort_key() {return sort_key;} private byte[] sort_key;
	public boolean				Missing() {return missing;} private boolean missing;	// not used; remove?;
	public void					Missing_y_() {missing = true;}

	public int compareTo(Object obj) {Xoctg_catpage_itm comp = (Xoctg_catpage_itm)obj; return Int_.Compare(page_id, comp.Page_id());}
	public static final    Xoctg_catpage_itm[] Ary_empty = new Xoctg_catpage_itm[0];
}
