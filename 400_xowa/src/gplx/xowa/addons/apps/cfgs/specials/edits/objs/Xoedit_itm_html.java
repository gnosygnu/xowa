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
import gplx.xowa.addons.apps.cfgs.types.*;
public class Xoedit_itm_html {
	public void Build_html(Bry_bfr bfr, Xocfg_type_mgr type_mgr, String key, String name, String data_type, int gui_type, String gui_args, String data) {
		switch (gui_type) {
			case Xoitm_gui_tid.Tid__checkbox:
				bfr.Add_str_u8_fmt("<input id=\"{0}\" data-xocfg=\"0\" type=\"checkbox\" accesskey=\"d\" class=\"xocfg_checkbox\"{1}></input>", key, String_.Eq(data, "y") ? " checked=\"checked\"" : "");
				break;
			case Xoitm_gui_tid.Tid__numeric:
				bfr.Add_str_u8_fmt("<input id=\"{0}\" data-xocfg=\"0\" type=\"text\" accesskey=\"d\" class=\"xocfg_numeric\" value=\"{1}\"></input>", key, data);
				break;
			case Xoitm_gui_tid.Tid__textbox:
				bfr.Add_str_u8_fmt("<input id=\"{0}\" data-xocfg=\"0\" type=\"text\" accesskey=\"d\" class=\"xocfg_textbox\" value=\"{1}\"></input>", key, data);
				break;
			case Xoitm_gui_tid.Tid__memo:
				bfr.Add_str_u8_fmt("<textarea id=\"{0}\" data-xocfg=\"0\" type=\"text\" accesskey=\"d\" class=\"xocfg_memo\">{1}</textarea>", key, data);
				break;
			case Xoitm_gui_tid.Tid__select:
				Keyval[] kvs_ary = type_mgr.Lists__get(data_type);
				int len = kvs_ary.length;
				bfr.Add_str_u8_fmt("<select id=\"{0}\" data-xocfg=\"0\" type=\"text\" accesskey=\"d\" class=\"xocfg_select\" size=\"{1}\">", key, len);
				for (int i = 0; i < len; i++) {
					Keyval kv = kvs_ary[i];
					String kv_val = kv.Val_to_str_or_null();
					bfr.Add_str_u8_fmt("<option value=\"{0}\"{2}>{1}</option>", kv.Key(), kv_val, String_.Eq(data, kv_val) ? " selected=\"selected\"" : "");
				}
				bfr.Add_str_u8_fmt("</select>");
				break;
			case Xoitm_gui_tid.Tid__fs_file:
				bfr.Add_str_u8_fmt("<input id=\"{0}\" data-xocfg=\"0\" type=\"text\" accesskey=\"d\" class=\"xocfg_fs_file\" value=\"{1}\"></input>", key, data);
				break;
			default:
				break;
		}
	}
}
