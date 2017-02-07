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
package gplx.xowa.addons.bldrs.centrals.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*;
import gplx.core.progs.*; import gplx.core.gfobjs.*;
public interface Xobc_cmd_itm extends Gfo_prog_ui, Gfo_invk {
	int			Task_id();
	int			Step_id();
	int			Cmd_id();
	String		Cmd_type();			// "xowa.core.http_download"
	String		Cmd_name();			// "download"
	boolean		Cmd_suspendable();	// "true"
	String		Cmd_uid();			// for thread_pool_mgr: "0:0:0"
	void		Cmd_cleanup();
	String		Cmd_fallback();
	void		Cmd_clear();

	Gfobj_nde	Save_to(Gfobj_nde nde);
	void		Load_checkpoint();
}
