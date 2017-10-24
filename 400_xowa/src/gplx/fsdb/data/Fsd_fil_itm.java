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
package gplx.fsdb.data; import gplx.*; import gplx.fsdb.*;
public class Fsd_fil_itm {
	public Fsd_fil_itm		(int mnt_id, int dir_id, int fil_id, int xtn_id, int ext_id, byte[] name, long size, String modified_on, String hash_md5, int bin_db_id) {
		this.mnt_id = mnt_id; this.dir_id = dir_id; this.fil_id = fil_id; this.xtn_id = xtn_id; this.ext_id = ext_id;
		this.name = name; this.size = size; this.modified_on = modified_on; this.hash_md5 = hash_md5; this.bin_db_id = bin_db_id; 
	}
	public int		Mnt_id()		{return mnt_id;}		private final    int mnt_id;
	public int		Dir_id()		{return dir_id;}		private final    int dir_id;
	public int		Fil_id()		{return fil_id;}		private final    int fil_id;
	public int		Xtn_id()		{return xtn_id;}		private final    int xtn_id;
	public int		Ext_id()		{return ext_id;}		private final    int ext_id;
	public byte[]	Name()			{return name;}			private final    byte[] name;
	public long		Size()			{return size;}			private final    long size;
	public String	Modified_on()	{return modified_on;}	private final    String modified_on;
	public String	Hash_md5()		{return hash_md5;}		private final    String hash_md5;
	public int		Bin_db_id()		{return bin_db_id;}		private final    int bin_db_id;

	public int Db_row_size() {return Db_row_size_fixed + name.length;}
	private static final int Db_row_size_fixed = 
		  (7 * 4)	// 6 int fields + 1 byte field
		+ 8			// 1 long field
		+ 32		// hash_md5
		+ 14		// modified_on
	;

	public static final    Fsd_fil_itm Null = null;
	public static byte[] Gen_cache_key(Bry_bfr bfr, int dir_id, byte[] name) {
		return bfr.Add_int_variable(dir_id).Add_byte_pipe().Add(name).To_bry_and_clear();
	}
}
