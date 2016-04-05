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
package gplx.xowa.bldrs.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
public abstract class Xob_itm_basic_base implements GfoInvkAble {
	protected Xoae_app app; protected Xob_bldr bldr; protected Xowe_wiki wiki; protected Gfo_usr_dlg usr_dlg;
	public void Cmd_ctor(Xob_bldr bldr, Xowe_wiki wiki) {
		this.bldr = bldr;
		this.wiki = wiki;
		this.app = bldr == null ? null : bldr.App();
		this.usr_dlg = bldr == null ? null : bldr.Usr_dlg();
		if (bldr != null && wiki != null)
			this.Cmd_ctor_end(bldr, wiki);
	}
	@gplx.Virtual public Xob_cmd Cmd_new(Xob_bldr bldr, Xowe_wiki wiki) {return null;}
	@gplx.Virtual protected void Cmd_ctor_end(Xob_bldr bldr, Xowe_wiki wiki) {}
	@gplx.Virtual public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_owner))				return bldr.Cmd_mgr();
		else	return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_owner = "owner";
}
