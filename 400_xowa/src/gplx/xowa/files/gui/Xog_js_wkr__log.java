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
package gplx.xowa.files.gui; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
public class Xog_js_wkr__log implements Xog_js_wkr {
	private final ListAdp log_list = ListAdp_.new_();
	public void Html_img_update			(String uid, String src, int w, int h)	{log_list.Add(Object_.Ary(Proc_img_update, uid, src, w, h));}
	public void Html_atr_set			(String uid, String key, String val)	{log_list.Add(Object_.Ary(Proc_atr_set, uid, key, val));}
	public void Html_redlink			(String uid)							{log_list.Add(Object_.Ary(Proc_redlink, uid));}
	public void Html_elem_replace_html	(String uid, String html)				{log_list.Add(Object_.Ary(Proc_replace_html, uid, html));}
	public void Html_elem_append_above	(String uid, String html)				{log_list.Add(Object_.Ary(Proc_append_above, uid, html));}
	public boolean Html_doc_loaded			()										{log_list.Add(Object_.Ary(Proc_doc_loaded)); return true;}

	public void Log__clear()			{log_list.Clear();}
	public int Log__len()				{return log_list.Count();}
	public Object[] Log__get_at(int i)	{return (Object[])log_list.FetchAt(i);}

	public static final String Proc_img_update = "img_update", Proc_atr_set = "atr_set", Proc_redlink = "redlink", Proc_replace_html = "replace_html", Proc_append_above = "append_above", Proc_doc_loaded = "doc_loaded";
}
