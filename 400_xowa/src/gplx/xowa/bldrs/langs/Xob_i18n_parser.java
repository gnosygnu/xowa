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
package gplx.xowa.bldrs.langs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
public class Xob_i18n_parser {
	public void Load_msgs(boolean dirty, Xol_lang lang, Io_url i18n_fil) {
		String i18n_str = Io_mgr._.LoadFilStr_args(i18n_fil).MissingIgnored_().Exec(); if (String_.Len_eq_0(i18n_str)) return;
		Json_itm_wkr__msgs wkr = new Json_itm_wkr__msgs();
		wkr.Ctor(dirty, lang.Msg_mgr());
		wkr.Exec(Bry_.new_utf8_(i18n_str));
	}
	public byte[] Xto_gfs(byte[] raw) {
		Json_itm_wkr__gfs wkr = new Json_itm_wkr__gfs();
		wkr.Exec(raw);
		return wkr.Xto_bry();
	}
}
