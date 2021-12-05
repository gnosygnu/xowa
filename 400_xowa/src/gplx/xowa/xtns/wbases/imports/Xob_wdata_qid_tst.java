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
package gplx.xowa.xtns.wbases.imports;
import gplx.Bry_;
import gplx.Io_mgr;
import gplx.Io_url_;
import gplx.Object_;
import gplx.String_;
import gplx.dbs.Db_conn;
import gplx.dbs.Db_conn_bldr;
import gplx.dbs.DbmetaFldList;
import gplx.objects.primitives.BoolUtl;
import gplx.xowa.bldrs.Xobldr_fxt;
import gplx.xowa.bldrs.wms.sites.Site_core_db;
import gplx.xowa.bldrs.wms.sites.Site_namespace_tbl;
import gplx.xowa.wikis.nss.Xow_ns_;
import gplx.xowa.wikis.nss.Xow_ns_case_;
import gplx.xowa.xtns.wbases.Wdata_wiki_mgr_fxt;
import org.junit.Before;
import org.junit.Test;
public class Xob_wdata_qid_tst {
	private Db_conn conn;
	private final Xobldr_fxt fxt = new Xobldr_fxt().Ctor_mem();
	private Xob_wdata_qid wkr;
	private final Gfo_db_tester db_tester = new Gfo_db_tester();
	private final DbmetaFldList flds__wbase_qid = new DbmetaFldList().BldStr("src_wiki").BldInt("src_ns").BldStr("src_ttl").BldStr("trg_ttl");
	@Before public void init() {
		Io_mgr.Instance.InitEngine_mem();
		Db_conn_bldr.Instance.Reg_default_mem();
		this.conn = Db_conn_bldr.Instance.New(Io_url_.mem_fil_("mem/db/wbase.xowa"));
		this.wkr = new Xob_wdata_qid(conn);
		wkr.Ctor(fxt.Bldr(), fxt.Wiki());
	}

	@Test  public void Basic() {
		fxt.Run_page_wkr(wkr
			,	fxt.New_page_wo_date(2, "q2", Wdata_wiki_mgr_fxt.New_json("q2", "links", String_.Ary("enwiki", "q2_en", "frwiki", "q2_fr")))
			,	fxt.New_page_wo_date(1, "q1", Wdata_wiki_mgr_fxt.New_json("q1", "links", String_.Ary("enwiki", "q1_en", "frwiki", "q1_fr")))
			);

		db_tester.Test__select_tbl(conn, "wbase_qid", flds__wbase_qid
		, Object_.Ary("enwiki", 0, "Q2_en", "q2")
		, Object_.Ary("frwiki", 0, "Q2_fr", "q2")
		, Object_.Ary("enwiki", 0, "Q1_en", "q1")
		, Object_.Ary("frwiki", 0, "Q1_fr", "q1")
		);
	}
	@Test public void Ns() {
		Site_core_db json_db = new Site_core_db(fxt.App().Fsys_mgr().Cfg_site_meta_fil());
		Site_namespace_tbl ns_tbl = json_db.Tbl__namespace();
		ns_tbl.Insert(Bry_.new_a7("en.w"), Xow_ns_.Tid__help, Xow_ns_case_.Bry__1st, Bry_.Empty, Bry_.new_a7("Help"), BoolUtl.N, BoolUtl.N, Bry_.Empty);
		ns_tbl.Insert(Bry_.new_a7("fr.w"), Xow_ns_.Tid__help, Xow_ns_case_.Bry__1st, Bry_.Empty, Bry_.new_a7("Aide"), BoolUtl.N, BoolUtl.N, Bry_.Empty);

		fxt.Run_page_wkr(wkr
		,	fxt.New_page_wo_date(1, "11", Wdata_wiki_mgr_fxt.New_json("q1", "links", String_.Ary("enwiki", "Help:Q1_en", "frwiki", "Aide:Q1_fr")))
		);

		db_tester.Test__select_tbl(conn, "wbase_qid", flds__wbase_qid
		, Object_.Ary("enwiki", 12, "Q1_en", "q1")
		, Object_.Ary("frwiki", 12, "Q1_fr", "q1")
		);
	}
	@Test public void Links_w_name() {	// PURPOSE: wikidata changed links node from "enwiki:A" to "enwiki:{name:A,badges:[]}"; DATE:2013-09-14
		String q1_str = String_.Concat_lines_nl
		(	"{ \"entity\":\"q1\""
		,	", \"links\":"
		,	"  { \"enwiki\":\"q1_en\""
		,	"  , \"frwiki\":\"q1_fr\""
		,	"  }"
		,	"}"
		);
		String q2_str = String_.Concat_lines_nl
		(	"{ \"entity\":[\"item\",2]"
		,	", \"links\":"
		,	"  { \"enwiki\":{\"name\":\"q2_en\",\"badges\":[]}"
		,	"  , \"frwiki\":{\"name\":\"q2_fr\",\"badges\":[]}"
		,	"  }"
		,	"}"
		);

		fxt.Run_page_wkr(wkr
		,	fxt.New_page_wo_date(1, "q1", q1_str)
		,	fxt.New_page_wo_date(2, "q2", q2_str)
		);

		db_tester.Test__select_tbl(conn, "wbase_qid", flds__wbase_qid
		, Object_.Ary("enwiki", 0, "Q1_en", "q1")
		, Object_.Ary("frwiki", 0, "Q1_fr", "q1")
		, Object_.Ary("enwiki", 0, "Q2_en", "q2")
		, Object_.Ary("frwiki", 0, "Q2_fr", "q2")
		);
	}
	@Test public void Spaces() {	// PURPOSE: assert that ttls with spaces are converted to unders DATE:2015-04-21
		fxt.Run_page_wkr(wkr
		,	fxt.New_page_wo_date(2, "q2", Wdata_wiki_mgr_fxt.New_json("q2", "links", String_.Ary("enwiki", "q2 en", "frwiki", "q2 fr")))	// note "q2 en" not "q2_en"
		,	fxt.New_page_wo_date(1, "q1", Wdata_wiki_mgr_fxt.New_json("q1", "links", String_.Ary("enwiki", "q1 en", "frwiki", "q1 fr")))
		);

		db_tester.Test__select_tbl(conn, "wbase_qid", flds__wbase_qid
		, Object_.Ary("enwiki", 0, "Q2_en", "q2") // NOTE: q2_en
		, Object_.Ary("frwiki", 0, "Q2_fr", "q2")
		, Object_.Ary("enwiki", 0, "Q1_en", "q1")
		, Object_.Ary("frwiki", 0, "Q1_fr", "q1")
		);
	}
}
