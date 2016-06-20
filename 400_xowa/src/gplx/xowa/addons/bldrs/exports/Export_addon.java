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
package gplx.xowa.addons.bldrs.exports; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*;
import gplx.xowa.bldrs.wkrs.*;
public class Export_addon implements Xoax_addon_itm, Xoax_addon_itm__bldr {
	public Xob_cmd[] Bldr_cmds() {
		return new Xob_cmd[] 
		{ gplx.xowa.addons.bldrs.exports.splits.Split_bldr_cmd.Prototype
		, gplx.xowa.addons.bldrs.exports.merges.Merge_bldr_cmd.Prototype
		, gplx.xowa.addons.bldrs.exports.packs.splits.Pack_split_bldr_cmd.Prototype
		, gplx.xowa.addons.bldrs.exports.packs.files.Pack_file_bldr_cmd.Prototype
		};
	}

	public String Addon__key() {return "xowa.builds.repacks";}
}
