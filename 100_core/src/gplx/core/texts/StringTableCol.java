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
package gplx.core.texts; import gplx.*; import gplx.core.*;
public class StringTableCol {
	public StringTableColAlign Halign() {return halign;} public StringTableCol Halign_(StringTableColAlign val) {halign = val; return this;} StringTableColAlign halign = StringTableColAlign.Left;
	public int LengthMax() {return lengthMax;} int lengthMax = Int_.Min_value;
	public int LengthMin() {return lengthMin;} int lengthMin = Int_.Max_value;
	public void AdjustFor(String s) {
		int length = String_.Len(s);
		if (length > lengthMax) lengthMax = length;
		if (length < lengthMin) lengthMin = length;
	}
	public String PadCell(String cell) {
		int diff = lengthMax - String_.Len(cell);
		int val = halign.Val();
		if		(val == StringTableColAlign.Left.Val())	return cell + String_.Repeat(" ", diff);
		else if (val == StringTableColAlign.Right.Val())	return String_.Repeat(" ", diff) + cell;
		else if (val == StringTableColAlign.Mid.Val())	return String_.Concat(String_.Repeat(" ", diff / 2), cell, String_.Repeat(" ", (diff / 2) + (diff % 2)));
		else									throw Err_.new_unhandled(halign.Val());
	}
	public static StringTableCol new_() {return new StringTableCol();} StringTableCol() {}
	public static StringTableCol as_(Object obj) {return obj instanceof StringTableCol ? (StringTableCol)obj : null;}
	public static StringTableCol cast(Object obj) {try {return (StringTableCol)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, StringTableCol.class, obj);}}
}
