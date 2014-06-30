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
import org.junit.*;
public class Xow_fsys_mgr_tst {
	Xow_fsys_mgr_fxt fxt = new Xow_fsys_mgr_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Basic() {
		fxt.Zip_(Xow_dir_info_.Tid_page, Bool_.N).Url_ns_fil(Xow_dir_info_.Tid_page, Xow_ns_.Id_main, 123, "mem/xowa/wiki/en.wikipedia.org/ns/000/page/00/00/00/01/0000000123.xdat");
		fxt.Zip_(Xow_dir_info_.Tid_page, Bool_.Y).Url_ns_fil(Xow_dir_info_.Tid_page, Xow_ns_.Id_main, 123, "mem/xowa/wiki/en.wikipedia.org/ns/000/page_zip/00/00/00/01/0000000123.zip");
	}
}
class Xow_fsys_mgr_fxt {
	public void Clear() {
		app = Xoa_app_fxt.app_();
		wiki = Xoa_app_fxt.wiki_tst_(app);
	}
	Xoa_app app; Xow_wiki wiki;
	public Xow_fsys_mgr_fxt Zip_(byte tid, boolean v) {wiki.Fsys_mgr().Dir_regy()[tid].Ext_tid_(v ? gplx.ios.Io_stream_.Tid_zip : gplx.ios.Io_stream_.Tid_file); return this;}
	public void Url_ns_fil(byte tid, int ns_id, int fil_idx, String expd) {
		Tfds.Eq(expd, wiki.Fsys_mgr().Url_ns_fil(tid, ns_id, fil_idx).Raw());
	}
}
