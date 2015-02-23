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
import gplx.xowa.langs.*;
public class Xow_xwiki_itm implements gplx.CompareAble {
	public Xow_xwiki_itm(byte[] key_bry, byte[] url_fmt, int lang_id, int domain_tid, byte[] domain_bry, byte[] domain_name) {
		this.key_bry = key_bry; this.key_str = String_.new_utf8_(key_bry); 
		this.url_fmt = url_fmt; this.lang_id = lang_id;
		this.domain_tid = domain_tid; this.domain_bry = domain_bry; this.domain_name = domain_name;
	}
	public byte[]	Key_bry() {return key_bry;} private final byte[] key_bry;				// EX: commons
	public String	Key_str() {return key_str;} private final String key_str;
	public byte[]	Url_fmt() {return url_fmt;} private final byte[] url_fmt;				// EX: //commons.wikimedia.org/wiki/Category:$1
	public int		Lang_id() {return lang_id;} private final int lang_id;					// EX: Id__unknown
	public int		Domain_tid() {return domain_tid;} private final int domain_tid;			// EX: Tid_int_commons
	public byte[]	Domain_bry() {return domain_bry;} private final byte[] domain_bry;		// EX: commons.wikimedia.org
	public byte[]	Domain_name() {return domain_name;} private final byte[] domain_name;	// EX: Wikimedia Commons
	public boolean		Offline() {return offline;} public Xow_xwiki_itm Offline_(boolean v) {offline = v; return this;} private boolean offline;
	public int compareTo(Object obj) {Xow_xwiki_itm comp = (Xow_xwiki_itm)obj; return Bry_.Compare(key_bry, comp.key_bry);}
	public boolean Type_is_xwiki_lang(int cur_lang_id) {
		return	lang_id != Xol_lang_itm_.Id__unknown		// valid lang code
			&&	domain_tid != Xow_domain_.Tid_int_commons	// commons should never be considered an xwiki_lang; EX:[[commons:A]] PAGE:species:Scarabaeidae; DATE:2014-09-10
			&&	lang_id != cur_lang_id 						// lang is different than current; EX: [[en:A]] in en.wikipedia.org shouldn't link back to self
			&&	Bry_.Len_gt_0(url_fmt)						// url_fmt exists
			;
	}

	public static Xow_xwiki_itm new_(byte[] key_bry, byte[] url_fmt, int lang_id, int domain_tid, byte[] domain_bry) {
		return new Xow_xwiki_itm(key_bry, url_fmt, lang_id, domain_tid, domain_bry, domain_bry);
	}
	public static Xow_xwiki_itm new_by_mw(Bry_bfr bfr, Gfo_url_parser url_parser, Gfo_url url, byte[] key, byte[] mw_url, byte[] domain_name) {// EX: "commons|//commons.wikimedia.org/wiki/Category:$1|Wikimedia Commons" "DMOZ|http://www.dmoz.org/Regional/Europe/$1/"|DMOZ"
		byte[] gfs_url = gplx.xowa.apps.Xoa_gfs_php_mgr.Xto_gfs(bfr, mw_url);	// EX: "//commons.wikimedia.org/wiki/Category:$1" -> "//commons.wikimedia.org/wiki/Category:~{0}"
		url_parser.Parse(url, gfs_url, 0, gfs_url.length);
		byte[] domain_bry = url.Site();											// extract "commons.wikimedia.org"
		Xow_domain domain = Xow_domain_.parse(domain_bry);
		Xol_lang_itm lang_itm = Xol_lang_itm_.Get_by_key(domain.Lang_key());
		int lang_id = lang_itm == null ? Xol_lang_itm_.Id__unknown : lang_itm.Id();
		return new Xow_xwiki_itm(key, gfs_url, lang_id, domain.Domain_tid(), domain_bry, domain_name);
	}
}
