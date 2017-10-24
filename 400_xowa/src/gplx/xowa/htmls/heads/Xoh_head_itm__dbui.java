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
package gplx.xowa.htmls.heads; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.xowa.guis.*;
public class Xoh_head_itm__dbui extends Xoh_head_itm__base {
	@Override public byte[] Key() {return Xoh_head_itm_.Key__dbui;}
	@Override public int Flags() {return Flag__css_text | Flag__css_include | Flag__js_tail_script | Flag__js_window_onload;}
	public Xoh_head_itm__dbui Init(Xoa_app app) {
		if (dir_url == null) {
			Io_url url_html_res = app.Fsys_mgr().Bin_any_dir().GenSubDir_nest("xowa", "html", "res");
			dir_url				= url_html_res.GenSubDir_nest("src", "xowa", "dbui");
			url_dbui_js			= dir_url.GenSubFil("dbui-0.1.1.js").To_http_file_bry();
			url_jquery_ui_js	= url_html_res.GenSubFil_nest("lib", "jquery-ui", "jquery-ui-1.11.4.js").To_http_file_bry();
			url_jquery_ui_css	= url_html_res.GenSubFil_nest("lib", "jquery-ui", "jquery-ui-1.11.4.css").To_http_file_bry();
			url_notify_js		= url_html_res.GenSubFil_nest("lib", "notifyjs"	, "notifyjs-0.3.1.js").To_http_file_bry();
		}
		return this;
	}
	@Override public void Write_css_include(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		wtr.Write_css_include(url_jquery_ui_css);
	}
	@Override public void Write_css_text(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		wtr.Write_css_style_ary(Css__table_as_div);
	}
	@Override public void Write_js_tail_script(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		wtr.Write_js_tail_load_lib(url_dbui_js);
		wtr.Write_js_tail_load_lib(url_notify_js);
		wtr.Write_js_tail_load_lib(url_jquery_ui_js);
	}
	@Override public void Write_js_window_onload(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		wtr.Write_js_lines(Js__sortable);
	}
	public static Io_url Img_dir() {return dir_url.GenSubDir("img");} private static Io_url dir_url;
	private static byte[] url_dbui_js, url_notify_js, url_jquery_ui_js, url_jquery_ui_css;
	private static final byte[][] 
	  Css__table_as_div = Bry_.Ary(String_.Ary
	( ".xo_tbl {"
	, "  display: table;"
	, "  table-layout: fixed;"	// don't auto-size to text content
	, "  width: 100%;"
	, "}"
	, ".xo_row {"
	, "  display: table-row;"
	, "}"
	, ".xo_head,.xo_origin {"
	, "  display: table-cell;"
	, "  padding: 3px 10px;"
	, "  border: 1px solid #999999;"
	, "  border-right: none;"
	, "  font-weight:bold;"
	, "}"
	, ".xo_origin {"
	, "  padding:0px;"			// else box is larger than should be
	, "}"
	, ".xo_cell {"
	, "  display: table-cell;"
	, "  padding: 3px 10px;"
	, "  vertical-align: top;"	// else <textarea> will cause <input> to align at bottom
	, "  border: 1px solid #999999;"
	, "  border-top: none;"
	, "  border-left: none;"
	, "  border-right: none;"
	, "  white-space: nowrap;"	
	, "  overflow: hidden;"
	, "  text-overflow: ellipsis;"
	, "}"
	, ".xo_head:nth-child(1),.xo_cell:nth-child(1),.xo_origin {"
	, "  border-left: 1px solid #999999;"
	, "}"
	, ".xo_head:nth-last-child(1),.xo_cell:nth-last-child(1) {"
	, "  border-right: 1px solid #999999;"
	, "}"
	, ".xo_header {"
	, "  display: table-header-group;"
	, "  background-color: #ddd;"
	, "  font-weight: bold;"
	, "}"
	, ".xo_footer {"
	, "  display: table-footer-group;"
	, "  font-weight: bold;"
	, "  background-color: #ddd;"
	, "}"
	, ".xo_tbody {"
	, "  display: table-row-group;"
	, "}"
	, ".xo_drag_handle {"
	, "  background-color: #ddd;"
	, "  cursor: move;"
	, "}"    
	))
	, Js__sortable = Bry_.Ary(String_.Ary
	( "  $('.xo_sortable').sortable({"
	, "    revert: 100,"			// 100 for "fast" snap back
	, "    items: '.xo_draggable',"	// to prevent header from being dragged
	, "    handle: '.xo_drag_handle',"
	, "    cursor: 'move',"
	, "    stop: function(event, ui) {"
	, "      var ids = $(this).sortable('toArray');"
	, "      rows__reorder(ids);"
	, "    },"
	, "  });"
	, "  $('.xo_resizable_col').resizable({"
	, "    handles: 'e',"
	, "    cancel : 'textarea',"
	, "  });"
	))
	;		
}
