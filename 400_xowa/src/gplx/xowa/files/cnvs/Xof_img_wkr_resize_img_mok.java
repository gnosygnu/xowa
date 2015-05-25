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
package gplx.xowa.files.cnvs; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.core.primitives.*; import gplx.gfui.*; import gplx.xowa.files.cnvs.*;
public class Xof_img_wkr_resize_img_mok implements Xof_img_wkr_resize_img {
	public boolean Exec(Io_url src, Io_url trg, int trg_w, int trg_h, int ext_id, String_obj_ref rslt_val) {
		SizeAdp src_size = ImageAdp_.txt_fil_(src).Size();
		int src_w = src_size.Width(), src_h = src_size.Height();
		if (trg_w < 1) throw Err_.new_fmt_("trg_w must be > 0: {0}", trg_w);
		if (trg_h < 1) trg_h = Xof_xfer_itm_.Scale_h(src_w, src_h, trg_w);
		Io_mgr.I.SaveFilStr(trg, SizeAdp_.new_(trg_w, trg_h).XtoStr());
		return true;
	}
	public static final Xof_img_wkr_resize_img_mok _ = new Xof_img_wkr_resize_img_mok(); Xof_img_wkr_resize_img_mok() {}
}
