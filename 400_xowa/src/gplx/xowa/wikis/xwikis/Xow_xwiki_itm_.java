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
package gplx.xowa.wikis.xwikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
public class Xow_xwiki_itm_ {
	public static Xow_xwiki_itm new_mw_(Bry_bfr tmp_bfr, Gfo_url_parser url_parser, Gfo_url tmp_url, byte[] key, byte[] url_php, byte[] name, byte cur_wiki_tid) {	// EX: "commons|//commons.wikimedia.org/wiki/Category:$1|Wikimedia Commons" "DMOZ|http://www.dmoz.org/Regional/Europe/$1/"|DMOZ"
		byte[] url_gfs = gplx.xowa.apps.Xoa_gfs_php_mgr.Xto_gfs(tmp_bfr, url_php);
		url_parser.Parse(tmp_url, url_gfs, 0, url_gfs.length);
		byte[] domain_bry = tmp_url.Site();
		Xow_wiki_domain domain = Xow_wiki_domain_.parse_by_domain(domain_bry);
		Xol_lang_itm lang_itm = Xol_lang_itm_.Get_by_key(domain.Lang());
		int lang_id = lang_itm == null ? Xol_lang_itm_.Id__unknown : lang_itm.Id();
		return new Xow_xwiki_itm(key, url_gfs, domain.Tid(), lang_id, domain_bry).Name_(name);
	}
}
