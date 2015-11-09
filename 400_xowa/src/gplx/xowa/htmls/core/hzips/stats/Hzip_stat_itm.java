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
package gplx.xowa.htmls.core.hzips.stats; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.hzips.*;
public class Hzip_stat_itm {
	public void Clear() {
		a_rhs = lnki_text_n = lnki_text_y = lnke_txt = lnke_brk_text_n = lnke_brk_text_y = 0;
		hdr_1 = hdr_2 = hdr_3 = hdr_4 = hdr_5 = hdr_6 = 0;
		img_full = 0;
		space = 0;
		escape = 0;
	}
	public int A_rhs() {return a_rhs;} public void A_rhs_add() {++a_rhs;} private int a_rhs;		
	public int Lnki_text_n() {return lnki_text_n;} public void Lnki_text_n_add() {++lnki_text_n;} private int lnki_text_n;
	public int Lnki_text_y() {return lnki_text_y;} public void Lnki_text_y_add() {++lnki_text_y;} private int lnki_text_y;
	public int Lnke_txt() {return lnke_txt;} public void Lnke_txt_add() {++lnke_txt;} private int lnke_txt;
	public int Lnke_brk_text_n() {return lnke_brk_text_n;} public void Lnke_brk_text_n_add() {++lnke_brk_text_n;} private int lnke_brk_text_n;
	public int Lnke_brk_text_y() {return lnke_brk_text_y;} public void Lnke_brk_text_y_add() {++lnke_brk_text_y;} private int lnke_brk_text_y;
	public int Img_full() {return img_full;} public void Img_full_add() {++img_full;} private int img_full;
	public int Hdr_1() {return hdr_1;} private int hdr_1;
	public int Hdr_2() {return hdr_2;} private int hdr_2;
	public int Hdr_3() {return hdr_3;} private int hdr_3;
	public int Hdr_4() {return hdr_4;} private int hdr_4;
	public int Hdr_5() {return hdr_5;} private int hdr_5;
	public int Hdr_6() {return hdr_6;} private int hdr_6;
	public int Space() {return space;} public void Space_add(int v) {space += v;} private int space;
	public int Escape() {return escape;} public void Escape_add_one() {++escape;} private int escape;
	public void Hdr_add(int hdr_num) {
		switch (hdr_num) {
			case 1:		++hdr_1; break;
			case 2:		++hdr_2; break;
			case 3:		++hdr_3; break;
			case 4:		++hdr_4; break;
			case 5:		++hdr_5; break;
			case 6:		++hdr_6; break;
			default:	throw Err_.new_unhandled(hdr_num);
		}
	}
}
