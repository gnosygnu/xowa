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
package gplx.xowa.addons.bldrs.volumes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*;
import gplx.core.brys.*;
class Volume_make_itm implements Bry_bfr_able {
	public int Uid = 0;
	public int Prep_id = 0;
	public int Item_type = 0;				// 0=page;1=thm
	public int Item_id = 0;					// either page_id or fsdb_id
	public String Item_name = "";			// friendly-name
	public byte[] Item_ttl = Bry_.Empty;	// actual name
	public long Item_size = 0;				// size of page / file
	public void To_bfr(Bry_bfr bfr) {
	}
}
/*
1|p|Earth|123|100
1|f|Earth.png|124|200
1|f|Moon.png|125|300
4|p|Sun|123|100
*/
