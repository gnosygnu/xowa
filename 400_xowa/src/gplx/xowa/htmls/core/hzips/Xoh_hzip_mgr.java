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
package gplx.xowa.htmls.core.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.core.primitives.*; import gplx.core.brys.*; import gplx.core.btries.*; import gplx.xowa.wikis.ttls.*;
import gplx.xowa.htmls.core.hzips.stats.*;
import gplx.xowa.htmls.core.wkrs.hdrs.*; import gplx.xowa.htmls.core.wkrs.lnkes.*; import gplx.xowa.htmls.core.wkrs.lnkis.*; import gplx.xowa.htmls.core.wkrs.spaces.*; import gplx.xowa.htmls.core.wkrs.escapes.*;
public class Xoh_hzip_mgr {
	private final Btrie_slim_mgr trie = Xoh_hzip_dict_.Trie;
	private final Bry_parser parser = new Bry_parser();
	public void Encode(Bry_bfr bfr, Xow_ttl_parser ttl_parser, byte[] page_url, byte[] src, Hzip_stat_itm stat_itm) {
		bfr.Clear(); stat_itm.Clear();
		((Xoh_lnki_hzip)Xoh_hzip_dict_.To_wkr(Xoh_hzip_dict_.Tid__lnki)).Ttl_parser_(ttl_parser);
		int pos = 0, add_bgn = -1; int src_len = src.length;
		parser.Init_src(page_url, src, src_len, 0);
		while (pos < src_len) {
			Object o = trie.Match_bgn_w_byte(src[pos], src, pos, src_len);
			if (o == null) {
				if (add_bgn == -1) add_bgn = pos;
				++pos;
			}
			else {
				if (add_bgn != -1) {bfr.Add_mid(src, add_bgn, pos); add_bgn = -1;}
				try {
					Xoh_hzip_wkr wkr = Xoh_hzip_dict_.To_wkr(((Byte_obj_val)o).Val());
					parser.Init_hook(wkr.Key(), pos, trie.Match_pos());
					wkr.Encode(bfr, stat_itm, parser, src, pos);
					pos = parser.Pos();
				} catch (Exception e) {
					Gfo_usr_dlg_.Instance.Warn_many("", "", Err_.Message_gplx_log(e));
					pos = trie.Match_pos();
				}
			}
		}
		if (add_bgn != -1) bfr.Add_mid(src, add_bgn, src_len);
	}
	public byte[] Decode(Bry_bfr bfr, Xow_ttl_parser ttl_parser, byte[] page_url, byte[] src) {			
		bfr.Clear();
		int pos = 0, add_bgn = -1; int src_len = src.length;
		parser.Init_src(page_url, src, src_len, 0);
		while (pos < src_len) {
			if (src[pos] == Xoh_hzip_dict_.Escape) {
				if (add_bgn != -1) {bfr.Add_mid(src, add_bgn, pos); add_bgn = -1;}
				try {
					Xoh_hzip_wkr wkr = Xoh_hzip_dict_.To_wkr(src[pos + 1]);
					parser.Init_hook(wkr.Key(), pos, pos + 2);
					wkr.Decode(bfr, parser, src, pos);
					pos = parser.Pos();
				} catch (Exception e) {
					Gfo_usr_dlg_.Instance.Warn_many("", "", Err_.Message_gplx_log(e));
					pos += 2;
				}
			}
			else {
				if (add_bgn == -1) add_bgn = pos;
				++pos;
			}
		}
		if (add_bgn != -1) bfr.Add_mid(src, add_bgn, src_len);
		return bfr.To_bry_and_clear();
	}
}
