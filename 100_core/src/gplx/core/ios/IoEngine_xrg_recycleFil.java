/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.ios;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateNow;
import gplx.types.commons.GfoGuid;
import gplx.types.commons.GfoGuidUtl;
import gplx.libs.files.Io_url;
import gplx.libs.files.Io_url_;
import gplx.types.commons.String_bldr;
import gplx.types.commons.String_bldr_;
public class IoEngine_xrg_recycleFil extends IoEngine_xrg_fil_affects1_base {
	public IoEngine_xrg_recycleFil MissingFails_off() {return MissingFails_(false);} public IoEngine_xrg_recycleFil MissingFails_(boolean v) {MissingFails_set(v); return this;}

	public int Mode() {return mode;} public IoEngine_xrg_recycleFil Mode_(int v) {mode = v; return this;} int mode;
	public String AppName() {return appName;} public IoEngine_xrg_recycleFil AppName_(String val) {appName = val; return this;} private String appName = "unknown_app";
	public GfoGuid Uuid() {return uuid;} public IoEngine_xrg_recycleFil Uuid_(GfoGuid val) {uuid = val; return this;} GfoGuid uuid;
	public boolean Uuid_include() {return uuid_include;} public IoEngine_xrg_recycleFil Uuid_include_() {uuid_include = true; return this;} private boolean uuid_include;
	public GfoDate Time() {return time;} public IoEngine_xrg_recycleFil Time_(GfoDate val) {time = val; return this;} GfoDate time;
	public List_adp RootDirNames() {return rootDirNames;} public IoEngine_xrg_recycleFil RootDirNames_(List_adp val) {rootDirNames = val; return this;} List_adp rootDirNames;
	public Io_url RecycleUrl() {
		String dayName = time.ToStrFmt("yyyyMMdd"), timeName = time.ToStrFmt("hhmmssfff");
		String rootDirStr = ConcatWith_ary(this.Url().Info().DirSpr(), rootDirNames);
		Io_url recycleDir = this.Url().OwnerRoot().GenSubDir_nest(rootDirStr, dayName);
		String uuidStr = uuid_include ? uuid.ToStr() : "";
		return recycleDir.GenSubFil_ary(appName, ";", timeName, ";", uuidStr, ";", StringUtl.LimitToFirst(this.Url().NameAndExt(), 128));
	}
	String ConcatWith_ary(String separator, List_adp ary) {
		String_bldr sb = String_bldr_.new_();
		int aryLen = ary.Len();
		for (int i = 0; i < aryLen; i++) {
			if (i != 0) sb.Add(separator);
			Object val = ary.GetAt(i);
			sb.AddObj(ObjectUtl.ToStrOrEmpty(val));
		}
		return sb.ToStr();
	}
	@Override public void Exec() {
		IoEnginePool.Instance.Get_by(this.Url().Info().EngineKey()).RecycleFil(this);
	}
	public IoEngine_xrg_recycleFil(int v) {
		mode = v;
		time = GfoDateNow.Get();
		uuid = GfoGuidUtl.New();
		rootDirNames = List_adp_.New(); rootDirNames.Add("z_trash");
	}
	public static IoEngine_xrg_recycleFil sysm_(Io_url url) {return new IoEngine_xrg_recycleFil(SysmConst);}
	public static IoEngine_xrg_recycleFil gplx_(Io_url url) {IoEngine_xrg_recycleFil rv = new IoEngine_xrg_recycleFil(GplxConst); rv.Url_set(url); return rv;}
		public static IoEngine_xrg_recycleFil proto_() {return gplx_(Io_url_.Empty);}
	public static final int GplxConst = 0, SysmConst = 1;
}
