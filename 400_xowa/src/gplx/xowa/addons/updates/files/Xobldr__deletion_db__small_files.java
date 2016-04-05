/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.addons.updates.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.updates.*;
import gplx.dbs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;	
import gplx.fsdb.*; import gplx.fsdb.meta.*; import gplx.xowa.files.*;
public class Xobldr__deletion_db__small_files extends Xob_cmd__base {
	public Xobldr__deletion_db__small_files(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	private final    int[] ext_max_ary = Xobldr__fsdb_db__delete_small_files_.New_ext_max_ary();
	@Override public void Cmd_run() {
		wiki.Init_assert();
		// get atr_conn
		Fsdb_db_mgr db_core_mgr = Fsdb_db_mgr_.new_detect(wiki, wiki.Fsys_mgr().Root_dir(), wiki.Fsys_mgr().File_dir());
		Fsdb_db_file atr_db = db_core_mgr.File__atr_file__at(Fsm_mnt_mgr.Mnt_idx_main);
		Db_conn atr_conn = atr_db.Conn();

		// get deletion_db
		Xob_db_file deletion_db = Xob_db_file.New__deletion_db(wiki);
		atr_conn.Env_db_attach("deletion_db", deletion_db.Conn());

		// insert into deletion_db if too small
		int len = ext_max_ary.length;
		for (int i = 0; i < len; ++i) {
			Find_small_files(atr_conn, i, ext_max_ary[i]);
		}

		atr_conn.Env_db_detach("deletion_db");
	}
	private static void Find_small_files(Db_conn conn, int ext_id, int max) {
		String ext_name = String_.new_u8(Xof_ext_.Get_ext_by_id_(ext_id));
		String reason = "small:" + ext_name;
		conn.Exec_sql_concat_w_msg
		( String_.Format("finding small files; ext={0} max={1}", ext_name, max)
		, "INSERT  INTO deletion_db.delete_regy (fil_id, thm_id, reason)"
		, "SELECT  t.thm_owner_id, t.thm_id, '" + reason + "'"
		, "FROM    fsdb_thm t"
		, "        JOIN fsdb_fil f ON t.thm_owner_id = f.fil_id"
		, "WHERE   f.fil_ext_id = " + Int_.To_str(ext_id)
		, "AND     t.thm_size <= " + Int_.To_str(max)
		);
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return this;}

	public static final String BLDR_CMD_KEY = "file.deletion_db.small_files";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;}
	public static final    Xob_cmd Prototype = new Xobldr__deletion_db__small_files(null, null);
	@Override public Xob_cmd Cmd_new(Xob_bldr bldr, Xowe_wiki wiki) {return new Xobldr__deletion_db__small_files(bldr, wiki);}
}
class Xobldr__fsdb_db__delete_small_files_ {
	public static int[] New_ext_max_ary() {
		int[] rv = new int[Xof_ext_.Id__max];
		Ext_max_(rv,   35, Xof_ext_.Id_svg);
		Ext_max_(rv,   40, Xof_ext_.Id_gif);
		Ext_max_(rv,  100, Xof_ext_.Id_png, Xof_ext_.Id_jpg, Xof_ext_.Id_jpeg);
		Ext_max_(rv,  500, Xof_ext_.Id_tif, Xof_ext_.Id_tiff);
		Ext_max_(rv,  500, Xof_ext_.Id_xcf);
		Ext_max_(rv, 1000, Xof_ext_.Id_bmp);
		Ext_max_(rv,  700, Xof_ext_.Id_webm);
		Ext_max_(rv, 1000, Xof_ext_.Id_ogv);
		Ext_max_(rv,  400, Xof_ext_.Id_pdf);
		Ext_max_(rv,  700, Xof_ext_.Id_djvu);
		return rv;
	}
	private static void Ext_max_(int[] ary, int max, int... exts) {for (int ext : exts) ary[ext] = max;}
}
