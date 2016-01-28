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
package gplx.xowa.xtns.pagebanners; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.langs.mustaches.*;
public class Pgbnr_icon implements Mustache_doc_itm {
	public byte[] name;
	public byte[] title;
	public byte[] url;
	public Pgbnr_icon(byte[] name, byte[] title, byte[] url) {this.name = name; this.title = title; this.url = url;}
	public byte[] Get_prop(String key) {
		if		(String_.Eq(key, "name"))	return name;
		else if	(String_.Eq(key, "title"))	return title;
		else if	(String_.Eq(key, "url"))	return url;
		else if	(String_.Eq(key, "html"))	return Mustache_doc_itm_.Null_val;
		return Mustache_doc_itm_.Null_val;
	}
	public Mustache_doc_itm[] Get_subs(String key) {return Mustache_doc_itm_.Ary__empty;}
	public static final Pgbnr_icon[] Ary_empty = new Pgbnr_icon[0];
}
