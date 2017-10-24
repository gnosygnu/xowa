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
package gplx.xowa.addons.bldrs.files.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import gplx.dbs.*; import gplx.core.ios.*; import gplx.xowa.files.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*; import gplx.xowa.bldrs.sql_dumps.*;
import gplx.xowa.addons.bldrs.files.dbs.*;
public class Xobldr__image__create extends Xob_itm_dump_base implements Xob_cmd, Gfo_invk, Xosql_dump_cbk {
	private Xosql_dump_parser parser;
	private Db_conn conn = null; private Db_stmt stmt = null;
	private Xob_image_tbl tbl_image = new Xob_image_tbl();
	private byte[] cur_ttl, cur_media_type, cur_minor_mime, cur_timestamp; private int cur_size, cur_width, cur_height, cur_bits, cur_ext_id;
	private int commit_count = 10000;
	public Xobldr__image__create(Xob_bldr bldr, Xowe_wiki wiki) {
		this.parser = new Xosql_dump_parser(this, "img_name", "img_size", "img_width", "img_height", "img_bits", "img_media_type", "img_minor_mime", "img_timestamp");
		this.Cmd_ctor(bldr, wiki);
	}
	public Io_url Src_fil() {return src_fil;} public Xobldr__image__create Src_fil_(Io_url v) {src_fil = v; return this;} private Io_url src_fil;
	public Xosql_dump_parser Parser() {return parser;}
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {
		wiki.Init_assert();	// NOTE: must init wiki for db_mgr_as_sql
		Init_dump(BLDR_CMD_KEY);
		if (src_fil == null) {
			src_fil = Xob_page_wkr_cmd.Find_fil_by(wiki.Fsys_mgr().Root_dir(), "*-image.sql");
			if (src_fil == null) throw Err_.new_wo_type(".sql file not found in dir", "dir", wiki.Fsys_mgr().Root_dir());
		}
		parser.Src_fil_(src_fil);
		this.conn = Xob_db_file.New__wiki_image(wiki.Fsys_mgr().Root_dir()).Conn();
		conn.Txn_bgn("bldr__image");
		this.tbl_image = new Xob_image_tbl();
		tbl_image.Create_table(conn);
		this.stmt = tbl_image.Insert_stmt(conn);		
	}
	public void Cmd_run() {
		parser.Parse(bldr.Usr_dlg());
		tbl_image.Create_index(conn);
		conn.Txn_end();
	}
	public void On_fld_done(int fld_idx, byte[] src, int val_bgn, int val_end) {
		switch (fld_idx) {
			case Fld_img_name: 			cur_ttl			= Bry_.Mid(src, val_bgn, val_end); break;
			case Fld_img_size: 			cur_size		= Bry_.To_int_or(src, val_bgn, val_end, -1); break;
			case Fld_img_width:			cur_width		= Bry_.To_int_or(src, val_bgn, val_end, -1); break;
			case Fld_img_height:		cur_height		= Bry_.To_int_or(src, val_bgn, val_end, -1); break;
			case Fld_img_bits: 			cur_bits		= Bry_.To_int_or(src, val_bgn, val_end, -1); break;
			case Fld_img_media_type:	cur_media_type	= Bry_.Mid(src, val_bgn, val_end); break;
			case Fld_img_minor_mime:	cur_minor_mime	= Bry_.Mid(src, val_bgn, val_end); break;
			case Fld_img_timestamp:		cur_timestamp	= Bry_.Mid(src, val_bgn, val_end); break;
		}
	}
	public void On_row_done() {
		cur_ext_id = Calc_ext_id(show_issues ? app.Usr_dlg() : Gfo_usr_dlg_.Noop, cur_ttl, cur_media_type, cur_minor_mime, cur_width, cur_height);
		tbl_image.Insert(stmt, cur_ttl, cur_media_type, cur_minor_mime, cur_size, cur_width, cur_height, cur_bits, cur_ext_id, cur_timestamp);
		++commit_count;
		if ((commit_count % 10000) == 0) {
			usr_dlg.Prog_many("", "", "committing: count=~{0} last=~{1}", commit_count, String_.new_u8(cur_ttl));
			conn.Txn_sav();
		}
	}
	public void Cmd_end() {}
	public void Cmd_term() {}
	private boolean show_issues = true;
	private static final int Fld_img_name = 0, Fld_img_size = 1, Fld_img_width = 2, Fld_img_height = 3, Fld_img_bits = 4, Fld_img_media_type = 5, Fld_img_minor_mime = 6, Fld_img_timestamp = 7;
	// Fld_img_name = 0, Fld_img_size = 1, Fld_img_width = 2, Fld_img_height = 3, Fld_img_bits = 5, Fld_img_media_type = 6, Fld_img_minor_mime = 8, Fld_img_timestamp = 12;
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_src_fil_))			src_fil = m.ReadIoUrl("v");
		else if	(ctx.Match(k, Invk_show_issues_))		show_issues = m.ReadYn("v");
		else	return super.Invk(ctx, ikey, k, m);
		return this;
	}	private static final    String Invk_src_fil_ = "src_fil_", Invk_show_issues_ = "show_issues_";
	public static int Calc_ext_id(Gfo_usr_dlg usr_dlg, byte[] file, byte[] media_type, byte[] minor_mime, int w, int h) {
		Xof_ext file_ext = Xof_ext_.new_by_ttl_(file);			int file_ext_id = file_ext.Id();
		Xof_ext mime_ext = Xof_mime_minor_.ext_(minor_mime);	int mime_ext_id = mime_ext.Id();
		int media_type_id = Xof_media_type.Xto_byte(String_.new_u8(media_type));
		if (file_ext_id != mime_ext_id) {							// file_ext_id != mime_ext_id; EX: "A.png" actually has a minor_mime of "jpg"
			boolean update = false, notify = true;
			switch (file_ext_id) {
				case Xof_ext_.Id_jpg: case Xof_ext_.Id_jpeg:
					if (Int_.In(mime_ext_id, Xof_ext_.Id_jpg, Xof_ext_.Id_jpeg)) notify = false;	// skip: both jpg
					break;
				case Xof_ext_.Id_tif: case Xof_ext_.Id_tiff:
					if (Int_.In(mime_ext_id, Xof_ext_.Id_tif, Xof_ext_.Id_tiff)) notify = false;	// skip: both tif
					break;
				case Xof_ext_.Id_ogg: case Xof_ext_.Id_oga: case Xof_ext_.Id_ogv:
					if (Int_.In(mime_ext_id, Xof_ext_.Id_ogg, Xof_ext_.Id_oga, Xof_ext_.Id_ogv)) notify = false;	// skip: both tif
					break;
				case Xof_ext_.Id_png:
					if (Int_.In(mime_ext_id, Xof_ext_.Id_jpg, Xof_ext_.Id_jpeg))
						update = true;
					break;
			}
			if (update)
				file_ext_id = mime_ext_id;
			else {
				if (notify)
					usr_dlg.Log_many("", "", "image.ext_calc.mismatch_exts: file=~{0} mime=~{1}", String_.new_u8(file), String_.new_u8(minor_mime));			
			}
		}
		if (    file_ext_id		== Xof_ext_.Id_ogg			// file_ext is ".ogg"
			&&	media_type_id	== Xof_media_type.Tid_video	// media_type is "VIDEO"
			) {
			if (w > 0 && h > 0)								// some .ogg files are "VIDEO" but have 0 width, 0 height
				file_ext_id = Xof_ext_.Id_ogv;				// manually specify ogv
			else
				usr_dlg.Log_many("", "", "image.ext_calc.ogg_video_with_null_size: media_type=~{0} minor_mime=~{1} w=~{2} h=~{3} file=~{4}", String_.new_u8(media_type), String_.new_u8(minor_mime), w, h, String_.new_u8(file));
		}
		return file_ext_id;
	}

	public static final String BLDR_CMD_KEY = "wiki.image";
	public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final    Xob_cmd Prototype = new Xobldr__image__create(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xobldr__image__create(bldr, wiki);}
}
