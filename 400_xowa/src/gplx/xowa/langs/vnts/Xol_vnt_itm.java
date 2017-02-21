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
package gplx.xowa.langs.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.xowa.langs.vnts.converts.*;
public class Xol_vnt_itm implements Gfo_invk {
	public Xol_vnt_itm(int idx, byte[] key, byte[] name, int mask__vnt) {
		this.idx = idx; this.key = key; this.name = name; this.mask__vnt = mask__vnt;
		this.convert_wkr = new Xol_convert_wkr(key);			
	}
	public int						Idx() {return idx;} private final    int idx;											// EX: 2
	public byte[]					Key() {return key;} private final    byte[] key;										// EX: zh-cn
	public byte[]					Name() {return name;} private final    byte[] name;										// EX: 大陆简体
	public boolean						Visible() {return visible;} private boolean visible = true;								// visible in menu
	public int						Mask__vnt() {return mask__vnt;} private final    int mask__vnt;							// EX: 8
	public int						Mask__fallbacks() {return mask_fallbacks;} private int mask_fallbacks;					// EX: 11 for zh,zh-hans,zh-cn
	public int						Dir() {return dir;} private int dir = Xol_vnt_dir_.Tid__bi;								// EX: "bidirectional"
	public byte[][]					Fallback_ary() {return fallback_ary;} private byte[][] fallback_ary = Bry_.Ary_empty;	// EX: zh-hans|zh
	public byte[][]					Convert_ary() {return convert_ary;} private byte[][] convert_ary = Bry_.Ary_empty;		// EX: zh-hans|zh-cn
	public Xol_convert_wkr			Convert_wkr() {return convert_wkr;} private final    Xol_convert_wkr convert_wkr;
	public void						Visible_(boolean v) {this.visible = v;}
	public void						Convert_ary_(byte[][] v) {convert_ary = v;}
	public void Init(int dir, byte[][] fallback_ary) {
		this.dir = dir; this.fallback_ary = fallback_ary;
	}
	public void Mask__fallbacks_(Xol_vnt_regy regy, byte[][] ary) {
		this.mask_fallbacks = regy.Mask__calc(Bry_.Ary_add(Bry_.Ary(key), ary));// NOTE: must add lang.key which is not part of fallback; EX: "zh-cn" has fallback of "zh-hans", but chain should calc "zh-cn","zh-hans"
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_fallbacks_))			fallback_ary = Bry_split_.Split(m.ReadBry("v"), Byte_ascii.Pipe);
		else if	(ctx.Match(k, Invk_converts_))			Convert_ary_(Bry_split_.Split(m.ReadBry("v"), Byte_ascii.Pipe));
		else if	(ctx.Match(k, Invk_dir_))				dir = Xol_vnt_dir_.Parse(m.ReadBry("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_fallbacks_ = "fallbacks_", Invk_converts_ = "converts_", Invk_dir_ = "dir_";
}
