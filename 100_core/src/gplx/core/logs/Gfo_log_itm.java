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
package gplx.core.logs; import gplx.*; import gplx.core.*;
public class Gfo_log_itm {
	public Gfo_log_itm(byte type, long time, long elapsed, String msg, Object[] args) {
		this.Type = type;
		this.Time = time;
		this.Elapsed = elapsed;
		this.Msg = msg;
		this.Args = args;
	}
	public final    byte Type;
	public final    long Time;
	public final    long Elapsed;
	public final    String Msg;
	public final    Object[] Args;

	public static final byte Type__fail = 0, Type__warn = 1, Type__note = 2, Type__info = 3, Type__prog = 4;
}
