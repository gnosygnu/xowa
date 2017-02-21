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
package gplx.xowa.xtns.wbases.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import org.junit.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.tdbs.*; import gplx.dbs.*;
import gplx.xowa.bldrs.wms.sites.*;
public class Xob_wdata_qid_base_tst {
	private gplx.xowa.bldrs.Xob_fxt fxt; // NOTE: reset memory instance (don't just call clear)
	@Before public void init() {
		this.fxt = new gplx.xowa.bldrs.Xob_fxt().Ctor_mem();
		gplx.dbs.Db_conn_bldr.Instance.Reg_default_mem();
	}
	@Test  public void Basic() {	
		fxt.doc_ary_
		(	fxt.doc_wo_date_(2, "q2", Xob_wdata_pid_base_tst.json_("q2", "links", String_.Ary("enwiki", "q2_en", "frwiki", "q2_fr")))
		,	fxt.doc_wo_date_(1, "q1", Xob_wdata_pid_base_tst.json_("q1", "links", String_.Ary("enwiki", "q1_en", "frwiki", "q1_fr")))
		)
		.Fil_expd(ttl_(fxt.Wiki(), "enwiki", "000", 0)
		,	"!!!!*|!!!!*|"
		,	"Q1_en|q1"
		,	"Q2_en|q2"
		,	""
		)
		.Fil_expd
		(	reg_(fxt.Wiki(), "enwiki", "000")
		,	"0|Q1_en|Q2_en|2"
		,	""
		)
		.Fil_expd(ttl_(fxt.Wiki(), "frwiki", "000", 0)
		,	"!!!!*|!!!!*|"
		,	"Q1_fr|q1"
		,	"Q2_fr|q2"
		,	""
		)
		.Fil_expd
		(	reg_(fxt.Wiki(), "frwiki", "000")
		,	"0|Q1_fr|Q2_fr|2"
		,	""
		)
		.Run(new Xob_wdata_qid_txt().Ctor(fxt.Bldr(), this.fxt.Wiki()))
		;
	}
	@Test  public void Ns() {
		// setup db
		Site_core_db json_db = new Site_core_db(fxt.App().Fsys_mgr().Cfg_site_meta_fil());
		Site_namespace_tbl ns_tbl = json_db.Tbl__namespace();
		ns_tbl.Insert(Bry_.new_a7("en.w"), Xow_ns_.Tid__help, Xow_ns_case_.Bry__1st, Bry_.Empty, Bry_.new_a7("Help"), Bool_.N, Bool_.N, Bry_.Empty);
		ns_tbl.Insert(Bry_.new_a7("fr.w"), Xow_ns_.Tid__help, Xow_ns_case_.Bry__1st, Bry_.Empty, Bry_.new_a7("Aide"), Bool_.N, Bool_.N, Bry_.Empty);
		// run test
		fxt.doc_ary_
		(	fxt.doc_wo_date_(1, "11", Xob_wdata_pid_base_tst.json_("q1", "links", String_.Ary("enwiki", "Help:Q1_en", "frwiki", "Aide:Q1_fr")))
		)
		.Fil_expd(ttl_(fxt.Wiki(), "enwiki", "012", 0)
		,	"!!!!*|"
		,	"Q1_en|q1"
		,	""
		)
		.Fil_expd
		(	reg_(fxt.Wiki(), "enwiki", "012")
		,	"0|Q1_en|Q1_en|1"
		,	""
		)
		.Fil_expd(ttl_(fxt.Wiki(), "frwiki", "012", 0)
		,	"!!!!*|"
		,	"Q1_fr|q1"
		,	""
		)
		.Fil_expd
		(	reg_(fxt.Wiki(), "frwiki", "012")
		,	"0|Q1_fr|Q1_fr|1"
		,	""
		)
		.Run(new Xob_wdata_qid_txt().Ctor(fxt.Bldr(), this.fxt.Wiki()))
		;
	}
	@Test  public void Links_w_name() {	// PURPOSE: wikidata changed links node from "enwiki:A" to "enwiki:{name:A,badges:[]}"; DATE:2013-09-14
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
		fxt.doc_ary_
		(	fxt.doc_wo_date_(1, "q1", q1_str)
		,	fxt.doc_wo_date_(2, "q2", q2_str)
		)
		.Fil_expd(ttl_(fxt.Wiki(), "enwiki", "000", 0)
		,	"!!!!*|!!!!*|"
		,	"Q1_en|q1"
		,	"Q2_en|q2"
		,	""
		)
		.Fil_expd
		(	reg_(fxt.Wiki(), "enwiki", "000")
		,	"0|Q1_en|Q2_en|2"
		,	""
		)
		.Fil_expd(ttl_(fxt.Wiki(), "frwiki", "000", 0)
		,	"!!!!*|!!!!*|"
		,	"Q1_fr|q1"
		,	"Q2_fr|q2"
		,	""
		)
		.Fil_expd
		(	reg_(fxt.Wiki(), "frwiki", "000")
		,	"0|Q1_fr|Q2_fr|2"
		,	""
		)
		.Run(new Xob_wdata_qid_txt().Ctor(fxt.Bldr(), this.fxt.Wiki()))
		;
	}
	@Test  public void Spaces() {	// PURPOSE: assert that ttls with spaces are converted to unders DATE:2015-04-21
		fxt.doc_ary_
		(	fxt.doc_wo_date_(2, "q2", Xob_wdata_pid_base_tst.json_("q2", "links", String_.Ary("enwiki", "q2 en", "frwiki", "q2 fr")))	// note "q2 en" not "q2_en"
		,	fxt.doc_wo_date_(1, "q1", Xob_wdata_pid_base_tst.json_("q1", "links", String_.Ary("enwiki", "q1 en", "frwiki", "q1 fr")))
		)
		.Fil_expd(ttl_(fxt.Wiki(), "enwiki", "000", 0)
		,	"!!!!*|!!!!*|"
		,	"Q1_en|q1"
		,	"Q2_en|q2"	// NOTE: q2_en
		,	""
		)
		.Fil_expd
		(	reg_(fxt.Wiki(), "enwiki", "000")
		,	"0|Q1_en|Q2_en|2"
		,	""
		)
		.Fil_expd(ttl_(fxt.Wiki(), "frwiki", "000", 0)
		,	"!!!!*|!!!!*|"
		,	"Q1_fr|q1"
		,	"Q2_fr|q2"
		,	""
		)
		.Fil_expd
		(	reg_(fxt.Wiki(), "frwiki", "000")
		,	"0|Q1_fr|Q2_fr|2"
		,	""
		)
		.Run(new Xob_wdata_qid_txt().Ctor(fxt.Bldr(), this.fxt.Wiki()))
		;
	}
	public static Io_url reg_(Xowe_wiki wdata, String wiki, String ns_id) {
		return Wdata_idx_wtr.dir_qid_(wdata, wiki, ns_id).GenSubFil(Xotdb_dir_info_.Name_reg_fil);
	}
	public static Io_url ttl_(Xowe_wiki wdata, String wiki, String ns_id, int fil_id) {
		Io_url root = Wdata_idx_wtr.dir_qid_(wdata, wiki, ns_id);
		return Xotdb_fsys_mgr.Url_fil(root, fil_id, Xotdb_dir_info_.Bry_xdat);
	}
}
