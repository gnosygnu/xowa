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
package gplx.xowa.apps.site_cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.xowa.apps.fsys.*;
class Xoa_site_cfg_loader__fsys implements Xoa_site_cfg_loader {
	private final Io_url site_cfg_dir;
	public Xoa_site_cfg_loader__fsys(Io_url site_cfg_dir) {this.site_cfg_dir = site_cfg_dir;}
	public int Tid() {return Xoa_site_cfg_loader_.Tid__fsys;}
	public void Load_csv__bgn(Xoa_site_cfg_mgr mgr, Xow_wiki wiki) {}
	public byte[] Load_csv(Xoa_site_cfg_mgr mgr, Xow_wiki wiki, Xoa_site_cfg_itm__base itm) {
		return Io_mgr.Instance.LoadFilBryOrNull(Make_load_url(wiki.Domain_str(), itm.Key_str()));
	}
	public Io_url Make_load_url(String wiki, String key) {return site_cfg_dir.GenSubFil_nest(key, key + "-" + wiki + ".csv");}
	public static Xoa_site_cfg_loader__fsys new_(Xoa_app app) {return new Xoa_site_cfg_loader__fsys(app.Fsys_mgr().Bin_xowa_dir().GenSubDir_nest("cfg", "wiki", "api"));}
}
