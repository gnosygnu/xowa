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
package gplx.xowa.xtns.math.texvcs.tkns; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.math.*; import gplx.xowa.xtns.math.texvcs.*;
import gplx.xowa.xtns.math.texvcs.funcs.*;
public class Texvc_tkn__func implements Texvc_tkn {
	public Texvc_tkn__func(Texvc_func_itm func_itm) {this.func_itm = func_itm;}
	public Texvc_func_itm Func_itm() {return func_itm;} private final Texvc_func_itm func_itm;
	public Texvc_root Root() {return root;} private Texvc_root root;
	public int Tid() {return tid;} private int tid;
	public int Uid() {return uid;} private int uid;
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public void Src_end_(int v) {this.src_end = v;}
	public Texvc_tkn Init(Texvc_root root, int tid, int uid, int src_bgn, int src_end) {
		this.root = root;
		this.tid = tid;
		this.uid = uid;
		this.src_bgn = src_bgn;
		this.src_end = src_end;
		return this;
	}
	public int Subs__len() {return root.Regy__get_subs_len(uid);}
	public Texvc_tkn Subs__get_at(int i) {return root.Regy__get_subs_tkn(uid, i);}
	public void Print_tex_bry(Bry_bfr bfr, byte[] src, int indent) {
		if (func_itm.Id() != Texvc_func_itm_.Id__xowa_arg)		// do not add func name if arg; EX: "{a}" vs "\alpha{a}"
			bfr.Add_byte_backslash().Add(func_itm.Key());		// funcs have pattern of "\key"; EX: "\alpha"
		int subs_len = Subs__len();
		if (subs_len > 0) {
			boolean curly_added = false;				
			for (int i = 0; i < subs_len; ++i) {
				Texvc_tkn sub_tkn = Subs__get_at(i);
				if (i == 0 && (sub_tkn.Tid() == Texvc_tkn_.Tid__func) && tid != Texvc_tkn_.Tid__curly) {
					bfr.Add_byte(Byte_ascii.Curly_bgn);
					curly_added = true;
				}
				if (sub_tkn.Tid() == Texvc_tkn_.Tid__curly)
					bfr.Add_byte(Byte_ascii.Curly_bgn);
				sub_tkn.Print_tex_bry(bfr, src, indent + 1);
				if (sub_tkn.Tid() == Texvc_tkn_.Tid__curly)
					bfr.Add_byte(Byte_ascii.Curly_end);
			}
			if (curly_added)
				bfr.Add_byte(Byte_ascii.Curly_end);
		}
	}
	public void Print_dbg_bry(Bry_bfr bfr, int indent) {
		Texvc_tkn_.Print_dbg_str__bgn(bfr, indent, this);
		Texvc_tkn_.Print_dbg_str__end_head(bfr);
		int subs_len = Subs__len();
		for (int i = 0; i < subs_len; ++i) {
			Texvc_tkn sub_tkn = Subs__get_at(i);
			sub_tkn.Print_dbg_bry(bfr, indent + 1);
		}
	}
}
