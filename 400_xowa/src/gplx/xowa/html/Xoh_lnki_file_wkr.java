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
package gplx.xowa.html; import gplx.*; import gplx.xowa.*;
import gplx.xowa.files.*;
public interface Xoh_lnki_file_wkr {
	void Write_img_full(Bry_bfr bfr, Xof_xfer_itm xfer_itm, int elem_id, byte[] link_ref, byte[] html_view_src, int html_w, int html_h, byte[] lnki_alt_text, byte[] lnki_ttl, byte[] anchor_cls, byte[] anchor_rel, byte[] anchor_title, byte[] itm_cls);
}
class Xoh_lnki_file_wkr_basic implements Xoh_lnki_file_wkr {		
	private Bry_fmtr img_full_fmtr;
	public void Init(Bry_fmtr img_full_fmtr) {
		this.img_full_fmtr = img_full_fmtr;
	}
	public void Write_img_full(Bry_bfr bfr, Xof_xfer_itm xfer_itm, int elem_id, byte[] lnki_href, byte[] html_view_src, int html_w, int html_h, byte[] lnki_alt_text, byte[] lnki_ttl, byte[] anchor_cls, byte[] anchor_rel, byte[] anchor_title, byte[] itm_cls) {
		img_full_fmtr.Bld_bfr_many(bfr, elem_id, lnki_href, html_view_src, html_w, html_h, lnki_alt_text, lnki_ttl, anchor_cls, anchor_rel, anchor_title, itm_cls);
	}
}
