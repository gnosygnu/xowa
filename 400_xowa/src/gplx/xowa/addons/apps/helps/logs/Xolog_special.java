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
package gplx.xowa.addons.apps.helps.logs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.helps.*;
import gplx.xowa.specials.*; import gplx.core.net.*; import gplx.core.net.qargs.*; import gplx.xowa.wikis.pages.*;
import gplx.core.net.emails.*;
public class Xolog_special implements Xow_special_page {
	public void Special__gen(Xow_wiki wiki, Xoa_page page, Xoa_url url, Xoa_ttl ttl) {
		// init
		Gfo_qarg_mgr url_args = new Gfo_qarg_mgr().Init(url.Qargs_ary());
		Xoa_app app = wiki.App();
		Gfo_log_.Instance.Flush();	// flush log to write all memory itms to disk

		// get file
		String file = url_args.Read_str_or_null("file");
		Io_url log_dir = app.Fsys_mgr().Root_dir().GenSubDir_nest("user", "anonymous", "app", "tmp", "xolog");
		Xolog_doc log_doc = Xolog_doc_.New(log_dir, file);
		if (log_doc.Log_file() == null) return;	// occurs when all logs are deleted

		// get cmd
		int cmd_tid = url_args.Read_enm_as_int_or(Enm_cmd.Itm, Enm_cmd.Tid__view);
		boolean redirect = true, redirect_to_same_file = false;;
		switch (cmd_tid) {
			case Enm_cmd.Tid__view:			redirect = false; break;
			case Enm_cmd.Tid__email:		redirect_to_same_file = true; Gfo_email_mgr_.Instance.Send("gnosygnu+xowa_xologs@gmail.com", "XOWA Log", String_.new_u8(log_doc.Log_data())); break;
			case Enm_cmd.Tid__delete_one:	Io_mgr.Instance.DeleteFil(Xolog_file_utl.To_url_by_file(log_dir, log_doc.Log_file())); break;
			case Enm_cmd.Tid__delete_all:	Io_mgr.Instance.DeleteDirDeep(log_dir); break;
		}

		if (redirect) {
			String redirect_ttl = special__meta.Ttl_str() + "?cmd=view";
			if (redirect_to_same_file && file != null)
				redirect_ttl += "&file=" + file;
			page.Redirect_to_ttl_(Bry_.new_u8(redirect_ttl));
			return;
		}
		else
			new Xolog_html(log_doc).Bld_page_by_mustache(app, page, this);
	}
	static class Enm_cmd {//#*nested
		public static final int Tid__view = 0, Tid__email = 1, Tid__delete_one = 2, Tid__delete_all = 3;
		public static final    Gfo_qarg_enum_itm Itm = new Gfo_qarg_enum_itm("cmd")
			.Add("view"			, Tid__view)
			.Add("email"		, Tid__email)
			.Add("delete_one"	, Tid__delete_one)
			.Add("delete_all"	, Tid__delete_all)
			;
	}
	Xolog_special(Xow_special_meta special__meta) {this.special__meta = special__meta;}
	public Xow_special_meta Special__meta()		{return special__meta;} private final    Xow_special_meta special__meta;
	public Xow_special_page Special__clone()	{return this;}
	public static final    Xow_special_page Prototype = new Xolog_special(Xow_special_meta.New_xo("XowaLog", "Logs"));
}
class Xolog_file_utl {// yyyyMMdd_HHmmss.log
	private static final String Gui__date_fmt = "yyyy-MM-dd HH:mm:ss";
	public static String To_name(Io_url url) {return To_name(url.NameOnly());}
	public static String To_name(String file) {
		DateAdp date = DateAdp_.parse_fmt(file, Gfo_log_.File__fmt);
		return date.XtoStr_fmt(Gui__date_fmt);
	}
	public static Io_url To_url_by_file(Io_url dir, String file) {
		return dir.GenSubFil(file + Gfo_log_.File__ext);
	}
}
