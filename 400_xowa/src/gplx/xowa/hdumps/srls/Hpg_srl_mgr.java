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
package gplx.xowa.hdumps.srls; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
import gplx.core.primitives.*; import gplx.xowa.hdumps.core.*; 
import gplx.xowa2.gui.*;
public class Hpg_srl_mgr {
	private Int_obj_ref count_ref = Int_obj_ref.zero_();
	public Hpg_srl_mgr(Hpg_srl_itm[] itm_ary) {this.itm_ary = itm_ary;}
	public Hpg_srl_itm		Itm_body()	{return itm_body;} private final Hpg_srl_itm itm_body = new Hpg_srl_itm__body();
	public Hpg_srl_itm[]	Itm_ary()	{return itm_ary;} private final Hpg_srl_itm[] itm_ary;
	public void Load(Xog_page hpg, byte[] bry) {
		if (bry == null) return;
		int bry_len = bry.length; if (bry_len == 0) return;
		int pos = itm_body.Load(hpg, bry, bry_len, 0, count_ref);				// assume every page has a body; saves 1 byte by not specifying tid for body
		while (pos < bry_len) {
			byte itm_tid = bry[pos];											// itm_tid is always 1-byte
			Hpg_srl_itm itm_parser = itm_ary[itm_tid];							// itm_tid always matches itm_ary's idx
			pos += itm_parser.Load(hpg, bry, bry_len, pos + 1, count_ref) + 1;	// +1 to skip tid
		}
	}
	public void Save(Xog_page hpg, Bry_bfr bfr) {
		for (Hpg_srl_itm itm : itm_ary)
			itm.Save(hpg, bfr);
	}
	public static final Hpg_srl_mgr _i_ = new Hpg_srl_mgr(Hpg_srl_itm_.Itms);
}
