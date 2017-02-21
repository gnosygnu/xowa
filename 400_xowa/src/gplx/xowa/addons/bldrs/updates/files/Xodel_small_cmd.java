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
package gplx.xowa.addons.bldrs.updates.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.updates.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;	
import gplx.xowa.files.*;
public class Xodel_small_cmd extends Xob_cmd__base {
	public Xodel_small_cmd(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	private final    int[] ext_max_ary = Xobldr__fsdb_db__delete_small_files_.New_ext_max_ary();
	@Override public void Cmd_run() {
		wiki.Init_assert();
		new Xodel_small_mgr().Exec(wiki, ext_max_ary);
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return this;}

	public static final String BLDR_CMD_KEY = "file.deletion_db.small_files";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;}
	public static final    Xob_cmd Prototype = new Xodel_small_cmd(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xodel_small_cmd(bldr, wiki);}
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
