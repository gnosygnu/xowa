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
package gplx.xowa.apps.site_cfgs;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.commons.GfoDateNow;
import gplx.libs.files.Io_mgr;
import gplx.libs.files.Io_url;
import gplx.types.basics.utls.StringUtl;
import gplx.core.btries.Btrie_slim_mgr;
import gplx.dbs.cfgs.Db_cfg_tbl;
import gplx.langs.jsons.Json_doc;
import gplx.langs.jsons.Json_printer;
import gplx.types.basics.utls.BoolUtl;
import gplx.xowa.Xoa_app_fxt;
import gplx.xowa.Xoa_test_;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.parsers.Xop_parser_tid_;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class Xoa_site_cfg_mgr_tst {
	private final Xoa_site_cfg_mgr_fxt fxt = new Xoa_site_cfg_mgr_fxt();
	@Before		public void init() {fxt.Init();}
	@After  public void term() {fxt.Term();}
	@Test public void Extensiontags__cfg() {
		fxt.Init_db(Xoa_site_cfg_loader__inet.Qarg__extensiontags, fxt.Make_data(Xoa_site_cfg_loader_.Tid__inet, 1, "math", "source"));
		fxt.Exec_load();
		fxt.Test_extensiontags_y("math"	, "source");
		fxt.Test_extensiontags_n("ref"	, "graph");
		fxt.Test_extensiontags_y("translate", "languages");	// always whitelist <translate>,<languages> DATE:2015-10-13
	}
	@Test public void Extensiontags__fsys() {
		fxt.Init_fsys(Xoa_site_cfg_loader__inet.Qarg__extensiontags, fxt.Make_data(Xoa_site_cfg_loader_.Tid__null, 1, "math", "source"));
		fxt.Exec_load();
		fxt.Test_extensiontags_y("math", "source");
		fxt.Test_extensiontags_n("ref"	, "graph");
		fxt.Test_db(Xoa_site_cfg_loader__inet.Qarg__extensiontags, fxt.Make_data(Xoa_site_cfg_loader_.Tid__fsys, 1, "math", "source"));
	}
	@Test public void Extensiontags__inet() {
		fxt.Init_inet(fxt.Make_api(fxt.Make_api_extensiontags("math", "source")));
		fxt.Exec_load();
		fxt.Test_extensiontags_y("math", "source");
		fxt.Test_extensiontags_n("ref"	, "graph");
		fxt.Test_db(Xoa_site_cfg_loader__inet.Qarg__extensiontags, fxt.Make_data(Xoa_site_cfg_loader_.Tid__inet, 1, "math", "source"));
	}
	@Test public void Extensiontags__fallback() {
		fxt.Exec_load();
		fxt.Test_db(Xoa_site_cfg_loader__inet.Qarg__extensiontags, fxt.Make_data(Xoa_site_cfg_loader_.Tid__fallback, 1));
		fxt.Test_extensiontags_y("math", "source", "ref", "graph");
	}
	@Test public void Interwikimap__inet() {
		fxt.Init_inet(fxt.Make_api(fxt.Make_api_interwikimap("w", "https://en.wikipedia.org", "c", "https://commons.wikimedia.org")));
		fxt.Exec_load();
		fxt.Test_db(Xoa_site_cfg_loader__inet.Qarg__interwikimap, fxt.Make_data(Xoa_site_cfg_loader_.Tid__inet, 2, "w", "https://en.wikipedia.org", "c", "https://commons.wikimedia.org"));
	}
//		@Test public void Print() {
//			String s = fxt.Make_api(fxt.Make_api_interwikimap("k1", "v1", "k2", "v2"), fxt.Make_api_extensiontags2("k3", "v3", "k4", "v4"));
//            Tfds.Dbg(s);
//		}
}
class Xoa_site_cfg_mgr_fxt {
	private final Xoae_app app; private final Xowe_wiki wiki;
	private final Xoa_site_cfg_mgr site_cfg_mgr;
	private final Db_cfg_tbl cfg_tbl;
	private final Json_printer printer = new Json_printer();
	private final BryWtr tmp_bfr = BryWtr.New();
	public Xoa_site_cfg_mgr_fxt() {
		// Xoa_app_.Usr_dlg_(Xoa_app_.New__usr_dlg__console());
		gplx.core.ios.IoEngine_system.Web_access_enabled = true;	// HACK: must manually enable web_access else above tests will fail due to some other test disabling singleton; DATE:2016-12-15
		Xoa_test_.Inet__init();
		this.app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app);
		Xoa_test_.Init__db__edit(wiki);
		this.cfg_tbl = wiki.Data__core_mgr().Tbl__cfg();
		this.site_cfg_mgr = app.Site_cfg_mgr();
	}
	public void Init() {
		GfoDateNow.ManualSetY(); GfoDateNow.AutoincrementSetN();
		Io_mgr.Instance.InitEngine_mem();
		cfg_tbl.Delete_grp(Xoa_site_cfg_loader__db.Grp__xowa_wm_api);
		site_cfg_mgr.Init_loader_bgn(wiki);
		app.Utl__inet_conn().Clear();
	}
	public void Term() {
		GfoDateNow.ManualSetN();
	}
	public void Init_db(String key, String data) {
		cfg_tbl.Assert_bry(Xoa_site_cfg_loader__db.Grp__xowa_wm_api, key, BryUtl.NewU8(data));
	}
	public void Test_db(String key, String expd) {
		byte[] actl = cfg_tbl.Select_bry_or(Xoa_site_cfg_loader__db.Grp__xowa_wm_api, key, null);
		GfoTstr.EqLines(expd, StringUtl.NewU8(actl));
	}
	public void Init_inet(String data) {
		String url = Xoa_site_cfg_loader__inet.Bld_url(tmp_bfr, wiki.Domain_str(), site_cfg_mgr.Data_hash(), site_cfg_mgr.Itm_ary());
		app.Utl__inet_conn().Upload_by_bytes(url, BryUtl.NewU8(data));
	}
	public void Init_fsys(String key, String data) {
		Xoa_site_cfg_loader__fsys loader = Xoa_site_cfg_loader__fsys.new_(app);
		Io_url url = loader.Make_load_url(wiki.Domain_str(), key);
		Io_mgr.Instance.SaveFilStr(url, data);
	}
	public Xoa_site_cfg_mgr_fxt Exec_load() {
		site_cfg_mgr.Load(wiki);
		return this;
	}
	public void Test_extensiontags_y(String... ary) {Test_extensiontags(BoolUtl.Y, ary);}
	public void Test_extensiontags_n(String... ary) {Test_extensiontags(BoolUtl.N, ary);}
	public void Test_extensiontags(boolean expd_exists, String... ary) {
		Btrie_slim_mgr trie = wiki.Mw_parser_mgr().Xnde_tag_regy().Get_trie(Xop_parser_tid_.Tid__defn);
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			String str = ary[i];
			byte[] bry = BryUtl.NewU8(str);
			boolean actl_exists = trie.Match_exact(bry, 0, bry.length) != null;
			GfoTstr.Eq(expd_exists, actl_exists, str);
		}
	}
	public void Test_inet_qarg(String expd) {
		Xoa_site_cfg_loader__inet loader__inet = (Xoa_site_cfg_loader__inet)site_cfg_mgr.Loader_ary()[2];
		String api_url = loader__inet.Api_url();
		GfoTstr.EqObj(expd, StringUtl.Mid(api_url, StringUtl.FindBwd(api_url, "=") + 1));
	}
	public String Make_api(byte[]... sections) {
		BryWtr bfr = wiki.Utl__bfr_mkr().GetB512();
		bfr.AddStrA7("{'query':");
		int len = sections.length;
		bfr.AddStrA7("{");
		for (int i = 0; i < len; ++i) {
			if (i != 0) bfr.AddStrA7(",");
			bfr.Add(sections[i]);
		}
		bfr.AddStrA7("}");
		bfr.AddStrA7("}");
		return printer.Print_by_bry(BryUtl.NewU8(Json_doc.Make_str_by_apos(bfr.ToStrAndRls()))).To_str();
	}
	public byte[] Make_api_interwikimap(String... ary) {
		BryWtr bfr = wiki.Utl__bfr_mkr().GetB512();
		bfr.AddStrA7("'interwikimap':");
		int len = ary.length;
		bfr.AddStrA7("[");
		for (int i = 0; i < len; i += 2) {
			if (i != 0) bfr.AddStrA7(",");
			bfr.AddStrA7("{'prefix':'"	+ ary[i] + "'");
			bfr.AddStrA7(",'url':'"	+ ary[i + 1] + "'");
			bfr.AddStrA7("}");
		}
		bfr.AddStrA7("]");
		return bfr.ToBryAndClear();
	}
	public byte[] Make_api_extensiontags(String... ary) {
		BryWtr bfr = wiki.Utl__bfr_mkr().GetB512();
		bfr.AddStrA7("'extensiontags':");
		int len = ary.length;
		bfr.AddStrA7("[");
		for (int i = 0; i < len; ++i) {
			if (i != 0) bfr.AddStrA7(",");
			bfr.AddStrA7("'<"	+ ary[i] + ">'");
		}
		bfr.AddStrA7("]");
		return bfr.ToBryAndClear();
	}
	public String Make_data(int loader_tid, int flds, String... ary) {
		BryWtr bfr = wiki.Utl__bfr_mkr().GetB512();
		if (loader_tid != Xoa_site_cfg_loader_.Tid__null)	// null when constructing data for fsys
			bfr.AddStrU8(Xoa_site_cfg_loader__db.Bld_meta(loader_tid)).AddByteNl();
		int len = ary.length;
		for (int i = 0; i < len; i += flds) {
			if (i != 0) bfr.AddByteNl();
			for (int j = 0; j < flds; ++j) {
				if (j != 0) bfr.AddBytePipe();
				bfr.AddStrU8(ary[i + j]);
			}
		}
		return bfr.ToStrAndRls();
	}
}
