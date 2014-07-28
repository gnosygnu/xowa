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
import gplx.xowa.files.*;
class Xoh_file_main_alts implements Bry_fmtr_arg {
	private Xof_xfer_itm orig_itm; private Xoh_file_page opt; private Xof_xfer_itm xfer_itm = new Xof_xfer_itm();
	public Xoh_file_main_alts Ctor(Xof_xfer_itm orig_itm, Xoh_file_page opt) {
		this.orig_itm = orig_itm; this.opt = opt; return this;
	}
	public void XferAry(Bry_bfr bfr, int idx) {
		Int_2_ref[] size_alts = opt.Size_alts();
		int len = size_alts.length;
		for (int i = 0; i < len; i++) {
			Int_2_ref size = size_alts[i];
			if (xfer_itm.Html_w() < size.Val_0()) continue;
			xfer_itm.Init_by_lnki(orig_itm.Lnki_ttl(), orig_itm.Lnki_redirect(), Xop_lnki_type.Id_none, size.Val_0(), size.Val_1(), Xop_lnki_tkn.Upright_null, Xof_doc_thumb.Null, Xof_doc_page.Null);
			xfer_itm.Atrs_calc_for_html();
			opt.Html_alts().Bld_bfr_many(bfr, xfer_itm.Html_w(), xfer_itm.Html_h(), xfer_itm.Html_view_src(), i == len - 1 ? opt.Html_alt_dlm_last() : opt.Html_alt_dlm_default());
		}
	}	
}
