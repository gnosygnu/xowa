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
package gplx.xowa.bldrs.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
public class Db_idx_mode {
	private int tid;
	Db_idx_mode(int tid) {this.tid = tid;}
	public boolean Tid_is_bgn()	{return tid == Tid_bgn;}
	public boolean Tid_is_end()	{return tid == Tid_end;}
	public boolean Tid_is_skip()	{return tid == Tid_skip;}
	private static final int Tid_skip = 0, Tid_bgn = 1, Tid_end = 2;
	private static final String Key_skip = "skip", Key_bgn = "bgn", Key_end = "end";
	public static final Db_idx_mode
	  Itm_skip	= new Db_idx_mode(Tid_skip)
	, Itm_bgn	= new Db_idx_mode(Tid_bgn)
	, Itm_end	= new Db_idx_mode(Tid_end)
	;
	public static Db_idx_mode Xto_itm(String key) {
		if		(String_.Eq(key, Key_skip))		return Itm_skip;
		else if	(String_.Eq(key, Key_bgn))		return Itm_bgn;
		else if	(String_.Eq(key, Key_end))		return Itm_end;
		else									throw Err_.unhandled(key);
	}
}
