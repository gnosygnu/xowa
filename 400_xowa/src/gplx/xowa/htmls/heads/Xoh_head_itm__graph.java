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
package gplx.xowa.htmls.heads; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.xowa.guis.*;
public class Xoh_head_itm__graph extends Xoh_head_itm__base {
	@Override public byte[] Key() {return Xoh_head_itm_.Key__graph;}
	@Override public int Flags() {return Flag__js_include | Flag__js_window_onload;}
	@Override public void Write_js_include(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		if (Url__ary == null) {
			Io_url lib_dir = app.Fsys_mgr().Bin_xtns_dir().GenSubDir_nest("Graph", "lib");
			Url__ary = new byte[][]
			{ app.Fsys_mgr().Bin_xowa_dir().GenSubFil_nest("html", "res", "lib", "jquery", "jquery-1.11.3.min.js").To_http_file_bry()
			, lib_dir.GenSubFil("d3.js").To_http_file_bry()
			, lib_dir.GenSubFil("d3.layout.cloud.js").To_http_file_bry()
			, lib_dir.GenSubFil("topojson.js").To_http_file_bry()
			, lib_dir.GenSubFil("vega.js").To_http_file_bry()
			, app.Fsys_mgr().Bin_xtns_dir().GenSubFil_nest("Graph", "js", "graph.js").To_http_file_bry()
			};
		}
		for (int i = 0; i < Url__ary_len; ++i)
			wtr.Write_js_include(Url__ary[i]);
	}
	@Override public void Write_js_window_onload(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		wtr.Write_js_line(Js__graph_exec);
	}
	private static final int Url__ary_len = 6;
	private static byte[][] Url__ary;
	private static final    byte[] Js__graph_exec = Bry_.new_a7("xtn__graph__exec();");
}
