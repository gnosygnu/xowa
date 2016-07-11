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
package gplx.xowa.wikis.pages.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.*;
public class Xopg_db_data {
	public Xopg_db_page			Page()			{return page;}			private final    Xopg_db_page page = new Xopg_db_page();
	public Xopg_db_text			Text()			{return text;}			private final    Xopg_db_text text = new Xopg_db_text();
	public Xopg_db_html			Html()			{return html;}			private final    Xopg_db_html html = new Xopg_db_html();
	public Xopg_db_protection	Protection()	{return protection;}	private final    Xopg_db_protection protection = new Xopg_db_protection();
	public void Clear() {
		page.Clear();
		html.Clear();
		// text.Clear();
		// protection.Clear();
	}
}
