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
package gplx.xowa.bldrs.sqls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
public class Sql_file_parser_data {
	public boolean Cancel_row() {return cancel_row;}
	public Sql_file_parser_data Cancel_row_n_() {cancel_row = false; return this;} 
	public Sql_file_parser_data Cancel_row_y_() {cancel_row = true; return this;} private boolean cancel_row;
}