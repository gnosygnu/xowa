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
package gplx.xowa.addons.apps.cfgs.enums; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*;
import gplx.gfui.*; import gplx.xowa.guis.bnds.*;
public class Xoitm_gui_binding {
	public static String[] To_gui(String val) {
		Gfui_bnd_parser parser = Gfui_bnd_parser.new_en_();
		String[] rv = new String[2];
		int pipe_pos = String_.FindFwd(val, "|");
		if (pipe_pos == -1) throw Err_.new_wo_type("cfg.binding:unknown gui binding; val=" + val);
		rv[0] = Xog_bnd_box_.To_gui_str(String_.Mid(val, 0, pipe_pos));
		rv[1] = parser.Xto_norm(String_.Mid(val, pipe_pos + 1, String_.Len(val)));
		return rv;
	}
	public static String To_db_str(String val) {
		Gfui_bnd_parser parser = Gfui_bnd_parser.new_en_();
		String[] rv = new String[2];
		int pipe_pos = String_.FindFwd(val, "|");
		if (pipe_pos == -1) throw Err_.new_wo_type("cfg.binding:unknown gui binding; val=" + val);
		rv[0] = Xog_bnd_box_.To_key_str(String_.Mid(val, 0, pipe_pos));
		rv[1] = parser.Xto_gfui(String_.Mid(val, pipe_pos + 1, String_.Len(val)));
		return rv[0] + "|" + rv[1];
	}
	public static String[] To_ary(String val) {
		String[] rv = new String[2];
		int pipe_pos = String_.FindFwd(val, "|");
		rv[0] = String_.Mid(val, 0, pipe_pos);
		rv[1] = String_.Mid(val, pipe_pos + 1, String_.Len(val));
		return rv;
	}
}
