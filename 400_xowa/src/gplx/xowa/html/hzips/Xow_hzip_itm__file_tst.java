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
package gplx.xowa.html.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import org.junit.*; import gplx.xowa.html.*; import gplx.xowa.hdumps.srls.*;
public class Xow_hzip_itm__file_tst {
	@Before public void init() {fxt.Clear();} private Xow_hzip_mgr_fxt fxt = new Xow_hzip_mgr_fxt();
	@Test   public void Srl_lnki_img_full() {
		byte[][] brys = Bry_.Ary(Xow_hzip_dict.Bry_img_full, Bry_.ints_(7), Bry_.ints_(12, 0), Bry_.new_ascii_("cls_other"), Bry_.new_ascii_("|caption_other"), Xow_hzip_dict.Escape_bry);
		fxt.Test_save(brys, "<a xtid='a_img_full' xatrs='1|1|1|12|cls_other|caption_other'/>");
//			fxt.Test_load(brys, "a_1<a href='/wiki/A' title='A'>A</a>a_2");
	}
}
