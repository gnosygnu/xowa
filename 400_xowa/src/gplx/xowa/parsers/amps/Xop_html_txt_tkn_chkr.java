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
public class Xop_html_txt_tkn_chkr extends Xop_tkn_chkr_base {
	@Override public Class<?> TypeOf() {return Xop_amp_tkn_txt.class;}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_html_ref;}
	public String Html_ref_key() {return html_ref_key;} public Xop_html_txt_tkn_chkr Html_ref_key_(String v) {html_ref_key = v; return this;} private String html_ref_key;
	@Override public int Chk_hook(Tst_mgr mgr, String path, Object actl_obj, int err) {
		Xop_amp_tkn_txt actl = (Xop_amp_tkn_txt)actl_obj;
		err += mgr.Tst_val(html_ref_key == null, path, "html_ref_key", html_ref_key, String_.new_u8(actl.Xml_name_bry()));
		return err;
	}
}
