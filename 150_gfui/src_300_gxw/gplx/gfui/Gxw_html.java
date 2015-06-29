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
public interface Gxw_html extends GxwElem {
	void		Html_doc_html_load_by_mem(String html);
	void		Html_doc_html_load_by_url(String path, String html);
	byte		Html_doc_html_load_tid(); void Html_doc_html_load_tid_(byte v);
	void		Html_js_enabled_(boolean v);
	String		Html_js_eval_proc_as_str	(String name, Object... args);
	boolean		Html_js_eval_proc_as_bool	(String name, Object... args);
	String		Html_js_eval_script			(String script);
	void		Html_js_cbks_add			(String js_func_name, GfoInvkAble invk);
	void		Html_invk_src_(GfoEvObj v);
	void		Html_dispose();
}
