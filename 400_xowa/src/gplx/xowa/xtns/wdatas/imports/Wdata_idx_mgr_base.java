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
package gplx.xowa.xtns.wdatas.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.core.json.*; import gplx.ios.*;
import gplx.xowa.xtns.wdatas.imports.*;
abstract class Wdata_idx_mgr_base {
	public void Ctor(Xob_itm_dump_base wkr, Xob_bldr bldr, Xowe_wiki wiki, int dump_fil_len) {
		this.wkr = wkr; this.wiki = wiki; this.bldr = bldr; this.dump_fil_len = dump_fil_len;
	} 	Xob_itm_dump_base wkr; protected Xowe_wiki wiki; Xob_bldr bldr; Xol_csv_parser csv_parser = Xol_csv_parser._; protected Ordered_hash hash = Ordered_hash_.new_(); protected int dump_fil_len;
	public void Flush() {
		int len = hash.Count();
		for (int i = 0; i < len; i++) {
			Wdata_idx_wtr wtr = (Wdata_idx_wtr)hash.Get_at(i);
			wtr.Flush();
		}
	}
	public void Make() {
		int len = hash.Count();
		for (int i = 0; i < len; i++) {
			Wdata_idx_wtr wtr = (Wdata_idx_wtr)hash.Get_at(i);
			wtr.Make(bldr.Usr_dlg(), wkr.Make_fil_len());
		}		
		if (wkr.Delete_temp()) Io_mgr.I.DeleteDirDeep(wkr.Temp_dir());		
	}
}
