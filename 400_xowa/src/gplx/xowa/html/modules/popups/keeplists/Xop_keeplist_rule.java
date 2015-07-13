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
package gplx.xowa.html.modules.popups.keeplists; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.modules.*; import gplx.xowa.html.modules.popups.*;
import gplx.core.regxs.*;
public class Xop_keeplist_rule {
	private Gfo_pattern[] excludes; private int excludes_len;
	public Xop_keeplist_rule(Gfo_pattern[] includes, Gfo_pattern[] excludes) {
		this.includes = includes; this.includes_len = includes.length;
		this.excludes = excludes; this.excludes_len = excludes.length;
	}
	public Gfo_pattern[] Includes() {return includes;} private Gfo_pattern[] includes; private int includes_len;
	public boolean Match(byte[] ttl) {
		boolean match_found = false;
		for (int i = 0; i < includes_len; ++i) {
			Gfo_pattern skip = includes[i];
			if (skip.Match(ttl)) {
				match_found = true;
				break;
			}
		}
		if (match_found) {
			for (int i = 0; i < excludes_len; ++i) {
				Gfo_pattern keep = excludes[i];
				if (keep.Match(ttl)) {
					match_found = false;
					break;
				}
			}
		}
		return match_found;
	}
}
