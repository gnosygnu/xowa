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
package gplx.langs.htmls.styles; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
public class Gfh_style_wkr__ary implements Gfh_style_wkr {
	private final List_adp list = List_adp_.new_();		
	public boolean On_atr(byte[] src, int atr_idx, int atr_bgn, int atr_end, int key_bgn, int key_end, int val_bgn, int val_end) {
		byte[] key = Bry_.Mid(src, key_bgn, key_end);
		byte[] val = Bry_.Mid(src, val_bgn, val_end);
		list.Add(new Gfh_style_itm(list.Count(), key, val));
		return true;
	}
	public Gfh_style_itm[] Parse(byte[] src, int src_bgn, int src_end) {
		Gfh_style_parser_.Parse(src, src_bgn, src_end, this);
		return (Gfh_style_itm[])list.To_ary_and_clear(Gfh_style_itm.class);
	}
        public static final Gfh_style_wkr__ary Instance = new Gfh_style_wkr__ary(); Gfh_style_wkr__ary() {}
}
