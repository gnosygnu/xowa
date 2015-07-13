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
public class Xoa_page__commons_mgr {
	public boolean			Xowa_mockup() {return xowa_mockup;} public void Xowa_mockup_(boolean v) {xowa_mockup = v;} private boolean xowa_mockup;
	public Xow_wiki		Source_wiki() {return source_wiki;} public void Source_wiki_(Xow_wiki v) {source_wiki = v;} private Xow_wiki source_wiki;
	public Xow_wiki		Source_wiki_or(Xow_wiki or) {return source_wiki == null ? or : source_wiki;}
	public void Clear() {
		this.xowa_mockup = false;
		this.source_wiki = null;
	}
}
