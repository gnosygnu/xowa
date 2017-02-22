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
package gplx.xowa.wikis.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import org.junit.*; import gplx.xowa.wikis.tdbs.*;
import gplx.xowa.wikis.nss.*;
public class Xow_data_mgr_tst {
	Xow_data_mgr_fxt fxt = new Xow_data_mgr_fxt();
	@Before public void init() {fxt.Clear(); Datetime_now.Manual_y_();}
	@After public void term() {Datetime_now.Manual_n_();}
	@Test  public void Create() {
		fxt	.Create("A1", "A1 data")
			.Create("B12", "B12 data")
			.Create("C123", "C123 data")
			.Tst_regy_title("0|A1|C123|3\n")
			.Tst_data_title(String_.Concat_lines_nl
			(	"!!!!>|!!!!?|!!!!@|"
			,	"!!!!!|!!!!!|!!!!!|0|!!!!(|A1"
			,	"!!!!\"|!!!!!|!!!!\"|0|!!!!)|B12"
			,	"!!!!#|!!!!!|!!!!#|0|!!!!*|C123"
			))
			.Tst_data_page(String_.Concat_lines_nl
			(	"!!!!9|!!!!;|!!!!=|"
			,	"!!!!!\t##PX+\tA1\tA1 data\t"
			,	"!!!!\"\t##PX/\tB12\tB12 data\t"
			,	"!!!!#\t##PX0\tC123\tC123 data\t"
			))
			;
	}
	@Test  public void Update() {
		fxt	.Create("A1", "A1 data")
			.Create("B12", "B12 data")
			.Create("C123", "C123 data")
			.Update("B12", "B12 changed")
			.Tst_regy_title("0|A1|C123|3\n")
			.Tst_data_title(String_.Concat_lines_nl
			(	"!!!!>|!!!!?|!!!!@|"
			,	"!!!!!|!!!!!|!!!!!|0|!!!!(|A1"
			,	"!!!!\"|!!!!!|!!!!\"|0|!!!!,|B12"
			,	"!!!!#|!!!!!|!!!!#|0|!!!!*|C123"
			))
			.Tst_data_page(String_.Concat_lines_nl
			(	"!!!!9|!!!!>|!!!!=|"
			,	"!!!!!\t##PX+\tA1\tA1 data\t"
			,	"!!!!\"\t##PX/\tB12\tB12 changed\t"
			,	"!!!!#\t##PX0\tC123\tC123 data\t"
			))
			;
	}
	@Test  public void Update_zip() {
//			fxt.Wiki().Fsys_mgr().Dir_regy()[Xow_ns_.Tid__main].Ext_tid_(gplx.core.ios.streams.Io_stream_tid_.Tid__zip);
//			fxt.Wiki().Data_mgr().Zip_mgr_(new Io_zip_mgr_mok());
//			fxt	.Create("A1", "A1 data")
//				.Create("B12", "B12 data")
//				.Create("C123", "C123 data")
//				.Update("B12", "B12 changed")
//				.Tst_regy_title("0|A1|C123|3\n")
//				.Tst_data_title(String_.Concat_lines_nl
//				(	"!!!!>|!!!!?|!!!!@|"
//				,	"!!!!!|!!!!!|!!!!!|0|!!!!(|A1"
//				,	"!!!!\"|!!!!!|!!!!\"|0|!!!!,|B12"
//				,	"!!!!#|!!!!!|!!!!#|0|!!!!*|C123"
//				))
//				.Tst_data_page(String_.Concat_lines_nl
//				(	"zipped:!!!!9|!!!!>|!!!!=|"
//				,	"!!!!!\t##PX+\tA1\tA1 data\t"
//				,	"!!!!\"\t##PX/\tB12\tB12 changed\t"
//				,	"!!!!#\t##PX0\tC123\tC123 data\t"
//				))
//				;
	}
	@Test  public void Create_out_of_order() {
		fxt	.Create("C123", "C123 data")
			.Create("B12", "B12 data")
			.Create("A1", "A1 data")
			.Tst_regy_title("0|A1|C123|3\n")
			.Tst_data_title(String_.Concat_lines_nl
			(	"!!!!>|!!!!?|!!!!@|"
			,	"!!!!#|!!!!!|!!!!#|0|!!!!(|A1"
			,	"!!!!\"|!!!!!|!!!!\"|0|!!!!)|B12"
			,	"!!!!!|!!!!!|!!!!!|0|!!!!*|C123"
			))
			.Tst_data_page(String_.Concat_lines_nl
			(	"!!!!=|!!!!;|!!!!9|"
			,	"!!!!!\t##PX+\tC123\tC123 data\t"
			,	"!!!!\"\t##PX/\tB12\tB12 data\t"
			,	"!!!!#\t##PX0\tA1\tA1 data\t"
			))
			;
	}
	@Test  public void Rename() {
		fxt	.Create("A1", "A1 data")
			.Create("B12", "B12 data")
			.Create("C123", "C123 data")
			.Rename("C123", "C1234")
			.Tst_regy_title("0|A1|C123|3\n")
			.Tst_data_title(String_.Concat_lines_nl
			(	"!!!!>|!!!!?|!!!!@|"
			,	"!!!!!|!!!!!|!!!!!|0|!!!!(|A1"
			,	"!!!!\"|!!!!!|!!!!\"|0|!!!!)|B12"
			,	"!!!!#|!!!!!|!!!!#|0|!!!!*|C123"
			))
			.Tst_data_page(String_.Concat_lines_nl
			(	"!!!!9|!!!!;|!!!!=|"
			,	"!!!!!\t##PX+\tA1\tA1 data\t"
			,	"!!!!\"\t##PX/\tB12\tB12 data\t"
			,	"!!!!#\t##PX0\tC123\tC123 data\t"
			))
			;
	}
}
class Xow_data_mgr_fxt {
	Xoae_app app;
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
	public void Clear() {
		app = Xoa_app_fxt.Make__app__edit();
		wiki = Xoa_app_fxt.Make__wiki__edit(app);
		wiki.Db_mgr().Save_mgr().Page_id_next_(0);
	}
	public Xow_data_mgr_fxt Create(String ttl_str, String data) {
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, Bry_.new_u8(ttl_str));
		wiki.Db_mgr().Save_mgr().Data_create(ttl, Bry_.new_u8(data));
		return this;
	}
	public Xow_data_mgr_fxt Update(String ttl_str, String data) {
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, Bry_.new_u8(ttl_str));
		Xoae_page page = Xoae_page.New_test(wiki, ttl);
		wiki.Db_mgr().Save_mgr().Data_update(page, Bry_.new_u8(data));
		return this;
	}
	public Xow_data_mgr_fxt Rename(String old_ttl, String new_ttl) {
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, Bry_.new_u8(old_ttl));
		Xoae_page page = Xoae_page.New_test(wiki, ttl);
		wiki.Db_mgr().Save_mgr().Data_rename(page, ttl.Ns().Id(), Bry_.new_u8(new_ttl));
		return this;
	}
	public Xow_data_mgr_fxt Tst_regy_title(String expd) {return Tst_regy(Xotdb_dir_info_.Name_title, expd);}
	Xow_data_mgr_fxt Tst_regy(String name, String expd) {
		Io_url file_orig = Io_url_.mem_fil_("mem/xowa/wiki/en.wikipedia.org/ns/000/" + name + "/reg.csv");
		Tfds.Eq_str_lines(expd, Io_mgr.Instance.LoadFilStr(file_orig));
		return this;
	}
	public Xow_data_mgr_fxt Tst_data_page(String expd) {return Tst_data(Xotdb_dir_info_.Tid_page , Xow_ns_.Tid__main, 0, expd);}
	public Xow_data_mgr_fxt Tst_data_title(String expd) {return Tst_data(Xotdb_dir_info_.Tid_ttl, Xow_ns_.Tid__main, 0, expd);}
	public Xow_data_mgr_fxt Tst_data(byte dir_tid, int ns_id, int fil, String expd) {
		Io_url url = wiki.Tdb_fsys_mgr().Url_ns_fil(dir_tid, ns_id, fil);
		Tfds.Eq_str_lines(expd, Io_mgr.Instance.LoadFilStr(url));
		return this;
	}
}
