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
package gplx.xowa.addons.builds.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.builds.*;
import gplx.xowa.bldrs.wkrs.*;
import gplx.xowa.addons.builds.files.cmds.*;
public class Xoax_builds_files_addon implements Xoax_addon_itm, Xoax_addon_itm__bldr {
	public Xob_cmd[] Cmds_ary() {
		return new Xob_cmd[]
		{ Xobldr__lnki_temp__create.Prototype
		, Xobldr__lnki_regy__create.Prototype
		, Xobldr__page_regy__create.Prototype
		, Xobldr__orig_regy__create.Prototype
		, Xobldr__xfer_temp__insert_thm.Prototype
		, Xobldr__xfer_temp__insert_orig.Prototype
		, Xobldr__xfer_regy__create.Prototype
		, Xobldr__xfer_regy__update_downloaded.Prototype

		, Xobldr__fsdb_db__create_data.Prototype
		, Xobldr__fsdb_db__create_orig.Prototype
		, Xobldr__page_file_map__create.Prototype

		, Xobldr__text_db__make_page.Prototype
		, Xobldr__text_db__drop_page.Prototype
		, Xobldr__redirect__create.Prototype
		, Xobldr__image__create.Prototype
		};
	}

	public static final    byte[] ADDON_KEY = Bry_.new_a7("xowa.builds.images");
	public byte[] Addon__key()	{return ADDON_KEY;}
}
