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
public abstract class IoItm_base implements Gfo_invk, CompareAble {
	public abstract int TypeId(); public abstract boolean Type_dir(); public abstract boolean Type_fil();
	public Io_url Url() {return ownerDir == null ? url : ownerDir.Url().GenSubFil(name); /*NOTE: must call .Url*/} Io_url url;		
	public IoItmDir OwnerDir() {return ownerDir;} IoItmDir ownerDir;
	public void OwnerDir_set(IoItmDir v) {if (v == this) throw Err_.new_wo_type("dir cannot be its own owner", "url", v.url.Raw());
		url = v == null && ownerDir != null
						? ownerDir.url.GenSubFil(name)	// create url, since ownerDir will soon be null; NOTE: must call .url
						: Io_url_.Empty;					// delete url, since ownerDir will be avail
		ownerDir = v;
	}
	public String Name() {return name;} private String name;
	public IoItm_base Name_(String v) {
		name = v; 
		if (ownerDir == null) url = url.OwnerDir().GenSubFil(name); 
		return this;
	} 
	public Object XtnProps_get(String key) {return props.Get_by(key);} Hash_adp props = Hash_adp_.Noop;
	public IoItm_base XtnProps_set(String key, Object val) {
		if (props == Hash_adp_.Noop) props = Hash_adp_.New();
		props.Del(key);
		props.Add(key, val);
		return this;
	}
	public int compareTo(Object comp) {return url.compareTo(((IoItm_base)comp).url);}	// NOTE: needed for comic importer (sort done on IoItmHash which contains IoItm_base)
//		public Object Data_get(String name)				{return Gfo_invk_.Invk_by_key(this, name);}
	@gplx.Virtual public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, IoItm_base_.Prop_Type))	return this.TypeId();
		else if	(ctx.Match(k, IoItm_base_.Prop_Path))	return this.Url();
		else if	(ctx.Match(k, IoItm_base_.Prop_Title))	return this.Url().NameOnly();	// needed for gfio script criteria;
		else if (ctx.Match(k, IoItm_base_.Prop_Ext))	return this.Url().Ext();		// needed for gfio script criteria; EX: where "ext LIKE '.java'"
		else return Gfo_invk_.Rv_unhandled;
	}
	@gplx.Internal protected void ctor_IoItmBase_url(Io_url url) {this.url = url; this.name = url.NameAndExt();}
	@gplx.Internal protected void ctor_IoItmBase_name(String name) {this.name = name;}
}
