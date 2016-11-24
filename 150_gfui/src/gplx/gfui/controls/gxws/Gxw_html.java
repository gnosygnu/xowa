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
package gplx.gfui.controls.gxws; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
public interface Gxw_html extends GxwElem {
	void		Html_doc_html_load_by_mem(String html);
	void		Html_doc_html_load_by_url(Io_url path, String html);
	byte		Html_doc_html_load_tid(); void Html_doc_html_load_tid_(byte v);
	void		Html_js_enabled_(boolean v);
	String		Html_js_eval_proc_as_str	(String name, Object... args);
	boolean		Html_js_eval_proc_as_bool	(String name, Object... args);
	String		Html_js_eval_script			(String script);
	Object		Html_js_eval_script_as_obj	(String script);
	void		Html_js_cbks_add			(String js_func_name, Gfo_invk invk);
	String		Html_js_send_json			(String name, String data);
	void		Html_invk_src_(Gfo_evt_itm v);
	void		Html_dispose();
}
