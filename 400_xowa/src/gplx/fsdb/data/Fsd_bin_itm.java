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
public class Fsd_bin_itm {
	public Fsd_bin_itm(int bin_owner_id, byte bin_owner_tid, int bin_part_id, String bin_data_url, byte[] bin_data) {
		this.bin_owner_id = bin_owner_id;
		this.bin_owner_tid = bin_owner_tid;
		this.bin_part_id = bin_part_id;
		this.bin_data_url = bin_data_url;
		this.bin_data = bin_data;
	}
	public int		Bin_owner_id()		{return bin_owner_id;}		private final    int bin_owner_id;
	public byte		Bin_owner_tid()		{return bin_owner_tid;}		private final    byte bin_owner_tid;
	public int		Bin_part_id()		{return bin_part_id;}		private final    int bin_part_id;
	public String	Bin_data_url()		{return bin_data_url;}		private final    String bin_data_url;
	public byte[]	Bin_data()			{return bin_data;}			private final    byte[] bin_data;

	public static final int Db_row_size_fixed = (3 * 4);	// bin_owner_id, bin_part_id, bin_owner_tid (assume byte saved as int in SQLITE)
}
