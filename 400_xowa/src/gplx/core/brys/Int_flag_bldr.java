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
package gplx.core.brys; import gplx.*; import gplx.core.*;
public class Int_flag_bldr {
	private int[] pow_ary;
	public int[] Val_ary() {return val_ary;} private int[] val_ary;
	public Int_flag_bldr Pow_ary_bld_(int... ary)	{
		this.pow_ary = Int_flag_bldr_.Bld_pow_ary(ary);
		this.val_ary = new int[pow_ary.length];
		return this;
	}
	public boolean Set_as_bool(int idx, boolean val)		{val_ary[idx] = val ? 1 : 0; return val;}
	public byte Set_as_byte(int idx, byte val)		{val_ary[idx] = val; return val;}
	public int Set_as_int(int idx, int val)			{val_ary[idx] = val; return val;}
	public Int_flag_bldr Set(int idx, boolean val)		{Set_as_bool(idx, val); return this;}
	public Int_flag_bldr Set(int idx, byte val)		{Set_as_byte(idx, val); return this;}
	public Int_flag_bldr Set(int idx, int val)		{Set_as_int(idx, val); return this;}
	public int Get_as_int(int idx)					{return val_ary[idx];}
	public byte Get_as_byte(int idx)				{return (byte)val_ary[idx];}
	public boolean Get_as_bool(int idx)				{return val_ary[idx] == 1;}
	public int Encode()								{return Int_flag_bldr_.To_int(pow_ary, val_ary);}
	public void Decode(int v)						{Int_flag_bldr_.To_int_ary(val_ary, pow_ary, v);}
}
