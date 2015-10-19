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
package gplx.xowa.langs.funcs; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.xowa.parsers.tmpls.*;
public class Xol_func_itm {
	public byte Tid() {return tid;} private byte tid = Xot_defn_.Tid_null;
	public Xot_defn Func() {return func;} private Xot_defn func = Xot_defn_.Null;
	public void Func_set(Xot_defn v, int colon_pos) {
		if (tid == Xot_defn_.Tid_null) tid = Xot_defn_.Tid_func; // only set tid if subst did not set it
		this.func = v; 
		this.colon_pos = colon_pos;
	}
	public int Colon_pos() {return colon_pos;} private int colon_pos = -1;
	public int Subst_bgn() {return subst_bgn;} private int subst_bgn = -1;
	public int Subst_end() {return subst_end;} private int subst_end = -1;
	public void Subst_set_(byte tid, int bgn, int end) {this.tid = tid; this.subst_bgn = bgn; this.subst_end = end;}
	public void Clear() {
		tid = Xot_defn_.Tid_null;
		func = Xot_defn_.Null;
		colon_pos = subst_bgn = subst_end = -1;
	}
}
