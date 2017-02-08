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
package gplx.xowa.mediawiki.includes.parsers.prepros; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
public abstract class Xomw_prepro_node__base implements Xomw_prepro_node {
	private List_adp subs;
	public int Subs__len() {return subs == null ? 0 : subs.Len();}
	public Xomw_prepro_node Subs__get_at(int i) {return subs == null ? null : (Xomw_prepro_node)subs.Get_at(i);}
	public void Subs__add(Xomw_prepro_node sub) {
		if (subs == null) subs = List_adp_.New();
		subs.Add(sub);
	}
	public abstract void To_xml(Bry_bfr bfr);
}
