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
public class Fsd_dir_itm {
	public Fsd_dir_itm(int dir_id, int owner, byte[] name) {
		this.dir_id = dir_id;
		this.owner = owner;
		this.name = name;
	}
	public int		Dir_id()	{return dir_id;}	private final    int dir_id;
	public int		Owner()		{return owner;}		private final    int owner;
	public byte[]	Name()		{return name;}		private final    byte[] name;

	public static final int Owner_root = 0;
	public static final    Fsd_dir_itm Null = null;
}
