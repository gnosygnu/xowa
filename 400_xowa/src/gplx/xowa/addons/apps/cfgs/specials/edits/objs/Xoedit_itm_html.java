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
	public static void Build_html(Bry_bfr bfr, Xocfg_type_mgr type_mgr, String key, String name, String type, String html_atrs, String html_cls, byte[] val) {
		// prepend space for html insertion; EX: "type='checkbox'{1}>" with "a=b" -> "type='checkbox' a='b'" x> "type='checkbox'a='b'"
		if (String_.Len_gt_0(html_atrs)) html_atrs = " " + html_atrs;
		if (String_.Len_gt_0(html_cls))	html_cls  = " " + html_cls;

		// build html
		switch (Xoitm_type_enum.To_uid(type)) {
			case Xoitm_type_enum.Tid__bool:
				bfr.Add_str_u8_fmt
				( "<input id=\"{3}\" data-xocfg-key=\"{3}\" data-xocfg-type=\"{0}\" accesskey=\"d\" class=\"xocfg__bool{2}\" type=\"checkbox\"{1}{4}></input>"
				, type, html_atrs, html_cls, key, Bry_.Eq(val, Bool_.Y_bry) ? " checked=\"checked\"" : "");
				break;
			case Xoitm_type_enum.Tid__int:
				bfr.Add_str_u8_fmt
				( "<input id=\"{3}\" data-xocfg-key=\"{3}\" data-xocfg-type=\"{0}\" accesskey=\"d\" class=\"xocfg__int{2}\" type=\"text\"{1} value=\"{4}\"></input>"
				, type, html_atrs, html_cls, key, val);
				break;
			case Xoitm_type_enum.Tid__str:
				bfr.Add_str_u8_fmt
				( "<input id=\"{3}\" data-xocfg-key=\"{3}\" data-xocfg-type=\"{0}\" accesskey=\"d\" class=\"xocfg__str{2}\" type=\"text\"{1} value=\"{4}\"></input>"
				, type, html_atrs, html_cls, key, val);
				break;
			case Xoitm_type_enum.Tid__memo:
				if (String_.Len_eq_0(html_atrs)) html_atrs = " rows=\"4\"";
				bfr.Add_str_u8_fmt
				( "<textarea id=\"{3}\" data-xocfg-key=\"{3}\" data-xocfg-type=\"{0}\" accesskey=\"d\" class=\"xocfg__memo{2}\"{1}>{4}</textarea>"
				, type, html_atrs, html_cls, key, val);
				break;
			case Xoitm_type_enum.Tid__list:
				// get list of kvs by type
				Keyval[] kvs_ary = type_mgr.Lists__get(type);
				int len = kvs_ary.length;
				if (String_.Len_eq_0(html_atrs)) html_atrs = "size=\"" + Int_.To_str(len) + "\"";

				// build html
				bfr.Add_str_u8_fmt
				( "<select id=\"{3}\" data-xocfg-key=\"{3}\" data-xocfg-type=\"{0}\" accesskey=\"d\" class=\"xocfg__list{2}\" {1}>\n"
				, type, html_atrs, html_cls, key);
				String val_str = String_.new_u8(val);
				for (int i = 0; i < len; i++) {
					Keyval kv = kvs_ary[i];
					String kv_key = kv.Key();
					String kv_val = kv.Val_to_str_or_null();
					bfr.Add_str_u8_fmt
					( "<option value=\"{0}\"{2}>{1}</option>\n"
					, kv_key, kv_val, String_.Eq(val_str, kv_key) ? " selected=\"selected\"" : "");
				}
				bfr.Add_str_u8_fmt("</select>\n");
				break;
			case Xoitm_type_enum.Tid__io_cmd:
				String[] lines = Xocfg_mgr.Parse_io_cmd(String_.new_u8(val));
				bfr.Add_str_u8_fmt
				( "<input  class=\"xocfg__io_cmd__exe__txt\" id=\"{3}-exe\" data-xocfg-key=\"{3}\" data-xocfg-type=\"{0}-exe\" accesskey=\"d\" class=\"xocfg__io_cmd__exe__txt{2}\" type=\"text\"{1} value=\"{4}\"></input>\n"
				+ "<button class=\"xocfg__io_cmd__exe__btn\" onclick='xo.cfg_edit.io_cmd__select(\"file\", \"{3}-exe\", \"Please select a file.\");'>...</button><br/>\n"
				, type, html_atrs, html_cls, key, lines[0]);
				bfr.Add_str_u8_fmt
				( "<input  class=\"xocfg__io_cmd__arg__txt\" id=\"{3}-arg\" data-xocfg-key=\"{3}\" data-xocfg-type=\"{0}-arg\" accesskey=\"d\" class=\"xocfg__io_cmd__arg__txt{2}\" type=\"text\"{1} value=\"{4}\">\n"
				, type, html_atrs, html_cls, key, lines[1]);
				break;
			case Xoitm_type_enum.Tid__gui_binding:
				String[] flds = Xoitm_gui_binding.To_gui(String_.new_u8(val));
				bfr.Add_str_u8_fmt
				( "<input  class=\"xocfg__gui_binding__box__txt\" id=\"{3}-box\" data-xocfg-key=\"{3}\" data-xocfg-type=\"{0}-box\" accesskey=\"d\" class=\"{2}\" type=\"text\"{1} value=\"{4}\"></input>\n"
				, type, html_atrs, html_cls, key, flds[0]);
				bfr.Add_str_u8_fmt
				( "<input  class=\"xocfg__gui_binding__ipt__txt\" id=\"{3}-ipt\" data-xocfg-key=\"{3}\" data-xocfg-type=\"{0}-ipt\" accesskey=\"d\" class=\"{2}\" type=\"text\"{1} value=\"{4}\"'>\n"
				, type, html_atrs, html_cls, key, flds[1]);
				break;
			case Xoitm_type_enum.Tid__btn:
				bfr.Add_str_u8_fmt
				( "<button id=\"{3}\" data-xocfg-key=\"{3}\" data-xocfg-type=\"{0}\" class=\"xocfg__btn{2}\" {1}>{4}</button>"
				, type, html_atrs, html_cls, key, name);
				break;
			default:
				break;
		}
	}
}
