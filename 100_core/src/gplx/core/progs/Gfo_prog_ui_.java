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
	public static final byte State__inited = 0, State__started = 1, State__paused = 2, State__finished = 3, State__canceled = 4;
	public static boolean Sleep_while_paused(Gfo_prog_ui prog_ui) {
		while (true) {
			gplx.core.threads.Thread_adp_.Sleep(100);
			if		(!prog_ui.Prog__paused())	return Bool_.Y;
			else if (prog_ui.Prog__canceled())	return Bool_.N;
		}
	}
}
