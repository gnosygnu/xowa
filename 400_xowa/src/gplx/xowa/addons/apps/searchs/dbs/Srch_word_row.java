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
package gplx.xowa.addons.apps.searchs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.searchs.*;
public class Srch_word_row {
	public Srch_word_row(int id, byte[] text, int link_count, int link_score_min, int link_score_max) {
		this.Id = id; this.Text = text; this.Link_count = link_count;
		this.Link_score_min = link_score_min; this.Link_score_max = link_score_max;
	}
	public final    int Id;
	public final    byte[] Text;
	public final    int Link_count;
	public final    int Link_score_min;
	public final    int Link_score_max;

        public static final    Srch_word_row Empty = new Srch_word_row(-1, Bry_.Empty, 0, 0, 0);
}
