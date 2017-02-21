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
package gplx.core.primitives; import gplx.*; import gplx.core.*;
import gplx.core.brys.*;
public class Bry_obj_ref implements gplx.core.brys.Bfr_arg {
	public byte[] Val() {return val;} private byte[] val; 
	public int Val_bgn() {return val_bgn;} private int val_bgn;
	public int Val_end() {return val_end;} private int val_end;
	public boolean Val_is_empty() {return val_bgn == val_end;}
	public Bry_obj_ref Val_(byte[] val)								{this.val = val; this.val_bgn = 0;			this.val_end = val == null ? 0 : val.length;	return this;}
	public Bry_obj_ref Mid_(byte[] val, int val_bgn, int val_end)	{this.val = val; this.val_bgn = val_bgn;	this.val_end = val_end;		return this;}
	@Override public int hashCode() {return CalcHashCode(val, val_bgn, val_end);}
	@Override public boolean equals(Object obj) {
		if (obj == null) return false;	// NOTE: strange, but null check needed; throws null error; EX.WP: File:Eug�ne Delacroix - La libert� guidant le peuple.jpg
		Bry_obj_ref comp = (Bry_obj_ref)obj;
		return Bry_.Match(val, val_bgn, val_end, comp.val, comp.val_bgn, comp.val_end);	
	}	
	public void Bfr_arg__add(Bry_bfr bfr) {
		bfr.Add_mid(val, val_bgn, val_end);
	}
	public static int CalcHashCode(byte[] ary, int bgn, int end) {
		int rv = 0;
		for (int i = bgn; i < end; i++)
			rv = (31 * rv) + ary[i];
		return rv;
	}
	public static Bry_obj_ref New_empty()		{return New(Bry_.Empty);}
        public static Bry_obj_ref New(byte[] val)	{return new Bry_obj_ref().Val_(val);}
        public static Bry_obj_ref New(String val)	{return new Bry_obj_ref().Val_(Bry_.new_u8(val));}
}
