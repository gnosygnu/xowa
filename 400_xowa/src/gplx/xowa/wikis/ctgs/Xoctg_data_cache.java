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
package gplx.xowa.wikis.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
public class Xoctg_data_cache {
	private Hash_adp_bry regy = Hash_adp_bry.cs();	// NOTE: changed to cs from ci; cache is hashing page_ttls by ctg_name which is always ttl.Page_txt(); DATE:2014-07-07
	public Xoctg_data_ctg Get_or_null(byte[] ctg_name) {return (Xoctg_data_ctg)regy.Get_by_bry(ctg_name);}
	public Xoctg_data_ctg Load_or_null(Xowe_wiki wiki, byte[] ctg_name) {
		Gfo_usr_dlg usr_dlg = wiki.Appe().Usr_dlg();
		usr_dlg.Prog_many("", "", "loading file for category: ~{0}", String_.new_u8(ctg_name));
		Xoctg_data_ctg rv = new Xoctg_data_ctg(ctg_name);
		boolean found = wiki.Db_mgr().Load_mgr().Load_ctg_v2(rv, ctg_name); if (!found) return null;
		regy.Add(ctg_name, rv);
		return rv;
	}
}
