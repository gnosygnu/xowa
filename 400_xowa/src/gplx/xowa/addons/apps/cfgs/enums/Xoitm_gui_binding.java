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
package gplx.xowa.addons.apps.cfgs.enums;
import gplx.gfui.Gfui_bnd_parser;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.guis.bnds.Xog_bnd_box_;
public class Xoitm_gui_binding {
	public static String[] To_gui(String val) {
		Gfui_bnd_parser parser = Gfui_bnd_parser.new_en_();
		String[] rv = new String[2];
		int pipe_pos = StringUtl.FindFwd(val, "|");
		if (pipe_pos == -1) throw ErrUtl.NewArgs("cfg.binding:unknown gui binding; val=" + val);
		rv[0] = Xog_bnd_box_.To_gui_str(StringUtl.Mid(val, 0, pipe_pos));
		rv[1] = parser.Xto_norm(StringUtl.Mid(val, pipe_pos + 1, StringUtl.Len(val)));
		return rv;
	}
	public static String To_db_str(String val) {
		Gfui_bnd_parser parser = Gfui_bnd_parser.new_en_();
		String[] rv = new String[2];
		int pipe_pos = StringUtl.FindFwd(val, "|");
		if (pipe_pos == -1) throw ErrUtl.NewArgs("cfg.binding:unknown gui binding; val=" + val);
		rv[0] = Xog_bnd_box_.To_key_str(StringUtl.Mid(val, 0, pipe_pos));
		rv[1] = parser.Xto_gfui(StringUtl.Mid(val, pipe_pos + 1, StringUtl.Len(val)));
		return rv[0] + "|" + rv[1];
	}
	public static String[] To_ary(String val) {
		String[] rv = new String[2];
		int pipe_pos = StringUtl.FindFwd(val, "|");
		rv[0] = StringUtl.Mid(val, 0, pipe_pos);
		rv[1] = StringUtl.Mid(val, pipe_pos + 1, StringUtl.Len(val));
		return rv;
	}
}
