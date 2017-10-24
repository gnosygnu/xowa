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
//namespace gplx.xowa.addons.wikis.searchs.v1s {
//	import org.junit.*; using gplx.xowa.wikis.tdbs; using gplx.xowa.wikis.data.tbls;
//	public class Xosrh_core_tst {
//		@Before public void Init() {fxt.Clear();} private Xos_search_mgr_fxt fxt = new Xos_search_mgr_fxt();
//		@Test   public void Basic() {
//			fxt.Init_basic();
//			fxt.Test_search_exact("b2", "B2_22", "B2_12", "B2__2");
//			fxt.Test_search_exact("a" , "A___0");
//			fxt.Test_search_exact("b1a");	// missing: mid
//			fxt.Test_search_exact("d");		// missing: end
//			fxt.Test_search_exact("$");		// missing: bgn
//			fxt.Test_search_match_bgn("b*", "B3_23", "B2_22", "B1_21", "B3_13", "B2_12", "B1_11", "B3__3", "B2__2", "B1__1");
//		}
//		@Test  public void Page_size() {
//			fxt.Init_basic();
//			fxt.Search_mgr().Page_mgr().Itms_per_page_(1);
//			fxt.Test_search("b*", 0, "B3_23");
//			fxt.Test_search("b*", 1, "B2_22");
//			fxt.Test_search("b*", 2, "B1_21");
//			fxt.Test_search("b*", 3, "B3_13");
//		}
//		@Test  public void Url() {
//			Xoa_url url = Xow_url_parser_old.Parse_url(fxt.App(), fxt.Wiki(), "Special:Search/Abc?fulltext=y&xowa_sort=len_desc");
//			fxt.Search_mgr().Args_mgr().Clear().Parse(url.Args());
//			Tfds.Eq(Srch_rslt_row_sorter.Tid_len_dsc, fxt.Search_mgr().Args_mgr().Sort_tid());
//		}
//		@Test  public void Url_arg_title() {// http://en.wikipedia.org/wiki/Special:Search/Earth?fulltext=yes&title=Mars
//			fxt.Test_url_search_bry("Special:Search?fulltext=y&search=Abc"		, "Abc");	// query arg
////			fxt.Test_url_search_bry("Special:Search/Abc?fulltext=y"				, "Abc");	// leaf
//			fxt.Test_url_search_bry("Special:Search/Abc?fulltext=y&search=Def"	, "Def");	// leaf overrides query arg
//		}
//		@Test  public void Url_ns() {
//			fxt.Test_url__ns("Special:Search?search=Abc&ns0=1&ns1=1", "0|1");
//			fxt.Test_url__ns("Special:Search?search=Abc&ns*=1", "*");
//			fxt.Test_url__ns("Special:Search?search=Abc", "0");
//		}
//		@Test  public void Html() {
//			fxt.Init_basic();
//			fxt.Test_html_by_url("B1", "", String_.Concat_lines_nl
//				(	"Result '''1''' of '''3''' for '''B1'''<br/>"
//				,	"{|"
//				,	"|-"
//				,	"| [[Special:Search/B1?fulltext=y&xowa_page_index=0|&lt;]]"
//				,	"| [[Special:Search/B1?fulltext=y&xowa_page_index=1|&gt;]]"
//				,	"|-"
//				,	"| [[Special:Search/B1?fulltext=y&xowa_sort=len_desc|length]]"
//				,	"| [[Special:Search/B1?fulltext=y&xowa_sort=title_asc|title]]"
//				,	"|-"
//				,	"| 42 || [[B1 21]]"
//				,	"|-"
//				,	"| 22 || [[B1 11]]"
//				,	"|-"
//				,	"| 2 || [[B1  1]]"
//				,	"|-"
//				,	"| [[Special:Search/B1?fulltext=y&xowa_page_index=0|&lt;]]"
//				,	"| [[Special:Search/B1?fulltext=y&xowa_page_index=1|&gt;]]"
//				,	"|}"
//				));
//		}
////		@Test   public void Page_next() {
////			fxt.Init_basic();
////			fxt.Search_mgr().Page_size_(1);
////			fxt.Test_search(Srch_special_page.Match_tid_all, "B1", 0, "B1 1");
////			fxt.Test_search(Srch_special_page.Match_tid_all, "B1", 1, "B1 11");
////		}
////		@Test   public void Misc_url() {
////			fxt.Init_basic();
////			fxt.Search_mgr().Page_size_(1);
////			fxt.Expd_address_page_("Special:Search/B1");
////			fxt.Test_search(Srch_special_page.Match_tid_all, "B1", 0, "B1 1");
////		}
//		@Test  public void Sort_defaults_to_len_desc() {
//			fxt.Init_basic();
//			fxt.Search_mgr().Page_mgr().Itms_per_page_(3);
//			fxt.Test_search2(Srch_special_page.Match_tid_bgn, "b"	, 0, Srch_rslt_row_sorter.Tid_ttl_asc	, "B1_11", "B1_21", "B1__1");	// sort by name; note that _ sorts after alphabet
//			fxt.Test_search2(Srch_special_page.Match_tid_bgn, "b"	, 1, Srch_rslt_row_sorter.Tid_none		, "B2_12", "B2_22", "B2__2");	// sort by name still; next page should not reset
//			fxt.Test_search2(Srch_special_page.Match_tid_bgn, "b2"	, 0, Srch_rslt_row_sorter.Tid_none		, "B2_22", "B2_12", "B2__2");	// sort by len desc; new search should reset
//		}
//	}
//	class Xos_search_mgr_fxt {
//		Xoae_app app; Xowe_wiki wiki; Bry_bfr bfr = Bry_bfr_.Reset(500); Srch_special_page search_mgr;
//		public Xoae_app App() {return app;}
//		public Xowe_wiki Wiki() {return wiki;}
//		public Xobl_regy_itm 	regy_itm_(int id, String bgn, String end, int count) {return new Xobl_regy_itm(id, Bry_.new_u8(bgn), Bry_.new_u8(end), count);}
//		public Xowd_page_itm 	data_ttl_(int id, String ttl) {return data_ttl_(id, 0, 0, false, 0, ttl);}
//		public Xowd_page_itm 	data_ttl_(int id, int fil, int row, boolean redirect, int len, String ttl) {return new Xowd_page_itm().Init(id, Bry_.new_u8(ttl), redirect, len, fil, row);}
//		public Xowd_page_itm 		data_id_(int id, String ttl) {return data_id_(id, Xow_ns_.Tid__main, ttl);} 
//		public Xowd_page_itm 		data_id_(int id, int ns, String ttl) {return new Xowd_page_itm().Id_(id).Ns_id_(ns).Ttl_page_db_(Bry_.new_u8(ttl)).Text_db_id_(0).Text_len_(0);}
//		public Xobl_search_ttl 	data_sttl_(String word, params int[] ids) {return new Xobl_search_ttl(Bry_.new_u8(word), data_ttl_word_page_ary_(ids));}
//		public Xobl_search_ttl_page[] data_ttl_word_page_ary_(params int[] ids) {
//			int ids_len = ids.length;
//			Xobl_search_ttl_page[] rv = new Xobl_search_ttl_page[ids_len];
//			for (int i = 0; i < ids_len; i++) {
//				int id = ids[i];
//				rv[i] = new Xobl_search_ttl_page(id, id * 2);
//			}
//			return rv;
//		}
//		public void Init_regy_site(byte dir_info, params Xobl_regy_itm[] ary) 	{Init_regy(wiki.Tdb_fsys_mgr().Url_site_reg(dir_info), ary);}	
//		public void Init_regy_ns  (String ns_num, byte tid, params Xobl_regy_itm[] ary) 	{Init_regy(wiki.Tdb_fsys_mgr().Url_ns_reg(ns_num, tid), ary);}
//		public void Init_regy(Io_url url, Xobl_regy_itm[] ary) {		
//			int ary_len = ary.length;
//			for (int i = 0; i < ary_len; i++) {
//				Xobl_regy_itm itm = ary[i];
//				itm.Srl_save(tmp_bfr);
//			}
//			Io_mgr.Instance.SaveFilBfr(url, tmp_bfr);
//		}	private Bry_bfr tmp_bfr = Bry_bfr_.Reset(255);
//		public void Init_data(Io_url fil, params Xobl_data_itm[] ary) {
//			Xob_xdat_file xdat_file = new Xob_xdat_file();
//			int ary_len = ary.length;
//			for (int i = 0; i < ary_len; i++) {
//				Xobl_data_itm itm = ary[i];
//				itm.Srl_save(tmp_bfr);
//				xdat_file.Insert(bfr, tmp_bfr.To_bry_and_clear());
//			}
//			xdat_file.Save(fil);
//		}
//		public void Init_basic() {
//			this.Init_regy_ns(wiki.Ns_mgr().Ns_main().Num_str(), Xotdb_dir_info_.Tid_search_ttl, this.regy_itm_(0, "A", "C", 5));
//			this.Init_data(wiki.Tdb_fsys_mgr().Url_ns_fil(Xotdb_dir_info_.Tid_search_ttl, Xow_ns_.Tid__main, 0)
//				, this.data_sttl_("a"	,  0)
//				, this.data_sttl_("b1"	,  1, 11, 21)
//				, this.data_sttl_("b2"	,  2, 12, 22)
//				, this.data_sttl_("b3"	,  3, 13, 23)
//				, this.data_sttl_("c"	,  4)
//				);
//			this.Init_regy_site(Xotdb_dir_info_.Tid_id, this.regy_itm_(0, "A", "C", 11));
//			this.Init_data(wiki.Tdb_fsys_mgr().Url_site_fil(Xotdb_dir_info_.Tid_id, 0)
//				, this.data_id_( 0, "A___0")
//				, this.data_id_( 1, "B1__1")
//				, this.data_id_( 2, "B2__2")
//				, this.data_id_( 3, "B3__3")
//				, this.data_id_( 4, "C___4")
//				, this.data_id_(11, "B1_11")
//				, this.data_id_(12, "B2_12")
//				, this.data_id_(13, "B3_13")
//				, this.data_id_(21, "B1_21")
//				, this.data_id_(22, "B2_22")
//				, this.data_id_(23, "B3_23")
//				);
//			search_mgr.Page_mgr().Ns_mgr().Add_all(); // WORKAROUND: xdat fmt does not store ns with search data; pages will be retrieved with ns_id = null; force ns_all (instead of allowing ns_main default);
//		}
//		public void Clear() {
//			Io_mgr.Instance.InitEngine_mem();
//			app = Xoa_app_fxt.Make__app__edit();
//			wiki = Xoa_app_fxt.Make__wiki__edit(app);
//			search_mgr = wiki.Special_mgr().Page_search();
//			wiki.Appe().Gui_mgr().Search_suggest_mgr().Args_default_str_("ns*=1"); // WORKAROUND: xdat fmt does not store ns with search data; pages will be retrieved with ns_id = null; force ns_all (instead of allowing ns_main default);
//		}
//		public Srch_special_page Search_mgr() {return search_mgr;}
//		public void Test_url_search_bry(String url_str, String expd) {
//			Xoa_url url = Xow_url_parser_old.Parse_url(app, wiki, url_str);
//			search_mgr.Args_mgr().Clear().Parse(url.Args());
//			Tfds.Eq(expd, String_.new_u8(search_mgr.Args_mgr().Search_bry()));
//		}
//		public void Test_url__ns(String url_str, String expd) {
//			Xoa_url url = Xow_url_parser_old.Parse_url(app, wiki, url_str);
//			search_mgr.Args_mgr().Clear().Parse(url.Args());
//			Tfds.Eq(expd, String_.new_a7(search_mgr.Args_mgr().Ns_mgr().Xto_hash_key()));
//		}
//		public void Test_search_exact(String ttl_str, params String[] expd_ary) {Test_search(ttl_str, 0, expd_ary);}
//		public void Test_search_match_bgn(String ttl_str, params String[] expd_ary) {Test_search(ttl_str, 0, expd_ary);}
//		public void Test_search(String ttl_str, int page_idx, params String[] expd_ary) {
//			byte[] ttl_bry = Bry_.new_a7(ttl_str);
//			Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b128();
//			Xosrh_rslt_grp page = search_mgr.Page_mgr().Search(bfr, wiki, ttl_bry, page_idx, search_mgr.Page_mgr());
//			bfr.Mkr_rls();
//			Tfds.Eq_ary(expd_ary, Search_itms_to_int_ary(page));
//		}
//		public void Test_html_by_url(String ttl_str, String args_str, String expd_html) {
//			wiki.Init_needed_(false);
//			byte[] ttl_bry = Bry_.new_a7(ttl_str);
//			Xoa_ttl ttl = Xoa_ttl.Parse(wiki, ttl_bry);
//			Xoae_page page = Xoae_page.New_test(wiki, ttl);
//			byte[] url_bry = Bry_.new_a7("http://en.wikipedia.org/wiki/Special:Search/" + ttl_str + args_str);
//			Xoa_url url = wiki.Appe().Url_parser().Parse(url_bry);
//			search_mgr.Special__gen(url, page, wiki, ttl);
//			Tfds.Eq_str_lines(expd_html, String_.new_u8(page.Root().Data_htm()));
//		}
//		public void Test_search2(byte match_tid, String ttl_str, int page_idx, byte sort_tid, params String[] expd_ary) {
//			Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b128();
//			Xow_url_parser_old url_parser = new Xow_url_parser_old();			
//			byte[] url_raw = Bry_.new_a7("Special:Search/" + ttl_str + ((match_tid == Srch_special_page.Match_tid_all) ? "" : "*")  + "?fulltext=y" + Srch_rslt_row_sorter.Xto_url_arg(sort_tid) + "&xowa_page_size=1&xowa_page_index=" + page_idx);
//			Xoa_url url = url_parser.Parse(url_raw);
//			Xoa_ttl ttl = Xoa_ttl.Parse(wiki, url_raw);
//			Xoae_page page = wiki.Ctx().Page();
//			search_mgr.Special__gen(url, page, wiki, ttl);
//			Xosrh_rslt_grp cur_grp = search_mgr.Cur_grp();
//			bfr.Mkr_rls();
//			Tfds.Eq_ary(expd_ary, Search_itms_to_int_ary(cur_grp));
//		}		
//		String[] Search_itms_to_int_ary(Xosrh_rslt_grp page) {
//			int itms_len = page.Itms_len();
//			String[] rv = new String[itms_len];
//			for (int i = 0; i < itms_len; i++) {
//				Xowd_page_itm itm = page.Itms_get_at(i);
//				rv[i] = String_.new_u8(itm.Ttl_page_db());
//			}
//			return rv;
//		}
//	}
//	interface Xobl_data_itm {
//		void Srl_save(Bry_bfr bfr);
//	}
//}
