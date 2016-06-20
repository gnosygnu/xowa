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
package gplx.xowa.addons.bldrs.exports.splits.mgrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
public class Split_db_size_calc {
	private final    long size_max;
	private long size_cur;		
	public Split_db_size_calc(long size_max, int idx) {
		this.size_max = size_max;
		this.idx = idx;
	}
	public int Idx() {return idx;} private int idx;
	public int Size_cur_add_(int v) {
		long size_new = size_cur + v;
		if (size_new > size_max) {
			++idx;
			size_cur = 0;
		}
		else
			size_cur = size_new;
		return idx;
	}
}
