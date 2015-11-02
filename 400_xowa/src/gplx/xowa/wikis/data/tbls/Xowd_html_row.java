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
package gplx.xowa.wikis.data.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
public class Xowd_html_row {
	public Xowd_html_row(int page_id, int tid, byte[] data) {this.page_id = page_id; this.tid = tid; this.data = data;}
	public int		Page_id()	{return page_id;}	private final int page_id;
	public int		Tid()		{return tid;}		private final int tid;
	public byte[]	Data()		{return data;}		private final byte[] data;
	public static final int // SERIALIZED
	  Tid__html		= 0
	, Tid__img		= 1
	, Tid__redlink	= 2
	;
}
