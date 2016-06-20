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
package gplx.xowa.files.origs; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.xowa.wikis.*; import gplx.xowa.files.*;
public class Xof_orig_itm {
	public byte			Repo() {return repo;} private byte repo;
	public byte[]		Ttl() {return ttl;} private byte[] ttl;	// without file ns; EX: "A.png" not "File:A.png"
	public int			Ext_id() {return ext_id;} private int ext_id;
	public Xof_ext		Ext() {if (ext == null) ext = Xof_ext_.new_by_id_(ext_id); return ext;} private Xof_ext ext;
	public int			W() {return w;} private int w;
	public int			H() {return h;} private int h;
	public byte[]		Redirect() {return redirect;} private byte[] redirect;	// redirect trg; EX: A.png is redirected to B.jpg; record will have A.png|jpg|220|200|B.jpg where jpg|220|200 are the attributes of B.jpg
	public boolean			Insert_new() {return insert_new;} public void Insert_new_y_() {insert_new = Bool_.Y;} private boolean insert_new;
	public void Clear() {
		this.repo = Repo_null;
		this.ttl = this.redirect = null;
		this.ext_id = Xof_ext_.Id_unknown;
		this.w = this.h = Xof_img_size.Null;
		this.ext = null;
	}
	public Xof_orig_itm Init(byte repo, byte[] ttl, int ext_id, int w, int h, byte[] redirect) {
		this.repo = repo; this.ttl = ttl; this.ext_id = ext_id;
		this.w = w; this.h = h;  this.redirect = redirect;
		return this;
	}

	public int Db_row_size() {return Db_row_size_fixed + redirect.length + ttl.length;}
	private static final int Db_row_size_fixed = (5 * 4);	// 3 ints; 2 bytes
	public static final byte Repo_comm = 0, Repo_wiki = 1, Repo_null = Byte_.Max_value_127;	// SERIALIZED: "wiki_orig.orig_repo"
	public static final    Xof_orig_itm Null = null;
	public static final int File_len_null = -1;	// file_len used for filters (EX: don't download ogg > 1 MB)
	public static String dump(Xof_orig_itm itm) {
		if (itm == null)
			return "NULL";
		Bry_bfr bfr = Bry_bfr_.New_w_size(255);
		bfr.Add_str_a7("repo").Add_byte_eq().Add_int_variable((int)itm.repo).Add_byte_semic();
		bfr.Add_str_a7("ttl").Add_byte_eq().Add(itm.ttl).Add_byte_semic();
		bfr.Add_str_a7("ext_id").Add_byte_eq().Add_int_variable(itm.ext_id).Add_byte_semic();
		bfr.Add_str_a7("w").Add_byte_eq().Add_int_variable(itm.w).Add_byte_semic();
		bfr.Add_str_a7("h").Add_byte_eq().Add_int_variable(itm.h).Add_byte_semic();
		bfr.Add_str_a7("redirect").Add_byte_eq().Add(itm.redirect).Add_byte_semic();
		return bfr.To_str_and_clear();
	}
}
