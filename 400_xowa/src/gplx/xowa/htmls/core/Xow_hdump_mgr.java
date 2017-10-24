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
package gplx.xowa.htmls.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.core.ios.*;
import gplx.xowa.htmls.core.hzips.*;
import gplx.xowa.wikis.data.*;
public class Xow_hdump_mgr {
	private final    Xoh_page tmp_hpg = new Xoh_page(); private final    Bry_bfr tmp_bfr = Bry_bfr_.Reset(255);
	private final    Io_stream_zip_mgr zip_mgr = new Io_stream_zip_mgr();
	public Xow_hdump_mgr(Xow_wiki wiki) {
		this.save_mgr = new Xow_hdump_mgr__save(wiki, hzip_mgr, zip_mgr, tmp_hpg);
		this.load_mgr = new Xow_hdump_mgr__load(wiki, hzip_mgr, zip_mgr, tmp_hpg, tmp_bfr);
	}
	public Xow_hdump_mgr__save Save_mgr() {return save_mgr;} private Xow_hdump_mgr__save save_mgr;
	public Xow_hdump_mgr__load Load_mgr() {return load_mgr;} private Xow_hdump_mgr__load load_mgr;
	public Xoh_hzip_mgr Hzip_mgr() {return hzip_mgr;} private final    Xoh_hzip_mgr hzip_mgr = new Xoh_hzip_mgr();
	public void Init_by_db(Xow_wiki wiki) {
		byte dflt_zip_tid = gplx.core.ios.streams.Io_stream_tid_.Tid__raw;
		boolean dflt_hzip_enable = false;
		boolean mode_is_b256 = false;
		if (wiki.Data__core_mgr() != null) {	// TEST: handle null data mgr
			Xowd_core_db_props props = wiki.Data__core_mgr().Props();
			dflt_zip_tid = props.Zip_tid_html();
			dflt_hzip_enable = props.Hzip_enabled();
			// mode_is_b256 = true;
		}
		Init_by_db(dflt_zip_tid, dflt_hzip_enable, mode_is_b256);
		load_mgr.Init_by_wiki(wiki);
	}
	public void Init_by_db(byte dflt_zip_tid, boolean dflt_hzip_enable, boolean mode_is_b256) {
		int dflt_hzip_tid = dflt_hzip_enable ? Xoh_hzip_dict_.Hzip__v1 : Xoh_hzip_dict_.Hzip__none;
		save_mgr.Init_by_db(dflt_zip_tid, dflt_hzip_tid, Bool_.N);
	}
}
