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
package gplx.xowa.bldrs.wiki_cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
public class Xoi_wiki_props_ns {
	public int Id() {return id;} private int id;
	public boolean Subpages_enabled() {return subpages_enabled;} private boolean subpages_enabled;
	public Xoi_wiki_props_ns Init_by_ctor(int id, boolean subpages_enabled) {this.id = id; this.subpages_enabled = subpages_enabled; return this;}
	public void Init_by_xml(gplx.xmls.XmlNde ns_nde) {
		this.id = Int_.parse_(ns_nde.Atrs().FetchValOr("id", "-1"));
		this.subpages_enabled = ns_nde.Atrs().Fetch_or_null("subpages") != null;// per api, subpages="" means ns has subpages; no subpages attribute means no subpages
	}
}
