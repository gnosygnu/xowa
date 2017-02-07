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
package gplx.dbs.stmts; import gplx.*; import gplx.dbs.*;
public class Db_stmt_arg {
	public Db_stmt_arg(boolean crt, int tid, String key, Object val) {this.Crt = crt; this.Tid = tid; this.Key = key; this.Val = val;}
	public final    boolean Crt;
	public final    int Tid;
	public final    String Key;
	public Object Val;
}
