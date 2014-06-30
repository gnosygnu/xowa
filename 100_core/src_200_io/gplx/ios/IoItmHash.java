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
package gplx.ios; import gplx.*;
public class IoItmHash extends OrderedHash_base {
	public Io_url Url() {return url;} Io_url url;
	public void Add(IoItm_base itm) {Add_base(MakeKey(itm.Url()), itm);}
	public void Del(Io_url url) {Del(MakeKey(url));}
	public IoItm_base Fetch(Io_url url) {return IoItm_base_.as_(Fetch_base(MakeKey(url)));}
	@gplx.New public IoItm_base FetchAt(int i) {return IoItm_base_.as_(FetchAt_base(i));}
	public Io_url[] XtoIoUrlAry() {
		int count = this.Count();
		Io_url[] rv = new Io_url[count];
		for (int i = 0; i < count; i++)
			rv[i] = this.FetchAt(i).Url();
		return rv;
	}
	String MakeKey(Io_url url) {return url.XtoCaseNormalized();}
	public static IoItmHash new_() {
		IoItmHash rv = new IoItmHash();
		rv.url = null;//Io_url_.Null;
		return rv;
	}	IoItmHash() {}
	public static IoItmHash list_(Io_url url) {
		IoItmHash rv = new IoItmHash();
		rv.url = url;
		return rv;
	}
}
