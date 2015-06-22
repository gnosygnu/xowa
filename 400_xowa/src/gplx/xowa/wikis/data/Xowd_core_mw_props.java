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
package gplx.xowa.wikis.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*;
public class Xowd_core_mw_props {
	public void Init_by_load(Db_cfg_tbl cfg_tbl) {
		this.main_page = cfg_tbl.Select_bry_or(Xow_cfg_consts.Grp__wiki_init, Xow_cfg_consts.Key__init__main_page, null);
		if (main_page == null) {
			Xoa_app_.Usr_dlg().Warn_many("", "", "mw_props.load; main_page not found; conn=~{0}", cfg_tbl.Conn().Conn_info().Xto_api());
			this.main_page = Main_page_default;
		}
	}
	public byte[] Main_page() {return main_page;} private byte[] main_page = Main_page_default;
	public void Main_page_(byte[] v) {this.main_page = v;}
	private static final byte[] Main_page_default = Bry_.new_a7("Main_Page");
}
