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
package gplx.xowa.addons.apps.cfgs.specials.edits.objs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.specials.*; import gplx.xowa.addons.apps.cfgs.specials.edits.*;
import gplx.xowa.addons.apps.cfgs.enums.*;
import gplx.xowa.addons.apps.cfgs.mgrs.types.*;
public class Xoedit_itm_html {
	public void Build_html(Bry_bfr bfr, Xocfg_type_mgr type_mgr, String key, String name, String data_type, String gui_type_key, String gui_args, String data) {
		switch (Xoitm_gui_tid.To_uid(gui_type_key)) {
			case Xoitm_gui_tid.Tid__bool:
				bfr.Add_str_u8_fmt("<input id=\"{1}\" data-xocfg-key=\"{1}\" data-xocfg-gui=\"{0}\" accesskey=\"d\" class=\"xocfg__bool\" type=\"checkbox\"{2}></input>", gui_type_key, key, String_.Eq(data, "y") ? " checked=\"checked\"" : "");
				break;
			case Xoitm_gui_tid.Tid__int:
				bfr.Add_str_u8_fmt("<input id=\"{1}\" data-xocfg-key=\"{1}\" data-xocfg-gui=\"{0}\" accesskey=\"d\" class=\"xocfg__int\" type=\"text\" value=\"{2}\"></input>", gui_type_key, key, data);
				break;
			case Xoitm_gui_tid.Tid__str:
				bfr.Add_str_u8_fmt("<input id=\"{1}\" data-xocfg-key=\"{1}\" data-xocfg-gui=\"{0}\" accesskey=\"d\" class=\"xocfg__str\" type=\"text\" value=\"{2}\"></input>", gui_type_key, key, data);
				break;
			case Xoitm_gui_tid.Tid__memo:
				bfr.Add_str_u8_fmt("<textarea id=\"{1}\" data-xocfg-key=\"{1}\" data-xocfg-gui=\"{0}\" accesskey=\"d\" class=\"xocfg__memo\" rows=\"4\">{2}</textarea>", gui_type_key, key, String_.Replace(data, "<", "&lt;"));
				break;
			case Xoitm_gui_tid.Tid__list:
				Keyval[] kvs_ary = type_mgr.Lists__get(data_type);
				int len = kvs_ary.length;
				bfr.Add_str_u8_fmt("<select id=\"{1}\" data-xocfg-key=\"{1}\" data-xocfg-gui=\"{0}\" accesskey=\"d\" class=\"xocfg__list\" type=\"text\" size=\"{2}\">\n", gui_type_key, key, len);
				for (int i = 0; i < len; i++) {
					Keyval kv = kvs_ary[i];
					String kv_key = kv.Key();
					String kv_val = kv.Val_to_str_or_null();
					bfr.Add_str_u8_fmt("<option value=\"{0}\"{2}>{1}</option>\n", kv_key, kv_val, String_.Eq(data, kv_key) ? " selected=\"selected\"" : "");
				}
				bfr.Add_str_u8_fmt("</select>\n");
				break;
			case Xoitm_gui_tid.Tid__io_cmd:
				String[] lines = Xocfg_mgr.Parse_io_cmd(data);
				bfr.Add_str_u8_fmt
				( "<input  class=\"xocfg__io_cmd__exe__txt\" id=\"{1}-exe\" data-xocfg-key=\"{1}\" data-xocfg-gui=\"{0}-exe\" accesskey=\"d\"  type=\"text\" value=\"{2}\"></input>\n"
				+ "<button class=\"xocfg__io_cmd__exe__btn\" onclick='xowa_io_select(\"file\", \"{1}-exe\", \"Please select a file.\");'>...</button><br/>\n"
				, gui_type_key, key, lines[0]);
				bfr.Add_str_u8_fmt
				( "<input  class=\"xocfg__io_cmd__arg__txt\" id=\"{1}-arg\" data-xocfg-key=\"{1}\" data-xocfg-gui=\"{0}-arg\" accesskey=\"d\" type=\"text\" value='{2}'>\n"
				, gui_type_key, key, lines[1]);
				break;
			default:
				break;
		}
	}
}
