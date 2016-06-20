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
package gplx.xowa.addons.wikis.searchs.bldrs.cmds.adjustments; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.bldrs.*; import gplx.xowa.addons.wikis.searchs.bldrs.cmds.*;
class Page_stub implements CompareAble {
	public Page_stub(int id, boolean is_redirect, int len, int score) {
		this.Id = id;
		this.Is_redirect = is_redirect;
		this.Len = len;
		this.Score = score;
	}
	public final    int Id;
	public final    boolean Is_redirect;
	public final    int Len;
	public final    int Score;

	public int compareTo(Object obj) {
		Page_stub comp = (Page_stub)obj;
		// sort redirects and small pages to bottom
		int is_redirect_compare = -Bool_.Compare(Is_redirect, comp.Is_redirect);
		if (is_redirect_compare == CompareAble_.Same)
			return Int_.Compare(Len, comp.Len);
		else
			return is_redirect_compare;
	}
}
