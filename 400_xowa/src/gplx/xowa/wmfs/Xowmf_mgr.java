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
package gplx.xowa.wmfs; import gplx.*; import gplx.xowa.*;
import gplx.xowa.wmfs.apis.*; import gplx.xowa.files.downloads.*;
public class Xowmf_mgr {
	public Xowmf_mgr() {
		download_wkr.Download_xrg().User_agent_(Xoa_app_.User_agent);
	}
	public void Init_by_app(Xoa_app app) {
		download_wkr.Download_xrg().Prog_dlg_(Xoa_app_.Usr_dlg());
	}
	public Xowmf_api_mgr		Api_mgr() {return api_mgr;} private Xowmf_api_mgr api_mgr = new Xowmf_api_mgr();
	public Xof_download_wkr		Download_wkr() {return download_wkr;} private final Xof_download_wkr download_wkr = new Xof_download_wkr_io();
}
