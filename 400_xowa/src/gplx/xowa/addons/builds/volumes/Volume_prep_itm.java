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
package gplx.xowa.addons.builds.volumes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.builds.*;
import gplx.core.brys.*;
class Volume_prep_itm implements Bry_bfr_able {
	public int Prep_id = 0;
	public byte[] Page_ttl = null;
	public long Max_bytes = 0;
	public int Max_depth = 2;
	public int Max_article_count = -1;
	public int Max_link_count_per_page = -1;
	public boolean Skip_navbox = false;
	public int Max_file_count = -1;
	public long Max_file_size = -1;
	public boolean Skip_audio = true;
	public static final    Volume_prep_itm[] Ary_empty = new Volume_prep_itm[0];
	public void To_bfr(Bry_bfr bfr) {
		bfr.Add(Page_ttl);
	}
}
// Earth|2|100|10|100|100MB
