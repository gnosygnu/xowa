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
package gplx.langs.phps; import gplx.*; import gplx.langs.*;
class Php_tkn_factory {
	public Php_tkn_generic 				Generic(int bgn, int end, byte tid) {return new Php_tkn_generic(bgn, end, tid);}
	public Php_tkn_txt					Txt(int bgn, int end) {return new Php_tkn_txt(bgn, end);}
	public Php_tkn 						Declaration(int bgn, int end) {return Php_tkn_declaration._;}
	public Php_tkn_ws 					Ws(int bgn, int end, byte ws_tid) {return new Php_tkn_ws(bgn, end, ws_tid);}
	public Php_tkn_var 					Var(int bgn, int end) {return new Php_tkn_var(bgn, end);}
	public Php_tkn_num 					Num(int bgn, int end) {return new Php_tkn_num(bgn, end);}
	public Php_tkn_comment 				Comment(int bgn, int end, byte comment_tid) {return new Php_tkn_comment(bgn, end, comment_tid);}
	public Php_tkn_quote 				Quote(int bgn, int end, byte quote_tid) {return new Php_tkn_quote(bgn, end, quote_tid);}
}
