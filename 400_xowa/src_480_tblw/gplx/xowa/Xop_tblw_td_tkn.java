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
package gplx.xowa; import gplx.*;
public class Xop_tblw_td_tkn extends Xop_tkn_itm_base implements Xop_tblw_tkn {
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_tblw_td;}
	public int Tblw_tid() {return Xop_xnde_tag_.Tid_td;}
	public int Atrs_bgn() {return atrs_bgn;} private int atrs_bgn = Xop_tblw_wkr.Atrs_null;
	public int Atrs_end() {return atrs_end;} private int atrs_end = -1;
	public void Atrs_rng_set(int bgn, int end) {this.atrs_bgn = bgn; this.atrs_end = end;}
	public Xop_xatr_itm[] Atrs_ary() {return atrs_ary;} public Xop_tblw_tkn Atrs_ary_as_tblw_(Xop_xatr_itm[] v) {atrs_ary = v; return this;} private Xop_xatr_itm[] atrs_ary;
	public boolean Tblw_xml() {return tblw_xml;} private boolean tblw_xml;
	public int Tblw_subs_len() {return tblw_subs_len;} public void Tblw_subs_len_add_() {++tblw_subs_len;} private int tblw_subs_len;
	public Xop_tblw_td_tkn Subs_add_ary(Xop_tkn_itm... ary) {for (Xop_tkn_itm itm : ary) super.Subs_add(itm); return this;}
	public Xop_tblw_td_tkn(int bgn, int end, boolean tblw_xml) {this.tblw_xml = tblw_xml; this.Tkn_ini_pos(false, bgn, end);}
}
