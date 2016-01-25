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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
import org.junit.*; import gplx.xowa.files.repos.*;
public class Xof_url_bldr_tst {
	private Xof_url_bldr_fxt fxt = new Xof_url_bldr_fxt();
	@Before public void init() {fxt.Clear();}
	@Test 	public void Ogv() 			{fxt.Dir_spr_http_().Root_("http://test/").Md5_("d0").Ttl_("A.ogv").W_(220).Expd_src_("http://test/thumb/d/d0/A.ogv/220px--A.ogv.jpg").Test();}
	@Test 	public void Ogv__seek() 	{fxt.Dir_spr_http_().Root_("http://test/").Md5_("d0").Ttl_("A.ogv").W_(220).Expd_src_("http://test/thumb/d/d0/A.ogv/220px-seek%3D5-A.ogv.jpg").Seek_(5).Test();}
	@Test 	public void Ogv__no_w() 	{fxt.Dir_spr_http_().Root_("http://test/").Md5_("d0").Ttl_("A.ogv").W_( -1).Expd_src_("http://test/thumb/d/d0/A.ogv/-1px--A.ogv.jpg").Test();}	// TODO: use orig_w, not -1
	@Test 	public void Xcf() 			{fxt.Dir_spr_http_().Root_("http://test/").Md5_("44").Ttl_("A.xcf").W_(220).Expd_src_("http://test/thumb/4/44/A.xcf/220px-A.xcf.png").Test();}
	@Test 	public void Bmp() 			{fxt.Dir_spr_http_().Root_("http://test/").Md5_("70").Ttl_("A.bmp").W_(220).Expd_src_("http://test/thumb/7/70/A.bmp/220px-A.bmp.png").Test();}
	@Test 	public void Pdf() 			{fxt.Dir_spr_http_().Root_("http://test/").Md5_("ef").Ttl_("A.pdf").W_(220).Expd_src_("http://test/thumb/e/ef/A.pdf/page1-220px-A.pdf.jpg").Test();}
	@Test 	public void Pdf__page_2() 	{fxt.Dir_spr_http_().Root_("http://test/").Md5_("ef").Ttl_("A.pdf").W_(220).Expd_src_("http://test/thumb/e/ef/A.pdf/page2-220px-A.pdf.jpg").Page_(2).Test();}
	@Test 	public void Long() {
		String filename = String_.Repeat("A", 200) + ".png";
		fxt.Dir_spr_http_().Root_("http://test/").Md5_("14").Ttl_(filename).W_(220)
			.Expd_src_("http://test/thumb/1/14/" + filename + "/220px-thumbnail.png")
			.Test()
			;
	}
}
class Xof_url_bldr_fxt {
	private final Xof_url_bldr url_bldr = new Xof_url_bldr();
	public Xof_url_bldr_fxt Clear() {
		dir_spr = Byte_.Zero; ext = null; root = md5 = ttl = expd_src = null;
		seek = Xof_lnki_time.Null;
		page = Xof_lnki_page.Null;
		w = Xof_img_size.Null;
		return this;
	}
	public Xof_url_bldr_fxt Dir_spr_http_() {return Dir_spr_(Byte_ascii.Slash);}
	public Xof_url_bldr_fxt Dir_spr_fsys_wnt_() {return Dir_spr_(Byte_ascii.Backslash);}
	public Xof_url_bldr_fxt Dir_spr_(byte v) {dir_spr = v; return this;} private byte dir_spr;
	public Xof_url_bldr_fxt Root_(String v) {root = v; return this;} private String root;
	public Xof_url_bldr_fxt Md5_(String v) {md5 = v; return this;} private String md5;
	public Xof_url_bldr_fxt Ttl_(String v) {ttl = v; ext = Xof_ext_.new_by_ttl_(Bry_.new_u8(v)); return this;} private String ttl; Xof_ext ext;
	public Xof_url_bldr_fxt W_(int v) {this.w = v; return this;} private int w;
	public Xof_url_bldr_fxt Page_(int v) {page = v; return this;} private int page = Xof_lnki_page.Null;
	public Xof_url_bldr_fxt Seek_(int v) {seek = v; return this;} private double seek = Xof_lnki_time.Null;
	public Xof_url_bldr_fxt Expd_src_(String v) {expd_src = v; return this;} private String expd_src;
	public Xof_url_bldr_fxt Test() {
		url_bldr.Init_by_root(Bry_.new_u8(root), dir_spr, Bool_.Y, Bool_.N, 2);
		url_bldr.Init_by_itm (Xof_repo_itm_.Mode_thumb, Bry_.new_u8(ttl), Bry_.new_u8(md5), ext, w, seek, page);
		Tfds.Eq(expd_src, url_bldr.Xto_str());
		return this;
	}
}
