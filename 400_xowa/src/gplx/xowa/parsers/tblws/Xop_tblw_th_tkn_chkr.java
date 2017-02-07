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
package gplx.xowa.parsers.tblws; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.tests.*;
public class Xop_tblw_th_tkn_chkr extends Xop_tkn_chkr_base {
	@Override public Class<?> TypeOf() {return Xop_tblw_th_tkn.class;}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_tblw_th;}
	public Xop_tblw_th_tkn_chkr Atrs_rng_(int bgn, int end) {this.atrs_bgn = bgn; this.atrs_end = end; return this;} private int atrs_bgn = Xop_tblw_wkr.Atrs_null, atrs_end = Xop_tblw_wkr.Atrs_null;
	@Override public int Chk_hook(Tst_mgr mgr, String path, Object actl_obj, int err) {
		Xop_tblw_th_tkn actl = (Xop_tblw_th_tkn)actl_obj;
		err += mgr.Tst_val(atrs_bgn == -1, path, "atrs_bgn", atrs_bgn, actl.Atrs_bgn());
		err += mgr.Tst_val(atrs_end == -1, path, "atrs_end", atrs_end, actl.Atrs_end());
		return err;
	}
}
