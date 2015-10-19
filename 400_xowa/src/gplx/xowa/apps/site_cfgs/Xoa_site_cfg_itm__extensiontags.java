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
import gplx.langs.jsons.*;
class Xoa_site_cfg_itm__extensiontags extends Xoa_site_cfg_itm__base {
	public Xoa_site_cfg_itm__extensiontags() {
		this.Ctor(Xoa_site_cfg_loader__inet.Qarg__extensiontags);
	}
	@Override public void Parse_json_ary_itm(Bry_bfr bfr, Xow_wiki wiki, int i, Json_itm itm) {
		byte[] tag = itm.Data_bry();
		if (i != 0) bfr.Add_byte_nl();
		int idx_last = tag.length - 1;
		if (	tag.length < 3
			||	tag[0]			!= Byte_ascii.Angle_bgn
			||	tag[idx_last]	!= Byte_ascii.Angle_end
			)
			throw Err_.new_("site_meta", "invalid extensiontag", "tag", tag);
		bfr.Add_mid(tag, 1, idx_last);	// convert "<pre>" to "pre"
	}
	@Override public void Exec_csv(Xow_wiki wiki, int loader_tid, byte[] dsv_bry) {
		Hash_adp_bry hash = null;
		if (loader_tid != Xoa_site_cfg_loader_.Tid__fallback) {	// fallback will result in null hash which will result in loading all extensions
			hash = Hash_adp_bry.ci_a7();						// NOTE: must be case-insensitive; EX: <imageMap> vs <imagemap>
			byte[][] lines = Bry_split_.Split_lines(dsv_bry);
			int lines_len = lines.length;
			for (int i = 0; i < lines_len; ++i) {
				byte[] line = lines[i]; if (Bry_.Len_eq_0(line)) continue;	// ignore blank lines
				hash.Add(line, line);
			}
		}
		wiki.Mw_parser_mgr().Xnde_tag_regy().Init_by_meta(hash);
	}
}
