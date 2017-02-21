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
package gplx.gfui.draws; import gplx.*; import gplx.gfui.*;
public class SolidBrushAdp_ {
	public static SolidBrushAdp as_(Object obj) {return obj instanceof SolidBrushAdp ? (SolidBrushAdp)obj : null;}
	public static SolidBrushAdp cast(Object obj) {try {return (SolidBrushAdp)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, SolidBrushAdp.class, obj);}}
	public static final    SolidBrushAdp Black = new_(ColorAdp_.Black);
	public static final    SolidBrushAdp White = new_(ColorAdp_.White);
	public static final    SolidBrushAdp Null = new_(ColorAdp_.Null);
	public static SolidBrushAdp new_(ColorAdp color) {return SolidBrushAdpCache.Instance.Get_by(color);}
}
class SolidBrushAdpCache {
	public SolidBrushAdp Get_by(ColorAdp color) {
		SolidBrushAdp rv = (SolidBrushAdp)hash.Get_by(color.Value());
		if (rv == null) {
			rv = SolidBrushAdp.new_(color);
			hash.Add(color.Value(), rv);
		}
		return rv;
	}
	Hash_adp hash = Hash_adp_.New();
	public static final    SolidBrushAdpCache Instance = new SolidBrushAdpCache(); SolidBrushAdpCache() {}
}
