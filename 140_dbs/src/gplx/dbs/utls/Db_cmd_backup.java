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
package gplx.dbs.utls; import gplx.*; import gplx.dbs.*;
import gplx.core.envs.*;
public class Db_cmd_backup implements Gfo_invk {
	public String DbName() {return dbName;} public Db_cmd_backup DbName_(String val) {dbName = val; return this;} private String dbName = "db";
	public Io_url ExeUrl() {return exeUrl;} public Db_cmd_backup ExeUrl_(Io_url val) {exeUrl = val; return this;} Io_url exeUrl;
	public Io_url BkpDir() {return bkpDir;} public Db_cmd_backup BkpDir_(Io_url val) {bkpDir = val; return this;} Io_url bkpDir;
	public String Usr() {return usr;} public Db_cmd_backup Usr_(String val) {usr = val; return this;} private String usr;
	public String Pwd() {return pwd;} public Db_cmd_backup Pwd_(String val) {pwd = val; return this;} private String pwd;
	public String DteFmt() {return dteFmt;} public Db_cmd_backup DteFmt_(String val) {dteFmt = val; return this;} private String dteFmt = "yyyyMMdd_HHmm";
	public String BkpFilNameFmt() {return bkpFilNameFmt;} public Db_cmd_backup BkpFilNameFmt_(String val) {bkpFilNameFmt = val; return this;} private String bkpFilNameFmt = "{0}_{1}.sql";
	public String BkpFilName() {return bkpFilName;} private String bkpFilName;
	public Io_url BkpFil() {return bkpFil;} Io_url bkpFil;
	public String CmdText() {return cmdText;} private String cmdText;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_ExeUrl))				return exeUrl;
		else if	(ctx.Match(k, Invk_BkpDir))				return bkpDir;
		else if	(ctx.Match(k, Invk_Usr))				return usr;
		else if	(ctx.Match(k, Invk_Pwd))				return pwd;
		else if	(ctx.Match(k, Invk_DteFmt))				return dteFmt;
		else if	(ctx.Match(k, Invk_BkpFilNameFmt))		return bkpFilNameFmt;
		else if	(ctx.Match(k, Invk_ExeUrl_))			exeUrl = GfoMsgUtl.SetIoUrl(ctx, m, exeUrl);
		else if	(ctx.Match(k, Invk_BkpDir_))			bkpDir = GfoMsgUtl.SetIoUrl(ctx, m, exeUrl);
		else if	(ctx.Match(k, Invk_Usr_))				usr = GfoMsgUtl.SetStr(ctx, m, usr);
		else if	(ctx.Match(k, Invk_Pwd_))				pwd = GfoMsgUtl.SetStr(ctx, m, pwd);
		else if	(ctx.Match(k, Invk_DteFmt_))			dteFmt = GfoMsgUtl.SetStr(ctx, m, dteFmt);
		else if	(ctx.Match(k, Invk_BkpFilNameFmt_))		bkpFilNameFmt = GfoMsgUtl.SetStr(ctx, m, bkpFilNameFmt);
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	public static final    String
			  Invk_ExeUrl = "ExeUrl", Invk_BkpDir = "BkpDir", Invk_Usr = "Usr", Invk_Pwd = "Pwd", Invk_DteFmt = "DteFmt", Invk_BkpFilNameFmt = "BkpFilNameFmt"
			, Invk_ExeUrl_ = "ExeUrl_", Invk_BkpDir_ = "BkpDir_", Invk_Usr_ = "Usr_", Invk_Pwd_ = "Pwd_", Invk_DteFmt_ = "DteFmt_", Invk_BkpFilNameFmt_ = "BkpFilNameFmt_"
			;
	public Db_cmd_backup Exec() {
		this.InitVars();
		Io_url bkpCmdFil = bkpDir.GenSubFil_ary("backup_", dbName, ".cmd");
		// Io_url bkpCmdFil = Io_url_.new_dir_("/home/").GenSubFil_ary("backup_", dbName, ".cmd"); // LNX: uncomment
		Io_mgr.Instance.SaveFilStr_args(bkpCmdFil, cmdText).Exec(); // explicitly state utf8; 
		Process_adp.run_wait_(bkpCmdFil);
		Io_mgr.Instance.DeleteFil(bkpCmdFil);
		return this;
	}
	@gplx.Internal protected Db_cmd_backup InitVars() {
		String dteStr = DateAdp_.Now().XtoStr_fmt(dteFmt);
		bkpFilName = String_.Format(bkpFilNameFmt, dbName, dteStr);
		bkpFil = bkpDir.GenSubFil(bkpFilName);
		cmdText = String_.Format("\"{0}\" -u {1} -p{2} {3} > {4}", exeUrl.Xto_api(), usr, pwd, dbName, bkpFil.Xto_api());
		return this;
	}
        public static Db_cmd_backup new_() {return new Db_cmd_backup();} Db_cmd_backup() {}
}
