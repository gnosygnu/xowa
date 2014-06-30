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
package gplx.gfui; import gplx.*;
import org.junit.*;
public class Gfui_html_cfg_tst {
	private Gfui_html_cfg_fxt fxt = new Gfui_html_cfg_fxt();
	@Before public void init() {fxt.Reset();}
	@Test  public void Html_window_vpos_parse() {
		fxt.Test_Html_window_vpos_parse("0|0,1,2", "0", "'0','1','2'");
		fxt.Test_Html_window_vpos_parse("org.eclipse.swt.SWTException: Permission denied for <file://> to get property Selection.rangeCount", null, null);	// check that invalid path doesn't fail; DATE:2014-04-05
	}
}
class Gfui_html_cfg_fxt {
	private String_obj_ref scroll_top = String_obj_ref.null_(), node_path = String_obj_ref.null_();
	public void Reset() {}
	public void Test_Html_window_vpos_parse(String raw, String expd_scroll_top, String expd_node_path) {
		scroll_top.Val_null_(); node_path.Val_null_();
		Gfui_html_cfg.Html_window_vpos_parse(raw, scroll_top, node_path);
		Tfds.Eq(expd_scroll_top, scroll_top.Val(), expd_scroll_top);
		Tfds.Eq(expd_node_path, node_path.Val(), expd_node_path);
	}
}
