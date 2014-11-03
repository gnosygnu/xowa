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
package gplx.xowa.html.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
public class Xow_hzip_stats {
	public void Clear() {
		a_rhs = lnki_text_n = lnki_text_y = lnke_txt = lnke_brk_text_n = lnke_brk_text_y = hdr = 0;
	}
	public int A_rhs() {return a_rhs;} public void A_rhs_add() {++a_rhs;} private int a_rhs;		
	public int Lnki_text_n() {return lnki_text_n;} public void Lnki_text_n_add() {++lnki_text_n;} private int lnki_text_n;
	public int Lnki_text_y() {return lnki_text_y;} public void Lnki_text_y_add() {++lnki_text_y;} private int lnki_text_y;
	public int Lnke_txt() {return lnke_txt;} public void Lnke_txt_add() {++lnke_txt;} private int lnke_txt;
	public int Lnke_brk_text_n() {return lnke_brk_text_n;} public void Lnke_brk_text_n_add() {++lnke_brk_text_n;} private int lnke_brk_text_n;
	public int Lnke_brk_text_y() {return lnke_brk_text_y;} public void Lnke_brk_text_y_add() {++lnke_brk_text_y;} private int lnke_brk_text_y;
	public int Hdr() {return hdr;} public void Hdr_add() {++hdr;} private int hdr;
}
