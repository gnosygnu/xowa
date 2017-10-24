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
package gplx.gfui.layouts; import gplx.*; import gplx.gfui.*;
import gplx.gfui.controls.elems.*;
public interface GftSizeCalc {
	int Key();
	int Calc(GftGrid grid, GftBand band, GftItem owner, GftItem item, int ownerWidth);
	GftSizeCalc Clone();
}
class GftSizeCalc_pct implements GftSizeCalc {	
	public int Key() {return KEY;} public static final    int KEY = 1;
	public float Val() {return pct;} float pct;		
	public int Calc(GftGrid grid, GftBand band, GftItem owner, GftItem item, int ownerWidth) {
		return Int_.Mult(ownerWidth, pct / 100);
	}
	public GftSizeCalc Clone() {return new GftSizeCalc_pct(pct);}
	public GftSizeCalc_pct(float v) {pct = v;}
}
class GftSizeCalc_abs implements GftSizeCalc {	
	public int Key() {return KEY;} public static final    int KEY = 2;
	public int Val() {return abs;} int abs;
	public int Calc(GftGrid grid, GftBand band, GftItem owner, GftItem item, int ownerWidth) {
		return abs;
	}
	public GftSizeCalc Clone() {return new GftSizeCalc_abs(abs);}
	public GftSizeCalc_abs(int v) {abs = v;}
}
class GftSizeCalc_num implements GftSizeCalc {	
	public int Key() {return KEY;} public static final    int KEY = 3;
	public int Val() {return num;} int num;
	public int Calc(GftGrid grid, GftBand band, GftItem owner, GftItem item, int ownerWidth) {
		return owner.Gft_w() / num;
	}
	public GftSizeCalc Clone() {return new GftSizeCalc_num(num);}
	public GftSizeCalc_num(int num) {this.num = num;}
}
class GftSizeCalc_var implements GftSizeCalc {	
	public int Key() {return KEY;} public static final    int KEY = 4;
	public int Val() {return num;} int num;
	public int Calc(GftGrid grid, GftBand band, GftItem owner, GftItem item, int ownerWidth) {
		GfuiElem elem = GfuiElem_.as_(item);
		return elem == null ? item.Gft_w() : elem.Width();
	}
	public GftSizeCalc Clone() {return new GftSizeCalc_var(num);}
	public GftSizeCalc_var(int num) {this.num = num;}
}