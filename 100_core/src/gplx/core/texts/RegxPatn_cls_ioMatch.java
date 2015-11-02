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
public class RegxPatn_cls_ioMatch {
	public String Raw() {return raw;} private String raw;
	public boolean CaseSensitive() {return caseSensitive;} private boolean caseSensitive;
	public boolean Matches(String text) {
		text = String_.CaseNormalize(caseSensitive, text);
		return Regx_adp_.Match(text, compiled);}	// WNT-centric: Io_mgr paths are case-insensitive;
	@Override public String toString() {return raw;}

	String compiled;
	@gplx.Internal protected RegxPatn_cls_ioMatch(String raw, String compiled, boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
		this.raw = raw;
		compiled = String_.CaseNormalize(caseSensitive, compiled);
		this.compiled = compiled;
	}
}
