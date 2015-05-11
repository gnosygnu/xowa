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
import gplx.xowa.apps.*; import gplx.xowa.apps.fsys.*;
import gplx.xowa.bldrs.css.*;
import gplx.xowa.files.caches.*; import gplx.xowa.files.imgs.*;
import gplx.xowa.urls.encoders.*;
import gplx.xowa.wmfs.*;
import gplx.xowa.html.wtrs.*;
public interface Xoa_app {
	Xoa_app_type			App_type();
	Xoa_fsys_mgr			Fsys_mgr();
	Xof_cache_mgr			File__cache_mgr();
	Xof_img_mgr				File__img_mgr();
	Xowmf_mgr				Wmf_mgr();
	Gfo_usr_dlg				Usr_dlg();
	Bry_bfr_mkr				Utl__bfr_mkr();
	Url_encoder_mgr			Utl__encoder_mgr();
	Xoh_href_parser			Html__href_parser();
	Xoh_lnki_bldr			Html__lnki_bldr();
	Xoa_css_extractor		Html__css_installer();
	boolean					Xwiki_mgr__missing(byte[] domain);
}
