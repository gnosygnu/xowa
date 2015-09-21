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
import gplx.xowa.langs.cfgs.*;
public class Xow_lang_itm {
	private final Xow_lang_grp html_grp; private final Xow_xwiki_itm xwiki; private final Xoac_lang_itm lang;
	public Xow_lang_itm(Xow_lang_grp html_grp, Xow_xwiki_itm xwiki, Xoac_lang_itm lang) {
		this.html_grp = html_grp; this.xwiki = xwiki; this.lang = lang;
	}
	public byte[] Lang_key() {return lang.Key_bry();}
	public byte[] Lang_domain() {return xwiki.Domain_bry();}
	public byte[] Lang_name() {return lang.Local_name_bry();}
	public byte[] Page_name() {return page_name;} private byte[] page_name;
	public boolean Page_name_has() {return Bry_.Len_gt_0(page_name);}
	public boolean Empty_xwiki() {return empty_xwiki;} private boolean empty_xwiki;
	public byte[][] Page_badges() {return page_badges;} private byte[][] page_badges;
	public void Html_bld(Bry_bfr bfr, Xowe_wiki wiki) {
		html_grp.Html_itm().Bld_bfr(bfr, lang.Key_bry(), xwiki.Domain_bry(), lang.Local_name_bry(), page_name);
	}
	public void Atrs_set(byte[] page_name, boolean empty_xwiki, byte[][] page_badges) {
		this.page_name = page_name; this.empty_xwiki = empty_xwiki; this.page_badges = page_badges;
		html_grp.Itms_active_len_add_one_();
	}
}
