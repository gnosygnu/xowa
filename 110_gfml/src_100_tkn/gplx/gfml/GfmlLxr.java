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
package gplx.gfml; import gplx.*;
import gplx.core.texts.*; /*CharStream*/
public interface GfmlLxr extends GfoEvObj {
	String Key();
	String[] Hooks();
	GfmlTkn CmdTkn();
	void CmdTkn_set(GfmlTkn val); // needed for lxr pragma
	GfmlTkn MakeTkn(CharStream stream, int hookLength);
	GfmlLxr SubLxr();
	void SubLxr_Add(GfmlLxr... lexer);
}
class GfmlLxrRegy {
	public int Count() {return hash.Count();}
	public void Add(GfmlLxr lxr) {hash.Add(lxr.Key(), lxr);}
	public GfmlLxr Get_by(String key) {return (GfmlLxr)hash.Get_by(key);}
	Hash_adp hash = Hash_adp_.new_();
}
