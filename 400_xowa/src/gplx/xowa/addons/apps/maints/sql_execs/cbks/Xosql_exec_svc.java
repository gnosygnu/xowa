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
package gplx.xowa.addons.apps.maints.sql_execs.cbks;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.wtrs.HtmlBryBfr;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.libs.files.Io_url_;
import gplx.xowa.*;
import gplx.langs.jsons.*;
import gplx.dbs.*; import gplx.xowa.specials.xowa.diags.*;
/*	TODO.XO
	* add backup function to backup db
	* option to output to file
	* run multiple SQL statements (need to change jdbc)
	* print column headers if SELECT
	* print row counts
	* run stored procs? EXEC debug_image 'A.png'
*/
class Xosql_exec_svc {
	private gplx.xowa.guis.cbks.Xog_cbk_trg cbk_trg = gplx.xowa.guis.cbks.Xog_cbk_trg.New_by_page(gplx.xowa.addons.apps.maints.sql_execs.specials.Xosql_exec_special.Prototype.Special__meta().Ttl_bry());
	private final Xoa_app app;
	public Xosql_exec_svc(Xoa_app app) {
		this.app = app;
	}
	public void Exec(Json_nde args) {
		String domain = args.Get_as_str("domain");
		String db = args.Get_as_str("db");
		String sql = args.Get_as_str("sql");

		// get conn
		Db_conn conn = null;
		if (StringUtl.Eq(domain, DOMAIN__APP)) {
			if (StringUtl.Eq(db, DB__USER)) {
				conn = app.User().User_db_mgr().Conn();
			}
		}
		else if (StringUtl.Eq(domain, DOMAIN__FILE)) {
			conn = Db_conn_bldr.Instance.Get_or_fail(Io_url_.new_any_(db));
		}
		else {
			Xow_wiki wiki = StringUtl.Eq(domain, DOMAIN__HOME)
				? wiki = app.User().Wikii()
				: app.Wiki_mgri().Get_by_or_make_init_y(BryUtl.NewU8(domain));
			
			// for now, only support core
			if (StringUtl.Eq(db, DB__CORE)) {
				conn = wiki.Data__core_mgr().Db__core().Conn();
			}
		}

		String results = null;
		try {
			// run sql
			// HACK: assume SQL starting with SELECT is a reader
			if (StringUtl.HasAtBgn(sql, "SELECT ")) {
				// run select
				BryWtr bfr = BryWtr.New();
				Object[][] rows = Db_rdr_utl.Load(conn, sql);
				Db_rdr_utl.Write_to_bfr(bfr, rows);
				results = bfr.ToStrAndClear();
			}
			else {
				conn.Exec_sql(sql);
				results = "executed";
			}
		} catch (Exception exc) {
			results = ErrUtl.ToStrFull(exc);
			results = StringUtl.Replace(results, "\t", "&nbsp;&nbsp;&nbsp;&nbsp;"); // tabs must be escaped, else "bad character in String literal"
		}

		// send results
		app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.sql_exec.results__recv", gplx.core.gfobjs.Gfobj_nde.New()
			.Add_bry("msg_text", HtmlBryBfr.EscapeHtml(BryUtl.NewU8(results))));	// escape html; EX: "<" -> "&lt;"
	}
	private static final String 
	  DOMAIN__APP  = "[xowa.app]"
	, DOMAIN__HOME = "[xowa.home]"
	, DOMAIN__FILE = "[file]"
	, DB__CORE = "core"
	, DB__USER = "user"
	;
}
