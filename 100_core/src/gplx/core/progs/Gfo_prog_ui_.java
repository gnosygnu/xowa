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
package gplx.core.progs; import gplx.*; import gplx.core.*;
public class Gfo_prog_ui_ {
	public static final    Gfo_prog_ui Noop = new Gfo_prog_ui__noop(), Always = new Gfo_prog_ui__always();
	public static final byte 
	  Status__init			=  1
	, Status__working		=  2
	, Status__done			=  4
	, Status__fail			=  8
	, Status__suspended		= 16
	, Status__runnable		= Status__init | Status__suspended
	;
}
class Gfo_prog_ui__noop implements Gfo_prog_ui {
	public boolean		Canceled() {return true;}
	public void		Cancel() {}
	public byte		Prog_status() {return Gfo_prog_ui_.Status__init;}
	public void		Prog_status_(byte v) {}
	public long		Prog_data_cur() {return 0;}
	public long		Prog_data_end() {return 0;}
	public boolean		Prog_notify_and_chk_if_suspended(long cur, long max) {return false;}
}
class Gfo_prog_ui__always implements Gfo_prog_ui {
	public boolean		Canceled() {return false;}
	public void		Cancel() {}
	public byte		Prog_status() {return Gfo_prog_ui_.Status__init;}
	public void		Prog_status_(byte v) {}
	public long		Prog_data_cur() {return 0;}
	public long		Prog_data_end() {return 0;}
	public boolean		Prog_notify_and_chk_if_suspended(long cur, long max) {return false;}
}
