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
package gplx.xowa.addons.wikis.pages.randoms.dbs;
import gplx.dbs.Db_conn;
import gplx.dbs.Db_conn_bldr;
import gplx.libs.files.Io_url;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
import gplx.xowa.Xow_wiki;
import gplx.xowa.wikis.data.Xow_db_layout;
public class Rndm_db_mgr {
	private final Xow_wiki wiki;
	public Rndm_db_mgr(Xow_wiki wiki) {
		this.wiki = wiki;
	}
	public Db_conn				Conn() {return conn;} private Db_conn conn;
	public Rndm_qry_tbl			Tbl__qry() {return tbl__qry;} private Rndm_qry_tbl tbl__qry; 
	public Rndm_rng_tbl			Tbl__rng() {return tbl__rng;} private Rndm_rng_tbl tbl__rng; 
	public Rndm_seq_tbl			Tbl__seq() {return tbl__seq;} private Rndm_seq_tbl tbl__seq; 
	public Rndm_db_mgr Init() {
		this.conn = Get_or_new(wiki);
		this.tbl__qry = new Rndm_qry_tbl(conn);
		this.tbl__rng = new Rndm_rng_tbl(conn);
		this.tbl__seq = new Rndm_seq_tbl(conn);
		if (!conn.Meta_tbl_exists(tbl__qry.Tbl_name())) {
			tbl__qry.Create_tbl();
			tbl__rng.Create_tbl();
			tbl__seq.Create_tbl();
		}
		return this;
	}

	private static Db_conn Get_or_new(Xow_wiki wiki) {
		int layout_text = wiki.Data__core_mgr().Db__core().Db_props().Layout_text().Tid();
		Io_url url = null;
		switch (layout_text) {
			case Xow_db_layout.Tid__all:	url = wiki.Data__core_mgr().Db__core().Url(); break;
			case Xow_db_layout.Tid__few:	url = wiki.Fsys_mgr().Root_dir().GenSubFil(StringUtl.Format("{0}-data.xowa", wiki.Domain_str())); break;
			case Xow_db_layout.Tid__lot:	url = wiki.Fsys_mgr().Root_dir().GenSubFil(StringUtl.Format("{0}-xtn.random.core.xowa", wiki.Domain_str())); break;
			default:						throw ErrUtl.NewUnhandled(layout_text);
		}
		return Db_conn_bldr.Instance.Get_or_autocreate(true, url);
	}
}
