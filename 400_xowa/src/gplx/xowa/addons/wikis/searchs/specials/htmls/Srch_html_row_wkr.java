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
package gplx.xowa.addons.wikis.searchs.specials.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.specials.*;
import gplx.langs.htmls.*; import gplx.xowa.files.gui.*;
import gplx.xowa.addons.wikis.searchs.searchers.*; import gplx.xowa.addons.wikis.searchs.searchers.rslts.*;
public class Srch_html_row_wkr {
	private final    Srch_html_row_bldr html_row_bldr; private final    Xog_js_wkr js_wkr;
	private final    Srch_rslt_row[] rows; private final    int rows_len;
	private final    Bry_bfr bfr = Bry_bfr_.New_w_size(255);
	private final    byte[] insert_new_key;
	public Srch_html_row_wkr(Srch_html_row_bldr html_row_bldr, Xog_js_wkr js_wkr, int slab_len, byte[] wiki) {			
		this.html_row_bldr = html_row_bldr; this.js_wkr = js_wkr;
		this.rows = new Srch_rslt_row[slab_len];
		this.rows_len = slab_len;
		this.insert_new_key = Gen_insert_key(wiki);
	}
	public void Set(int i, Srch_rslt_row row) {rows[i] = row;}
	public void On_rslt_found(Srch_rslt_row new_row) {
		Srch_rslt_row last_row = rows[rows_len - 1];
		if (last_row != null) {
			if (Compare(new_row, last_row) == CompareAble_.MoreOrSame) return;	// new_row is < last_row; exit
		}
		int new_row_slot = Find_insert_slot(new_row); if (new_row_slot == -1) return;
		Srch_rslt_row insert_row = rows[new_row_slot];
		byte[] insert_key = insert_row == null ? insert_new_key : insert_row.Key;
		Displace(new_row_slot, new_row);
		html_row_bldr.Bld_html(bfr, new_row);
		String html_tbl = bfr.To_str_and_clear();
		js_wkr.Html_elem_append_above(Gfh_utl.Encode_id_as_str(insert_key), html_tbl);
		if (last_row != null) {
			js_wkr.Html_elem_replace_html(Gfh_utl.Encode_id_as_str(last_row.Key), "");
		}
	}
	private int Find_insert_slot(Srch_rslt_row new_row) {
		for (int i = 0; i < rows_len; ++i) {
			Srch_rslt_row cur_row = rows[i];
			if (cur_row == null) return i;
			if (Compare(new_row, cur_row) == CompareAble_.Less) return i;
		}
		return -1;
	}
	private void Displace(int new_row_slot, Srch_rslt_row new_row) {
		for (int i = rows_len - 2; i >= new_row_slot; --i) {
			rows[i + 1] = rows[i];
		}
		rows[new_row_slot] = new_row;
	}
	private int Compare(Srch_rslt_row lhs, Srch_rslt_row rhs) {
		return -Int_.Compare(lhs.Page_score, rhs.Page_score);
	}
	public static byte[] Gen_insert_key(byte[] wiki) {return Bry_.Add(Bry_insert_key, wiki);}
	private static final    byte[] Bry_insert_key = Bry_.new_a7("xowa_insert_");
}
