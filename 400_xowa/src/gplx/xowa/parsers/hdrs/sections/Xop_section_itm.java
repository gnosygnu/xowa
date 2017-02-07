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
package gplx.xowa.parsers.hdrs.sections; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.hdrs.*;
class Xop_section_itm {
	public Xop_section_itm(int idx, int num, byte[] key, int src_bgn, int src_end) {
		this.idx = idx;
		this.num = num;
		this.key = key;
		this.src_bgn = src_bgn;
		this.src_end = src_end;
	}
	public int Idx() {return idx;} private final    int idx;
	public int Num() {return num;} private final    int num;
	public byte[] Key() {return key;} private final    byte[] key;
	public int Src_bgn() {return src_bgn;} private final    int src_bgn;
	public int Src_end() {return src_end;} private final    int src_end;
}
