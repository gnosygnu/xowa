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
package gplx.core.stores; import gplx.*; import gplx.core.*;
import org.junit.*;
import gplx.dbs.*; /*Db_conn_info*/
public class DbMaprMgr_tst {
	@Before public void setup() {
		mgr = DbMaprMgr.new_().RootIndexFlds_(DbMaprArg.new_("id", "disc_id"))
			.Root_
			(	DbMaprItm.proto_(MockDisc.Instance, "discs", "mock_discs")
			.	 Flds_add(MockDisc.id_idk, "disc_id").Flds_add(MockDisc.name_idk, "disc_name")
			.	 ContextFlds_add(MockDisc.id_idk).Subs_add
			(		DbMaprItm.proto_(MockTitle.Instance, "titles", "mock_titles")
			.		 Flds_add(MockTitle.id_idk, "title_id").Flds_add(MockTitle.name_idk, "title_name")
			.		 ContextFlds_add(MockTitle.id_idk).Subs_add
			(			DbMaprItm.proto_(MockChapter.Instance, "chapters", "mock_chapters")
			.			 Flds_add(MockChapter.id_idk, "chapter_id").Flds_add(MockChapter.name_idk, "chapter_name")
			,			DbMaprItm.proto_(MockStream.Instance, "audios", "mock_streams")
			.			 Flds_add(MockStream.id_idk, "stream_id").Flds_add(MockStream.name_idk, "stream_name")
			.			 ConstantFlds_add("stream_type", 0)
			,			DbMaprItm.proto_(MockStream.Instance, "subtitles", "mock_streams")
			.			 Flds_add(MockStream.id_idk, "stream_id").Flds_add(MockStream.name_idk, "stream_name")
			.			 ConstantFlds_add("stream_type", 1)
			)));				
		wtr = DbMaprWtr.new_by_url_(Db_conn_info_.Test);
		wtr.EnvVars().Add(DbMaprWtr.Key_Mgr, mgr);
		conn = Db_conn_pool.Instance.Get_or_new(Db_conn_info_.Test);
		Db_qry_fxt.DeleteAll(conn, "mock_discs", "mock_titles", "mock_chapters", "mock_streams");
	}	DbMaprMgr mgr; DbMaprWtr wtr; Db_conn conn; MockDisc disc; MockTitle title; MockChapter chapter; MockStream audio, subtitle; SrlMgr rdr;
	@Test  public void PurgeObjTree() {
		disc = MockDisc.new_().Id_(1);
		Db_qry_fxt.Insert_kvo(conn, "mock_discs", Keyval_list.New_with_one("disc_id", 1));
		DbMaprWtrUtl.PurgeObjTree(disc, mgr, conn);
		Tfds.Eq(0, Db_qry_fxt.SelectAll_count(conn, "mock_discs"));
	}
	@Test  public void PurgeObjTree_deep() {
		disc = MockDisc.new_().Id_(1);
		Db_qry_fxt.Insert_kvo(conn, "mock_discs", Keyval_list.New_with_one("disc_id", 1));
		Db_qry_fxt.Insert_kvo(conn, "mock_titles", Keyval_list.New_with_one("disc_id", 1).Add("title_id", 1));
		Db_qry_fxt.Insert_kvo(conn, "mock_chapters", Keyval_list.New_with_one("disc_id", 1).Add("title_id", 2).Add("chapter_id", 3));
		Db_qry_fxt.Insert_kvo(conn, "mock_chapters", Keyval_list.New_with_one("disc_id", 2).Add("title_id", 2).Add("chapter_id", 3));
		DbMaprWtrUtl.PurgeObjTree(disc, mgr, conn);

		Tfds.Eq(0, Db_qry_fxt.SelectAll_count(conn, "mock_discs"));
		Tfds.Eq(0, Db_qry_fxt.SelectAll_count(conn, "mock_titles"));
		Tfds.Eq(1, Db_qry_fxt.SelectAll_count(conn, "mock_chapters"));	// ignore chapter with disc_id=2
	}
	@Test  public void Save_root() {
		disc = MockDisc.new_().Id_(1).Name_("disc");

		wtr.StoreRoot(disc, "mock_discs");
		Db_qry_fxt.tst_Select(conn, "mock_discs", Db_mock_row.vals_only_(1, "disc"));
	}
	@Test  public void Save_subs() {
		disc = MockDisc.new_().Id_(1).Name_("disc");
		title = MockTitle.new_().Id_(2).Name_("title").Disc_(disc);

		wtr.StoreRoot(disc, "mock_discs");
		Db_qry_fxt.tst_Select(conn, "mock_discs", Db_mock_row.vals_only_(1, "disc"));
		Db_qry_fxt.tst_Select(conn, "mock_titles", Db_mock_row.vals_only_(1, 2, "title"));
	}
	@Test  public void Save_deep() {
		disc = MockDisc.new_().Id_(1).Name_("disc");
		title = MockTitle.new_().Id_(2).Name_("title").Disc_(disc);
		chapter = MockChapter.new_().Id_(3).Name_("chap").Title_(title);
		audio = MockStream.new_().Id_(4).Name_("audio").Title_(title.Audios());
		subtitle = MockStream.new_().Id_(5).Name_("subtitle").Title_(title.Subtitles());

		wtr.StoreRoot(disc, "mock_discs");
		Db_qry_fxt.tst_Select(conn, "mock_discs", Db_mock_row.vals_only_(1, "disc"));
		Db_qry_fxt.tst_Select(conn, "mock_titles", Db_mock_row.vals_only_(1, 2, "title"));
		Db_qry_fxt.tst_Select(conn, "mock_chapters", Db_mock_row.vals_only_(1, 2, 3, "chap"));
		Db_qry_fxt.tst_Select(conn, "mock_streams"
			, Db_mock_row.vals_only_(1, 2, null, 4, "audio")
			, Db_mock_row.vals_only_(1, 2, null, 5, "subtitle")
			);
	}
	@Test  public void Load_root() {
		rdr = rdr_();
		Db_qry_fxt.Insert_kvo(conn, "mock_discs", Keyval_list.New_with_one("disc_id", 1).Add("disc_name", "name"));
		disc = (MockDisc)rdr.StoreRoot(MockDisc.Instance, null);

		Tfds.Eq(1, disc.Id());
		Tfds.Eq("name", disc.Name());
		Tfds.Eq(0, disc.Titles().Count());
	}
	@Test  public void Load_subs() {
		rdr = rdr_();
		Db_qry_fxt.Insert_kvo(conn, "mock_discs", Keyval_list.New_with_one("disc_id", 1).Add("disc_name", "name"));
		Db_qry_fxt.Insert_kvo(conn, "mock_titles", Keyval_list.New_with_one("disc_id", 1).Add("title_id", 1).Add("title_name", "title1"));
		Db_qry_fxt.Insert_kvo(conn, "mock_titles", Keyval_list.New_with_one("disc_id", 1).Add("title_id", 2).Add("title_name", "title2"));
		disc = (MockDisc)rdr.StoreRoot(MockDisc.Instance, null);

		Tfds.Eq(1, disc.Id());
		Tfds.Eq("name", disc.Name());
		Tfds.Eq(2, disc.Titles().Count());
		Tfds.Eq("title1", ((MockTitle)disc.Titles().Get_at(0)).Name());
		Tfds.Eq("title2", ((MockTitle)disc.Titles().Get_at(1)).Name());
	}
	@Test  public void Load_deep() {
		rdr = rdr_();
		Db_qry_fxt.Insert_kvo(conn, "mock_discs", Keyval_list.New_with_one("disc_id", 1).Add("disc_name", "name"));
		Db_qry_fxt.Insert_kvo(conn, "mock_titles", Keyval_list.New_with_one("disc_id", 1).Add("title_id", 1).Add("title_name", "title1"));
		Db_qry_fxt.Insert_kvo(conn, "mock_chapters", Keyval_list.New_with_one("disc_id", 1).Add("title_id", 1).Add("chapter_id", 3).Add("chapter_name", "chapter1"));
		Db_qry_fxt.Insert_kvo(conn, "mock_streams", Keyval_list.New_with_one("disc_id", 1).Add("title_id", 1).Add("stream_id", 4).Add("stream_type", 0).Add("stream_name", "audio1"));
		Db_qry_fxt.Insert_kvo(conn, "mock_streams", Keyval_list.New_with_one("disc_id", 1).Add("title_id", 1).Add("stream_id", 5).Add("stream_type", 1).Add("stream_name", "subtitle1"));
		disc = (MockDisc)rdr.StoreRoot(MockDisc.Instance, null);

		Tfds.Eq(1, disc.Id());
		Tfds.Eq("name", disc.Name());
		Tfds.Eq(1, disc.Titles().Count());
		MockTitle t = ((MockTitle)disc.Titles().Get_at(0));
		Tfds.Eq("title1", t.Name());
		Tfds.Eq("chapter1", ((MockChapter)t.Chapters().Get_at(0)).Name());
		Tfds.Eq(1, t.Audios().Count());
		Tfds.Eq(1, t.Subtitles().Count());
		Tfds.Eq("audio1", ((MockStream)t.Audios().Get_at(0)).Name());
		Tfds.Eq("subtitle1", ((MockStream)t.Subtitles().Get_at(0)).Name());
	}
	DbMaprRdr rdr_() {
		DbMaprRdr rv = DbMaprRdr.new_(Db_conn_info_.Test, Db_crt_.New_eq("disc_id", 1));
		rv.EnvVars().Add(DbMaprWtr.Key_Mgr, mgr);
		return rv;
	}
}
