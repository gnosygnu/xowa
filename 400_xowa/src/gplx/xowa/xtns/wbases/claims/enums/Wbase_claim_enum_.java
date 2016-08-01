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
package gplx.xowa.xtns.wbases.claims.enums; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.claims.*;
public class Wbase_claim_enum_ {
	public static byte To_tid_or_invalid(Hash_adp hash, byte[] url, String key) {
		Object rv_obj = hash.Get_by(key);
		if (rv_obj == null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "unknown enum key for wikibase; url=~{0} key=~{1}", url, key);
			return Tid__invalid;
		}
		return ((Wbase_claim_enum)rv_obj).Tid();
	}
	public static byte To_tid_or_invalid(Hash_adp_bry hash, byte[] url, byte[] key) {
		Object rv_obj = hash.Get_by_bry(key);
		if (rv_obj == null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "unknown enum key for wikibase; url=~{0} key=~{1}", url, key);
			return Tid__invalid;
		}
		return ((Wbase_claim_enum)rv_obj).Tid();
	}
	public static final byte Tid__invalid = Byte_.Max_value_127;
}