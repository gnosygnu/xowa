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
public class Texvc_tkn__leaf_raw implements Texvc_tkn {
	public Texvc_tkn Init(Texvc_root root, int tid, int uid, int src_bgn, int src_end) {
		this.root = root;
		this.tid = tid;
		this.uid = uid;
		this.src_bgn = src_bgn;
		this.src_end = src_end;
		return this;
	}
	public Texvc_root Root() {return root;} private Texvc_root root;
	@gplx.Virtual public int Tid() {return tid;} private int tid;
	public int Uid() {return uid;} private int uid;
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public void Src_end_(int v) {this.src_end = v;}
	public int Subs__len() {return 0;}
	public Texvc_tkn Subs__get_at(int i)	{throw Err_.new_unsupported();}
	public void Print_tex_bry(Bry_bfr bfr, byte[] src, int indent) {bfr.Add_mid(src, src_bgn, src_end);}
	public void Print_dbg_bry(Bry_bfr bfr, int indent) {
		Texvc_tkn_.Print_dbg_str__bgn(bfr, indent, this);
		Texvc_tkn_.Print_dbg_str__end_head(bfr);
	}
}
