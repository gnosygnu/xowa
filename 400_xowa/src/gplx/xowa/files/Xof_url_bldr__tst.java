/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
import org.junit.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.imgs.*;
public class Xof_url_bldr__tst {
	private Xof_url_bldr__fxt fxt = new Xof_url_bldr__fxt();
	@Before public void init() {fxt.Clear().Dir_spr_http_();}
	@Test 	public void Ogv() 			{fxt.Root_("http://test/").Md5_("d0").Ttl_("A.ogv").W_(220).Expd_src_("http://test/thumb/d/d0/A.ogv/220px--A.ogv.jpg").Test();}
	@Test 	public void Ogv__seek() 	{fxt.Root_("http://test/").Md5_("d0").Ttl_("A.ogv").W_(220).Expd_src_("http://test/thumb/d/d0/A.ogv/220px-seek%3D5-A.ogv.jpg").Seek_(5).Test();}
	@Test 	public void Ogv__no_w() 	{fxt.Root_("http://test/").Md5_("d0").Ttl_("A.ogv").W_( -1).Expd_src_("http://test/thumb/d/d0/A.ogv/-1px--A.ogv.jpg").Test();}	// TODO_OLD: use orig_w, not -1
	@Test 	public void Xcf() 			{fxt.Root_("http://test/").Md5_("44").Ttl_("A.xcf").W_(220).Expd_src_("http://test/thumb/4/44/A.xcf/220px-A.xcf.png").Test();}
	@Test 	public void Bmp() 			{fxt.Root_("http://test/").Md5_("70").Ttl_("A.bmp").W_(220).Expd_src_("http://test/thumb/7/70/A.bmp/220px-A.bmp.png").Test();}
	@Test 	public void Pdf() 			{fxt.Root_("http://test/").Md5_("ef").Ttl_("A.pdf").W_(220).Expd_src_("http://test/thumb/e/ef/A.pdf/page1-220px-A.pdf.jpg").Test();}
	@Test 	public void Pdf__page_2() 	{fxt.Root_("http://test/").Md5_("ef").Ttl_("A.pdf").W_(220).Expd_src_("http://test/thumb/e/ef/A.pdf/page2-220px-A.pdf.jpg").Page_(2).Test();}
	@Test 	public void Long() {
		String filename = String_.Repeat("A", 200) + ".png";
		fxt.Root_("http://test/").Md5_("14").Ttl_(filename).W_(220)
			.Expd_src_("http://test/thumb/1/14/" + filename + "/220px-thumbnail.png")
			.Test()
			;
	}
	@Test 	public void Math__http() 	{fxt.Repo_tid_(Xof_repo_tid_.Tid__math).Fsys_is_wnt_(Bool_.N).Root_("http://test/").Ttl_("random_md5.svg").Expd_src_("http://test/random_md5").Test();}	// NOTE: strip ".svg" if online
	@Test 	public void Math__file() 	{fxt.Repo_tid_(Xof_repo_tid_.Tid__math).Fsys_is_wnt_(Bool_.Y).Root_("file://xowa/").Ttl_("random_md5.svg").Expd_src_("file://xowa/random_md5.svg").Test();}	// NOTE: keep ".svg" if online
}
class Xof_url_bldr__fxt {
	private final    Xof_url_bldr url_bldr = new Xof_url_bldr();
	public Xof_url_bldr__fxt Clear() {
		dir_spr = Byte_.Zero; ext = null; root = md5 = ttl = expd_src = null;
		seek = Xof_lnki_time.Null;
		page = Xof_lnki_page.Null;
		w = Xof_img_size.Null;
		return this;
	}
	public Xof_url_bldr__fxt Dir_spr_http_() {return Dir_spr_(Byte_ascii.Slash);}
	public Xof_url_bldr__fxt Dir_spr_fsys_wnt_() {return Dir_spr_(Byte_ascii.Backslash);}
	public Xof_url_bldr__fxt Dir_spr_(byte v) {dir_spr = v; return this;} private byte dir_spr;
	public Xof_url_bldr__fxt Root_(String v) {root = v; return this;} private String root;
	public Xof_url_bldr__fxt Md5_(String v) {md5 = v; return this;} private String md5;
	public Xof_url_bldr__fxt Ttl_(String v) {ttl = v; ext = Xof_ext_.new_by_ttl_(Bry_.new_u8(v)); return this;} private String ttl; Xof_ext ext;
	public Xof_url_bldr__fxt W_(int v) {this.w = v; return this;} private int w;
	public Xof_url_bldr__fxt Page_(int v) {page = v; return this;} private int page = Xof_lnki_page.Null;
	public Xof_url_bldr__fxt Seek_(int v) {seek = v; return this;} private double seek = Xof_lnki_time.Null;
	public Xof_url_bldr__fxt Repo_tid_(byte v) {repo_tid = v; return this;} private byte repo_tid = Xof_repo_tid_.Tid__null;
	public Xof_url_bldr__fxt Fsys_is_wnt_(boolean v) {fsys_is_wnt = v; return this;} private boolean fsys_is_wnt;
	public Xof_url_bldr__fxt Expd_src_(String v) {expd_src = v; return this;} private String expd_src;
	public Xof_url_bldr__fxt Test() {
		url_bldr.Init_by_repo(repo_tid, Bry_.new_u8(root), fsys_is_wnt, dir_spr, Bool_.Y, Bool_.N, 2);
		url_bldr.Init_by_itm (Xof_img_mode_.Tid__thumb, Bry_.new_u8(ttl), Bry_.new_u8_safe(md5), ext, w, seek, page);
		Tfds.Eq(expd_src, url_bldr.Xto_str());
		return this;
	}
}
