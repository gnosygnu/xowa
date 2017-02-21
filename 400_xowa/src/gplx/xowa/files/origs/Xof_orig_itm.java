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
package gplx.xowa.files.origs; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.xowa.wikis.*; import gplx.xowa.files.*;
public class Xof_orig_itm {
	public Xof_orig_itm(byte repo, byte[] ttl, int ext_id, int w, int h, byte[] redirect) {
		this.repo = repo; this.ttl = ttl; this.ext_id = ext_id;
		this.w = w; this.h = h;  this.redirect = redirect;
	}
	public byte			Repo() {return repo;} private final    byte repo;
	public byte[]		Ttl() {return ttl;} private final    byte[] ttl;	// without file ns; EX: "A.png" not "File:A.png"
	public int			Ext_id() {return ext_id;} private final    int ext_id;
	public Xof_ext		Ext() {if (ext == null) ext = Xof_ext_.new_by_id_(ext_id); return ext;} private Xof_ext ext;
	public int			W() {return w;} private final    int w;
	public int			H() {return h;} private final    int h;
	public byte[]		Redirect() {return redirect;} private final    byte[] redirect;	// redirect trg; EX: A.png is redirected to B.jpg; record will have A.png|jpg|220|200|B.jpg where jpg|220|200 are the attributes of B.jpg
	public boolean			Insert_new() {return insert_new;} public void Insert_new_y_() {insert_new = Bool_.Y;} private boolean insert_new;

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
