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
package gplx.core.brys; import gplx.*; import gplx.core.*;
public class Bry_tmp {
	public byte[] src;
	public int src_bgn;
	public int src_end;
	public boolean dirty;
	public Bry_tmp Init(byte[] src, int src_bgn, int src_end) {
		this.dirty = false;
		this.src = src;
		this.src_bgn = src_bgn;
		this.src_end = src_end;
		return this;
	}
	public void Set_by_bfr(Bry_bfr bfr) {
		dirty = true;
		src = bfr.To_bry_and_clear();
		src_bgn = 0;
		src_end = src.length;
	}
	public void Add_to_bfr(Bry_bfr bfr) {
		bfr.Add_mid(src, src_bgn, src_end);
	}
}
