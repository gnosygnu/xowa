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
public abstract class Gfo_prog_ui_base implements Gfo_prog_ui {
	public boolean		Prog__started()		{return started;}		private boolean started;
	public boolean		Prog__paused()		{return paused;}		private boolean paused;
	public boolean		Prog__finished()	{return finished;}		private boolean finished;
	public boolean		Prog__canceled()	{return canceled;}		private boolean canceled;
	public long		Prog__cur()			{return cur;}			private long cur;
	public long		Prog__end()			{return end;}			private long end;		public void Prog__end_(long v) {this.end = v;}
	public void		Prog__start()		{started = true;}
	public void		Prog__pause()		{paused = true;}
	public void		Prog__resume()		{paused = false;}
	public void		Prog__cancel()		{canceled = true;}
	@gplx.Virtual public void Prog__notify__finished() {
		this.finished = true;
	}
	@gplx.Virtual public byte Prog__notify__working(long cur, long end) {
		this.cur = cur; this.end = end;
		if		(paused)		return Gfo_prog_ui_.State__paused;
		else if (canceled)		return Gfo_prog_ui_.State__canceled;
		else					return Gfo_prog_ui_.State__started;
	}
}
