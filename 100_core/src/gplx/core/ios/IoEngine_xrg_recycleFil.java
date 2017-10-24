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
import gplx.core.strings.*;
public class IoEngine_xrg_recycleFil extends IoEngine_xrg_fil_affects1_base {
	public IoEngine_xrg_recycleFil MissingFails_off() {return MissingFails_(false);} public IoEngine_xrg_recycleFil MissingFails_(boolean v) {MissingFails_set(v); return this;}

	public int Mode() {return mode;} public IoEngine_xrg_recycleFil Mode_(int v) {mode = v; return this;} int mode;
	public String AppName() {return appName;} public IoEngine_xrg_recycleFil AppName_(String val) {appName = val; return this;} private String appName = "unknown_app";
	public Guid_adp Uuid() {return uuid;} public IoEngine_xrg_recycleFil Uuid_(Guid_adp val) {uuid = val; return this;} Guid_adp uuid;
	public boolean Uuid_include() {return uuid_include;} public IoEngine_xrg_recycleFil Uuid_include_() {uuid_include = true; return this;} private boolean uuid_include;
	public DateAdp Time() {return time;} public IoEngine_xrg_recycleFil Time_(DateAdp val) {time = val; return this;} DateAdp time;
	public List_adp RootDirNames() {return rootDirNames;} public IoEngine_xrg_recycleFil RootDirNames_(List_adp val) {rootDirNames = val; return this;} List_adp rootDirNames;
	public Io_url RecycleUrl() {
		String dayName = time.XtoStr_fmt("yyyyMMdd"), timeName = time.XtoStr_fmt("hhmmssfff");
		String rootDirStr = ConcatWith_ary(this.Url().Info().DirSpr(), rootDirNames);
		Io_url recycleDir = this.Url().OwnerRoot().GenSubDir_nest(rootDirStr, dayName);
		String uuidStr = uuid_include ? uuid.To_str() : "";
		return recycleDir.GenSubFil_ary(appName, ";", timeName, ";", uuidStr, ";", String_.LimitToFirst(this.Url().NameAndExt(), 128));
	}
	String ConcatWith_ary(String separator, List_adp ary) {
		String_bldr sb = String_bldr_.new_();
		int aryLen = ary.Count();
		for (int i = 0; i < aryLen; i++) {
			if (i != 0) sb.Add(separator);
			Object val = ary.Get_at(i);
			sb.Add_obj(Object_.Xto_str_strict_or_empty(val));
		}
		return sb.To_str();			
	}
	@Override public void Exec() {
		IoEnginePool.Instance.Get_by(this.Url().Info().EngineKey()).RecycleFil(this);
	}
	public IoEngine_xrg_recycleFil(int v) {
		mode = v;
		time = Datetime_now.Get();
		uuid = Guid_adp_.New();
		rootDirNames = List_adp_.New(); rootDirNames.Add("z_trash");
	}
	public static IoEngine_xrg_recycleFil sysm_(Io_url url) {return new IoEngine_xrg_recycleFil(SysmConst);}
	public static IoEngine_xrg_recycleFil gplx_(Io_url url) {IoEngine_xrg_recycleFil rv = new IoEngine_xrg_recycleFil(GplxConst); rv.Url_set(url); return rv;}
        public static IoEngine_xrg_recycleFil proto_() {return gplx_(Io_url_.Empty);}
	public static final    int GplxConst = 0, SysmConst = 1;
}
