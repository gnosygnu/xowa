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
public class IptArgChainMgr_tst {
	@Before public void setup() {
		fx = new IptArgChainMgr_fx();
	}	IptArgChainMgr_fx fx;
	@Test  public void Add() {
		fx.run_Add(IptKey_.A, IptKey_.B, IptKey_.C);
		fx.tst_(IptKey_.A, 1);
		fx.tst_(IptKey_.B, 1);
		fx.tst_(IptKey_.C, 2);
		fx.tst_(IptKey_.B, 0);
		fx.tst_(IptKey_.C, 0);
	}
	@Test  public void Del() {
		fx.run_Add(IptKey_.A, IptKey_.B, IptKey_.C);
		fx.run_Del(IptKey_.A, IptKey_.B, IptKey_.C);
		fx.tst_(IptKey_.A, 0);
		fx.tst_(IptKey_.B, 0);
		fx.tst_(IptKey_.C, 0);
	}
	class IptArgChainMgr_fx {
		public IptArgChainMgr Under() {return under;} IptArgChainMgr under = new IptArgChainMgr();
		public IptArgChainMgr_fx run_Add(IptKey... ary) {under.Add(new IptKeyChain(ary)); return this;}
		public IptArgChainMgr_fx run_Del(IptKey... ary) {under.Del(new IptKeyChain(ary)); return this;}
		public IptArgChainMgr_fx tst_(IptKey key, int expd) {
			String process = under.Process(key);
			String activeKey = under.ActiveKey();
			String literal = key.Key();
	        if		(expd == 0) {
				Tfds.Eq(process, "", "0:{0} should be empty:process", literal);
				Tfds.Eq(activeKey, "", "0:{0} should be noop:activeKey", literal);
			}
	        else if	(expd == 1) {
				Tfds.Eq(process, "", "1:{0} should be empty:process", literal);
				Tfds.Eq_true(String_.Has_at_end(activeKey, key.Key() + ","), "1:{0} should set key:activeKey,{1}", literal, activeKey);
			}
	        else if	(expd == 2) {
				Tfds.Eq_true(String_.EqNot(process, ""), "2:{0} should not be empty;process,{1}", literal, process);
				Tfds.Eq(activeKey, "", "2:{0} should be empty:activeKey", literal);
			}
			return this;
		}
	}
}
