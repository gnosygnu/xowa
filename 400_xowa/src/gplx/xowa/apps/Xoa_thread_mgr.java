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
package gplx.xowa.apps; import gplx.*; import gplx.xowa.*;
import gplx.threads.*;
public class Xoa_thread_mgr {
	public Gfo_thread_pool		Page_load_mgr() {return page_load_mgr;} private Gfo_thread_pool page_load_mgr = new Gfo_thread_pool();
	public Gfo_thread_pool		File_load_mgr() {return file_load_mgr;} private Gfo_thread_pool file_load_mgr = new Gfo_thread_pool();
	public void Usr_dlg_(Gfo_usr_dlg usr_dlg) {
		page_load_mgr.Usr_dlg_(usr_dlg);
		file_load_mgr.Usr_dlg_(usr_dlg);
	}
}
