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
package gplx.xowa; import gplx.*;
public class Xob_xdat_itm {
	public byte[] Src() {return src;} public Xob_xdat_itm Src_(byte[] v) {src = v; return this;} private byte[] src;
	public int Itm_bgn() {return itm_bgn;} public Xob_xdat_itm Itm_bgn_(int v) {itm_bgn = v; return this;} private int itm_bgn = -1;
	public int Itm_end() {return itm_end;} public Xob_xdat_itm Itm_end_(int v) {itm_end = v; return this;} private int itm_end = -1;
	public int Itm_idx() {return itm_idx;} public Xob_xdat_itm Itm_idx_(int v) {itm_idx = v; return this;} private int itm_idx = -1;
	public void Clear() {itm_bgn = itm_end = itm_idx = -1; src = null; found_exact = false;}
	public boolean Found_exact() {return found_exact;} private boolean found_exact;
	public Xob_xdat_itm Found_exact_y_() {found_exact = true; return this;}
	public boolean Missing() {return itm_bgn == -1;}
	public byte[] Itm_bry() {return Bry_.Mid(src, itm_bgn, itm_end);}
}
