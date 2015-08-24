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
package gplx.xowa.xtns.math.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.math.*;
class Mwm_tkn__node implements Mwm_tkn {
	public Mwm_tkn__root Root() {return root;} private Mwm_tkn__root root;
	public int Tid() {return tid;} private int tid;
	public int Uid() {return uid;} private int uid;
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public Mwm_tkn Init(Mwm_tkn__root root, int tid, int uid, int src_bgn, int src_end) {
		this.root = root;
		this.tid = tid;
		this.uid = uid;
		this.src_bgn = src_bgn;
		this.src_end = src_end;
		return this;
	}
	public int Subs__len() {return root.Regy__get_subs_len(uid);}
	public Mwm_tkn Subs__get_at(int i) {return root.Regy__get_subs_tkn(uid, i);}
	public void To_bry(Bry_bfr bfr, int indent) {
		Mwm_tkn_.Tkn_to_bry__bgn(bfr, indent, this);
		Mwm_tkn_.Tkn_to_bry__end_head(bfr);
	}
}
