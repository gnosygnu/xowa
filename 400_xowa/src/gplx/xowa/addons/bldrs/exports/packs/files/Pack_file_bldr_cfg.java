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
package gplx.xowa.addons.bldrs.exports.packs.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.packs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;
public class Pack_file_bldr_cfg implements Gfo_invk {
	public Io_url Deploy_dir() {return deploy_dir;} private Io_url deploy_dir;
	public boolean Pack_html() {return pack_html;} private boolean pack_html = true;
	public boolean Pack_file() {return pack_file;} private boolean pack_file = true;
	public boolean Pack_fsdb_delete() {return pack_fsdb_delete;} private boolean pack_fsdb_delete;
	public DateAdp Pack_file_cutoff() {return pack_file_cutoff;} private DateAdp pack_file_cutoff = null;

	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__deploy_dir_))			deploy_dir = m.ReadIoUrl("v");
		else if	(ctx.Match(k, Invk__pack_html_))			pack_html = m.ReadYn("v");
		else if	(ctx.Match(k, Invk__pack_file_))			pack_file = m.ReadYn("v");
		else if	(ctx.Match(k, Invk__pack_file_cutoff_))		pack_file_cutoff = m.ReadDate("v");
		else if	(ctx.Match(k, Invk__pack_fsdb_delete_))		pack_fsdb_delete = m.ReadYn("v");
		else												return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk__deploy_dir_ = "deploy_dir_"
	, Invk__pack_html_ = "pack_html_", Invk__pack_file_ = "pack_file_", Invk__pack_file_cutoff_ = "pack_file_cutoff_"
	, Invk__pack_fsdb_delete_ = "pack_fsdb_delete_"
	;
}
