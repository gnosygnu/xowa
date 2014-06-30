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
package gplx.xowa.gui.bnds; import gplx.*; import gplx.xowa.*; import gplx.xowa.gui.*;
public class Xog_bnd_box {
	private OrderedHash bnds = OrderedHash_.new_();
	public Xog_bnd_box(int tid, String key) {this.tid = tid; this.key = key;}
	public int Tid() {return tid;} private int tid;
	public String Key() {return key;} private String key;
	public int Len() {return bnds.Count();}
	public void Add(Xog_bnd_itm itm) {bnds.AddReplace(itm.Key(), itm);}	// AddReplace, else Xou_user_tst.Run fails; DATE:2014-05-15
	public void Del(String key) {bnds.Del(key);}
	public Xog_bnd_itm Get_at(int i) {return (Xog_bnd_itm)bnds.FetchAt(i);}
	public Xog_bnd_itm Get(String key) {return (Xog_bnd_itm)bnds.Fetch(key);}
}
