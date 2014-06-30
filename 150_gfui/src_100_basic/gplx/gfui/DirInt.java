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
package gplx.gfui; import gplx.*;
public class DirInt {
	public int Val() {return val;} int val;
	public DirInt Rev() {return this == Fwd ? Bwd : Fwd;}
	public int CompareToRng(int v, int lo, int hi) {
		if		(v < lo)	return -1 * val;
		else if (v > hi)	return  1 * val;
		else				return 0;
	}
	public int GetValByDir(int ifBwd, int ifFwd) {
		return this == Bwd ? ifBwd : ifFwd;
	}
	public boolean BoundFail(int i, int bound) {return this == Bwd ? i < bound : i > bound;}
	DirInt(int v) {this.val = v;}
	public static final DirInt
		  Fwd = new DirInt(1)
		, Bwd = new DirInt(-1);
}
