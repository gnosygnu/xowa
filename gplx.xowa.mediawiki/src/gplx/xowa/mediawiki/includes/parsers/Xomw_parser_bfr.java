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
package gplx.xowa.mediawiki.includes.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
public class Xomw_parser_bfr {	// manages 2 bfrs to eliminate multiple calls to new memory allocations ("return bfr.To_bry_and_clear()")
	private final    Bry_bfr bfr_1 = Bry_bfr_.New(), bfr_2 = Bry_bfr_.New();
	private Bry_bfr src, trg;
	public Xomw_parser_bfr() {
		this.src = bfr_1;
		this.trg = bfr_2;
	}		
	public Bry_bfr Src() {return src;}
	public Bry_bfr Trg() {return trg;}
	public Bry_bfr Rslt() {return src;}
	public Xomw_parser_bfr Init(byte[] text) {
		// resize each bfr once by guessing that html_len = text_len * 2
		int text_len = text.length;
		int html_len = text_len * 2;
		src.Resize(html_len);
		trg.Resize(html_len);

		// clear and add
		src.Clear();
		trg.Clear();
		src.Add(text);
		return this;
	}
	public void Switch() {
		Bry_bfr tmp = src;
		this.src = trg;
		this.trg = tmp;
		trg.Clear();
	}
}
