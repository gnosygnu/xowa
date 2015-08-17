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
import gplx.core.strings.*;
public class GfmlFld {
	public String Name() {return name;} private String name;
	public boolean Name_isKey() {return name_isKey;} private boolean name_isKey;
	public String TypeKey() {return typeKey;} private String typeKey;
	public GfmlObj DefaultTkn() {return defaultTkn;} public GfmlFld DefaultTkn_(GfmlObj val) {defaultTkn = val; return this;} GfmlObj defaultTkn = GfmlTkn_.Null;
	public GfmlFld Clone() {
		GfmlFld rv = new GfmlFld();
		rv.name = name; rv.name_isKey = name_isKey; rv.typeKey = typeKey;
		rv.defaultTkn = defaultTkn;	// FIXME: defaultTkn.clone_()
		return rv;
	}
	public String To_str() {String_bldr sb = String_bldr_.new_(); this.To_str(sb); return sb.Xto_str_and_clear();}
	public void To_str(String_bldr sb) {sb.Add_fmt("name={0} typeKey={1}", name, typeKey);}

	public static final GfmlFld Null = new_(false, GfmlItmKeys.NullKey, GfmlType_.AnyKey);
	public static GfmlFld new_(boolean name_isKey, String name, String typeKey) {
		GfmlFld rv = new GfmlFld();
		rv.name_isKey = name_isKey; rv.name = name; rv.typeKey = typeKey;
		return rv;
	}	GfmlFld() {}
}
