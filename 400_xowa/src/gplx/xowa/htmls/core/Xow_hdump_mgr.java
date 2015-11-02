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
package gplx.xowa.htmls.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.xowa.htmls.core.hzips.*;
import gplx.xowa.wikis.data.*;
public class Xow_hdump_mgr {
	private final Xoh_page tmp_hpg = new Xoh_page(); private final Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	public Xow_hdump_mgr(Xow_wiki wiki) {
		this.hzip_mgr = new Xow_hzip_mgr(wiki);
		this.save_mgr = new Xow_hdump_mgr__save(wiki, hzip_mgr, tmp_hpg, tmp_bfr);
		this.load_mgr = new Xow_hdump_mgr__load(wiki, hzip_mgr, tmp_hpg, tmp_bfr);
	}
	public void Init_by_db(Xow_wiki wiki) {
		byte default_zip_tid = gplx.core.ios.Io_stream_.Tid_raw;
		boolean default_hzip_enable = false;
		if (wiki.Data__core_mgr() != null) {	// TEST: handle null data mgr
			Xowd_core_db_props props = wiki.Data__core_mgr().Props();
			default_zip_tid = props.Zip_tid_html();
			default_hzip_enable = props.Hzip_enabled();
		}
		Init_by_db(wiki, default_zip_tid, default_hzip_enable);
	}
	public void Init_by_db(Xow_wiki wiki, byte default_zip_tid, boolean default_hzip_enable) {
		hzip_mgr.Init_by_atrs(default_zip_tid, default_hzip_enable);
	}
	public Xow_hzip_mgr Hzip_mgr() {return hzip_mgr;} private Xow_hzip_mgr hzip_mgr;
	public Xow_hdump_mgr__save Save_mgr() {return save_mgr;} private Xow_hdump_mgr__save save_mgr;
	public Xow_hdump_mgr__load Load_mgr() {return load_mgr;} private Xow_hdump_mgr__load load_mgr;
}
