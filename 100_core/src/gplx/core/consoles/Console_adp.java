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
package gplx.core.consoles; import gplx.*; import gplx.core.*;
public interface Console_adp {
	boolean Enabled();	// optimization; allows Write to be skipped (since Write may Concat strings or generate arrays)
	boolean Canceled_chk();
	int Chars_per_line_max(); void Chars_per_line_max_(int v);
	void Write_str(String s);
	void Write_fmt_w_nl(String fmt, Object... args);
	void Write_tmp(String s);
	char Read_key(String msg);
	String Read_line(String msg);
}
