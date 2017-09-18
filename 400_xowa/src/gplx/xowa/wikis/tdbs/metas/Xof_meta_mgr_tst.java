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
package gplx.xowa.wikis.tdbs.metas; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.tdbs.*;
import org.junit.*;
import gplx.core.primitives.*;
import gplx.gfui.*; import gplx.xowa.files.*;
public class Xof_meta_mgr_tst {
	Xof_file_regy_fxt fxt = new Xof_file_regy_fxt();
	@Before public void init() {fxt.Ini();}
	@Test  public void Set_one() {
		fxt	.Set("A.png", 440, 400, true, "440,400", "220,200")
			.tst_Save("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv", "A.png|y||1?440,400|1?440,400;1?220,200");
	}
	@Test  public void Set_many() {
		fxt	.Set("A.png"		, 440, 400, true, "440,400", "220,200")
			.Set("Cabbage.jpg"	, 640, 456, false, "220,200", "200,180")
			.tst_Save("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv"
			,	"A.png|y||1?440,400|1?440,400;1?220,200"
			,	"Cabbage.jpg|y||2?640,456|1?220,200;1?200,180"
			);
	}
	@Test  public void Load_and_add() {
		fxt.Save_fil("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv"
			,	"A.png|y||1?440,400|"
			,	"Cabbage.jpg|y||2?640,456|1?440,220;1?220,200"
			)
			.Set("Wooden_hourglass_3.jpg", 967, 1959, false, "220,200")
			.tst_Save("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv"
			,	"A.png|y||1?440,400|"
			,	"Cabbage.jpg|y||2?640,456|1?440,220;1?220,200"
			,	"Wooden_hourglass_3.jpg|y||2?967,1959|1?220,200"
			);
	}
	@Test  public void Load_and_update() {
		fxt.Save_fil("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv"
			,	"A.png|y||0?440,400|"
			,	"Cabbage.jpg|y||2?640,456|1?440,400;1?220,200"
			)
			.Set("A.png", 550, 500, false, "220,200")
			.tst_Save("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv"
			,	"A.png|y||2?550,500|1?220,200"
			,	"Cabbage.jpg|y||2?640,456|1?440,400;1?220,200"
			);
	}
}
class Xof_file_regy_fxt {
	Xof_meta_mgr regy_mgr;
	byte[] md5_(byte[] name) {return Xof_file_wkr_.Md5(name);}
	public void Ini() {
		Io_mgr.Instance.InitEngine_mem();
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app);
		regy_mgr = new Xof_meta_mgr(wiki);
		regy_mgr.Clear();
		regy_mgr.Depth_(2);
	}
	public Xof_file_regy_fxt Save_fil(String url, String... data) {Io_mgr.Instance.SaveFilStr(Io_url_.mem_fil_(url), String_.Concat_lines_nl(data)); return this;}
	public Xof_file_regy_fxt Set(String name_str, int w, int h, boolean orig_exists, String... thumbs) {
		byte[] name = Bry_.new_u8(name_str);		
		byte[] md5 = md5_(name);
		Xof_meta_itm itm = regy_mgr.Get_itm_or_new(name, md5);
		itm.Vrtl_repo_(Xof_meta_itm.Repo_same);	// all tests above assume this is main
		itm.Update_all(Bry_.Empty, w, h, orig_exists ? Xof_meta_itm.Exists_y : Xof_meta_itm.Exists_unknown, To_ary(thumbs));
		return this;
	}
	Xof_meta_thumb[] To_ary(String[] ary) {
		int len = ary.length;
		Xof_meta_thumb[] rv = new Xof_meta_thumb[len];
		Int_ary_parser parser = new Int_ary_parser();
		for (int i = 0; i < len; i++) {
			int[] size = parser.Parse_ary(ary[i], Byte_ascii.Comma);
			rv[i] = new Xof_meta_thumb().Width_(size[0]).Height_(size[1]).Exists_y_();
		}
		return rv;
	}
	public Xof_file_regy_fxt tst_Save(String url_str, String... expd_ary) {
		regy_mgr.Save();
		Tfds.Eq_ary_str(expd_ary, Io_mgr.Instance.LoadFilStr_args(Io_url_.new_fil_(url_str)).ExecAsStrAryLnx());
		return this;
	}
}
