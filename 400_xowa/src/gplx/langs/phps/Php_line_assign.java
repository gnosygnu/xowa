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
public class Php_line_assign implements Php_line {
	public Php_key Key() {return key;} public Php_line_assign Key_(Php_key v) {this.key = v; return this;} Php_key key;
	public Php_key[] Key_subs() {return key_subs;} public Php_line_assign Key_subs_(Php_key[] v) {this.key_subs = v; return this;} Php_key[] key_subs = Php_key_.Ary_empty;
	public Php_itm Val() {return val;} public Php_line_assign Val_(Php_itm v) {this.val = v; return this;} Php_itm val;
}
