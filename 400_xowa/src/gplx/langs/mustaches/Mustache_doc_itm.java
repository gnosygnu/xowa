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
package gplx.langs.mustaches; import gplx.*; import gplx.langs.*;
import gplx.langs.jsons.*;
interface Mustache_doc_itm {
	byte[] Get_by_key(byte[] key);
	Mustache_doc_itm Get_owner();
	void Move_next();
	void Move_down(byte[] key);
	void Move_up();
}
class Mustache_doc_itm_ {
	public static final byte[] Null_val = null;
	public static final Mustache_doc_itm Null_itm = null;
}
class Mustache_doc_itm__json implements Mustache_doc_itm {
	// private Json_doc jdoc; 
	private final List_adp stack = List_adp_.new_();
	private Json_nde cur; private int cur_idx = -1;
	public void Init_by_jdoc(Json_doc jdoc) {
		// this.jdoc = jdoc;
		this.cur = jdoc.Root_nde();
	}
	public byte[] Get_by_key(byte[] key) {return cur.Get_bry_or_null(key);}
	public Mustache_doc_itm Get_owner() {return Mustache_doc_itm_.Null_itm;}
	public void Move_next() {
		++cur_idx;
		// cur = cur.Owner().Get_at();
	}
	public void Move_down(byte[] key) {
		stack.Add(cur);
		cur_idx = 0;
		cur = (Json_nde)cur.Get_itm(key);
	}
	public void Move_up() {
		if (cur_idx == 0) {}
		cur = (Json_nde)stack.Get_at_last();
	}
}
