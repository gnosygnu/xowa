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
interface Mwm_lxr {
	int		Tid();
	int		Make_tkn(Mwm_ctx ctx, Mwm_tkn__root root, byte[] src, int src_len, int bgn_pos, int cur_pos);
}
class Mwm_lxr_ {
	public static final int
		Tid__ws				= 0
	,	Tid__raw			= 1
	,	Tid__backslash		= 2
	,	Tid__curly_bgn		= 3
	,	Tid__curly_end		= 4
	,	Tid__brack_bgn		= 5
	,	Tid__brack_end		= 6
	;
}
