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
package gplx.xowa.addons.bldrs.app_cfgs.wm_server_cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.app_cfgs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;
public class Xowm_server_cfg_mgr {
	public void Exec() {
		byte[] src = Load_or_download();
		Parse_cat_collation(src);
	}
	private byte[] Load_or_download() {
		// 				Io_mgr.Instance.DownloadFil_args("", Io_url_.NullPtr).Exec_as_bry("https://noc.wikimedia.org/conf/InitialiseSettings.php.txt");
		return null;
	}
	private void Parse_cat_collation(byte[] src) {
		int bgn_pos = Bry_find_.Find_fwd(src, Bry_.new_a7("wgCategoryCollation"));
		if (bgn_pos == Bry_find_.Not_found) throw Err_.new_wo_type("could not find wgCategoryCollation bgn");
		int end_pos = Bry_find_.Find_fwd(src, Bry_.new_a7("],"));
		if (end_pos == Bry_find_.Not_found) throw Err_.new_wo_type("could not find wgCategoryCollation end");
            Tfds.Write(src, bgn_pos, end_pos);
	}
}
