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
public class Xop_hdr_tkn_chkr extends Xop_tkn_chkr_base {
	@Override public Class<?> TypeOf() {return Xop_hdr_tkn.class;}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_hdr;}
	public int Hdr_len() {return hdr_len;} public Xop_hdr_tkn_chkr Hdr_len_(int v) {hdr_len = v; return this;} private int hdr_len = -1;
	public int Hdr_ws_bgn() {return hdr_ws_bgn;} public Xop_hdr_tkn_chkr Hdr_ws_bgn_(int v) {hdr_ws_bgn = v; return this;} private int hdr_ws_bgn = -1;
	public int Hdr_ws_end() {return hdr_ws_end;} public Xop_hdr_tkn_chkr Hdr_ws_end_(int v) {hdr_ws_end = v; return this;} private int hdr_ws_end = -1;
	public int Hdr_ws_trailing() {return hdr_ws_trailing;} public Xop_hdr_tkn_chkr Hdr_ws_trailing_(int v) {hdr_ws_trailing = v; return this;} private int hdr_ws_trailing = -1;	
	public Xop_hdr_tkn_chkr Hdr_html_id_(String v) {hdr_html_id = Bry_.new_ascii_(v); return this;} private byte[] hdr_html_id = Bry_.Empty;
	@Override public int Chk_hook(Tst_mgr mgr, String path, Object actl_obj, int err) {
		Xop_hdr_tkn actl = (Xop_hdr_tkn)actl_obj;
		err += mgr.Tst_val(hdr_len == -1, path, "hdr_len", hdr_len, actl.Hdr_len());
		err += mgr.Tst_val(hdr_html_id == Bry_.Empty, path, "hdr_html_id", String_.new_ascii_(hdr_html_id), String_.new_ascii_(actl.Hdr_html_id()));
		return err;
	}
}
