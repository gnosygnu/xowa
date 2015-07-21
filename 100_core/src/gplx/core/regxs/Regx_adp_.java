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
package gplx.core.regxs; import gplx.*; import gplx.core.*;
public class Regx_adp_ {
	public static Regx_adp new_(String pattern) {return new Regx_adp(pattern);}
	public static List_adp Find_all(String input, String find) {
		Regx_adp regx = Regx_adp_.new_(find);
		int idx = 0;
		List_adp rv = List_adp_.new_();
		while (true)  {
			Regx_match match = regx.Match(input, idx);
			if (match.Rslt_none()) break;
			rv.Add(match);
			int findBgn = match.Find_bgn();
			idx = findBgn + match.Find_len();
			if (idx > String_.Len(input)) break;
		}
		return rv;
	}
	public static String Replace(String raw, String regx_str, String replace) {
		Regx_adp regx = Regx_adp_.new_(regx_str);
		return regx.ReplaceAll(raw, replace);
	}
	public static boolean Match(String input, String pattern) {
		Regx_adp rv = new Regx_adp(pattern);
		return rv.Match(input, 0).Rslt();
	}
}
