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
public interface Texvc_tkn {
	int					Tid();
	Texvc_root			Root();
	int					Uid();
	int					Src_bgn();
	int					Src_end();
	void				Src_end_(int v);
	Texvc_tkn			Init(Texvc_root root, int tid, int uid, int src_bgn, int src_end);
	int					Subs__len();
	Texvc_tkn			Subs__get_at(int i);
	void				Print_dbg_bry(Bry_bfr bfr, int indent);
	void				Print_tex_bry(Bry_bfr bfr, byte[] src, int indent);
}
