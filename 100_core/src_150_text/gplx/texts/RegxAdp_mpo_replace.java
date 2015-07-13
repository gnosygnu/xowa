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
public class RegxAdp_mpo_replace {
	public String Input() {return input;} public RegxAdp_mpo_replace Input_(String val) {input = val; return this;} private String input;
	public String Find() {return find;} public RegxAdp_mpo_replace Find_(String val) {find = val; return this;} private String find;
	public String Replace() {return replace;} public RegxAdp_mpo_replace Replace_(String val) {replace = val; return this;} private String replace;
	public String Exec_asStr() {
		RegxAdp regx = RegxAdp_.new_(find);
		return regx.ReplaceAll(input, replace);
	}
}
