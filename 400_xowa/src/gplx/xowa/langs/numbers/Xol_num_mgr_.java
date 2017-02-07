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
package gplx.xowa.langs.numbers; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
public class Xol_num_mgr_ {
	public static Xol_num_mgr new_by_lang_id(int lang_id) {
		switch (lang_id) {
			case Xol_lang_stub_.Id_be_tarask:
			case Xol_lang_stub_.Id_bg:
			case Xol_lang_stub_.Id_ru:
			case Xol_lang_stub_.Id_pl:
			case Xol_lang_stub_.Id_uk:
			case Xol_lang_stub_.Id_es:
			case Xol_lang_stub_.Id_et:
			case Xol_lang_stub_.Id_hy:
			case Xol_lang_stub_.Id_kaa:
			case Xol_lang_stub_.Id_kk_cyrl:
			case Xol_lang_stub_.Id_ksh:
			// case Xol_lang_stub_.Id_ku_ku:
										return new Xol_num_mgr__commafy_5();
			case Xol_lang_stub_.Id_km:
			case Xol_lang_stub_.Id_my:	return new Xol_num_mgr__noop();
			default:					return new Xol_num_mgr();
		}
	}
}
