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
import gplx.core.primitives.*; import gplx.xowa.html.hzips.*; import gplx.xowa.gui.*;
public interface Xohd_page_srl_itm {	// INFO: serializes Xog_page to a byte[] before storing in the wiki_page_html table
	byte	Tid();
	void	Save(Xog_page hpg, Bry_bfr bfr);
	int		Load(Xog_page hpg, byte[] bry, int bry_len, int itm_bgn, Int_obj_ref count_ref);
}
abstract class Xohd_page_srl_itm__base implements Xohd_page_srl_itm {
	public abstract byte Tid();
	protected void Save_tid_n_() {save_tid = false;} private boolean save_tid = true;
	public void Save(Xog_page hpg, Bry_bfr bfr) {
		byte[] bry = Save_itm(hpg); if (bry == null) return;		// subclasses return null if nothing to save
		int len = bry.length;		if (len == 0) return;			// don't bother saving 0-len
		if (save_tid)												// body doesn't save tid
			bfr.Add_byte(this.Tid());
		Xow_hzip_int_.Save_bin_int_abrv(bfr, len);
		bfr.Add(bry);
	}
	public abstract byte[] Save_itm(Xog_page hpg);
	public int Load(Xog_page hpg, byte[] bry, int bry_len, int itm_bgn, Int_obj_ref count_ref) {
		int itm_len = Xow_hzip_int_.Load_bin_int_abrv(bry, bry_len, itm_bgn, count_ref); if (itm_len == -1) throw Err_.new_wo_type("bry_itm has invalid len", "page", hpg.Page_id(), "tid", this.Tid());			
		int data_bgn = itm_bgn + count_ref.Val(); if (itm_len == 0) return data_bgn;
		int data_end = data_bgn + itm_len;
		byte[] itm_data = Bry_.Mid(bry, data_bgn, data_end);
		this.Load_itm(hpg, itm_data);
		return data_end - itm_bgn;
	}
	public abstract void Load_itm(Xog_page hpg, byte[] data);
}
