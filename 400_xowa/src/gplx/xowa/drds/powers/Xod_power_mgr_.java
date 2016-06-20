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
package gplx.xowa.drds.powers; import gplx.*; import gplx.xowa.*; import gplx.xowa.drds.*;
public class Xod_power_mgr_ {
	public static Xod_power_mgr Instance = new Xod_power_mgr__shim();
}
class Xod_power_mgr__shim implements Xod_power_mgr {
	// private final    Ordered_hash hash = Ordered_hash_.New();
	public void Wake_lock__get(String name) {
		// if (hash.Has(name)) {hash.Clear(); throw Err_.new_("itm exists", "name", name);}
		// hash.Add(name, name);
	}
	public void Wake_lock__rls(String name) {
		// if (!hash.Has(name)) throw Err_.new_("itm missing", "name", name);
		// hash.Del(name);
	}
}
