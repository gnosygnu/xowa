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
import gplx.core.btries.*;
class Mwm_trie_bldr {
	public static Btrie_fast_mgr new_() {
		Btrie_fast_mgr rv = Btrie_fast_mgr.cs();
		rv.Add(" "		, new Mwm_lxr__ws());
		rv.Add("\\"		, new Mwm_lxr__backslash());
		rv.Add("{"		, new Mwm_lxr__curly_bgn());
		rv.Add("}"		, new Mwm_lxr__curly_end());
		rv.Add("["		, new Mwm_lxr__brack_bgn());
		rv.Add("]"		, new Mwm_lxr__brack_end());
		return rv;
	}
}
