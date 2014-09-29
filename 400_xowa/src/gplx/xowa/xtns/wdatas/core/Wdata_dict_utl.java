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
public class Wdata_dict_utl {
	public static byte Get_tid_or_invalid(byte[] qid, Hash_adp_bry dict, byte[] key) {
		Object rv_obj = dict.Get_by_bry(key);
		if (rv_obj == null) {
			Gfo_usr_dlg_._.Warn_many("", "", "unknown wikidata key; qid=~{0} key=~{1}", String_.new_utf8_(qid), String_.new_utf8_(key));
			return Tid_invalid;
		}
		return ((Byte_obj_val)rv_obj).Val();
	}
	public static final byte Tid_invalid = Byte_.MaxValue_127;
}
