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
package gplx.langs.mustaches; import gplx.*; import gplx.langs.*;
public class Mustache_doc_itm_ {
	public static final byte[] Null_val = null;
	public static final Mustache_doc_itm Null_itm = null;
	public static final Mustache_doc_itm Itm__bool__y = new Mustache_doc_itm__bool(Bool_.Y), Itm__bool__n = new Mustache_doc_itm__bool(Bool_.N);
	public static final Mustache_doc_itm[] Ary__empty = new Mustache_doc_itm[0], Ary__bool__y = new Mustache_doc_itm[] {Itm__bool__y}, Ary__bool__n = new Mustache_doc_itm[] {Itm__bool__n};
	public static Mustache_doc_itm[] Ary__bool(boolean v) {return v ? Ary__bool__y : Ary__bool__n;}
}
class Mustache_doc_itm__bool implements Mustache_doc_itm {
	public Mustache_doc_itm__bool(boolean val) {this.val = val;}
	public boolean Val() {return val;} private final boolean val;
	public byte[] Get_prop(String key) {return Mustache_doc_itm_.Null_val;}
	public Mustache_doc_itm[] Get_subs(String key) {return Mustache_doc_itm_.Ary__empty;}
}
