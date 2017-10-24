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
package gplx.gfui; import gplx.*;
public class DirInt {
	public int Val() {return val;} int val;
	public DirInt Rev() {return this == Fwd ? Bwd : Fwd;}
	public int CompareToRng(int v, int lo, int hi) {
		if		(v < lo)	return -1 * val;
		else if (v > hi)	return  1 * val;
		else				return 0;
	}
	public int GetValByDir(int ifBwd, int ifFwd) {
		return this == Bwd ? ifBwd : ifFwd;
	}
	public boolean BoundFail(int i, int bound) {return this == Bwd ? i < bound : i > bound;}
	DirInt(int v) {this.val = v;}
	public static final    DirInt
		  Fwd = new DirInt(1)
		, Bwd = new DirInt(-1);
}
