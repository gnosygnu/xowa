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
public class Gfo_log__mem extends Gfo_log__base {		
	@Override public List_adp Itms() {return itms;} @Override public Gfo_log Itms_(List_adp v) {this.itms = v; return this;} private List_adp itms = List_adp_.New();		
	@Override public void Exec(byte type, long time, long elapsed, String msg, Object[] args) {
		Gfo_log_itm itm = new Gfo_log_itm(type, time, elapsed, msg, args);
		itms.Add(itm);
	}
	@Override public void Flush() {}
}
