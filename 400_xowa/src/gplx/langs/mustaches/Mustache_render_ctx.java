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
public class Mustache_render_ctx {
	private final List_adp stack = List_adp_.new_();
	private Mustache_doc_itm cur;
	private Mustache_doc_itm[] subs; private int subs_idx, subs_len; private boolean cur_is_bool;
	public Mustache_render_ctx Init(Mustache_doc_itm cur) {
		this.cur = cur;
		this.subs = null;
		this.subs_idx = subs_len = 0; this.cur_is_bool = false;
		return this;
	}
	public byte[] Render_variable(String key) {
		byte[] rv = Mustache_doc_itm_.Null_val;
		Mustache_doc_itm itm = cur;
		while (itm != Mustache_doc_itm_.Null_itm) {
			rv = cur.Get_prop(key);
			if (rv != Mustache_doc_itm_.Null_val) break;
			else break;
			// TODO: itm = itm.Get_owner();
		}
		return rv;
	}
	public void Section_bgn(String key) {
		subs = cur.Get_subs(key); if (subs == null) subs = Mustache_doc_itm_.Ary__empty;
		subs_len = subs.length;
		subs_idx = -1;
		cur_is_bool = false;
	}
	public boolean Section_do() {
		if (++subs_idx >= subs_len) return false;
		Mustache_doc_itm sub = subs[subs_idx];
		if (subs_idx == 0) {	// special logic to handle 1st item; note that there always be at least one item
			if (subs_len == 1) {
				if		(sub == Mustache_doc_itm_.Itm__bool__y) {cur_is_bool = true; return Bool_.Y;}
				else if (sub == Mustache_doc_itm_.Itm__bool__n) {cur_is_bool = true; return Bool_.N;}
			}
			Mustache_stack_itm stack_itm = new Mustache_stack_itm(cur, subs, subs_idx, subs_len); // note that cur is "owner" since subs_idx == 0
			stack.Add(stack_itm);
		}			
		cur = sub;
		return true;
	}
	public void Section_end() {
		if (cur_is_bool) return;
		if (stack.Count() == 0) return;
		Mustache_stack_itm itm = (Mustache_stack_itm)List_adp_.Pop(stack);
		subs = itm.subs;
		subs_len = itm.subs_len;
		subs_idx = itm.subs_idx;
		cur = itm.cur;
		// cur = subs_idx < subs_len ? subs[subs_idx] : null;
	}
}
class Mustache_stack_itm {
	public Mustache_stack_itm(Mustache_doc_itm cur, Mustache_doc_itm[] subs, int subs_idx, int subs_len) {
		this.cur = cur;
		this.subs = subs;
		this.subs_idx = subs_idx;
		this.subs_len = subs_len;
	}
	public Mustache_doc_itm cur;
	public Mustache_doc_itm[] subs;
	public int subs_idx;
	public int subs_len;
}
