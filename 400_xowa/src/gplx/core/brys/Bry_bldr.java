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
package gplx.core.brys;
import gplx.objects.strings.AsciiByte;
public class Bry_bldr {
	public byte[] Val() {return val;} private byte[] val;
	public Bry_bldr New_256() {return New(256);}
	public Bry_bldr New(int len) {val = new byte[len]; return this;}
	public Bry_bldr Set_rng_ws(byte v)					{return Set_many(v, AsciiByte.Space, AsciiByte.Tab, AsciiByte.Nl, AsciiByte.Cr);}
	public Bry_bldr Set_rng_xml_identifier(byte v)		{return Set_rng_alpha_lc(v).Set_rng_alpha_uc(v).Set_rng_num(v).Set_many(v, AsciiByte.Underline, AsciiByte.Dash);}
	public Bry_bldr Set_rng_alpha(byte v)				{return Set_rng_alpha_lc(v).Set_rng_alpha_uc(v);}
	public Bry_bldr Set_rng_alpha_lc(byte v)			{return Set_rng(v, AsciiByte.Ltr_a, AsciiByte.Ltr_z);}
	public Bry_bldr Set_rng_alpha_uc(byte v)			{return Set_rng(v, AsciiByte.Ltr_A, AsciiByte.Ltr_Z);}
	public Bry_bldr Set_rng_num(byte v)					{return Set_rng(v, AsciiByte.Num0, AsciiByte.Num9);}
	public Bry_bldr Set_rng(byte v, int bgn, int end) {
		for (int i = bgn; i <= end; i++)
			val[i] = v;
		return this;
	}
	public Bry_bldr Set_many(byte v, int... ary) {
		int len = ary.length; 
		for (int i = 0; i < len; i++)
			val[ary[i]] = v;
		return this;
	}
}
