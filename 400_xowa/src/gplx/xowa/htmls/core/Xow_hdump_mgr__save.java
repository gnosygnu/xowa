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
import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.heads.*; import gplx.xowa.htmls.core.dbs.*;
import gplx.core.ios.*; import gplx.core.primitives.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.pages.*;
public class Xow_hdump_mgr__save {
	private final    Xow_wiki wiki; private final    Xoh_hzip_mgr hzip_mgr; private final    Io_stream_zip_mgr zip_mgr;
	private final    Xoh_page tmp_hpg; private final    Xoh_hzip_bfr tmp_bfr = Xoh_hzip_bfr.New_txt(32); private Bool_obj_ref html_db_is_new = Bool_obj_ref.n_();		
	private int dflt_zip_tid, dflt_hzip_tid;
	public Xow_hdump_mgr__save(Xow_wiki wiki, Xoh_hzip_mgr hzip_mgr, Io_stream_zip_mgr zip_mgr, Xoh_page tmp_hpg) {
		this.wiki = wiki; this.hzip_mgr = hzip_mgr; this.zip_mgr = zip_mgr; this.tmp_hpg = tmp_hpg;
	}
	public void Init_by_db(int dflt_zip_tid, int dflt_hzip_tid, boolean mode_is_b256) {
		this.dflt_zip_tid = dflt_zip_tid; this.dflt_hzip_tid = dflt_hzip_tid; tmp_bfr.Mode_is_b256_(mode_is_b256);
	}
	public byte[] Src_as_hzip() {return src_as_hzip;} private byte[] src_as_hzip;
	public int Save(Xoae_page page) {
		synchronized (tmp_hpg) {
			Bld_hdump(page);
			tmp_hpg.Ctor_by_hdiff(tmp_bfr, page, page.Wikie().Msg_mgr().Val_by_id(gplx.xowa.langs.msgs.Xol_msg_itm_.Id_toc));
			Xow_db_file html_db = Get_html_db(wiki, page, html_db_is_new.Val_n_());
			return Save(tmp_hpg, html_db.Tbl__html(), html_db_is_new.Val(), true);
		}
	}
	public int Save(Xoh_page hpg, Xowd_html_tbl html_tbl, boolean insert, boolean use_hzip_dflt) {
		int hzip_tid = use_hzip_dflt ? dflt_hzip_tid : Xoh_hzip_dict_.Hzip__none;
		byte[] db_body = Write(tmp_bfr, wiki, hpg, hzip_mgr, zip_mgr, dflt_zip_tid, hzip_tid, hpg.Db().Html().Html_bry());
		if (insert)		html_tbl.Insert(hpg, dflt_zip_tid, dflt_hzip_tid, db_body);
		else			html_tbl.Update(hpg, dflt_zip_tid, dflt_hzip_tid, db_body);
		return db_body.length;
	}
	public void Bld_hdump(Xoae_page page) {
		page.File_queue().Clear();																	// need to reset uid to 0, else xowa_file_# will keep incrementing upwards
		wiki.Html__wtr_mgr().Wkr(Xopg_page_.Tid_read).Write_body(tmp_bfr, page.Wikie().Parser_mgr().Ctx(), Xoh_wtr_ctx.Hdump, page); // save as hdump_fmt
		page.Db().Html().Html_bry_(tmp_bfr.To_bry_and_clear());
	}
	private byte[] Write(Xoh_hzip_bfr bfr, Xow_wiki wiki, Xoh_page hpg, Xoh_hzip_mgr hzip_mgr, Io_stream_zip_mgr zip_mgr, int zip_tid, int hzip_tid, byte[] src) {
		if (hzip_tid != Xoh_hzip_dict_.Hzip__none) src = hzip_mgr.Encode_as_bry((Xoh_hzip_bfr)bfr.Clear(), wiki, hpg, src);
		src_as_hzip = src;
		if (zip_tid > gplx.core.ios.streams.Io_stream_tid_.Tid__raw)
			src = zip_mgr.Zip((byte)zip_tid, src);
		return src;
	}
	private static Xow_db_file Get_html_db(Xow_wiki wiki, Xoae_page page, Bool_obj_ref html_db_is_new) {
		Xow_db_file rv = Xow_db_file.Null;
		Xow_db_mgr core_data_mgr = wiki.Data__core_mgr();
		int html_db_id = page.Db().Page().Html_db_id();
		if (html_db_id == -1) {
			html_db_is_new.Val_y_();
			rv = core_data_mgr.Db__html();
			if (rv == null) rv = core_data_mgr.Dbs__make_by_tid(Xow_db_file_.Tid__html_data);
			html_db_id = rv.Id();
			page.Db().Page().Html_db_id_(html_db_id);
			core_data_mgr.Tbl__page().Update__html_db_id(page.Db().Page().Id(), html_db_id);
		}
		else {
			rv = core_data_mgr.Dbs__get_by_id_or_fail(html_db_id);
		}
		return rv;
	}
}
