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
package gplx.ios; import gplx.*;
public abstract class IoItm_base implements GfoInvkAble, CompareAble {
	public abstract int TypeId(); public abstract boolean Type_dir(); public abstract boolean Type_fil();
	public Io_url Url() {return ownerDir == null ? url : ownerDir.Url().GenSubFil(name); /*NOTE: must call .Url*/} Io_url url;		
	public IoItmDir OwnerDir() {return ownerDir;} IoItmDir ownerDir;
	public void OwnerDir_set(IoItmDir v) {if (v == this) throw Err_.new_("dir cannot be its own owner").Add("url", v.url.Raw());
		url = v == null && ownerDir != null
						? ownerDir.url.GenSubFil(name)	// create url, since ownerDir will soon be null; NOTE: must call .url
						: Io_url_.Null;					// delete url, since ownerDir will be avail
		ownerDir = v;
	}
	public String Name() {return name;} private String name;
	public IoItm_base Name_(String v) {
		name = v; 
		if (ownerDir == null) url = url.OwnerDir().GenSubFil(name); 
		return this;
	} 
	public Object XtnProps_get(String key) {return props.Fetch(key);} HashAdp props = HashAdp_.Null;
	public IoItm_base XtnProps_set(String key, Object val) {
		if (props == HashAdp_.Null) props = HashAdp_.new_();
		props.Del(key);
		props.Add(key, val);
		return this;
	}
	public int compareTo(Object comp) {return url.compareTo(((IoItm_base)comp).url);}	// NOTE: needed for comic importer (sort done on IoItmHash which contains IoItm_base)
//		public Object Data_get(String name)				{return GfoInvkAble_.InvkCmd(this, name);}
	@gplx.Virtual public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, IoItm_base_.Prop_Type))	return this.TypeId();
		else if	(ctx.Match(k, IoItm_base_.Prop_Path))	return this.Url();
		else if	(ctx.Match(k, IoItm_base_.Prop_Title))	return this.Url().NameOnly();	// needed for gfio script criteria;
		else if (ctx.Match(k, IoItm_base_.Prop_Ext))	return this.Url().Ext();		// needed for gfio script criteria; EX: where "ext LIKE '.java'"
		else return GfoInvkAble_.Rv_unhandled;
	}
	@gplx.Internal protected void ctor_IoItmBase_url(Io_url url) {this.url = url; this.name = url.NameAndExt();}
	@gplx.Internal protected void ctor_IoItmBase_name(String name) {this.name = name;}
}
