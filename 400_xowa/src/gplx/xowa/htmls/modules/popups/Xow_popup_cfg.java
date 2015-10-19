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
package gplx.xowa.htmls.modules.popups; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.modules.*;
import gplx.xowa.apps.apis.xowa.html.modules.*;
public class Xow_popup_cfg {
	public int Show_all_if_less_than() {return show_all_if_less_than;} public void Show_all_if_less_than_(int v) {show_all_if_less_than = v;} private int show_all_if_less_than = Xoapi_popups.Dflt_show_all_if_less_than;
	public int Tmpl_read_max() {return tmpl_read_max;} public void Tmpl_read_max_(int v) {tmpl_read_max = v;} private int tmpl_read_max = Xoapi_popups.Dflt_scan_max;
	public int Tmpl_read_len() {return tmpl_read_len;} public void Tmpl_read_len_(int v) {tmpl_read_len = v;} private int tmpl_read_len = Xoapi_popups.Dflt_scan_len;
	public int Read_til_stop_fwd() {return read_til_stop_fwd;} public void Read_til_stop_fwd_(int v) {read_til_stop_fwd = v;} private int read_til_stop_fwd = Xoapi_popups.Dflt_read_til_stop_fwd;
	public int Read_til_stop_bwd() {return read_til_stop_bwd;} public void Read_til_stop_bwd_(int v) {read_til_stop_bwd = v;} private int read_til_stop_bwd = Xoapi_popups.Dflt_read_til_stop_bwd;
	public int Stop_if_hdr_after() {return stop_if_hdr_after;} public void Stop_if_hdr_after_(int v) {stop_if_hdr_after = v;} private int stop_if_hdr_after = Xoapi_popups.Dflt_stop_if_hdr_after;
	public boolean Stop_if_hdr_after_enabled() {return stop_if_hdr_after > 0;}
	public byte[] Ellipsis() {return ellipsis;} public void Ellipsis_(byte[] v) {ellipsis = v;} private byte[] ellipsis = Bry_.Empty;
	public byte[] Notoc() {return notoc;} public void Notoc_(byte[] v) {notoc = v;} private byte[] notoc = Notoc_const;
	public static final byte[]
	  Notoc_const		= Bry_.new_a7("\n__NOTOC__") // NOTE: always add a whitespace tkn else __NOTOC__ will be deactivated if last tkn is lnke; DATE:2014-06-22
	, Msg_key_ellipsis	= Bry_.new_a7("ellipsis")
	;
}
