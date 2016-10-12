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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages.langs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*;
import gplx.core.intls.ucas.*;
public class Xoctg_collation_mgr {
	private final    Xow_wiki wiki;
	private Xoctg_collation_wkr wkr;
	public Xoctg_collation_mgr(Xow_wiki wiki) {
		this.wiki = wiki;
		this.wkr = new Xoctg_collation_wkr__uppercase(wiki);	// REF:https://noc.wikimedia.org/conf/InitialiseSettings.php.txt|wgCategoryCollation|default
	}
	public void Collation_name_(String v) {
		this.wkr = Xoctg_collation_wkr_.Make(wiki, v);
	}
	public byte[] Get_sortkey(byte[] src) {
		return wkr.Get_sortkey(src);
	}
}
