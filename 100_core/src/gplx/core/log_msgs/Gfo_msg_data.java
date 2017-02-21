/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
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
