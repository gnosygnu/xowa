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
package gplx.xowa.parsers.amps; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.tests.*;
public class Xop_html_num_tkn_chkr extends Xop_tkn_chkr_base {
	@Override public Class<?> TypeOf() {return Xop_amp_tkn_num.class;}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_html_ncr;}
	public int Html_ncr_val() {return html_ncr_val;} public Xop_html_num_tkn_chkr Html_ncr_val_(int v) {html_ncr_val = v; return this;} private int html_ncr_val = -1;
	@Override public int Chk_hook(Tst_mgr mgr, String path, Object actl_obj, int err) {
		Xop_amp_tkn_num actl = (Xop_amp_tkn_num)actl_obj;
		err += mgr.Tst_val(html_ncr_val == -1, path, "html_ncr_val", html_ncr_val, actl.Val());
		return err;
	}
}
