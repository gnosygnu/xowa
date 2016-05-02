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
package gplx.xowa.addons.builds.centrals; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.builds.*;
class Xobc_rate_mgr {
	private final    Ordered_hash hash = Ordered_hash_.New();
	public int Len() {return hash.Len();}
	public Xobc_rate_itm Get_at(int i)		{return (Xobc_rate_itm)hash.Get_at(i);}
	public Xobc_rate_itm Get_by(String k)	{return (Xobc_rate_itm)hash.Get_by(k);}
	public void Add(Xobc_rate_itm rate)		{hash.Add_if_dupe_use_nth(rate.Key(), rate);}
}
class Xobc_rate_itm {
	public Xobc_rate_itm(String type, String key, long rate) {this.type = type; this.key = key; this.rate = rate;}
	public String Type() {return type;} private final    String type;		// EX: download
	public String Key() {return key;} private final    String key;			// EX: http://archive.org|C:\xowa\wiki\
	public long Rate() {return rate;} private final    long rate;			// EX: 4000000
}
