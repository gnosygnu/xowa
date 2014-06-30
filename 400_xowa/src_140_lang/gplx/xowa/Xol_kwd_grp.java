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
public class Xol_kwd_grp {// REF.MW: Messages_en.php; EX: 'redirect'               => array( 0,    '#REDIRECT'              )
	public Xol_kwd_grp(byte[] key) {this.key = key;}
	public byte[] Key() {return key;} private byte[] key;
	public boolean Case_match() {return case_match;} private boolean case_match;
	public Xol_kwd_itm[] Itms() {return itms;} private Xol_kwd_itm[] itms;
	public void Srl_load(boolean case_match, byte[][] words) {
		this.case_match = case_match;
		int words_len = words.length;
		itms = new Xol_kwd_itm[words_len];
		for (int i = 0; i < words_len; i++) {
			byte[] word = words[i];
			Xol_kwd_itm itm = new Xol_kwd_itm(word);
			itms[i] = itm;
		}
	}
}
