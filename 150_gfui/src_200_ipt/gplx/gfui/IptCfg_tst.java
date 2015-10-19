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
package gplx.gfui; import gplx.*;
import org.junit.*;
public class IptCfg_tst {
	@Before public void setup() {
		IptCfgRegy.Instance.Clear();
		box = new IptBndsOwner_mok();
		cfg = new IptCfg_mok();
		key = IptBndsOwner_mok.Invk_Reg;
	}	IptBndsOwner_mok box; IptCfg_mok cfg; String key;
	@Test  public void Basic() {
		cfg.run_GetOrDflt(box, key, IptKey_.A);
		box.tst_SendKey(IptKey_.A, 1);
	}
	@Test  public void Del() {
		cfg.run_GetOrDflt(box, key, IptKey_.A);
		box.IptBnds().Cfgs_delAll();
		box.tst_SendKey(IptKey_.A, 0);
	}
	@Test  public void Change() {
		cfg.run_GetOrDflt(box, key, IptKey_.A);
		cfg.run_Set(key, IptKey_.B);
		box.tst_SendKey(IptKey_.B, 1);

		cfg.run_Set(key, IptKey_.C);
		box.tst_SendKey(IptKey_.C, 1);
		box.tst_SendKey(IptKey_.B, 0);
	}
	@Test  public void SetBeforeInit() {
		cfg.run_Set(key, IptKey_.B);
		cfg.run_GetOrDflt(box, key, IptKey_.A);
		box.tst_SendKey(IptKey_.B, 1);
		box.tst_SendKey(IptKey_.A, 0);
	}
	@Test  public void SetBeforeInit_msg() {
		cfg.run_Set_msg(key, 2, IptKey_.B);
		cfg.run_GetOrDflt(box, key, IptKey_.A);	// iptBnd exists; ignore Key_.A (and also msg=1)
		box.tst_SendKey(IptKey_.B, 2);
		box.tst_SendKey(IptKey_.A, 0);
	}
	@Test  public void Chained() {
		cfg.run_GetOrDflt(box, key, IptKeyChain.parse("key.ctrl+key.a,key.b"));
		cfg.run_Set(key, IptKey_.A);
		box.tst_SendKey(IptKey_.A, 1);
	}
	class IptCfg_mok {
		public IptCfg Cfg() {return cfg;}
		public void run_GetOrDflt(IptBndsOwner box, String key, IptArg... ary) {IptBnd_.msg_(cfg, box, key, make_(key, 1), ary);}
		public void run_Set(String key, IptArg... ary)			{cfg.Set(key, make_(key, 1), ary);}
		public void run_Set_msg(String key, int i, IptArg... ary)	{cfg.Set(key, make_(key, i), ary);}
		GfoMsg make_(String key, int i) {return GfoMsg_.new_cast_(key).Add("val", i);}
		public IptCfg_mok() {cfg = (IptCfg_base)IptCfg_.new_("cfg");} IptCfg_base cfg;
	}
	class IptBndsOwner_mok implements IptBndsOwner {
		public IptBndMgr IptBnds() {return iptBnds;} IptBndMgr iptBnds = IptBndMgr.new_();
		public GfoEvMgr EvMgr() {if (evMgr == null) evMgr = GfoEvMgr.new_(this); return evMgr;} GfoEvMgr evMgr;
		public void tst_SendKey(IptKey key, int expd) {
			iptBnds.Process(IptEventData.new_(null, IptEventType_.KeyDown, key, IptEvtDataKey.new_(key), IptEvtDataMouse.Null));
			Tfds.Eq(expd, actl);
			actl = 0;
		}	int actl;
		public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
			if		(ctx.Match(k, Invk_Reg)) {actl = m.ReadIntOr("val", 0);}
			else	return GfoInvkAble_.Rv_unhandled;
			return this;
		}	public static final String Invk_Reg = "Reg";
	}
}
