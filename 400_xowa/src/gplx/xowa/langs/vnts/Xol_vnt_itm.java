/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.langs.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.xowa.langs.vnts.converts.*;
public class Xol_vnt_itm implements GfoInvkAble {
	public Xol_vnt_itm(byte[] key, byte[] name, int mask__vnt) {
		this.key = key; this.name = name; this.mask__vnt = mask__vnt;
		this.convert_wkr = new Xol_convert_wkr(key);			
	}
	public byte[]					Key() {return key;} private final byte[] key;										// EX: zh-cn
	public byte[]					Name() {return name;} private final byte[] name;										// EX: 大陆简体
	public boolean						Visible() {return visible;} private boolean visible = true;								// visible in menu
	public byte[][]					Fallback_ary() {return fallback_ary;} private byte[][] fallback_ary = Bry_.Ary_empty;	// EX: zh-hans|zh
	public int						Mask__vnt() {return mask__vnt;} private final int mask__vnt;							// EX: 8
	public int						Mask__fallbacks() {return mask_fallbacks;} private int mask_fallbacks;					// EX: 11 for zh,zh-hans,zh-cn
	public byte[][]					Convert_ary() {return convert_ary;} private byte[][] convert_ary = Bry_.Ary_empty;		// EX: zh-hans|zh-cn
	public Xol_convert_wkr			Convert_wkr() {return convert_wkr;} private final Xol_convert_wkr convert_wkr;
	public void						Visible_(boolean v) {this.visible = v;}
	public void						Convert_ary_(byte[][] v) {convert_ary = v;}
	public void Mask__fallbacks__calc(Xol_vnt_regy regy, byte[][] ary) {
		this.mask_fallbacks = regy.Mask__calc(Bry_.Ary_add(Bry_.Ary(key), ary));// NOTE: must add lang.key which is not part of fallback; EX: "zh-cn" has fallback of "zh-hans", but chain should calc "zh-cn","zh-hans"
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_fallbacks_))			fallback_ary = Bry_split_.Split(m.ReadBry("v"), Byte_ascii.Pipe);
		else if	(ctx.Match(k, Invk_converts_))			convert_ary  = Bry_split_.Split(m.ReadBry("v"), Byte_ascii.Pipe);
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_fallbacks_ = "fallbacks_", Invk_converts_ = "converts_";
}
