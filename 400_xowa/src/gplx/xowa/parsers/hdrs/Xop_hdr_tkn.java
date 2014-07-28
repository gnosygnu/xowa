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
package gplx.xowa.parsers.hdrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xop_hdr_tkn extends Xop_tkn_itm_base {
	public Xop_hdr_tkn(int bgn, int end, int hdr_len) {this.Tkn_ini_pos(false, bgn, end); this.hdr_len = hdr_len;}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_hdr;}
	public int Hdr_len() {return hdr_len;} public Xop_hdr_tkn Hdr_len_(int v) {hdr_len = v; return this;} private int hdr_len = -1;
	public int Hdr_bgn_manual() {return hdr_bgn_manual;} public Xop_hdr_tkn Hdr_bgn_manual_(int v) {hdr_bgn_manual = v; return this;} private int hdr_bgn_manual;
	public int Hdr_end_manual() {return hdr_end_manual;} public Xop_hdr_tkn Hdr_end_manual_(int v) {hdr_end_manual = v; return this;} private int hdr_end_manual;
	public boolean Hdr_html_first() {return hdr_html_first;} public Xop_hdr_tkn Hdr_html_first_y_() {hdr_html_first = true; return this;} private boolean hdr_html_first;
	public int Hdr_html_dupe_idx() {return hdr_html_dupe_idx;} private int hdr_html_dupe_idx;
	public byte[] Hdr_toc_text() {return hdr_toc_text;} public Xop_hdr_tkn Hdr_toc_text_(byte[] v) {hdr_toc_text = v; return this;} private byte[] hdr_toc_text;
	public int Hdr_html_dupe_idx_next() {
		hdr_html_dupe_idx = hdr_html_dupe_idx == 0 ? 2 : hdr_html_dupe_idx + 1;
		return hdr_html_dupe_idx;
	} 
	public byte[] Hdr_html_id() {return hdr_html_id;} public Xop_hdr_tkn Hdr_html_id_(byte[] v) {hdr_html_id = v; return this;} private byte[] hdr_html_id = Bry_.Empty;
}
