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
package gplx.xowa.addons.apps.scripts.apis; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.scripts.*;
public abstract class Xoscript_doc_sect_base {
	protected final    Xoscript_doc doc;
	private final    Hash_adp_bry marker_hash = Hash_adp_bry.cs();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	public Xoscript_doc_sect_base(Xoscript_doc doc) {this.doc = doc;}
	private byte[] get_marker_by_pos(byte[] pos_bry) {
		return (byte[])marker_hash.Get_by_or_fail(pos_bry);
	}
	public void reg_marker(String marker_str, String... pos_ary) {
		int len = pos_ary.length;
		byte[] marker_bry = Bry_.new_u8(marker_str);
		for (int i = 0; i < len; ++i) {
			marker_hash.Add_if_dupe_use_nth(Bry_.new_u8(pos_ary[i]), marker_bry);
		}
	}
	public void add_html(String html)					{add_html(Pos__default, html);}
	public void add_html(String pos_str, String html)	{add_html(Pos__default, Bry_.new_u8(html));}
	public void add_html(String pos_str, byte[] html) {
		doc.html_by_marker(get_marker_by_pos(Bry_.new_u8(pos_str)), html);
	}
	public void add_tag(String pos_str, String tag_str, String body, Object... head_atrs) {
		// build tag.bgn; EX: '<tag k1="v1">'
		tmp_bfr.Add_byte(Byte_ascii.Angle_bgn);
		tmp_bfr.Add_str_u8(tag_str);
		int head_atrs_len = head_atrs.length;
		for (int i = 0; i < head_atrs_len; i += 2) {
			tmp_bfr.Add_byte_space();
			tmp_bfr.Add_obj_strict(head_atrs[i]);
			tmp_bfr.Add_byte_eq();
			tmp_bfr.Add_byte_quote();
			tmp_bfr.Add_obj_strict(head_atrs[i + 1]);
			tmp_bfr.Add_byte_quote();
		}
		tmp_bfr.Add_byte(Byte_ascii.Angle_end);

		// build tag.body; EX: 'some body'
		tmp_bfr.Add_str_u8(body);

		// build tag.end; EX: '</tag>\n'
		tmp_bfr.Add_byte(Byte_ascii.Angle_bgn).Add_byte(Byte_ascii.Slash);
		tmp_bfr.Add_str_u8(tag_str);
		tmp_bfr.Add_byte(Byte_ascii.Angle_end);
		tmp_bfr.Add_byte_nl();

		add_html(pos_str, tmp_bfr.To_bry_and_clear());
	}
	public void add_js_file(String file_str) {add_js_file(Pos__default, file_str);}
	public void add_js_file(String pos_str, String file_str) {			
		add_tag(pos_str, "script", Body__empty, "src", Xoscript_env.Resolve_file(Bool_.Y, doc.page().env().Root_dir(), file_str), "type", "text/javascript");
	}
	public void add_js_code(String code_str) {add_js_file(Pos__default, code_str);}
	public void add_js_code(String pos_str, String code_str) {			
		add_tag(pos_str, "script", code_str, "type", "text/javascript");
	}
	public void add_css_file(String file_str) {add_js_file(Pos__default, file_str);}
	public void add_css_file(String pos_str, String file_str) {			
		add_tag(pos_str, "link", Body__empty, "rel", "stylesheet", "href", Xoscript_env.Resolve_file(Bool_.Y, doc.page().env().Root_dir(), file_str), "type", "text/css");
	}
	public void add_css_code(String code_str) {add_css_code(Pos__default, code_str);}
	public void add_css_code(String pos_str, String code_str) {	
		add_tag(pos_str, "style", code_str, "type", "text/css");
	}
	public static final String Pos__default = "", Body__empty = "";
}
