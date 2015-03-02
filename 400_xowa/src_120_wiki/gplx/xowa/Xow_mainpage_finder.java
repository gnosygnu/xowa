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
package gplx.xowa; import gplx.*;
import gplx.xowa.langs.msgs.*;
public class Xow_mainpage_finder {
	public static byte[] Find_or(Xowe_wiki wiki, byte[] or) {
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b512();
		Xol_msg_itm msg_itm = Xol_msg_mgr_.Get_msg_itm(tmp_bfr, wiki, wiki.Lang(), Msg_mainpage);
		byte[] rv = msg_itm.Src_is_missing()
			? or
			: Xol_msg_mgr_.Get_msg_val(tmp_bfr, wiki, msg_itm, Bry_.Ary_empty)
			;
		tmp_bfr.Mkr_rls();
		return rv;
	}
	public static final byte[] Msg_mainpage = Bry_.new_ascii_("mainpage");
}
