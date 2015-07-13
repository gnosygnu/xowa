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
package gplx.texts; import gplx.*;
public class RegxAdp_mpo_find {
	public String Input() {return input;} public RegxAdp_mpo_find Input_(String val) {input = val; return this;} private String input;
	public String Find() {return find;} public RegxAdp_mpo_find Find_(String val) {find = val; return this;} private String find;
	public List_adp Exec_asList() {
		RegxAdp regx = RegxAdp_.new_(find);
		int idx = 0;
		List_adp rv = List_adp_.new_();
		while (true)  {
			RegxMatch match = regx.Match(input, idx);
			if (match.Rslt_none()) break;
			rv.Add(match);
			int findBgn = match.Find_bgn();
			idx = findBgn + match.Find_len();
			if (idx > String_.Len(input)) break;
		}
		return rv;
	}
}
