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
package gplx.langs.mustaches; import gplx.*; import gplx.langs.*;
import gplx.langs.jsons.*;
public class Mustache_render_ctx {
	private final	List_adp stack = List_adp_.New();
	private Mustache_doc_itm cur;
		private Json_itm nde; private Json_ary subary;
	private Mustache_doc_itm[] subs; private int subs_idx, subs_len; private byte cur_is_bool;
	public Mustache_render_ctx Init(Mustache_doc_itm cur) {
		this.cur = cur;
		this.subs = null;
		this.subs_idx = subs_len = 0; this.cur_is_bool = Bool_.__byte;
		return this;
	}
	public Mustache_render_ctx Init(Json_nde nde) {
			this.cur = null;
		this.nde = nde;
		this.subs = null;
		this.subs_idx = subs_len = 0; this.cur_is_bool = Bool_.__byte;
		return this;
	}
        // partial implementation of {{.}}
	public boolean Render_variable(Mustache_bfr bfr, String key) {
		boolean rv = false;
		int stack_pos = stack.Len();
		if (cur == null) {
			Json_itm itm = nde;
			while (itm != null) {
				if (itm instanceof Json_nde) {
					Json_nde n = (Json_nde)itm;
					Json_kv kv = (Json_kv)n.Get_itm(Bry_.new_u8(key));
					if (kv == null)
						return rv;
					Json_itm jitm = kv.Val();
					if (jitm instanceof Json_itm_str) {
						Json_itm_str s = (Json_itm_str)jitm;
						bfr.Add_bry(s.Data_bry());
						return true;
					}
					else if (jitm instanceof Json_itm_int) {
						Json_itm_int s = (Json_itm_int)jitm;
						bfr.Bfr().Add_int_variable(s.Data_as_int());
						return true;
					}
					else {
						int a = 1/0;
					}
				}
                                else if (key.equals(".") && itm instanceof Json_itm_str) {
                                        Json_itm_str s = (Json_itm_str)itm;
                                        bfr.Add_bry(s.Data_bry());
                                        return true;
                                }
				--stack_pos;
				if (stack_pos == -1)	// nothing else in stack
					break;
				else
					itm = ((Mustache_stack_itm)stack.Get_at(stack_pos)).nde;
			}
		}
		else {
			Mustache_doc_itm itm = cur;
			while (itm != Mustache_doc_itm_.Null_itm) {
				boolean resolved = itm.Mustache__write(key, bfr);
				if (resolved) {
					rv = true;
					break;
				}
				else {
					--stack_pos;
					if (stack_pos == -1)	// nothing else in stack
						break;
					else
						itm = ((Mustache_stack_itm)stack.Get_at(stack_pos)).cur;
				}
			}
		}
		return rv;
	}
	public void Section_bgn(String key) {
		if (cur == null) {
			Mustache_stack_itm stack_itm = new Mustache_stack_itm(nde, subs, subs_idx, subs_len, cur_is_bool); // note that cur is "owner" since subs_idx == 0
			stack.Add(stack_itm);
			Json_nde n = null;
			Json_itm itm = nde;
			int stack_pos = stack.Len();
			while (stack_pos >= 0) {
				if (itm instanceof Json_nde) {
					n = (Json_nde)itm;
                                        break;
                                }
				else {
					stack_pos--;
					itm = ((Mustache_stack_itm)stack.Get_at(stack_pos)).nde;
				}
			}
			Json_kv kv = null;
			if (n != null)
				kv = (Json_kv)n.Get_itm(Bry_.new_u8(key));
			if (kv == null)
				nde = null;
			else {
				nde = kv.Val();
				if (nde instanceof Json_ary) {
					subary = (Json_ary)nde;
					subs_len = subary.Len();
				}
				else
					subs_len = 1;
			}
		}
		else {
			Mustache_stack_itm stack_itm = new Mustache_stack_itm(cur, subs, subs_idx, subs_len, cur_is_bool); // note that cur is "owner" since subs_idx == 0
			stack.Add(stack_itm);
			subs = cur.Mustache__subs(key); if (subs == null) subs = Mustache_doc_itm_.Ary__empty;	// subs == null if property does not exist; EX: "folder{{#files}}file{{/files}}" and folder = new Folder(File[0]);
			subs_len = subs.length;
		}
		subs_idx = -1;
	}
	public boolean Section_do(boolean inverted) {
		if (cur == null) {
			if (++subs_idx >= subs_len) return false;
			if (nde == null) return false;
			if (nde instanceof Json_itm_bool) {
				Json_itm_bool b = (Json_itm_bool)nde;
				if (b.Data_as_bool() == false) {
					boolean rv = Bool_.N;
					if (inverted) rv = !rv;
					cur_is_bool = Bool_.To_byte(rv);
					return rv;
				}
				else {
					boolean rv = Bool_.Y;
					if (inverted) rv = !rv;
					cur_is_bool = Bool_.To_byte(rv);
					return rv;
				}
			}
                        else if (subary != null) {
                            nde = subary.Get_at(subs_idx);
                        }
		}
		else {
			if (++subs_idx >= subs_len) return false;
			Mustache_doc_itm sub = subs[subs_idx];
			if (subs_idx == 0) {	// special logic to handle 1st item; note that there always be at least one item
				if		(sub == Mustache_doc_itm_.Itm__bool__n) {
					boolean rv = Bool_.N;
					if (inverted) rv = !rv;
					cur_is_bool = Bool_.To_byte(rv);
					return rv;
				}
				else if	(sub == Mustache_doc_itm_.Itm__bool__y) {
					boolean rv = Bool_.Y;
					if (inverted) rv = !rv;
					cur_is_bool = Bool_.To_byte(rv);
					return rv;
				}
				else
					cur_is_bool = Bool_.__byte;
			}
			cur = sub;
		}
		return true;
	}
	public void Section_end() {
		Mustache_stack_itm itm = (Mustache_stack_itm)List_adp_.Pop(stack);
		if (cur == null) {
			nde = itm.nde;
                        subary = null;
		}
		else {
			cur = itm.cur;
		}
		subs = itm.subs;
		subs_len = itm.subs_len;
		subs_idx = itm.subs_idx;
		cur_is_bool = itm.cur_is_bool;
	}
}
class Mustache_stack_itm {
	public Mustache_stack_itm(Mustache_doc_itm cur, Mustache_doc_itm[] subs, int subs_idx, int subs_len, byte cur_is_bool) {
		this.cur = cur;
		this.nde = null;
		this.cur_is_bool = cur_is_bool;
		this.subs = subs;
		this.subs_idx = subs_idx;
		this.subs_len = subs_len;
	}
	public Mustache_stack_itm(Json_itm nde, Mustache_doc_itm[] subs, int subs_idx, int subs_len, byte cur_is_bool) {
		this.cur = null;
		this.nde = nde;
		this.cur_is_bool = cur_is_bool;
		this.subs = subs;
		this.subs_idx = subs_idx;
		this.subs_len = subs_len;
	}
	public final	Json_itm nde;
	public final	Mustache_doc_itm cur;
	public final	byte cur_is_bool;
	public final	Mustache_doc_itm[] subs;
	public final	int subs_idx;
	public final	int subs_len;
}
