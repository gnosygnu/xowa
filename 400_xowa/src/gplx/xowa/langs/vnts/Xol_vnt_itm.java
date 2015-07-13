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
import gplx.intl.*;
import gplx.xowa.langs.cnvs.*;
public class Xol_vnt_itm implements GfoInvkAble {
	public Xol_vnt_itm(Xol_vnt_mgr owner, byte[] key) {
		this.owner = owner;
		this.lang = owner.Lang(); this.key = key;
		converter = new Xol_vnt_converter(this);
	}
	public Xol_vnt_mgr Owner() {return owner;} private Xol_vnt_mgr owner;
	public Xol_lang Lang() {return lang;} private Xol_lang lang;
	public Xol_vnt_converter Converter() {return converter;} private Xol_vnt_converter converter;
	public byte[] Key() {return key;} private byte[] key;
	public byte[][] Fallback_ary() {return fallback_ary;} private byte[][] fallback_ary = Bry_.Ary_empty;
	public byte[][] Convert_ary() {return convert_ary;} private byte[][] convert_ary = Bry_.Ary_empty;
	public void Convert_ary_(byte[][] v) {convert_ary = v;}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_fallbacks_))			fallback_ary = Bry_.Split(m.ReadBry("v"), Byte_ascii.Pipe);
		else if	(ctx.Match(k, Invk_converts_))			{convert_ary  = Bry_.Split(m.ReadBry("v"), Byte_ascii.Pipe); converter.Rebuild();}	// setting converts will always force rebuild
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_fallbacks_ = "fallbacks_", Invk_converts_ = "converts_";
}
