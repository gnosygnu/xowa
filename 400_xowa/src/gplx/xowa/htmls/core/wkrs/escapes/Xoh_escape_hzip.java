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
package gplx.xowa.htmls.core.wkrs.escapes; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*;
import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.hzips.stats.*;
public class Xoh_escape_hzip implements Xoh_hzip_wkr {
	public String Key() {return Xoh_hzip_dict_.Key__escape;}
	public void Encode(Bry_bfr bfr, Hzip_stat_itm stat_itm) {
		stat_itm.Escape_add_one();
		bfr.Add(Xoh_hzip_dict_.Bry__escape);
	}
	public int Decode(Bry_bfr bfr, Xoh_decode_ctx ctx, Bry_rdr rdr, byte[] src, int hook_bgn) {
		bfr.Add_byte(Xoh_hzip_dict_.Escape);
		return rdr.Pos();
	}
}
