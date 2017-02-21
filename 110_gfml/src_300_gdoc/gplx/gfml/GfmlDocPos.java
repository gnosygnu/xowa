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
package gplx.gfml; import gplx.*;
import gplx.core.strings.*;
public class GfmlDocPos implements CompareAble {
	public String Path() {if (path == null) MakePath(); return path;} private String path;
	public int compareTo(Object obj) {
		/*	Same: same coord (ex: 0_1 = 0_1)
			More: higher level (ex: 0_1 > 0_1_0) or higher idx (ex: 0_1 > 0_2)
			Less: lower level (ex: 0_1 < 0) or lower idx (ex: 0_1 < 0_0)			*/
		GfmlDocPos comp = (GfmlDocPos)obj;
		for (int i = 0; i < ary.length; i++) {
			if (i >= comp.ary.length) return CompareAble_.More;				// more ary than comp and whatever ary they share are equal; must be more;
			int origVal = ary[i];
			int compVal = comp.ary[i];
			if		(origVal == compVal)	continue;						// indexes are equal; continue to next
			else if (origVal <	compVal)	return CompareAble_.Less;
			else if (origVal >	compVal)	return CompareAble_.More;
		}
		if (ary.length < comp.ary.length)	return CompareAble_.Less;		// less ary than comp, and whatever ary they share are equal; must be less
		return Int_.Compare(idx, comp.idx);									// compare idx
	}
	public GfmlDocPos NewClone() {return new GfmlDocPos(ary, idx);}
	public GfmlDocPos NewDown(int newIdx) {
		int oldLen = ary.length;
		int[] newAry = new int[oldLen + 1];
		for (int i = 0; i < oldLen; i++)
			newAry[i] = ary[i];
		newAry[oldLen] = idx;
		return new GfmlDocPos(newAry, newIdx);
	}
	public GfmlDocPos NewUp() {
		int oldLen = ary.length; if (oldLen == 0) return GfmlDocPos_.Null;
		int[] newAry = new int[oldLen - 1];
		for (int i = 0; i < oldLen - 1; i++)
			newAry[i] = ary[i];
		int newIdx = ary[oldLen - 1];
		return new GfmlDocPos(newAry, newIdx);
	}
	@Override public String toString() {return path;} public String To_str() {return path;}
	void MakePath() {
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < ary.length; i++) {
			sb.Add(ary[i]);
			sb.Add("_");
		}
		sb.Add(idx);
		path = sb.To_str();
	}
	int[] ary; int idx;
	@gplx.Internal protected GfmlDocPos(int[] ary, int idx) {this.ary = ary; this.idx = idx;}
}
class GfmlDocPos_ {
	public static final GfmlDocPos Null = new GfmlDocPos(new int[0], -1);
	public static final GfmlDocPos Root = new GfmlDocPos(new int[0], 0);
}