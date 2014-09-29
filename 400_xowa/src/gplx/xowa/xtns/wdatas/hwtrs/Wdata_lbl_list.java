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
package gplx.xowa.xtns.wdatas.hwtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.xowa.xtns.wdatas.core.*;
class Wdata_lbl_list {
	private HashAdp hash = HashAdp_.new_(); private Int_obj_ref hash_temp_key = Int_obj_ref.neg1_();
	public Wdata_lbl_list(boolean is_pid) {this.is_pid = is_pid;} private boolean is_pid;
	public ListAdp Queue() {return queue;} private ListAdp queue = ListAdp_.new_();
	public byte[] Get_text_or_empty(int id) {
		Wdata_lbl_itm rv_itm = (Wdata_lbl_itm)hash.Fetch(hash_temp_key.Val_(id));
		return rv_itm == null ? Bry_.Empty : rv_itm.Text();
	}
	public void Queue_if_missing(int id) {
		boolean has = hash.Has(hash_temp_key.Val_(id));
		if (!has) {
			Wdata_lbl_itm itm = new Wdata_lbl_itm(is_pid, id);
			hash.Add(Int_obj_ref.new_(id), itm);
			queue.Add(itm);
		}
	}
	public void Resolve(OrderedHash found) {
		int len = queue.Count();
		for (int i = 0; i < len; ++i) {
			Wdata_lbl_itm pending_itm = (Wdata_lbl_itm)queue.FetchAt(i);
			Wdata_langtext_itm found_itm = (Wdata_langtext_itm)found.Fetch(pending_itm.Ttl());
			if (found_itm != null)
				pending_itm.Load_vals(found_itm.Lang(), found_itm.Text());
		}
		queue.Clear();
	}
}
class Wdata_lbl_itm {
	public Wdata_lbl_itm(boolean is_pid, int id) {
		this.is_pid = is_pid; this.id = id; this.ttl = Make_ttl(is_pid, id);
	}
	public boolean Is_pid() {return is_pid;} private final boolean is_pid;
	public int Id() {return id;} private final int id;
	public byte[] Ttl() {return ttl;} private final byte[] ttl;
	public byte[] Lang() {return lang;} private byte[] lang;
	public byte[] Text() {return text;} private byte[] text;
	public void Load_vals(byte[] lang, byte[] text) {this.lang = lang; this.text = text;}
	public static byte[] Make_ttl(boolean is_pid, int id) {
		return is_pid
			? Bry_.Add(Ttl_prefix_pid, Int_.Xto_bry(id))
			: Bry_.Add(Ttl_prefix_qid, Int_.Xto_bry(id))
			;
	}
	private static final byte[] Ttl_prefix_pid = Bry_.new_ascii_("Property:P"), Ttl_prefix_qid = Bry_.new_ascii_("Q");
}
