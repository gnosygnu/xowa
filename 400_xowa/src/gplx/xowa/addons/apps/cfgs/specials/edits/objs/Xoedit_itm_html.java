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
	public void Build_html(Bry_bfr bfr, Xocfg_type_mgr type_mgr, String key, String name, String data_type, String gui_type_key, String gui_args, String val) {
		// if gui_args exists, prepend space for html insertion; EX: "type='checkbox'{1}>" with "a=b" -> "type='checkbox' a='b'" x> "type='checkbox'a='b'"
		if (String_.Len_gt_0(gui_args)) gui_args = " " + gui_args;
		switch (Xoitm_gui_tid.To_uid(gui_type_key)) {
			case Xoitm_gui_tid.Tid__bool:
				bfr.Add_str_u8_fmt("<input id=\"{2}\" data-xocfg-key=\"{2}\" data-xocfg-gui=\"{0}\" accesskey=\"d\" class=\"xocfg__bool\" type=\"checkbox\"{1}{3}></input>", gui_type_key, gui_args, key, String_.Eq(val, "y") ? " checked=\"checked\"" : "");
				/*
				String span_args = "";
				if (String_.Has(gui_args, "disabled=\"disabled\""))
					span_args = " class=\"xocfg__bool__disabled\"";
				bfr.Add_str_u8_fmt("<label><input id=\"{2}\" data-xocfg-key=\"{2}\" data-xocfg-gui=\"{0}\" accesskey=\"d\" class=\"xocfg__bool\" type=\"checkbox\"{1}{3}></input><span{4}></span></label>", gui_type_key, gui_args, key, String_.Eq(val, "y") ? " checked=\"checked\"" : "", span_args);
				*/
				break;
			case Xoitm_gui_tid.Tid__int:
				bfr.Add_str_u8_fmt("<input id=\"{2}\" data-xocfg-key=\"{2}\" data-xocfg-gui=\"{0}\" accesskey=\"d\" class=\"xocfg__int\" type=\"text\"{1} value=\"{3}\"></input>", gui_type_key, gui_args, key, val);
				break;
			case Xoitm_gui_tid.Tid__str:
				bfr.Add_str_u8_fmt("<input id=\"{2}\" data-xocfg-key=\"{2}\" data-xocfg-gui=\"{0}\" accesskey=\"d\" class=\"xocfg__str\" type=\"text\"{1} value=\"{3}\"></input>", gui_type_key, gui_args, key, val);
				break;
			case Xoitm_gui_tid.Tid__memo:
				if (String_.Len_eq_0(gui_args)) gui_args = " rows=\"4\"";
				bfr.Add_str_u8_fmt("<textarea id=\"{2}\" data-xocfg-key=\"{2}\" data-xocfg-gui=\"{0}\" accesskey=\"d\" class=\"xocfg__memo\"{1}>{3}</textarea>", gui_type_key, gui_args, key, String_.Replace(val, "<", "&lt;"));
				break;
			case Xoitm_gui_tid.Tid__list:
				// get list of kvs by type
				Keyval[] kvs_ary = type_mgr.Lists__get(data_type);
				int len = kvs_ary.length;
				if (String_.Len_eq_0(gui_args)) gui_args = "size=\"" + Int_.To_str(len) + "\"";

				// build html
				bfr.Add_str_u8_fmt("<select id=\"{2}\" data-xocfg-key=\"{2}\" data-xocfg-gui=\"{0}\" accesskey=\"d\" class=\"xocfg__list\" {1}>\n", gui_type_key, gui_args, key);
				for (int i = 0; i < len; i++) {
					Keyval kv = kvs_ary[i];
					String kv_key = kv.Key();
					String kv_val = kv.Val_to_str_or_null();
					bfr.Add_str_u8_fmt("<option value=\"{0}\"{2}>{1}</option>\n", kv_key, kv_val, String_.Eq(val, kv_key) ? " selected=\"selected\"" : "");
				}
				bfr.Add_str_u8_fmt("</select>\n");
				break;
			case Xoitm_gui_tid.Tid__io_cmd:
				String[] lines = Xocfg_mgr.Parse_io_cmd(val);
				bfr.Add_str_u8_fmt
				( "<input  class=\"xocfg__io_cmd__exe__txt\" id=\"{2}-exe\" data-xocfg-key=\"{2}\" data-xocfg-gui=\"{0}-exe\" accesskey=\"d\" type=\"text\"{1} value=\"{3}\"></input>\n"
				+ "<button class=\"xocfg__io_cmd__exe__btn\" onclick='xowa_io_select(\"file\", \"{2}-exe\", \"Please select a file.\");'>...</button><br/>\n"
				, gui_type_key, gui_args, key, lines[0]);
				bfr.Add_str_u8_fmt
				( "<input  class=\"xocfg__io_cmd__arg__txt\" id=\"{2}-arg\" data-xocfg-key=\"{2}\" data-xocfg-gui=\"{0}-arg\" accesskey=\"d\" type=\"text\"{1} value=\"{3}\">\n"
				, gui_type_key, gui_args, key, String_.Replace(lines[1], "\"", "&quot;"));
				break;
			case Xoitm_gui_tid.Tid__gui_binding:
				String[] flds = Xoitm_gui_binding.To_gui(val);
				bfr.Add_str_u8_fmt
				( "<input  class=\"xocfg__gui_binding__box__txt\" id=\"{2}-box\" data-xocfg-key=\"{2}\" data-xocfg-gui=\"{0}-box\" accesskey=\"d\" type=\"text\"{1} value=\"{3}\"></input>\n"
				, gui_type_key, gui_args, key, flds[0]);
				bfr.Add_str_u8_fmt
				( "<input  class=\"xocfg__gui_binding__ipt__txt\" id=\"{2}-ipt\" data-xocfg-key=\"{2}\" data-xocfg-gui=\"{0}-ipt\" accesskey=\"d\" type=\"text\"{1} value=\"{3}\"'>\n"
				, gui_type_key, gui_args, key, flds[1]);
				break;
			case Xoitm_gui_tid.Tid__btn:
				bfr.Add_str_u8_fmt("<button id=\"{2}\" data-xocfg-key=\"{2}\" data-xocfg-gui=\"{0}\" class=\"xocfg__btn\" {1}>{3}</button>", gui_type_key, gui_args, key, name);
				break;
			default:
				break;
		}
	}
}
