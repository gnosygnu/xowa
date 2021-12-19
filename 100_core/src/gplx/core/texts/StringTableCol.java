/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.texts;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
public class StringTableCol {
	public StringTableColAlign Halign() {return halign;} public StringTableCol Halign_(StringTableColAlign val) {halign = val; return this;} StringTableColAlign halign = StringTableColAlign.Left;
	public int LengthMax() {return lengthMax;} int lengthMax = IntUtl.MinValue;
	public int LengthMin() {return lengthMin;} int lengthMin = IntUtl.MaxValue;
	public void AdjustFor(String s) {
		int length = StringUtl.Len(s);
		if (length > lengthMax) lengthMax = length;
		if (length < lengthMin) lengthMin = length;
	}
	public String PadCell(String cell) {
		int diff = lengthMax - StringUtl.Len(cell);
		int val = halign.Val();
		if        (val == StringTableColAlign.Left.Val())    return cell + StringUtl.Repeat(" ", diff);
		else if (val == StringTableColAlign.Right.Val())    return StringUtl.Repeat(" ", diff) + cell;
		else if (val == StringTableColAlign.Mid.Val())    return StringUtl.Concat(StringUtl.Repeat(" ", diff / 2), cell, StringUtl.Repeat(" ", (diff / 2) + (diff % 2)));
		else                                    throw ErrUtl.NewUnhandled(halign.Val());
	}
	public static StringTableCol new_() {return new StringTableCol();} StringTableCol() {}
	public static StringTableCol as_(Object obj) {return obj instanceof StringTableCol ? (StringTableCol)obj : null;}
	public static StringTableCol cast(Object obj) {try {return (StringTableCol)obj;} catch(Exception exc) {throw ErrUtl.NewCast(exc, StringTableCol.class, obj);}}
}
