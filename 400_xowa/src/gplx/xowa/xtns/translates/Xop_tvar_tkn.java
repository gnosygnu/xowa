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
package gplx.xowa.xtns.translates; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Xop_tvar_tkn extends Xop_tkn_itm_base {
	public Xop_tvar_tkn(int tkn_bgn, int tkn_end, int key_bgn, int key_end, int txt_bgn, int txt_end, byte[] wikitext) {
		this.Tkn_ini_pos(false, tkn_bgn, tkn_end);
		this.key_bgn = key_bgn; this.key_end = key_end;
		this.txt_bgn = txt_bgn; this.txt_end = txt_end;
		this.wikitext = wikitext;
	}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_tvar;}
	public int Key_bgn() {return key_bgn;} private int key_bgn;
	public int Key_end() {return key_end;} private int key_end;
	public int Txt_bgn() {return txt_bgn;} private int txt_bgn;
	public int Txt_end() {return txt_end;} private int txt_end;
	public byte[] Wikitext() {return wikitext;} private byte[] wikitext;
}
