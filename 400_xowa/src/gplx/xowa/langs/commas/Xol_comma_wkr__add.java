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
package gplx.xowa.langs.commas; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.xowa.langs.msgs.*;
public class Xol_comma_wkr__add implements Xol_comma_wkr {
	private byte[] comma_bry = Bry_.new_a7(", ");	// needed for TEST
	public void Evt_lang_changed(Xol_lang_itm lang_itm) {
		this.comma_bry = lang_itm.Msg_mgr().Val_by_bry_or(Bry_.new_a7("comma-separator"), Byte_ascii.Comma_bry);
	}
	public void Comma__itm(Bry_bfr bfr, int itm_idx, int itms_len) {
		if (itm_idx != itms_len - 1)
			bfr.Add(comma_bry);
	}
	public void Comma__end(Bry_bfr bfr) {}
}
