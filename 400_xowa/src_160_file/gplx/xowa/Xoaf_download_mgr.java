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
package gplx.xowa; import gplx.*;
public class Xoaf_download_mgr implements GfoInvkAble {
	public Xoaf_download_mgr(Xoa_app app) {
		this.app = app;
		download_wkr = new Xof_download_wkr_io(app);
		download_wkr.Download_xrg().User_agent_(Xoa_app_.User_agent).Prog_dlg_(app.Usr_dlg());		
	}	private Xoa_app app;
	public boolean Enabled() {return enabled;} 
	public Xoaf_download_mgr Enabled_(boolean v) {
		enabled = v;
		File_download_enable(app, v);
		return this;
	} 	private boolean enabled;
	public Xof_download_wkr Download_wkr() {return download_wkr;} public Xoaf_download_mgr Download_wkr_(Xof_download_wkr v) {download_wkr = v; return this;} private Xof_download_wkr download_wkr;
	public Xof_img_wkr_api_size_base	Api_size_wkr() {return api_size_wkr;} public Xoaf_download_mgr Api_size_wkr_(Xof_img_wkr_api_size_base v) {api_size_wkr = v; return this;} private Xof_img_wkr_api_size_base api_size_wkr = new Xof_img_wkr_api_size_base_wmf();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_enabled))			return Yn.X_to_str(enabled);
		else if	(ctx.Match(k, Invk_enabled_))			Enabled_(m.ReadYn("v"));
		else											return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_enabled = "enabled", Invk_enabled_ = "enabled_";
	public static void File_download_enable(Xoa_app app, boolean v) {
		int len = app.Wiki_mgr().Count();
		for (int i = 0; i < len; i++) {
			Xow_wiki wiki = app.Wiki_mgr().Get_at(i);
			wiki.File_mgr().Cfg_download().Enabled_(v);
		}		
	}
}
