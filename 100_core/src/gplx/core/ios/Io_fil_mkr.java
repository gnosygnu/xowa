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
package gplx.core.ios; import gplx.*; import gplx.core.*;
public class Io_fil_mkr {
	private final List_adp list = List_adp_.new_();
	public Io_fil_mkr Add(String url, String data) {return Add(Io_url_.mem_fil_(url), data);}
	public Io_fil_mkr Add(Io_url url, String data) {list.Add(new Io_fil(url, data)); return this;}
	public Io_fil[] To_ary() {return (Io_fil[])list.To_ary(Io_fil.class);}
}
