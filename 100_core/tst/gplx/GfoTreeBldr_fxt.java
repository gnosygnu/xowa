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
package gplx;
public class GfoTreeBldr_fxt {
	public List_adp Atrs() {return atrs;}  List_adp atrs = List_adp_.New();
	public List_adp Subs() {return subs;}  List_adp subs = List_adp_.New();
	public GfoTreeBldr_fxt atr_(Object key, Object val) {
		atrs.Add(new Object[] {key, val});
		return this;
	}
	public GfoTreeBldr_fxt sub_(GfoTreeBldr_fxt... ary) {
		for (GfoTreeBldr_fxt sub : ary)
			subs.Add(sub);
		return this;
	}
	public static GfoTreeBldr_fxt new_() {return new GfoTreeBldr_fxt();} GfoTreeBldr_fxt() {}
}
