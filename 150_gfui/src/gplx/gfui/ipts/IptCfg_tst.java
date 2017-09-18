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
package gplx.gfui.ipts; import gplx.*; import gplx.gfui.*;
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
		public Gfo_evt_mgr Evt_mgr() {if (evt_mgr == null) evt_mgr = new Gfo_evt_mgr(this); return evt_mgr;} Gfo_evt_mgr evt_mgr;
		public void tst_SendKey(IptKey key, int expd) {
			iptBnds.Process(IptEventData.new_(null, IptEventType_.KeyDown, key, IptEvtDataKey.new_(key), IptEvtDataMouse.Null));
			Tfds.Eq(expd, actl);
			actl = 0;
		}	int actl;
		public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
			if		(ctx.Match(k, Invk_Reg)) {actl = m.ReadIntOr("val", 0);}
			else	return Gfo_invk_.Rv_unhandled;
			return this;
		}	public static final    String Invk_Reg = "Reg";
	}
}
