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
package gplx.xowa.addons.bldrs.hdumps.diffs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.hdumps.*;
class Dumpdiff_cfg implements Gfo_invk {
	public Io_url Prv_dir() {return prv_dir;} private Io_url prv_dir;
	public Io_url Cur_dir() {return cur_dir;} private Io_url cur_dir;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__cur_dir_))			this.cur_dir = m.ReadIoUrl("v");
		else if	(ctx.Match(k, Invk__prv_dir_))			this.prv_dir = m.ReadIoUrl("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}		
	public static final String Invk__cur_dir_ = "cur_dir_", Invk__prv_dir_ = "prv_dir_";
}
