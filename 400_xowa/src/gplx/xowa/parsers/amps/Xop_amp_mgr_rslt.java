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
package gplx.xowa.parsers.amps; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xop_amp_mgr_rslt {
	public Xop_amp_mgr_rslt(int pos, int val, Xop_tkn_itm tkn) {
		this.pos = pos;
		this.val = val;
		this.tkn = tkn;
	}
	public Xop_amp_mgr_rslt() {}
	public boolean Pass() {return pass;} private boolean pass; public void Valid_(boolean v) {this.pass = v;} 
	public int Pos() {return pos;} private int pos; public void Pos_(int v) {this.pos = v;}
	public int Val() {return val;} private int val; public void Val_(int v) {this.val = v;}
	public Xop_tkn_itm Tkn() {return tkn;} private Xop_tkn_itm tkn; public void Tkn_(Xop_tkn_itm v) {this.tkn = v;}
	public boolean Pass_y_(int pos, int val) {
		this.pos = pos; this.val = val;
		this.pass = true;
		return true;
	}
	public boolean Pass_n_(int pos) {
		this.pass = false;
		this.pos = pos;
		this.val = -1;
		this.tkn = null;
		return false;
	}
}
