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
package gplx.xowa.xtns.math.texvcs.tkns;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.errs.ErrUtl;
public class Texvc_tkn__leaf_raw implements Texvc_tkn {
	public Texvc_tkn Init(Texvc_root root, int tid, int uid, int src_bgn, int src_end) {
		this.root = root;
		this.tid = tid;
		this.uid = uid;
		this.src_bgn = src_bgn;
		this.src_end = src_end;
		return this;
	}
	public Texvc_root Root() {return root;} private Texvc_root root;
	public int Tid() {return tid;} private int tid;
	public int Uid() {return uid;} private int uid;
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public void Src_end_(int v) {this.src_end = v;}
	public int Subs__len() {return 0;}
	public Texvc_tkn Subs__get_at(int i)	{throw ErrUtl.NewUnsupported();}
	public void Print_tex_bry(BryWtr bfr, byte[] src, int indent) {bfr.AddMid(src, src_bgn, src_end);}
	public void Print_dbg_bry(BryWtr bfr, int indent) {
		Texvc_tkn_.Print_dbg_str__bgn(bfr, indent, this);
		Texvc_tkn_.Print_dbg_str__end_head(bfr);
	}
}
