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
package gplx.xowa.addons.apps.cfgs.mgrs.dflts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.mgrs.*;
import gplx.langs.jsons.*;
class Xocfg_dflt_loader {
	public void Load_by_file(Xocfg_dflt_mgr dflt_mgr, Io_url url) {
		byte[] src = Io_mgr.Instance.LoadFilBryOrNull(url);
		if (src == null) return;
		Json_parser parser = new Json_parser();
		Json_doc doc = parser.Parse(src);
		Json_ary ary = doc.Root_ary();
		int len = ary.Len();
		for (int i = 0; i < len; i++) {
			Json_nde nde = ary.Get_at_as_nde(i);
			String key = nde.Get_as_str_or("key", null);
			String val = nde.Get_as_str_or("val", null);
			dflt_mgr.Add(key, val);
		}
	}
}
