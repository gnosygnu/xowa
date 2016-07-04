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
package gplx.xowa.parsers2; import gplx.*; import gplx.xowa.*;
class Xop_parser_mgr {
//		public Xop_parser_env Envs__get_or_new() {
//			return null;
//		}
	public Xop_parser_wkr Wkrs__get_or_new() {
		return null;
	}
}
interface Xop_parser_env {
}
interface Xop_parser_wkr {
	void Parse_to_bfr(Xop_parser_env env, Bry_bfr bfr, byte[] src);
}
