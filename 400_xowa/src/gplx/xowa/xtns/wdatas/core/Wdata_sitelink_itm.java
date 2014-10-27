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
package gplx.xowa.xtns.wdatas.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.json.*; import gplx.xowa.wikis.*;
public class Wdata_sitelink_itm implements Wdata_lang_sortable {
	public Wdata_sitelink_itm(byte[] site, byte[] name, byte[][] badges) {this.site = site; this.name = name; this.badges = badges;} 
	public byte[] Site() {return site;} private final byte[] site;
	public byte[] Name() {return name;} private final byte[] name;
	public byte[][] Badges() {return badges;} private final byte[][] badges;
	public byte[] Lang() {return lang;} public void Lang_(byte[] v) {lang = v;} private byte[] lang = Bry_.Empty;
	public byte[] Lang_code() {return lang;}
	public int Lang_sort() {return lang_sort;} public void Lang_sort_(int v) {lang_sort = v;} private int lang_sort = Wdata_lang_sorter.Sort_null;
	public Xow_wiki_domain Domain_info() {if (domain_info == null) domain_info = Xow_wiki_alias.parse_by_wmf_key(site); return domain_info;} private Xow_wiki_domain domain_info;
	public Xoa_ttl Page_ttl() {return page_ttl;} public Wdata_sitelink_itm Page_ttl_(Xoa_ttl v) {page_ttl = v; return this;} private Xoa_ttl page_ttl;	// PERF: cache title to avoid creating new Object for "In Other langs"; DATE:2014-10-20
	@Override public String toString() {// TEST:
		return String_.Concat_with_str("|", String_.new_utf8_(site), String_.new_utf8_(name), String_.Concat_with_str(",", String_.Ary(badges)));
	}
}
