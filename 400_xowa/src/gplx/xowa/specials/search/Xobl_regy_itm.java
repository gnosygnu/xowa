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
package gplx.xowa.specials.search; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
class Xobl_regy_itm {
	public Xobl_regy_itm(int id, byte[] bgn, byte[] end, int count) {this.id = id; this.bgn = bgn; this.end = end; this.count = count;}
	public int Id() {return id;} private int id;
	public byte[] Bgn() {return bgn;} private byte[] bgn;
	public byte[] End() {return end;} private byte[] end;
	public int Count() {return count;} private int count;
	public void Srl_save(Bry_bfr bfr) {
		bfr	.Add_int_variable(id)	.Add_byte_pipe()
			.Add(bgn)				.Add_byte_pipe()
			.Add(end)				.Add_byte_pipe()
			.Add_int_variable(count).Add_byte_nl()
		;
	}
}
class Xobl_search_ttl implements Xobl_data_itm {
	public Xobl_search_ttl() {}
	public Xobl_search_ttl(byte[] word, Xobl_search_ttl_page[] pages) {this.word = word; this.pages = pages;}
	public byte[] Word() {return word;} private byte[] word;
	public Xobl_search_ttl_page[] Pages() {return pages;} private Xobl_search_ttl_page[] pages;
	public void Srl_save(Bry_bfr bfr) {
		bfr.Add(word);
		int pages_len = pages.length;
		for (int i = 0; i < pages_len; i++) {
			bfr.Add_byte_pipe();
			pages[i].Srl_save(bfr);
		}
		bfr.Add_byte_nl();
	}
}
class Xobl_search_ttl_page {
	public Xobl_search_ttl_page(int id, int len) {this.id = id; this.len = len;}
	public int Id() {return id;} private int id;
	public int Len() {return len;} private int len;
	public void Srl_save(Bry_bfr bfr) {
		bfr	.Add_base85_len_5(id).Add_byte(Byte_ascii.Semic)
			.Add_base85_len_5(len)
		;
	}
}
