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
package gplx.xowa; import gplx.*;
public class Xof_meta_fil {
	public Xof_meta_fil(Xof_meta_mgr meta_mgr, byte[] md5) {this.meta_mgr = meta_mgr; this.md5 = md5;}
	public Xof_meta_mgr Owner_mgr() {return meta_mgr;}  Xof_meta_mgr meta_mgr;
	public byte[] Md5() {return md5;} private byte[] md5;
	public void Dirty_() {meta_mgr.Dirty_(this);}
	public Xof_meta_itm Get_or_new(byte[] ttl) {
		Xof_meta_itm rv = Get_or_null(ttl);
		if (rv == null) {
			rv = new Xof_meta_itm(this, ttl);
			itms.Add(ttl, rv);
		}
		return rv;
	}	private OrderedHash itms = OrderedHash_.new_bry_();
	public Xof_meta_itm Get_or_null(byte[] ttl) {return (Xof_meta_itm)itms.Fetch(ttl);}
	public void Save(Gfo_fld_wtr wtr) {
		int itms_len = itms.Count();
		for (int i = 0; i < itms_len; i++) {
			Xof_meta_itm itm = (Xof_meta_itm)itms.FetchAt(i);
			itm.Save(wtr);
		}
	}
	public Xof_meta_fil Load(Gfo_fld_rdr rdr, Xof_meta_thumb_parser parser) {
		itms.Clear();
		int bry_len = rdr.Data().length;
		while (rdr.Pos() < bry_len) {
			Xof_meta_itm itm = new Xof_meta_itm(this, Bry_.Empty);
			itm.Load(rdr, parser);
			itms.Add(itm.Ttl(), itm);
		}
		return this;
	}
	public static Io_url Bld_url(Io_url root, byte[] md5, int depth) {
		Bld_url_bfr.Add(root.RawBry());
		for (int i = 0; i < depth - 1; i++)
			Bld_url_bfr.Add_byte(md5[i]).Add_byte(root.Info().DirSpr_byte());
		for (int i = 0; i < depth; i++)
			Bld_url_bfr.Add_byte(md5[i]);
		Bld_url_bfr.Add(Bry_url_ext);
		return Io_url_.new_fil_(Bld_url_bfr.XtoStrAndClear());
	}	static final byte[] Bry_url_ext = Bry_.new_ascii_(".csv"); static Bry_bfr Bld_url_bfr = Bry_bfr.new_(260);	// 260 is max path of url
}
