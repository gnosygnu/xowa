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
package gplx.xowa.parsers.mws; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.parsers.htmls.*;
import gplx.xowa.parsers.mws.utils.*;
import gplx.xowa.parsers.uniqs.*;
public class Xomw_parser_ctx {
	public Xomw_sanitizer_mgr Sanitizer() {return sanitizer;} private final    Xomw_sanitizer_mgr sanitizer = new Xomw_sanitizer_mgr();
	public Xop_uniq_mgr Uniq_mgr() {return uniq_mgr;} private final    Xop_uniq_mgr uniq_mgr = new Xop_uniq_mgr();
	
	public static final int Pos__bos = -1;
}
