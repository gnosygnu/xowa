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
package gplx.core.brys.fmts; import gplx.*; import gplx.core.*; import gplx.core.brys.*;
public class Bry_fmt_itm {
	public Bry_fmt_itm(int tid, int src_bgn, int src_end) {
		this.Tid = tid;
		this.Src_bgn = src_bgn;
		this.Src_end = src_end;
	}
	public int		Tid;
	public int		Src_bgn;
	public int		Src_end;
	public int		Key_idx;
	public Bfr_arg	Arg;

	public static final int Tid__txt = 0, Tid__key = 1, Tid__arg = 2;
}
