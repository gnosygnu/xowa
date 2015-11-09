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
package gplx.xowa.htmls.core.wkrs.spaces; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*; import gplx.xowa.wikis.ttls.*;
import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.hzips.stats.*;
public class Xoh_space_hzip implements Xoh_hzip_wkr {
	public String Key() {return Xoh_hzip_dict_.Key__space;}
	public void Encode(Bry_bfr bfr, Hzip_stat_itm stat_itm, byte[] src, int src_end, int rng_bgn, int rng_end) {// "    " -> 27,9,4
		int space_len = Bry_find_.Find_fwd_while(src, rng_end, src_end, Byte_ascii.Space) - rng_bgn;

		stat_itm.Space_add(space_len);
		bfr.Add(Xoh_hzip_dict_.Bry__space);
		Xoh_hzip_int_.Encode(1, bfr, space_len);
	}
	public int Decode(Bry_bfr bfr, Xoh_decode_ctx ctx, Bry_rdr rdr, byte[] src, int hook_bgn) {
		int space_len = rdr.Read_int_by_base85(1);

		bfr.Add_byte_repeat(Byte_ascii.Space, space_len);
		return rdr.Pos();
	}
}
