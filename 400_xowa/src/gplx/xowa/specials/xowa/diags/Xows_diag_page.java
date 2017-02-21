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
package gplx.xowa.specials.xowa.diags; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*; import gplx.xowa.specials.xowa.*;
import gplx.core.primitives.*; import gplx.core.net.*; import gplx.core.net.qargs.*;
import gplx.xowa.apps.urls.*;
public class Xows_diag_page implements Xow_special_page {
	private Gfo_qarg_mgr_old arg_hash = new Gfo_qarg_mgr_old();
	public Xow_special_meta Special__meta() {return Xow_special_meta_.Itm__diag;}
	public void Special__gen(Xow_wiki wikii, Xoa_page pagei, Xoa_url url, Xoa_ttl ttl) {
		Xowe_wiki wiki = (Xowe_wiki)wikii; Xoae_page page = (Xoae_page)pagei;
		arg_hash.Load(url.Qargs_ary());
		byte[] cmd_type_bry = arg_hash.Get_val_bry_or(Arg_type, null);					if (cmd_type_bry == null) {Xoa_app_.Usr_dlg().Warn_many("", "", "special.cmd; no type: url=~{0}", url.Raw()); return;}
		Byte_obj_val cmd_type_val = (Byte_obj_val)type_hash.Get_by_bry(cmd_type_bry);	if (cmd_type_val == null) {Xoa_app_.Usr_dlg().Warn_many("", "", "special.cmd; bad type: url=~{0}", url.Raw()); return;}
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_m001();
		bfr.Add_str_a7("<pre>\n");
		switch (cmd_type_val.Val()) {
			case Type_file_check:	Xows_cmd__file_check.Instance.Exec(bfr, wiki.App(), url, arg_hash); break;
			case Type_fs_check:		Xows_cmd__fs_check.Instance.Exec(bfr, wiki.App(), url, arg_hash); break;
			case Type_sql_dump:		Xows_cmd__sql_dump.Instance.Exec(bfr, wiki.App(), url, arg_hash); break;
		}
		bfr.Add_str_a7("</pre>\n");
		page.Db().Text().Text_bry_(bfr.To_bry_and_clear());
	}
	private static final    byte[] Arg_type = Bry_.new_a7("type");
	private static final byte Type_file_check = 1, Type_fs_check = 2, Type_sql_dump = 3;
	private static final    Hash_adp_bry type_hash = Hash_adp_bry.cs()
	.Add_str_byte("file.check"		, Type_file_check)
	.Add_str_byte("fs.check"		, Type_fs_check)
	.Add_str_byte("sql.dump"		, Type_sql_dump)
	;

	public Xow_special_page Special__clone() {return this;}
}
