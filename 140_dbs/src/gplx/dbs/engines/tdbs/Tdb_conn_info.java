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
package gplx.dbs.engines.tdbs; import gplx.dbs.*;
import gplx.libs.files.Io_url;
import gplx.libs.files.Io_url_;
import gplx.types.commons.KeyVal;
import gplx.types.commons.KeyValHash;
public class Tdb_conn_info extends Db_conn_info__base {
	public Tdb_conn_info(String raw, String db_api, String database, Io_url url) {super(raw, db_api, database);this.url = url; this.server = url.Raw(); }
	@Override public String Key() {return Tid_const;} public static final String Tid_const = "tdb";
	public Io_url Url() {return url;} private final Io_url url;
	public String Server() {return server;} private final String server;
	@Override public Db_conn_info New_self(String raw, KeyValHash hash) {
		Io_url url = Io_url_.new_any_(hash.GetByValAsStrOrFail("url"));
		String db = url.NameOnly();
		String api = Bld_api(hash, KeyVal.NewStr("version", "3"));
		return new Tdb_conn_info(raw, api, db, url);
	}
	public static Db_conn_info new_(Io_url url) {
		return Db_conn_info_.parse(Bld_raw
		( "gplx_key", Tid_const
		, "url", url.Raw()
		));
	}
        public static final Tdb_conn_info Instance = new Tdb_conn_info("", "", "", Io_url_.Empty);
}
