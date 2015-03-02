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
package gplx.xowa.html.hdumps.data.srl; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.hdumps.*; import gplx.xowa.html.hdumps.data.*;
import gplx.core.primitives.*; import gplx.xowa.html.hdumps.core.*; 
import gplx.xowa2.gui.*;
public class Xohd_page_srl_mgr {
	private final Xohd_page_srl_itm[] itm_ary; private final Xohd_page_srl_itm itm_body;
	private final Int_obj_ref count_ref = Int_obj_ref.zero_();
	public Xohd_page_srl_mgr(Xohd_page_srl_itm[] itm_ary) {
		this.itm_ary	= itm_ary;
		this.itm_body	= itm_ary[0];
	}
	public byte[] Save(Xog_page hpg, Bry_bfr bfr) {
		for (Xohd_page_srl_itm itm : itm_ary)
			itm.Save(hpg, bfr);
		return bfr.Xto_bry_and_clear();
	}
	public void Load(Xog_page hpg, byte[] bry) {
		if (bry == null) return;
		int bry_len = bry.length; if (bry_len == 0) return;
		int pos = itm_body.Load(hpg, bry, bry_len, 0, count_ref);		// assume every page has a body; saves 1 byte by not specifying tid for body
		while (pos < bry_len) {
			byte itm_tid = bry[pos];									// itm_tid is always 1-byte
			Xohd_page_srl_itm itm = itm_ary[itm_tid];					// itm_tid always matches itm_ary's idx
			pos += itm.Load(hpg, bry, bry_len, pos + 1, count_ref) + 1;	// +1 to skip tid
		}
	}
	public static final Xohd_page_srl_mgr I = new Xohd_page_srl_mgr(Xohd_page_srl_itm_.Itms);
}
