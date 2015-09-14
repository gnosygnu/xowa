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
package gplx.xowa.parsers.miscs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xop_tkn_chkr_hr extends Xop_tkn_chkr_base {
	@Override public Class<?> TypeOf() {return Xop_hr_tkn.class;}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_hr;}
	public Xop_tkn_chkr_hr(int bgn, int end) {super.Src_rng_(bgn, end);}
	public int Hr_len() {return hr_len;} public Xop_tkn_chkr_hr Hr_len_(int v) {hr_len = v; return this;} private int hr_len = -1;
	@Override public int Chk_hook(Tst_mgr mgr, String path, Object actl_obj, int err) {
		Xop_hr_tkn actl = (Xop_hr_tkn)actl_obj;
		err += mgr.Tst_val(hr_len == -1, path, "hr_len", hr_len, actl.Hr_len());
		return err;
	}
}
