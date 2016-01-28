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
package gplx.xowa.xtns.pagebanners; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Pgbnr_cfg {
	private static final int[] wpb_standard_sizes = new int[] {640, 1280, 2560};
	public byte[] Default_file() {return null;}
	public boolean Enabled_in_ns(int ns_id) {return false;}
	public boolean Get__wpb_enable_default_banner() {return false;}
	public boolean Get__wpb_enable_heading_override() {return false;}
	public byte[] Get__wpb_image() {return null;}
	public int[] Get__wpb_standard_sizes() {return wpb_standard_sizes;}
	public boolean Chk_pgbnr_allowed(Xoa_ttl ttl, Xowe_wiki wiki) {
		return	this.Enabled_in_ns(ttl.Ns().Id())					// chk if ns allows banner
			&&	!Bry_.Eq(ttl.Page_db(), wiki.Props().Main_page()); 	// never show on main page
	}
}
