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
package gplx.xowa.bldrs; import gplx.*; import gplx.xowa.*;
public abstract class Xob_cmd_base implements Xob_cmd {
	public abstract String Cmd_key();
	@gplx.Virtual public void Cmd_init(Xob_bldr bldr) {}
	@gplx.Virtual public void Cmd_bgn(Xob_bldr bldr) {}
	@gplx.Virtual public void Cmd_run() {}
	@gplx.Virtual public void Cmd_end() {}
	@gplx.Virtual public void Cmd_term() {}
	@gplx.Virtual public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return GfoInvkAble_.Rv_unhandled;}
}
