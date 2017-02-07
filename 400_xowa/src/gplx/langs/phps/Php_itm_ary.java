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
package gplx.langs.phps; import gplx.*; import gplx.langs.*;
public class Php_itm_ary implements Php_itm, Php_itm_sub {
	public Php_itm_ary() {}
	public byte Itm_tid() {return Php_itm_.Tid_ary;}
	public byte[] Val_obj_bry() {return null;}
	public int Subs_len() {return subs_len;} private int subs_len;
	public Php_itm_sub Subs_get(int i) {return ary[i];}
	public Php_itm_sub Subs_pop() {return ary[--subs_len];}
	public void Subs_add(Php_itm_sub v) {
		int new_len = subs_len + 1;
		if (new_len > subs_max) {	// ary too small >>> expand
			subs_max = new_len * 2;
			Php_itm_sub[] new_ary = new Php_itm_sub[subs_max];
			Array_.Copy_to(ary, 0, new_ary, 0, subs_len);
			ary = new_ary;
		}
		ary[subs_len] = v;
		subs_len = new_len;
	}	Php_itm_sub[] ary = Php_itm_sub_.Ary_empty; int subs_max;
}
