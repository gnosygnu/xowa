/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.htmls.core.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.xowa.htmls.core.wkrs.lnkes.*;
public class Xoh_stat_itm {
	public void Clear() {
		a_rhs = lnki_text_n = lnki_text_y = lnke__free = lnke__auto = lnke__text = 0;
		hdr_1 = hdr_2 = hdr_3 = hdr_4 = hdr_5 = hdr_6 = timeline = gallery = 0;
		img_full = 0;
		space = 0;
		Bry_.Clear(escape_bry);
	}
	public int A_rhs() {return a_rhs;} public void A_rhs_add() {++a_rhs;} private int a_rhs;		
	public int Lnki_text_n() {return lnki_text_n;} public void Lnki_text_n_add() {++lnki_text_n;} private int lnki_text_n;
	public int Lnki_text_y() {return lnki_text_y;} public void Lnki_text_y_add() {++lnki_text_y;} private int lnki_text_y;
	public int Lnke__free() {return lnke__free;} public void Lnke__free__add() {++lnke__free;} private int lnke__free;
	public int Lnke__auto() {return lnke__auto;} public void Lnke__auto__add() {++lnke__auto;} private int lnke__auto;
	public int Lnke__text() {return lnke__text;} public void Lnke__text__add() {++lnke__text;} private int lnke__text;
	public int Img_full() {return img_full;} public void Img_full_add() {++img_full;} private int img_full;
	public int Timeline() {return timeline;} public void Timeline_add() {++timeline;} private int timeline;
	public int Gallery() {return gallery;} public void Gallery_add() {++gallery;} private int gallery;
	public int Hdr_1() {return hdr_1;} private int hdr_1;
	public int Hdr_2() {return hdr_2;} private int hdr_2;
	public int Hdr_3() {return hdr_3;} private int hdr_3;
	public int Hdr_4() {return hdr_4;} private int hdr_4;
	public int Hdr_5() {return hdr_5;} private int hdr_5;
	public int Hdr_6() {return hdr_6;} private int hdr_6;
	public int Space() {return space;} public void Space_add(int v) {space += v;} private int space;
	public byte[] Escape_bry() {return escape_bry;} public void Escape_add(byte v) {escape_bry[v] += 1;} private final    byte[] escape_bry = new byte[256];
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
	public void Lnki_add(int orig_len, int hzip_len, int flag) {
	}
	public void Lnke_add(byte lnke_type) {
		switch (lnke_type) {
			case Xoh_lnke_dict_.Type__free:		++lnke__free; break;
			case Xoh_lnke_dict_.Type__auto:		++lnke__auto; break;
			case Xoh_lnke_dict_.Type__text:		++lnke__text; break;
		}
	}
}
