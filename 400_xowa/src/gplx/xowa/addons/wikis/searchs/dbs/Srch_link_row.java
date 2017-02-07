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
package gplx.xowa.addons.wikis.searchs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
public class Srch_link_row {
	public Srch_link_row(int word_id, int page_id, int link_score) {
		this.Word_id = word_id;
		this.Page_id = page_id;
		this.Link_score = link_score;
	}
	public final    int Word_id;
	public final    int Page_id;
	public final    int Link_score;
	public int Trg_db_id;

	public int Db_row_size() {return Db_row_size_fixed;}
	private static final int Db_row_size_fixed = (3 * 4);	// 5 ints
}
