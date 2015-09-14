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
package gplx.xowa.tdbs; import gplx.*; import gplx.xowa.*;
import gplx.core.primitives.*;
public class ByteAry_fil {
	public List_adp Itms() {return itms;} List_adp itms = List_adp_.new_();
	public Io_url Fil() {return fil;} Io_url fil;
	public byte[] Raw_bry() {return raw_bry;} private byte[] raw_bry = Bry_.Empty;
	public int Raw_len() {return raw_len.Val();} Int_obj_ref raw_len = Int_obj_ref.zero_();
	public int Raw_max() {return raw_max;} private int raw_max = Io_mgr.Len_mb;
	public ByteAry_fil Ini_file(Io_url fil) {
		this.fil = fil;
		raw_bry = Io_mgr.I.LoadFilBry_reuse(fil, raw_bry, raw_len);
		return this;
	}
	public Object Xto_itms(Class<?> itm_type) {
		Object rv = itms.To_ary(itm_type);
		itms.Clear();
		if (raw_bry.length > raw_max) raw_bry = Bry_.Empty;
		raw_len.Val_zero_();
		return rv;
	}
	public static final ByteAry_fil _ = new ByteAry_fil(); ByteAry_fil() {}
}
