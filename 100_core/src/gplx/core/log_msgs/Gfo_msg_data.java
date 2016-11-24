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
package gplx.core.log_msgs; import gplx.*; import gplx.core.*;
public class Gfo_msg_data {
	public int Uid() {return uid;} int uid = uid_next++;
	public Gfo_msg_itm Item() {return item;} Gfo_msg_itm item;
	public Object[] Vals() {return vals;} Object[] vals;
	public byte[] Src_bry() {return src_bry;} private byte[] src_bry;
	public int Src_bgn() {return src_bgn;} int src_bgn;
	public int Src_end() {return src_end;} int src_end;
	public Gfo_msg_data Ctor_val_many(Gfo_msg_itm item, Object[] vals) {this.item = item; this.vals = vals; return this;}
	public Gfo_msg_data Ctor_src_many(Gfo_msg_itm item, byte[] src_bry, int src_bgn, int src_end, Object[] vals) {this.item = item; this.src_bry = src_bry; this.src_bgn = src_bgn; this.src_end = src_end; this.vals = vals; return this;}
	public void Clear() {
		item = null; vals = null; src_bry = null;
	}
	public String Gen_str_ary() {return item.Gen_str_ary(vals);}
	static int uid_next = 0;
	public static final Gfo_msg_data[] Ary_empty = new Gfo_msg_data[0];
}
