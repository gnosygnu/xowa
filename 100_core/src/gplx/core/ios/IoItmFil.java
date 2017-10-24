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
package gplx.core.ios; import gplx.*; import gplx.core.*;
import gplx.langs.gfs.*;
public class IoItmFil extends IoItm_base {
	@Override public int TypeId() {return IoItmFil.Type_Fil;} @Override public boolean Type_dir() {return false;} @Override public boolean Type_fil() {return true;} public static final    int Type_Fil = 2;
	public boolean Exists() {return size != Size_invalid;}	// NOTE: questionable logic, but preserved for historical reasons; requires that length be set to -1 if !.exists
	public DateAdp ModifiedTime() {return modifiedTime;}
	public IoItmFil ModifiedTime_(DateAdp val) {modifiedTime = val; return this;} DateAdp modifiedTime;
	public IoItmFil ModifiedTime_(String val) {return ModifiedTime_(DateAdp_.parse_gplx(val));}
	@gplx.Virtual public long Size() {return size;} public IoItmFil Size_(long val) {size = val; return this;} private long size;
	public IoItmAttrib Attrib() {return attrib;} public IoItmFil Attrib_(IoItmAttrib val) {attrib = val; return this;} IoItmAttrib attrib = IoItmAttrib.normal_();
	public boolean ReadOnly() {return attrib.ReadOnly();} public IoItmFil ReadOnly_(boolean val) {attrib.ReadOnly_(val); return this;} 
	@gplx.New public IoItmFil XtnProps_set(String key, Object val) {return (IoItmFil)super.XtnProps_set(key, val);}
	
	@gplx.Internal protected IoItmFil ctor_IoItmFil(Io_url url, long size, DateAdp modifiedTime) {
		ctor_IoItmBase_url(url); this.size = size; this.modifiedTime = modifiedTime;
		return this;
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, IoItmFil_.Prop_Size))	return size;
		else if	(ctx.Match(k, IoItmFil_.Prop_Modified))	return modifiedTime;
		else return super.Invk(ctx, ikey, k, m);
	}
	@gplx.Internal protected IoItmFil() {}
	public static final long Size_invalid		= -1;
	public static final int Size_invalid_int	= -1;
}
