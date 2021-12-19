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
package gplx.xowa.addons.apps.cfgs.specials.edits.objs;
import gplx.types.basics.utls.BryLni;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.KeyVal;
import gplx.xowa.addons.apps.cfgs.*;
import gplx.langs.htmls.*;
import gplx.xowa.guis.bnds.*;
import gplx.xowa.addons.apps.cfgs.enums.*; import gplx.xowa.addons.apps.cfgs.mgrs.types.*;
public class Xoedit_itm_html {
	public static void Build_html(BryWtr bfr, Xocfg_type_mgr type_mgr, String key, String name, String type, String html_atrs, String html_cls, byte[] val) {
		// prepend space for html insertion; EX: "type='checkbox'{1}>" with "a=b" -> "type='checkbox' a='b'" x> "type='checkbox'a='b'"
		if (StringUtl.IsNotNullOrEmpty(html_atrs)) html_atrs = " " + html_atrs;
		if (StringUtl.IsNotNullOrEmpty(html_cls))	html_cls  = " " + html_cls;

		// build html
		bfr.AddByteNl();
		switch (Xoitm_type_enum.To_uid(type)) {
			case Xoitm_type_enum.Tid__bool:
				bfr.AddStrU8Fmt
				( "<input id=\"{3}\" data-xocfg-key=\"{3}\" data-xocfg-type=\"{0}\" accesskey=\"d\" class=\"xocfg_data__bool{2}\" type=\"checkbox\"{1}{4}></input>"
				, type, html_atrs, html_cls, key, BryLni.Eq(val, BoolUtl.YBry) ? " checked=\"checked\"" : "");
				break;
			case Xoitm_type_enum.Tid__int:
				bfr.AddStrU8Fmt
				( "<input id=\"{3}\" data-xocfg-key=\"{3}\" data-xocfg-type=\"{0}\" accesskey=\"d\" class=\"xocfg_data__int{2}\" type=\"text\"{1} value=\"{4}\"></input>"
				, type, html_atrs, html_cls, key, val);
				break;
			case Xoitm_type_enum.Tid__str:
				bfr.AddStrU8Fmt
				( "<input id=\"{3}\" data-xocfg-key=\"{3}\" data-xocfg-type=\"{0}\" accesskey=\"d\" class=\"xocfg_data__str{2}\" type=\"text\"{1} value=\"{4}\"></input>"
				, type, html_atrs, html_cls, key, val);
				break;
			case Xoitm_type_enum.Tid__memo:
				if (StringUtl.IsNullOrEmpty(html_atrs)) html_atrs = " rows=\"4\"";
				bfr.AddStrU8Fmt
				( "<textarea id=\"{3}\" data-xocfg-key=\"{3}\" data-xocfg-type=\"{0}\" accesskey=\"d\" class=\"xocfg_data__memo{2}\"{1}>{4}</textarea>"
				, type, html_atrs, html_cls, key, Gfh_utl.Escape_html_as_bry(val));
				break;
			case Xoitm_type_enum.Tid__list:
				// get list of kvs by type
				KeyVal[] kvs_ary = type_mgr.Lists__get(type);
				int len = kvs_ary.length;
				if (StringUtl.IsNullOrEmpty(html_atrs)) html_atrs = "size=\"" + IntUtl.ToStr(len) + "\"";

				// build html
				bfr.AddStrU8Fmt
				( "<select id=\"{3}\" data-xocfg-key=\"{3}\" data-xocfg-type=\"{0}\" accesskey=\"d\" class=\"xocfg_data__list{2}\" {1}>\n"
				, type, html_atrs, html_cls, key);
				String val_str = StringUtl.NewU8(val);
				for (int i = 0; i < len; i++) {
					KeyVal kv = kvs_ary[i];
					String kv_key = kv.KeyToStr();
					String kv_val = kv.ValToStrOrNull();
					bfr.AddStrU8Fmt
					( "<option value=\"{0}\"{2}>{1}</option>\n"
					, kv_key, kv_val, StringUtl.Eq(val_str, kv_key) ? " selected=\"selected\"" : "");
				}
				bfr.AddStrU8Fmt("</select>\n");
				break;
			case Xoitm_type_enum.Tid__io_cmd:
				String[] lines = Xocfg_mgr.Parse_io_cmd(StringUtl.NewU8(val));
				bfr.AddStrU8Fmt
				( "<input  class=\"xocfg_data__io_cmd__exe__txt{2}\" id=\"{3}-exe\" data-xocfg-key=\"{3}\" data-xocfg-type=\"{0}-exe\" accesskey=\"d\" type=\"text\"{1} value=\"{4}\"></input>\n"
				+ "<span class=\"xoimg_btn_x16 xoimg_app_configure\" onclick='xo.cfg_edit.io_cmd__select(\"file\", \"{3}-exe\", \"Please select a file.\");'>&nbsp;</span><br/>\n"
				, type, html_atrs, html_cls, key, lines[0]);
				bfr.AddStrU8Fmt
				( "<input  class=\"xocfg_data__io_cmd__arg__txt{2}\" id=\"{3}-arg\" data-xocfg-key=\"{3}\" data-xocfg-type=\"{0}-arg\" accesskey=\"d\" type=\"text\"{1} value=\"{4}\">\n"
				, type, html_atrs, html_cls, key, lines[1]);
				break;
			case Xoitm_type_enum.Tid__gui_binding: {
				String[] flds = Xoitm_gui_binding.To_gui(StringUtl.NewU8(val));

				// write box
				bfr.AddStrU8Fmt
				( "<select class=\"xocfg_data__gui_binding__box\" id=\"{3}-box\" data-xocfg-key=\"{3}\" data-xocfg-type=\"{0}-box\" size=\"1\" accesskey=\"d\"{1}>\n"
				, type, html_atrs, html_cls, key, flds[0]);
				Xog_bnd_box[] box_ary = Xog_bnd_box_.Ary();
				int box_len = box_ary.length;
				String selected_box = flds[0];
				for (int i = 0; i < box_len; i++) {
					Xog_bnd_box kv = box_ary[i];
					String kv_key = kv.Key();
					String kv_val = Xog_bnd_box_.To_gui_str(kv_key);
					bfr.AddStrU8Fmt
					( "<option value=\"{0}\"{2}>{1}</option>\n"
					, kv_key, kv_val, StringUtl.Eq(selected_box, kv_val) ? " selected=\"selected\"" : "");
				}
				bfr.AddStrU8Fmt("</select>\n");

				// write ipt
				bfr.AddStrU8Fmt
				( "<input class=\"xocfg_data__gui_binding__ipt\" id=\"{3}-ipt\" data-xocfg-key=\"{3}\" data-xocfg-type=\"{0}-ipt\" accesskey=\"d\" type=\"text\"{1} value=\"{4}\"></input>\n"
				, type, html_atrs, html_cls, key, flds[1]);
				bfr.AddStrU8Fmt
				( "<span class=\"xoimg_btn_x16 xoimg_app_configure\" onclick='xo.cfg_edit.gui_binding__remap_send(\"{3}\", \"{4}\");'>&nbsp;</span>\n"
				, type, html_atrs, html_cls, key, name);
				break;
			}
			case Xoitm_type_enum.Tid__btn:
				bfr.AddStrU8Fmt
				( "<button id=\"{3}\" data-xocfg-key=\"{3}\" data-xocfg-type=\"{0}\" class=\"xocfg_data__btn{2}\" {1}>{4}</button>"
				, type, html_atrs, html_cls, key, name);
				break;
			default:
				break;
		}
	}
}
