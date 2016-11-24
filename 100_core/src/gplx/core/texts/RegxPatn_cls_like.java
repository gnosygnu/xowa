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
package gplx.core.texts; import gplx.*; import gplx.core.*;
import gplx.langs.regxs.*;
public class RegxPatn_cls_like {
	public char Escape() {return escape;} char escape; public static final char EscapeDefault = '|';
	public String Raw() {return raw;} private String raw;
	public boolean Matches(String text) {return Regx_adp_.Match(text, compiled);}
	@Override public String toString() {return String_.Format("LIKE {0} ESCAPE {1} -> {2}", raw, escape, compiled);}

	String compiled;
	@gplx.Internal protected RegxPatn_cls_like(String raw, String compiled, char escape) {
		this.raw = raw;
		this.compiled = compiled;
		this.escape = escape;
	}
}
