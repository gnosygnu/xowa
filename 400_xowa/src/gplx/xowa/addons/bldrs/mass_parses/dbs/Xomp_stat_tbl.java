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
package gplx.xowa.addons.bldrs.mass_parses.dbs;
import gplx.frameworks.objects.Rls_able;
import gplx.xowa.*;
import gplx.dbs.*;
import gplx.xowa.htmls.*;
import gplx.xowa.parsers.logs.stats.*;
public class Xomp_stat_tbl implements Rls_able {
	private static final String tbl_name = "xomp_stats"; private static final DbmetaFldList flds = new DbmetaFldList();
	private static final String
	  fld_page_id = flds.AddIntPkey("page_id"), fld_wkr_uid = flds.AddInt("wkr_uid")
	, fld_wtxt_len = flds.AddInt("wtxt_len"), fld_html_len = flds.AddInt("html_len"), fld_zip_len = flds.AddInt("zip_len")
	, fld_page_time = flds.AddLong("page_time"), fld_tidy_time = flds.AddLong("tidy_time"), fld_fulltext_time = flds.AddLong("fulltext_time")
	, fld_scrib_time = flds.AddLong("scrib_time"), fld_scrib_count = flds.AddInt("scrib_count"), fld_scrib_depth = flds.AddInt("scrib_depth")
	, fld_image_count = flds.AddInt("image_count"), fld_audio_count = flds.AddInt("audio_count"), fld_video_count = flds.AddInt("video_count"), fld_media_count = flds.AddInt("media_count")
	, fld_lnki_count = flds.AddInt("lnki_count"), fld_lnke_count = flds.AddInt("lnke_count"), fld_hdr_count = flds.AddInt("hdr_count")
	, fld_math_count = flds.AddInt("math_count"), fld_imap_count = flds.AddInt("imap_count"), fld_hiero_count = flds.AddInt("hiero_count")
	, fld_gallery_count = flds.AddInt("gallery_count"), fld_gallery_packed_count = flds.AddInt("gallery_packed_count")
	;		
	private final Db_conn conn; private Db_stmt stmt_insert;
	public Xomp_stat_tbl(Db_conn conn) {
		this.conn = conn;
		this.Create_tbl();
		conn.Rls_reg(this);
	}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds, Dbmeta_idx_itm.new_unique_by_tbl(tbl_name, "pkey", fld_page_id)));}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}
	public void Stmt_new() {
		stmt_insert = conn.Stmt_insert(tbl_name, flds);
	}
	public void Insert_by_copy(Db_rdr rdr) {
		stmt_insert.Clear()
			.Val_int (fld_page_id               , rdr.Read_int(fld_page_id))
			.Val_int (fld_wkr_uid               , rdr.Read_int(fld_wkr_uid))
			.Val_int (fld_wtxt_len              , rdr.Read_int(fld_wtxt_len))
			.Val_int (fld_html_len              , rdr.Read_int(fld_html_len))
			.Val_int (fld_zip_len               , rdr.Read_int(fld_zip_len))
			.Val_long(fld_page_time             , rdr.Read_long(fld_page_time))
			.Val_long(fld_tidy_time             , rdr.Read_long(fld_tidy_time))
			.Val_long(fld_fulltext_time         , rdr.Read_long(fld_fulltext_time))
			.Val_long(fld_scrib_time            , rdr.Read_long(fld_scrib_time))
			.Val_int (fld_scrib_count           , rdr.Read_int (fld_scrib_count))
			.Val_int (fld_scrib_depth           , rdr.Read_int (fld_scrib_depth))
			.Val_int (fld_image_count           , rdr.Read_int (fld_image_count))
			.Val_int (fld_audio_count           , rdr.Read_int (fld_audio_count))
			.Val_int (fld_video_count           , rdr.Read_int (fld_video_count))
			.Val_int (fld_media_count           , rdr.Read_int (fld_media_count))
			.Val_int (fld_lnki_count            , rdr.Read_int (fld_lnki_count))
			.Val_int (fld_lnke_count            , rdr.Read_int (fld_lnke_count))
			.Val_int (fld_hdr_count             , rdr.Read_int (fld_hdr_count))
			.Val_int (fld_math_count            , rdr.Read_int (fld_math_count))
			.Val_int (fld_imap_count            , rdr.Read_int (fld_imap_count))
			.Val_int (fld_hiero_count           , rdr.Read_int (fld_hiero_count))
			.Val_int (fld_gallery_count         , rdr.Read_int (fld_gallery_count))
			.Val_int (fld_gallery_packed_count  , rdr.Read_int (fld_gallery_packed_count))
		.Exec_insert();
	}
	public void Insert(Xoae_page wpg, Xoh_page hpg, int wkr_uid, long page_time, long fulltext_time) {
		Xop_log_stat stat = wpg.Stat_itm();
		stmt_insert.Clear()
			.Val_int(fld_page_id                , hpg.Page_id())
			.Val_int(fld_wkr_uid                , wkr_uid)
			.Val_int(fld_wtxt_len               , Len_or_0(wpg.Root().Root_src()))
			.Val_int(fld_html_len               , Len_or_0(hpg.Db().Html().Html_bry()))
			.Val_int(fld_zip_len                , hpg.Db().Html().Zip_len())
			.Val_long(fld_page_time             , page_time)
			.Val_long(fld_tidy_time             , stat.Tidy_time)
			.Val_long(fld_fulltext_time         , fulltext_time)
			.Val_long(fld_scrib_time            , stat.Scrib().Time())
			.Val_int (fld_scrib_count           , stat.Scrib().Count())
			.Val_int (fld_scrib_depth           , stat.Scrib().Depth_max())
			.Val_int (fld_image_count           , stat.Image_count)
			.Val_int (fld_audio_count           , stat.Audio_count)
			.Val_int (fld_video_count           , stat.Video_count)
			.Val_int (fld_media_count           , stat.Media_count)
			.Val_int (fld_lnki_count            , stat.Lnki_count)
			.Val_int (fld_lnke_count            , stat.Lnke_count)
			.Val_int (fld_hdr_count             , stat.Hdr_count)
			.Val_int (fld_math_count            , stat.Math_count)
			.Val_int (fld_imap_count            , stat.Imap_count)
			.Val_int (fld_hiero_count           , stat.Hiero_count)
			.Val_int (fld_gallery_count         , stat.Gallery_count)
			.Val_int (fld_gallery_packed_count  , stat.Gallery_packed_count)
		.Exec_insert();
	}
	public void Stmt_rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}
	private static int Len_or_0(byte[] bry) {return bry == null ? 0 : bry.length;}
}
