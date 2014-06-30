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
public class Xof_img_wkr_api_size_base_fxt {
	Xoa_app app; Xow_wiki wiki; Xof_img_wkr_api_size_base_rslts rv = new Xof_img_wkr_api_size_base_rslts();
	public void Clear() {
		this.app = Xoa_app_fxt.app_();
		this.wiki = Xoa_app_fxt.wiki_tst_(app);
	}
	public void Bld_api_url_tst(String ttl_str, int w, int h, String expd) {
		String actl = Xof_img_wkr_api_size_base_wmf.Bld_api_url(wiki.Domain_bry(), Bry_.new_utf8_(ttl_str), w, h);
		Tfds.Eq(expd, actl);
	}
	public void Parse_size_tst(String xml_str, int expd_w, int expd_h) {
		byte[] xml_bry = Bry_.new_utf8_(xml_str);
		Xof_img_wkr_api_size_base_wmf.Parse_xml(rv, app.Usr_dlg(), xml_bry);
		Tfds.Eq(expd_w, rv.Orig_w());
		Tfds.Eq(expd_h, rv.Orig_h());
	}
	public void Parse_reg_tst(String xml_str, String expd_wiki, String expd_page) {
		byte[] xml_bry = Bry_.new_utf8_(xml_str);
		Xof_img_wkr_api_size_base_wmf.Parse_xml(rv, app.Usr_dlg(), xml_bry);
		Tfds.Eq(expd_wiki, String_.new_utf8_(rv.Reg_wiki()));
		Tfds.Eq(expd_page, String_.new_utf8_(rv.Reg_page()));
	}
}
