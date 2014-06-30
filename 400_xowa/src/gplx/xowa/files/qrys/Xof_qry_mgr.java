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
package gplx.xowa.files.qrys; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.xowa.files.fsdb.*;
public class Xof_qry_mgr {
	private Xof_qry_wkr[] wkrs; private int wkrs_len;
	private Xof_img_size img_size = new Xof_img_size();
	public Xof_qry_mgr() {this.Clear();}
	public void Clear() {wkrs = Xof_qry_wkr_.Ary_empty; wkrs_len = 0;}
	public void Add(Xof_qry_wkr wkr) {Add_many(wkr);}
	public void Add_many(Xof_qry_wkr... v) {
		wkrs = (Xof_qry_wkr[])Array_.Resize_add(wkrs, v);
		wkrs_len += v.length;
	}
	public boolean Find(byte exec_tid, Xof_fsdb_itm itm) {
		boolean rv = false;
		for (int i = 0; i < wkrs_len; i++) {
			Xof_qry_wkr wkr = wkrs[i];
			if (wkr.Qry_file(itm)) {
				itm.Rslt_qry_(wkr.Tid());
				rv = true;
				break;
			}
		}
		if (!rv) {
			itm.Rslt_qry_(Xof_qry_wkr_.Tid_missing);
			return false;	// not found in any wkr; exit;
		}
		if (Bry_.Len_gt_0(itm.Orig_redirect()))
			itm.Init_by_redirect(itm.Orig_redirect());
		itm.Html_size_calc(img_size, exec_tid);
		return true;
	}
}
