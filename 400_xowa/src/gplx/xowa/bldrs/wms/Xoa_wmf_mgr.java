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
package gplx.xowa.bldrs.wms; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.xowa.bldrs.wms.apis.*; import gplx.xowa.wikis.*;	
public class Xoa_wmf_mgr implements GfoInvkAble {
	private final Xoae_wiki_mgr wiki_mgr;
	public Xoa_wmf_mgr(Gfo_usr_dlg usr_dlg, Xoae_wiki_mgr wiki_mgr) {
		this.wiki_mgr = wiki_mgr;
	}
	public boolean Enabled() {return enabled;} private boolean enabled = true; // default to true; DATE:2015-01-05
	public void Enabled_(boolean v) {
		enabled = v;
		int len = wiki_mgr.Count();
		for (int i = 0; i < len; i++) {
			Xowe_wiki wiki = wiki_mgr.Get_at(i);
			wiki.File_mgr().Cfg_download().Enabled_(v);
		}		
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_enabled))			return Yn.To_str(enabled);
		else if	(ctx.Match(k, Invk_enabled_))			Enabled_(m.ReadYn("v"));
		else											return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_enabled = "enabled", Invk_enabled_ = "enabled_";
}
