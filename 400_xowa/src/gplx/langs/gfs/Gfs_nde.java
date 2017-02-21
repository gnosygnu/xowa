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
package gplx.langs.gfs; import gplx.*; import gplx.langs.*;
public class Gfs_nde {
	public byte[] Name_bry(byte[] src) {return name == null ? Bry_.Mid(src, name_bgn, name_end) : name;}
	public byte[] Name() {return name;} public Gfs_nde Name_(byte[] v) {name = v; return this;} private byte[] name;
	public int Name_bgn() {return name_bgn;} private int name_bgn = -1;
	public int Name_end() {return name_end;} private int name_end = -1;
	public Gfs_nde Name_rng_(int name_bgn, int name_end) {this.name_bgn = name_bgn; this.name_end = name_end; return this;}
	public byte Op_tid() {return op_tid;} public Gfs_nde Op_tid_(byte v) {op_tid = v; return this;} private byte op_tid;
	public void Subs_clear() {
		for (int i = 0; i < subs_len; i++)
			subs[i] = null;
		subs_len = 0;
	}
	public int Subs_len() {return subs_len;} private int subs_len;
	public Gfs_nde Subs_add_many(Gfs_nde... ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++)
			Subs_add(ary[i]);
		return this;
	}
	public Gfs_nde Subs_add(Gfs_nde nde) {
		int new_len = subs_len + 1;
		if (new_len > subs_max) {	// ary too small >>> expand
			subs_max = new_len * 2;
			Gfs_nde[] new_subs = new Gfs_nde[subs_max];
			Array_.Copy_to(subs, 0, new_subs, 0, subs_len);
			subs = new_subs;
		}
		subs[subs_len] = nde;
		subs_len = new_len;
		return this;
	}	Gfs_nde[] subs = Gfs_nde.Ary_empty; int subs_max; int[] subs_pos_ary = Int_.Ary_empty;
	public Gfs_nde Subs_get_at(int i) {return subs[i];}
	public Gfs_nde[] Subs_to_ary() {
		Gfs_nde[] rv = new Gfs_nde[subs_len];
		for (int i = 0; i < subs_len; i++)
			rv[i] = subs[i];
		return rv;
	}
	public int Atrs_len() {return args_len;} private int args_len;
	public Gfs_nde Atrs_get_at(int i) {return args[i];}
	public Gfs_nde Atrs_add_many(Gfs_nde... ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++)
			Atrs_add(ary[i]);
		return this;
	}
	public Gfs_nde Atrs_add(Gfs_nde nde) {
		int new_len = args_len + 1;
		if (new_len > args_max) {	// ary too small >>> expand
			args_max = new_len * 2;
			Gfs_nde[] new_args = new Gfs_nde[args_max];
			Array_.Copy_to(args, 0, new_args, 0, args_len);
			args = new_args;
		}
		args[args_len] = nde;
		args_len = new_len;
		return this;
	}	Gfs_nde[] args = Gfs_nde.Ary_empty; int args_max; int[] args_pos_ary = Int_.Ary_empty;
	public Gfs_nde[] Atrs_to_ary() {
		Gfs_nde[] rv = new Gfs_nde[args_len];
		for (int i = 0; i < args_len; i++)
			rv[i] = args[i];
		return rv;		
	}
	public static final    Gfs_nde[] Ary_empty = new Gfs_nde[0]; 
	public static final byte Op_tid_null = 0, Op_tid_assign = 1;
}
